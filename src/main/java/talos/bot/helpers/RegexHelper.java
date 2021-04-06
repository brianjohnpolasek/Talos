package talos.bot.helpers;

import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import talos.bot.Config;

import java.util.HashMap;

public class RegexHelper {

    private static HashMap<String, Boolean> channelWhitelist = new HashMap<String, Boolean>();

    public RegexHelper() {

        this.channelWhitelist.put(Config.get("MAIN_TEXT_CHANNEL"), true);
    }

    public void setChannelWhitelistEntry(String id, Boolean flag) {

        this.channelWhitelist.replace(id, flag);

        return;
    }

    public Boolean getChannelWhiteListEntry(String id) {

        return this.channelWhitelist.get(id);
    }

    public String regexSearch(String message) {
        //Replace with regex dictionary search
        //FINISH
        if (message.equals("test")) {
            return "Hello there";
        }
        else {
            return "";
        }
    }

    public void handle(GuildMessageReceivedEvent event, String response) {

        GuildChannel channel = event.getChannel();

        if (this.channelWhitelist.get(channel.getId())) {
            String message = event.getMessage().getContentRaw();

            event.getChannel().sendMessage(response).queue();
        }

        return;
    }
}
