package talos.bot.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import talos.bot.Config;

import java.util.List;

public class BucketManager {

    private static final AWSCredentials awsCredentials = new BasicAWSCredentials(
            Config.get("AWS_KEY_ID"),
            Config.get("AWS_SECRET_KEY")
    );

    private AmazonS3 s3client = null;

    public BucketManager() {
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.US_EAST_2)
                .build();
    }

    //Add bucket to S3 if name is available
    public boolean createBucket(String bucketName) {
        if (s3client.doesBucketExistV2(bucketName)) {
            System.out.println("Bucket name already in use.");
            return false;
        }

        this.s3client.createBucket(bucketName);
        return true;
    }

    //Get list of all buckets
    public List<Bucket> getBuckets() {
        return this.s3client.listBuckets();
    }

    //Try to delete a bucket of specified name
    public boolean deleteBucket(String bucketName) {
        try {
            this.s3client.deleteBucket(bucketName);
        } catch (AmazonServiceException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    public AmazonS3 getS3Client() {
        return s3client;
    }
}
