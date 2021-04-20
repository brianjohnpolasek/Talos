package talos.bot.commands.modules.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;
import talos.bot.Config;
import talos.bot.audio.GuildMusicManager;
import talos.bot.audio.PlayerManager;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.AudioHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class PlayModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

        final AudioManager audioManager = commandsContext.getGuild().getAudioManager();
        final VoiceChannel voiceChannel = commandsContext.getGuild().getVoiceChannelById(Config.get("MAIN_AUDIO_CHANNEL"));

        final Guild guild = commandsContext.getGuild();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(guild);
        final AudioPlayer audioPlayer = musicManager.getAudioPlayer();

        final Member talosMember = commandsContext.getSelfMember();
        final GuildVoiceState talosVoiceState = talosMember.getVoiceState();

        AudioHelper.getINSTANCE().setCommandsContext(commandsContext);

        PlayerManager playerManager = PlayerManager.getInstance();
        TextChannel textChannel = commandsContext.getChannel();

        List<String> args = commandsContext.getArgs();

        if (args.isEmpty()) {
            if (audioPlayer.isPaused()) {
                audioPlayer.setPaused(false);
            }
            else {
                playerManager.loadAndPlay(textChannel, "https://youtu.be/oHg5SJYRHA0");
            }
            return;
        }

        String song = String.join(" ", args);

        if (!isUrl(song)) {
            song = "ytsearch:" + song;
        }

        playerManager.loadAndPlay(textChannel, song);

        final AudioTrack track = audioPlayer.getPlayingTrack();

        if (track == null) {
            return;
        }

        if (!talosVoiceState.inVoiceChannel()) {
            //Error checking for multiple servers
            try {
                audioManager.openAudioConnection(voiceChannel);
            }catch (Exception e){
                System.out.println(e);
                return;
            }
        }
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "Plays a song given either a url or pre-downloaded audio file.";
    }

    private boolean isUrl (String url) {
        try {
            new URL(url);
            return true;

        } catch (MalformedURLException e) {
            return false;
        }
    }
}
