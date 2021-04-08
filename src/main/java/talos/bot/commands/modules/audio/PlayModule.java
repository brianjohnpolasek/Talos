package talos.bot.commands.modules.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.audio.PlayerManager;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlayModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

        PlayerManager playerManager = PlayerManager.getInstance();
        TextChannel channel = commandsContext.getChannel();

        List<String> args = commandsContext.getArgs();

        if (args.isEmpty()) {
            playerManager.loadAndPlay(channel, "https://youtu.be/oHg5SJYRHA0");

            return;
        }

        String song = String.join(" ", args);

        if (!isUrl(song)) {
            song = "ytsearch:" + song;
        }

        playerManager.loadAndPlay(channel, song);

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
