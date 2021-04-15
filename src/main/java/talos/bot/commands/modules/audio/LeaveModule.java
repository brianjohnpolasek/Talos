package talos.bot.commands.modules.audio;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import talos.bot.Config;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

@SuppressWarnings("ConstantConditions")
public class LeaveModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        final Member talosMember = commandsContext.getSelfMember();
        final GuildVoiceState talosVoiceState = talosMember.getVoiceState();

        if (!talosVoiceState.inVoiceChannel()) {
            commandsContext.getChannel().sendMessage("Talos isn't currently in a voice channel.").queue();
            return;
        }

        final AudioManager audioManager = commandsContext.getGuild().getAudioManager();

        audioManager.closeAudioConnection();
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
