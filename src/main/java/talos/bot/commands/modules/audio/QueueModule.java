package talos.bot.commands.modules.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import talos.bot.audio.GuildMusicManager;
import talos.bot.audio.PlayerManager;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class QueueModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        final TextChannel textChannel = commandsContext.getChannel();
        final Guild guild = commandsContext.getGuild();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(guild);
        final BlockingQueue<AudioTrack> queue = musicManager.getScheduler().getQueue();

        if (queue.isEmpty()) {
            textChannel.sendMessage("Queue is currently empty,").queue();
            return;
        }

        final int queueSizeLimit = Math.min(queue.size(), 20);

        final List<AudioTrack> tracks = new ArrayList<>(queue);
        final MessageAction messageAction = textChannel.sendMessage("**Talos' Queue:**\n");

        messageAction.append("(There are ").append(String.valueOf(tracks.size())).append(" songs in the queue.)\n");

        for (int i = 0; i < queueSizeLimit; i++) {
            final AudioTrack track = tracks.get(i);
            final AudioTrackInfo trackInfo = track.getInfo();
            final Date time = new Date(track.getInfo().length);
            DateFormat dateFormat = new SimpleDateFormat("mm:ss");

            messageAction.append(String.valueOf(i + 1))
                    .append(": **")
                    .append(trackInfo.title)
                    .append("** by *")
                    .append(trackInfo.author).append("* (").append(dateFormat.format(time)).append(")\n");
        }

        //Check for overflow songs
        if (queue.size() > queueSizeLimit) {
            messageAction.append("+ ")
                    .append(String.valueOf(queue.size() - queueSizeLimit))
                    .append(" more songs.");
        }

        messageAction.queue();
    }

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getHelp() {
        return "Provides the user with the current audio queue.";
    }
}
