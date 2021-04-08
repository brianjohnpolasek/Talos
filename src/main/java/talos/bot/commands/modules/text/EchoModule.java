package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.JDA;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.MessageHelper;

public class EchoModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

        JDA jda = commandsContext.getJDA();
        MessageHelper messageHelper = new MessageHelper(commandsContext, this.getName());

        String message = messageHelper.stripCommandName();

        messageHelper.sendMessage(message);

        return;
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
