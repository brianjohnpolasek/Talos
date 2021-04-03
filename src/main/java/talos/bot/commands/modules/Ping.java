package talos.bot.commands.modules;

import net.dv8tion.jda.api.JDA;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

public class Ping implements ICommands {

    @Override
    public void handle(CommandsContext commandsContext) {
        JDA jda = commandsContext.getJDA();

        commandsContext.getChannel().sendTyping().queue();
        jda.getRestPing().queue((ping) ->
                commandsContext.getChannel().sendMessageFormat("Ping: %sms", ping).queue());
    }

    @Override
    public String getName() {
        return "ping";
    }
}
