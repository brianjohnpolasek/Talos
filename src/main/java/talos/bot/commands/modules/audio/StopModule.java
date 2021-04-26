package talos.bot.commands.modules.audio;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.AudioManager;
import talos.bot.audio.GuildMusicManager;
import talos.bot.audio.PlayerManager;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.AudioHelper;

@SuppressWarnings("ConstantConditions")
public class StopModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        final Member talosMember = commandsContext.getSelfMember();
        final GuildVoiceState talosVoiceState = talosMember.getVoiceState();

        if (!talosVoiceState.inVoiceChannel()) {
            commandsContext.getChannel().sendMessage("Talos isn't playing anything.").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager
                .getInstance()
                .getGuildMusicManager(commandsContext.getGuild());

        //Clear the queue
        musicManager.getScheduler().getPlayer().stopTrack();
        musicManager.getScheduler().getQueue().clear();

        //Leave the voice channel
        final AudioManager audioManager = commandsContext.getGuild().getAudioManager();
        audioManager.closeAudioConnection();

        commandsContext.getChannel().sendMessage("Talos has stopped the music & cleared the queue.").queue();

        AudioHelper.getINSTANCE().resetActivity();
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getHelp() {
        return "Stops the current song & empties the queue.";
    }
}
