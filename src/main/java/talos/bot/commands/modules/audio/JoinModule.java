package talos.bot.commands.modules.audio;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import talos.bot.Config;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

@SuppressWarnings("ConstantConditions")
public class JoinModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

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

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "Manually have Talos join a voice channel.";
    }
}
