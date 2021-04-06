package talos.bot.commands.modules;

import net.dv8tion.jda.api.JDA;
import talos.bot.Config;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

public class EchoModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        JDA jda = commandsContext.getJDA();

        commandsContext.getChannel().sendTyping().queue();
        commandsContext.getChannel().sendMessage(commandsContext.getMessage()
                .getContentRaw().replace(Config.get("PREFIX") + this.getName(), "")).queue();
    }

    @Override
    public String getName() {
        return "echo";
    }

    @Override
    public String getHelp() {
        return ":laughing: %help echo";
    }
}
