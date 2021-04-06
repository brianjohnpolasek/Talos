package talos.bot.commands.modules;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import talos.bot.Config;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

import java.util.List;

public class StatusModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {
        JDA jda = commandsContext.getJDA();

        commandsContext.getChannel().sendMessage("Setting custom status.").queue();
        jda.getPresence().setActivity(Activity.playing(commandsContext.getMessage()
                .getContentRaw().replace(Config.get("PREFIX") + this.getName(), "")));
    }

    @Override
    public String getName() {
        return "status";
    }

    @Override
    public String getHelp() {
        return "Use to set custom status for Talos.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("setstatus", "set_status", "setactivity", "set_activity");
    }
}
