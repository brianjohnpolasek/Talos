package talos.bot.commands.modules.audio;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.Config;
import talos.bot.audio.GuildMusicManager;
import talos.bot.audio.PlayerManager;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

public class RepeatModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        final TextChannel textChannel = commandsContext.getChannel();
        final Guild guild = commandsContext.getGuild();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(guild);

        musicManager.getScheduler().repeating = !musicManager.getScheduler().repeating;

        if (musicManager.getScheduler().repeating) {
            textChannel.sendMessage("Setting the current song to repeat.").queue();
        }
        else {
            textChannel.sendMessage("Resuming normal play.").queue();
        }
    }

    @Override
    public String getName() {
        return "repeat";
    }

    @Override
    public String getHelp() {
        return "Repeats the currently playing song.\n\n**Usage:** "
                + Config.get("PREFIX")
                + "repeat";
    }
}
