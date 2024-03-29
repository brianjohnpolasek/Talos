package talos.bot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    //Constructor
    private PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager((playerManager));
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;

    }

    public void loadAndPlay(TextChannel channel, String trackURL) {
        final GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack song) {
                channel.sendMessage("Adding " + song.getInfo().title + " to the queue").queue();

                musicManager.getScheduler().queue(song);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

                final List<AudioTrack> songs = playlist.getTracks();

                channel.sendMessage("From " + playlist.getName() + " adding " + songs.size() + " songs to the queue").queue();

                for (final AudioTrack song: songs) {
                    musicManager.getScheduler().queue(song);
                }
            }

            @Override
            public void noMatches() {
                channel.sendMessage("No song at the url: " + trackURL).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception){
                channel.sendMessage("Failed to play: " + exception.getMessage()).queue();

                if (musicManager.getScheduler().getPlayer().getPlayingTrack() == null) {
                    channel.sendMessage("%leave").queue();
                }
            }
        });
    }

    private void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.getScheduler().queue(track);
    }

    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }
}