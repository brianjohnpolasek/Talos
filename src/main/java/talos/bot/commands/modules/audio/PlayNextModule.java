package talos.bot.commands.modules.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.audio.GuildMusicManager;
import talos.bot.audio.PlayerManager;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.AudioHelper;

import java.util.List;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("ConstantConditions")
public class PlayNextModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        final TextChannel textChannel = commandsContext.getChannel();
        final Member talosMember = commandsContext.getSelfMember();
        final GuildVoiceState talosVoiceState = talosMember.getVoiceState();

        //Check if Talos is in Voice Channel
        if (!talosVoiceState.inVoiceChannel()) {
            textChannel.sendMessage("Nothing playing.").queue();
            return;
        }

        final Guild guild = commandsContext.getGuild();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(guild);
        final AudioPlayer audioPlayer = musicManager.getAudioPlayer();

        //Check if anything is playing
        if (audioPlayer.getPlayingTrack() == null) {
            textChannel.sendMessage("Nothing playing.").queue();
            return;
        }

        final BlockingQueue<AudioTrack> queue = musicManager.getScheduler().getQueue();

        //Leave if no more music in queue
        if (queue.isEmpty()) {
            textChannel.sendMessage("SEE YOU SPACE COWBOY...").queue();
            musicManager.getScheduler().nextTrack();
            AudioHelper.getINSTANCE().leave();
        }

        List<String> args = commandsContext.getArgs();
        int numSkips = 1;

        //Check for multiple skips
        if (args.isEmpty()) {
            textChannel.sendMessage("Playing next track.").queue();
            musicManager.getScheduler().nextTrack();
        }
        else {
            numSkips = Integer.parseInt(args.get(0));

            //Make sure enough songs are in queue
            if (queue.size() > numSkips) {
                textChannel.sendMessage("Skipping ahead " + numSkips + " songs.").queue();

                for (int i = 1; i < numSkips; i++) {
                    musicManager.getScheduler().nextTrack();
                }
            }
            else {
                textChannel.sendMessage("Can't skip that far ahead, try '%stop' to clear the queue.").queue();
            }
        }

    }

    @Override
    public String getName() {
        return "playnext";
    }

    @Override
    public String getHelp() {
        StringBuilder message = new StringBuilder();

        message.append("Allows users to skip ahead in the audio queue.\n\n")
                .append("**Usage: ** *%playnext [NUMBER_OR_BLANK]*\n");

        return message.toString();
    }

    @Override
    public List<String> getAliases() {
        return List.of("skip");
    }
}
