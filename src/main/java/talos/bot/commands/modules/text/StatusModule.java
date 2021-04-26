package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import talos.bot.Config;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;
import talos.bot.helpers.MessageHelper;

import java.util.List;
import java.util.Locale;

public class StatusModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        JDA jda = commandsContext.getJDA();
        MessageHelper messageHelper = new MessageHelper(commandsContext, this.getName());

        //Set the status
        if (messageHelper.checkArgs(1)) {
            switch (commandsContext.getArgs().get(0).toLowerCase(Locale.ROOT)){
                case "reset":
                    jda.getPresence().setPresence(OnlineStatus.ONLINE, null); break;
                case "playing":
                    jda.getPresence().setActivity(Activity.playing(messageHelper.stripCommandName().replace("playing", ""))); break;
                case "watching":
                    jda.getPresence().setActivity(Activity.watching(messageHelper.stripCommandName().replace("watching", ""))); break;
                case "listening":
                    jda.getPresence().setActivity(Activity.listening(messageHelper.stripCommandName().replace("listening", ""))); break;
                default:
                    jda.getPresence().setActivity(Activity.watching(messageHelper.stripCommandName())); break;
            }
            messageHelper.sendMessage("Status successfully set.");
        }
        else {
            messageHelper.sendMessage("Please enter a valid status next time.");
        }
    }

    @Override
    public String getName() {
        return "status";
    }

    @Override
    public String getHelp() {
        return "Use to set custom status for Talos.\n\n**Usage:** "
                + Config.get("PREFIX")
                + "status [WATCHING/PLAYING/LISTENING/BLANK] [CUSTOM_STATUS]";
    }

    @Override
    public List<String> getAliases() {
        return List.of("setstatus", "set_status", "setactivity", "set_activity");
    }
}
