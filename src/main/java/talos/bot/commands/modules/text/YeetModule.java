package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.ImageHelper;

import java.util.List;
import java.util.Random;

public class YeetModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

        TextChannel textChannel = commandsContext.getChannel();
        List<String> args = commandsContext.getArgs();

        JDA jda = commandsContext.getJDA();

        String message;
        String otherTextChannelID;

        //Validate number of args
        if (args.size() < 1){
            List<TextChannel> allChannels = jda.getTextChannels();

            otherTextChannelID = allChannels.get(new Random().nextInt(allChannels.size())).getId();
        }
        else {
            otherTextChannelID = args.get(0);
        }


        //Grab previous message if not provided
        if (args.size() <= 1) {
            message = textChannel.getHistory().retrievePast(2).complete().get(1).getContentRaw();
        }
        else {
            message = String.join(" ", args.subList(1, args.size()));
        }

        TextChannel otherTextChannel = jda.getTextChannelById(otherTextChannelID);


        //Validate otherTextChannel id
        if (otherTextChannel == null) {
            textChannel.sendMessage("Talos does not have access to the channel " + otherTextChannelID).queue();
            return;
        }


        // Validate message
        if (message.isEmpty()) {
            ImageHelper imageHelper = new ImageHelper();
            imageHelper.sendImage(otherTextChannel, imageHelper.getLatestImage(textChannel));

            message = "";
        }
        else {
            otherTextChannel.sendMessage(message).queue();
        }

        // Send success report
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("Message ")
                .append("" + message + "")
                .append(" successfully yeeted to otherTextChannel '")
                .append(jda.getTextChannelById(otherTextChannelID).getName())
                .append("'.");

        textChannel.sendMessage(stringBuilder.toString()).queue();
    }

    @Override
    public String getName() {
        return "yeet";
    }

    @Override
    public String getHelp() {
        return "Send messages or images to other servers either to random or to a specified channel: ."
                + "**Usage:** %yeet [CHANNEL_ID_OR_EMPTY] [MESSAGE_OR_EMPTY]";
    }
}
