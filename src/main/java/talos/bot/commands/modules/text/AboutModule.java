package talos.bot.commands.modules.text;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ApplicationInfo;
import net.dv8tion.jda.api.entities.TextChannel;
import talos.bot.commands.CommandsContext;
import talos.bot.commands.ICommands;

public class AboutModule implements ICommands {
    @Override
    public void handle(CommandsContext commandsContext) {

        TextChannel channel = commandsContext.getChannel();

        //channel.sendFile(new File("README.md")).queue();

        ApplicationInfo applicationInfo = commandsContext.getJDA().retrieveApplicationInfo().complete();

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("**About Talos**")
                .addField("Name: ", applicationInfo.getName(), false)
                .addField("Owner: ", applicationInfo.getOwner().getName(), false)
                .setFooter("*made with love*", applicationInfo.getIconUrl());

        channel.sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "about";
    }

    @Override
    public String getHelp() {
        return null;
    }
}
