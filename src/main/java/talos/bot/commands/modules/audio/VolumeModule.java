package talos.bot.commands.modules.audio;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.Config;
import talos.bot.audio.GuildMusicManager;
import talos.bot.audio.PlayerManager;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

import java.util.List;

public class VolumeModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

        final TextChannel textChannel = commandsContext.getChannel();

        List<String> args = commandsContext.getArgs();

        //Check for arguments
        if (args.isEmpty()) {
            textChannel.sendMessage("Must include a value.").queue();
            return;
        }

        int volumeLevel = 50;

        //Validate argument value
        try {
            volumeLevel = Integer.parseInt(args.get(0));
        }catch (NumberFormatException e) {
            System.out.println(e);
            textChannel.sendMessage("Invalid volume level specified.").queue();
            return;
        }

        final Guild guild = commandsContext.getGuild();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(guild);

        musicManager.getAudioPlayer().setVolume(volumeLevel);
    }

    @Override
    public String getName() {
        return "volume";
    }

    @Override
    public String getHelp() {
        return "Allows users to set the master volume of Talos.\n\n**Usage:** "
                + Config.get("PREFIX")
                + "volume [VALUE_1_100]";
    }
}
