package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

import java.util.List;

public class YeetModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

        TextChannel textChannel = commandsContext.getChannel();

        List<String> args = commandsContext.getArgs();

        //Validate number of args
        if (args.size() < 1){
            textChannel.sendMessage("Invalid arguments, type '%help yeet' for more information.").queue();
        }

        String message;
        String channel = args.get(0);

        //Grab previous message if not provided
        if (args.size() == 1) {
            message = textChannel.getHistory().retrievePast(2).complete().get(1).getContentRaw();
        }
        else {
            message = String.join(" ", args.subList(1, args.size()));
        }

        JDA jda = commandsContext.getJDA();
        TextChannel otherTextChannel = jda.getTextChannelById(args.get(0));

        //Validate channel id
        if (otherTextChannel == null) {
            textChannel.sendMessage("Talos does not have access to the channel " + channel).queue();
        }

        assert otherTextChannel != null;
        otherTextChannel.sendMessage(message).queue();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("Message '")
                .append(message)
                .append("' successfully yeeted to channel '")
                .append(jda.getTextChannelById(channel).getName())
                .append("'.");

        textChannel.sendMessage(stringBuilder.toString()).queue();
    }

    @Override
    public String getName() {
        return "yeet";
    }

    @Override
    public String getHelp() {
        return "Send messages to other servers.";
    }
}
