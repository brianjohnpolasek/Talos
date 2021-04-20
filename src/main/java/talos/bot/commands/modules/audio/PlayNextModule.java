package talos.bot.commands.modules.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.audio.GuildMusicManager;
import talos.bot.audio.PlayerManager;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class PlayNextModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        final TextChannel textChannel = commandsContext.getChannel();
        final Member talosMember = commandsContext.getSelfMember();
        final GuildVoiceState talosVoiceState = talosMember.getVoiceState();

        if (!talosVoiceState.inVoiceChannel()) {
            textChannel.sendMessage("Nothing playing.").queue();
            return;
        }

        final Guild guild = commandsContext.getGuild();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(guild);
        final AudioPlayer audioPlayer = musicManager.getAudioPlayer();

        if (audioPlayer.getPlayingTrack() == null) {
            textChannel.sendMessage("Nothing playing.").queue();
            return;
        }

        textChannel.sendMessage("Playing next track.").queue();
        musicManager.getScheduler().nextTrack();
    }

    @Override
    public String getName() {
        return "playnext";
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public List<String> getAliases() {
        return List.of("skip");
    }
}
