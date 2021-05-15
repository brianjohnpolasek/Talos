package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.StravaHelper;

public class StravaModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        TextChannel textChannel = commandsContext.getChannel();
        StravaHelper stravaHelper = new StravaHelper();

        textChannel.sendMessage("Fetching latest activity by Brian.").queue();
        textChannel.sendMessage(stravaHelper.getLatestActivity().build()).queue();
    }

    @Override
    public String getName() {
        return "strava";
    }

    @Override
    public String getHelp() {
        return "testing strava API";
    }
}
