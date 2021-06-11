package talos.bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import talos.bot.Config;
import talos.bot.commands.modules.audio.*;
import talos.bot.commands.modules.text.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandHandler {

    private final List<ICommands> commands = new ArrayList<>();

    //Constructor (REGISTER NEW COMMANDS HERE)
    public CommandHandler() {
        //Text Commands
        setCommand(new PingModule());
        setCommand(new EchoModule());
        setCommand(new StatusModule());
        setCommand(new RegexModule());
        setCommand(new WideModule());
        setCommand(new StravaModule());
        setCommand(new SayModule());
        setCommand(new HelpModule(this));

        //Audio Commands
        setCommand(new JoinModule());
        setCommand(new LeaveModule());
        setCommand(new PlayModule());
        setCommand(new PlayNextModule());
        setCommand(new PauseModule());
        setCommand(new QueueModule());
        setCommand(new RepeatModule());
        setCommand(new StopModule());
        setCommand(new VolumeModule());
    }

    //Check if command exists, adds if not
    private void setCommand(ICommands command) {

        boolean nameExists = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(command.getName()));

        if (nameExists) {
            throw new IllegalArgumentException("Command already exists.");
        }

        commands.add(command);
    }

    //Returns command if it exists
    @Nullable
    public ICommands getCommand(String name) {

        //Search for command
        for (ICommands command : this.commands) {
            if (command.getName().equals(name.toLowerCase()) || command.getAliases().contains(name.toLowerCase())) {
                return command;
            }
        }

        //No command found
        return null;
    }

    //Returns all commands
    public List<ICommands> getAllCommands() {
        return commands;
    }

    public void handle(GuildMessageReceivedEvent event) {

        String prefix = Config.get("PREFIX");
        String message = event.getMessage().getContentRaw();

        //Remove prefix and whitespace
        String[] split = message
                .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                .split("\\s+");

        //Get command to execute
        String name = split[0].toLowerCase();
        ICommands command = this.getCommand(name);

        //Check if command exists, execute if so
        if (command != null) {
            event.getChannel().sendTyping().queue();

            //Split for multi-word commands
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandsContext commandContext = new CommandsContext(event, args);

            command.handle(commandContext);
        }
        else {
            MessageChannel channel = event.getChannel();

            channel.sendTyping().queue();
            channel.sendMessage("Invalid command").queue();
        }

        return;
    }
}
