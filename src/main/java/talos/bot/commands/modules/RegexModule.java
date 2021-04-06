package talos.bot.commands.modules;

import talos.bot.Config;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

public class RegexModule implements ICommands {

    @Override
    public void handle(CommandsContext commandsContext) {

        //SWITCH CASE ON COMMAND
    }

    @Override
    public String getName() {
        return "regex";
    }

    @Override
    public String getHelp() {
        String prefix = Config.get("PREFIX");

        return "Add or remove regex patterns and modify the channel whitelist:\n"
                + prefix + "regex add [NAME, PATTERN, RESPONSE]" + "\n"
                + prefix + "regex remove [NAME]" + "\n"
                + prefix + "regex whitelist [CHANNEL_ID]" + "\n"
                + prefix + "regex blacklist [CHANNEL_ID]";
    }
}
