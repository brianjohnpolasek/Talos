package talos.bot.commands.modules.image;

import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.ImageHelper;

public class RepostModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        TextChannel channel = commandsContext.getChannel();

        ImageHelper imageHelper = new ImageHelper();

        imageHelper.sendImage(channel);
    }

    @Override
    public String getName() {
        return "repost";
    }

    @Override
    public String getHelp() {
        return "Reposts attached image or latest valid image.";
    }
}
