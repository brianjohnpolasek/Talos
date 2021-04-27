package talos.bot.helpers;

import talos.bot.Config;
import talos.bot.commands.CommandsContext;

public class MessageHelper {

    private final CommandsContext commandsContext;
    private final String name;

    public MessageHelper(CommandsContext commandsContext, String name) {

        this.commandsContext = commandsContext;
        this.name = name;
    }

    public String stripCommandName() {
        String message = this.commandsContext.getMessage()
                .getContentRaw().replace(Config.get("PREFIX") + this.name, "");

        return message;
    }

    public void sendMessage(String message) {
        this.commandsContext.getChannel().sendMessage(message).queue();
    }

    public boolean checkArgs(int numArgs) {
        return commandsContext.getArgs().size() >= numArgs;
    }
}
