package talos.bot.commands.modules.text;

import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

public class YeetModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

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
