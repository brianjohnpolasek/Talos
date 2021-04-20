package talos.bot.commands.modules.audio;

import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.AudioHelper;

@SuppressWarnings("ConstantConditions")
public class JoinModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

        AudioHelper.getINSTANCE().setCommandsContext(commandsContext);
        AudioHelper.getINSTANCE().join();
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "Manually have Talos join a voice channel.";
    }
}
