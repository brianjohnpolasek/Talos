package talos.bot.commands.modules.text;

import talos.bot.Config;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.RegexHelper;

import java.util.List;

public class RegexModule implements ICommands {

    @Override
    public void handle(CommandsContext commandsContext) {
        List<String> args = commandsContext.getArgs();

        /* Execute the corresponding command */
        String command = "";

        if (args.size() >= 2) {
            command = args.get(0) + args.get(1);
        }
        else if (args.size() > 0) {
            command = args.get(0);
        }

        switch (command.replaceAll("//s", "")) {
            case "enable": whitelist(commandsContext); break;
            case "disable": blacklist(commandsContext); break;
            case "enableall": RegexHelper.whitelistAll(commandsContext); break;
            case "disableall": RegexHelper.blacklistAll(commandsContext); break;
            default: commandsContext.getChannel().sendMessage(Config.get("COMMAND_ERROR")).queue();
        }
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
                + prefix + "regex enable" + "\n"
                + prefix + "regex disable";
    }

    public void whitelist(CommandsContext commandsContext) {
        String channelID = commandsContext.getChannel().getId();

        /* Channel already whitelisted */
        if (RegexHelper.getChannelWhiteListEntry(channelID)) {
            commandsContext.getChannel().sendMessage("Channel already white.").queue();
        }
        else {
            RegexHelper.setChannelWhitelistEntry(channelID, Boolean.TRUE);
            commandsContext.getChannel().sendMessage("Channel whitelisted.").queue();
        }
    }

    public void blacklist(CommandsContext commandsContext) {
        String channelID = commandsContext.getChannel().getId();

        /* Channel already blacklisted */
        if (!RegexHelper.getChannelWhiteListEntry(channelID)) {
            commandsContext.getChannel().sendMessage("Channel already black.").queue();
        }
        else {
            RegexHelper.setChannelWhitelistEntry(channelID, Boolean.FALSE);
            commandsContext.getChannel().sendMessage("Channel blacklisted.").queue();
        }
    }
}
