package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

import java.util.ArrayList;
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
            sendWideHistory(textChannel, wideAmount);
            return;
        }
        else {

            //Modify amount of spaces if specified
            if (Character.digit(args.get(0).charAt(0), 10) > 0) {

                try {
                    wideAmount = Integer.parseInt(args.get(0));

                    List<String> newArgs = args.subList(1, args.size());

                    //Check arguments again
                    if (newArgs.isEmpty()) {
                        sendWideHistory(textChannel, wideAmount);
                        return;
                    }
                    else {
                        args = newArgs;
                    }
                }catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        //Add the spaces
        message = addSpaces(args);

        textChannel.sendMessage(message.replace(" ", " ".repeat(Math.max(0, wideAmount)))).queue();

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

    public void sendWideHistory(TextChannel textChannel, int wideAmount) {
        textChannel.getHistory().retrievePast(2)
                .map(messages -> messages.get(1))
                .queue((message) ->
                        textChannel.sendMessage(message.getContentDisplay().replace("", " ".repeat(Math.max(0, wideAmount)))).queue()
                );
    }

    private String addSpaces(List<String> args) {
        List<String> newArgs = new ArrayList<>();

        for (int i=0; i<args.size(); i++) {
            if (args.get(i).matches("^.*[^a-zA-Z0-9 ].*$")) {
                newArgs.add(args.get(i));
            }
            else {
                newArgs.add(args.get(i).replace("", " "));
            }
        }

        return String.join(" ", newArgs);
    }
}
