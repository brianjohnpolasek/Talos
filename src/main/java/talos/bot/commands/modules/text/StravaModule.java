package talos.bot.commands.modules.text;

import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.StravaHelper;

import java.io.IOException;

public class StravaModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        StravaHelper stravaHelper = new StravaHelper();

        try {
            commandsContext.getChannel().sendMessage(stravaHelper.getActivities()).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
