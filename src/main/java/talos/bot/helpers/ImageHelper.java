package talos.bot.helpers;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ImageHelper {
    public File getLatestImage(TextChannel channel) {

        List<Message> messages = channel.getHistory().retrievePast(10).complete();

        for (Message message : messages
        ) {
            try {
                if (!message.getAttachments().isEmpty()) {
                    return message.getAttachments().get(0).downloadToFile().get();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void sendImage(TextChannel channel, File image) {
        if (image == null) {
            channel.sendMessage("Could not find valid image.").queue();
        }
        else {
            channel.sendFile(image).queue();
        }
    }

    public void negativeImage(TextChannel channel, File image) {
        if (image == null) {
                channel.sendMessage("Could not find valid image.").queue();
        }
        else {

            BufferedImage picture = null;

            //Obtain image object to manipulate data
            try {
                picture = ImageIO.read(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Get image width and height
            int width = picture.getWidth();
            int height = picture.getHeight();

            // Convert to negative
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    int p = picture.getRGB(x,y);
                    int a = (p>>24)&0xff;
                    int r = (p>>16)&0xff;
                    int g = (p>>8)&0xff;
                    int b = p&0xff;

                    //subtract RGB from 255
                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;

                    //set new RGB value
                    p = (a<<24) | (r<<16) | (g<<8) | b;
                    picture.setRGB(x, y, p);
                }
            }

            //Update the image with the updated negative picture
            try {
                ImageIO.write(picture, "jpg", image);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Send the negative image to the channel
            channel.sendFile(image).queue();
        }
    }

    public void downloadImage(TextChannel textChannel) {

    }
}
