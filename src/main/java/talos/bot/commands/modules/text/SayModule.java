package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

public class SayModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

        TextChannel channel = commandsContext.getChannel();

        channel.getHistory().retrievePast(2)
                .map(messages -> messages.get(0))
                .queue((message) ->
                        channel.deleteMessageById(message.getId()).queue()
        );

        channel.sendMessage(commandsContext.getMessage()
                .getContentRaw()
                .replace("%say", "")
                .stripLeading())
                .queue();
    }

    @Override
    public String getName() {
        return "say";
    }

    @Override
    public String getHelp() {
        return "Make Talos say something!";
    }
}
