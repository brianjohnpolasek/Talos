package talos.bot.aws;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class S3Manager {

    private BucketManager bucketManager = null;

    public S3Manager() {

        this.bucketManager = new BucketManager();
    }

    //Upload file to the specified path on S3 if file is valid
    public void uploadObject(String bucketName, String keyPath, String filePath) {
        File objectFile = new File(filePath);

        if (bucketManager.createBucket(bucketName)) {
            System.out.println("Created new bucket: " + bucketName);
        }

        if (objectFile.canRead()) {
            this.bucketManager.getS3Client().putObject(
                    bucketName,
                    keyPath,
                    objectFile
            );
        }
    }

    //Get list of objects in bucket if it exists
    public ObjectListing getObjects(String bucketName) {
        return this.bucketManager.getS3Client().listObjects(bucketName);
    }

    //Attempts to create a local copy of a S3 object
    public boolean downloadObject(String bucketName, String keyPath, String filePath) {
        S3Object s3object = null;

        //Attempt to download object
        try {
            s3object = this.bucketManager.getS3Client().getObject(bucketName, keyPath);
        }catch (AmazonS3Exception e) {
            e.printStackTrace();
            return false;
        }

        S3ObjectInputStream inputStream = s3object.getObjectContent();

        //Attempt to copy object to local file
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File(filePath));
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }
}
