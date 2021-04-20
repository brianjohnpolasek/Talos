package talos.bot.helpers;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import talos.bot.Config;
import talos.bot.audio.PlayerManager;
import talos.bot.commands.CommandsContext;

@SuppressWarnings("ConstantConditions")
public class AudioHelper {
    private static AudioHelper INSTANCE;
    private CommandsContext commandsContext;

    public void setCommandsContext(CommandsContext commandsContext) {
        this.commandsContext = commandsContext;
    }

    public void join() {
        final Member talosMember = commandsContext.getSelfMember();
        final GuildVoiceState talosVoiceState = talosMember.getVoiceState();

        if (talosVoiceState.inVoiceChannel()) {
            commandsContext.getChannel().sendMessage("Talos is already in a voice channel.").queue();
            return;
        }

        final AudioManager audioManager = commandsContext.getGuild().getAudioManager();
        final VoiceChannel voiceChannel = commandsContext.getGuild().getVoiceChannelById(Config.get("MAIN_AUDIO_CHANNEL"));

        audioManager.openAudioConnection(voiceChannel);
    }

    public void leave() {
        final Member talosMember = commandsContext.getSelfMember();
        final GuildVoiceState talosVoiceState = talosMember.getVoiceState();

        if (!talosVoiceState.inVoiceChannel()) {
            commandsContext.getChannel().sendMessage("Talos isn't currently in a voice channel.").queue();
            return;
        }

        final AudioManager audioManager = commandsContext.getGuild().getAudioManager();

        audioManager.closeAudioConnection();
    }

    public CommandsContext getCommandsContext() {
        return commandsContext;
    }

    public static synchronized AudioHelper getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new AudioHelper();
        }

        return INSTANCE;
    }
}
