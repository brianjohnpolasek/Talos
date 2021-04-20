package talos.bot.commands.modules.audio;

import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.AudioHelper;

@SuppressWarnings("ConstantConditions")
public class LeaveModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

        AudioHelper.getINSTANCE().leave();
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getHelp() {
        return "Manually force Talos to leave a voice channel.";
    }
}
