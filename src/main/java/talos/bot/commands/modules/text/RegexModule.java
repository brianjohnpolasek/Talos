package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.Config;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.RegexHelper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class RegexModule implements ICommands {

    @Override
    public void handle(CommandsContext commandsContext) {
        final TextChannel textChannel = commandsContext.getChannel();

        List<String> args = commandsContext.getArgs();

        /* Execute the corresponding command */
        String command = "";

        if (args.size() > 0) {
            command = args.get(0);
        }

        if (args.size() > 1) {
            switch (command.replaceAll("//s", "")) {
                case "add": addRegex(textChannel, args.subList(1, args.size())); break;
                case "remove": removeRegex(commandsContext, args.get(1)); break;
                default: commandsContext.getChannel().sendMessage(Config.get("COMMAND_ERROR")).queue();
            }
        }
        else {
            switch (command.replaceAll("//s", "")) {
                case "list": listRegex(textChannel); break;
                case "enable": whitelist(commandsContext); break;
                case "disable": blacklist(commandsContext); break;
                case "enableall": RegexHelper.whitelistAll(commandsContext); break;
                case "disableall": RegexHelper.blacklistAll(commandsContext); break;
                default: commandsContext.getChannel().sendMessage(Config.get("COMMAND_ERROR")).queue();
            }
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
                + prefix + "regex add [NAME; PATTERN; RESPONSE]" + "\n"
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

    public void addRegex(TextChannel textChannel, List<String> args) {

        String newRegexCommand = String.join(" ", args);

        String[] regexArgs = newRegexCommand.split(";");

        //Size check
        if (regexArgs.length < 2) {
            textChannel.sendMessage("Incorrect format (try %help regex)").queue();
            return;
        }

        String name = regexArgs[0];
        String command = regexArgs[1];
        String response = regexArgs[2];

        RegexHelper regexHelper = new RegexHelper();

        //Check if name exists
        if (regexHelper.nameExists(name)) {
            textChannel.sendMessage("Already added the regex under the name **" + name + "**").queue();
            return;
        }

        //Check to see if regex already exists
        if (!regexHelper.regexSearch(command).isEmpty()) {
            textChannel.sendMessage("Already added the regex **" + command + "** or a similar one.").queue();
            return;
        }

        //Check to see if response already exists
        if (regexHelper.responseExists(response)) {
            textChannel.sendMessage("Already added the response **" + response + "** or a similar one.").queue();
            return;
        }

        //Check for possible infinite cycles
        if (!regexHelper.regexSearch(response).isEmpty()
                || command.contains(response)
                || response.contains(command)) {
            textChannel.sendMessage("Can't have loops!").queue();
            return;
        }

        //Attempt to write the new command to the file
        newRegexCommand = "\n" + newRegexCommand;

        try (
            FileWriter regexFile = new FileWriter(Config.get("REGEX_PATH"), true);
            BufferedWriter regexOut = new BufferedWriter(regexFile)) {

            regexOut.write(newRegexCommand);
        } catch (IOException e) {
            e.printStackTrace();
            textChannel.sendMessage("Failed to add new regex command.").queue();
            return;
        }

        regexHelper.updateRegex();

        textChannel.sendMessage("Added *" + args.get(0) + "* successfully.").queue();
    }

    public void removeRegex(CommandsContext commandsContext, String name) {
        commandsContext.getChannel().sendMessage("FOOL! Only Brian can remove the regex command " + name).queue();
    }

    public void listRegex(TextChannel textChannel) {

        textChannel.sendMessage("Here are all of the current regex commands:\n\n").queue();

        RegexHelper regexHelper = new RegexHelper();

        Iterator regexMapIterator = regexHelper.getRegexResponseMap().entrySet().iterator();

        while (regexMapIterator.hasNext()) {
            textChannel.sendMessage(regexMapIterator.next().toString()).queue();
        }
    }
}
