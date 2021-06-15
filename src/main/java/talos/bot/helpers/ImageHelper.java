package talos.bot.helpers;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
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

    public void sendImage(TextChannel channel) {
        File image = null;
        if ((image = getLatestImage(channel)) != null) {
            channel.sendFile(image).queue();
        }
        else {
            channel.sendMessage("Could not find valid image.").queue();
        }
    }

    public void downloadImage(TextChannel textChannel) {

    }
}
