package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.MessageHelper;

public class EchoModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

        MessageHelper messageHelper = new MessageHelper(commandsContext, this.getName());
        String message = messageHelper.stripCommandName();

        if (message.isEmpty()){
            sendEchoHistory(commandsContext.getChannel());
        }
        else {
            messageHelper.sendMessage(message);
        }
    }

    @Override
    public String getName() {
        return "echo";
    }

    @Override
    public String getHelp() {
        return ":laughing: %help echo";
    }

    public void sendEchoHistory(TextChannel textChannel) {
        textChannel.getHistory().retrievePast(1)
                .map(messages -> messages.get(0))
                .queue((message) ->
                        textChannel.sendMessage(message.getContentDisplay()).queue()
                );
    }
}
