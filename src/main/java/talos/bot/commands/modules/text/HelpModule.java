package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.commands.CommandHandler;
import talos.bot.Config;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

import java.util.List;

public class HelpModule implements ICommands {

    private final CommandHandler handler;

    private final String prefix = Config.get("PREFIX");

    public HelpModule(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handle(CommandsContext commandsContext) {
        List<String> args = commandsContext.getArgs();
        TextChannel channel = commandsContext.getChannel();

        //Default response lists all possible commands
        if (args.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            builder.append("Type '" + prefix + "help [command_name]' for more information.\n\n");

            builder.append("Here are all of the commands:\n\n");

            handler.getAllCommands().stream().map(ICommands::getName).forEach((it) -> builder
                    .append("* ")
                    .append(Config.get("PREFIX"))
                    .append(it).append("\n")
            );

            channel.sendMessage(builder.toString()).queue();

            return;
        }

        //Gets command name if it exists
        String name = args.get(0);
        ICommands command = handler.getCommand(name);

        //Error message if command name doesn't exist
        if (command == null) {
            channel.sendTyping().queue();
            channel.sendMessage("No such command: " + name).queue();
        }

        //Returns the help commands specified in the module
        channel.sendMessage(command.getHelp()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Displays information for all commands (" + Config.get("PREFIX") + "help [command_name])";
    }

    @Override
    public List<String> getAliases() {
        return List.of("commands", "ls");
    }
}
