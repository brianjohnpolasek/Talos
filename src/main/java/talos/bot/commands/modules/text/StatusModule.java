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

        final List<String> args = commandsContext.getArgs();

        //Size check
        if (!messageHelper.checkArgs(1)) {
            return;
        }

        //Reset the status
        if (args.get(0).equals("reset")) {
            jda.getPresence().setPresence(OnlineStatus.ONLINE, null);
            return;
        }

        //Set the status
        if (messageHelper.checkArgs(2)) {

            final String statusMessage = String.join(" ", args.subList(1, args.size()));

            switch (args.get(0).toLowerCase(Locale.ROOT)){
                case "playing":
                    jda.getPresence().setActivity(Activity.playing(statusMessage)); break;
                case "watching":
                    jda.getPresence().setActivity(Activity.watching(statusMessage)); break;
                case "listening":
                    jda.getPresence().setActivity(Activity.listening(statusMessage)); break;
                default:
                    jda.getPresence().setActivity(Activity.watching(String.join(" ", args))); break;
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
