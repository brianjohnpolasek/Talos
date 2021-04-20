package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

import java.util.List;

public class WideModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        final TextChannel textChannel = commandsContext.getChannel();

        List<String> args =  commandsContext.getArgs();

        String message = "";
        int wideAmount = 1;

        //Check arguments
        if (args.isEmpty()) {
            message = String.valueOf(commandsContext.getChannel().getHistory().retrievePast(2));
        }
        else {

            //Modify amount of spaces if specified
            if (Character.digit(args.get(0).charAt(0), 10) > 0) {

                wideAmount = Integer.parseInt(args.get(0));
            }

            //Check arguments again
            if (args.isEmpty()) {
                message = String.valueOf(commandsContext.getChannel().getHistory().retrievePast(2));
            }
            else {
                message = String.join(" ", args.subList(1, args.size()));
            }
        }

        //Add the spaces
        textChannel.sendMessage(message.replace("", " ".repeat(Math.max(0, wideAmount)))).queue();

    }

    @Override
    public String getName() {
        return "wide";
    }

    @Override
    public String getHelp() {
        StringBuilder helpMessage = new StringBuilder();

        helpMessage.append("Use to make the message  w i d e.\n\n")
                .append("**Usage: ** *%wide [NUMBER_OR_BLANK] [TEXT_OR_BLANK]*\n")
                .append("(A blank value defaults to 1 and supports a maximum of 9)");
        
        return helpMessage.toString();
    }
}
