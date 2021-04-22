package talos.bot.helpers;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;
import talos.bot.commands.CommandsContext;

@SuppressWarnings("ConstantConditions")
public class AudioHelper {
    private static AudioHelper INSTANCE;
    private CommandsContext commandsContext;

    public CommandsContext getCommandsContext() {
        return commandsContext;
    }

    public void setCommandsContext(CommandsContext commandsContext) {
        this.commandsContext = commandsContext;
    }

    public void join() {
        final TextChannel textChannel = commandsContext.getChannel();
        final Member talosMember = commandsContext.getSelfMember();
        final GuildVoiceState talosVoiceState = talosMember.getVoiceState();

        if (talosVoiceState.inVoiceChannel()) {
            textChannel.sendMessage("Talos is already in a voice channel.").queue();
            return;
        }

        final AudioManager audioManager = commandsContext.getGuild().getAudioManager();
        //final VoiceChannel voiceChannel = commandsContext.getGuild().getVoiceChannelById(Config.get("MAIN_AUDIO_CHANNEL"));

        //Get the default voice channel to connect to
        final VoiceChannel voiceChannel = commandsContext.getGuild().getVoiceChannels().get(0);

        try {
            audioManager.openAudioConnection(voiceChannel);
            textChannel.sendMessage("Talos is in.").queue();
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    public void leave() {
        final TextChannel textChannel = commandsContext.getChannel();
        final Member talosMember = commandsContext.getSelfMember();
        final GuildVoiceState talosVoiceState = talosMember.getVoiceState();

        if (!talosVoiceState.inVoiceChannel()) {
            textChannel.sendMessage("Talos isn't currently in a voice channel.").queue();
            return;
        }

        final AudioManager audioManager = commandsContext.getGuild().getAudioManager();

        try {
            audioManager.closeAudioConnection();
            textChannel.sendMessage("Goodbye cruel world!").queue();
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setListingStatus(String songName) {
        getCommandsContext().getJDA().getPresence().setActivity(Activity.listening(songName));
    }

    public static synchronized AudioHelper getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new AudioHelper();
        }

        return INSTANCE;
    }
}
