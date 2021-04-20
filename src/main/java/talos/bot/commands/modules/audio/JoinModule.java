package talos.bot.commands.modules.audio;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import talos.bot.Config;
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
