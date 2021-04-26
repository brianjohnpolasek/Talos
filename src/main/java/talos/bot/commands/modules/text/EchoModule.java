package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.MessageHelper;

public class EchoModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

        MessageHelper messageHelper = new MessageHelper(commandsContext, this.getName());
        String message = String.join(" ", commandsContext.getArgs());

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
        textChannel.getHistory().retrievePast(2)
                .map(messages -> messages.get(1))
                .queue((message) ->
                        textChannel.sendMessage(message.getContentDisplay()).queue()
                );
    }
}
