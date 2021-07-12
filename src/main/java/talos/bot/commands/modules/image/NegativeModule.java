package talos.bot.commands.modules.image;

import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.ImageHelper;

import java.io.File;

public class NegativeModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        TextChannel channel = commandsContext.getChannel();

        ImageHelper imageHelper = new ImageHelper();

        File image = imageHelper.getLatestImage(channel);

        imageHelper.negativeImage(channel, image);
    }

    @Override
    public String getName() {
        return "negative";
    }

    @Override
    public String getHelp() {
        return "Inverts a given image or the most recent image if no image is provided.";
    }
}
