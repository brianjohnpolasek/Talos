package talos.bot.helpers;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import talos.bot.Bot;
import talos.bot.Config;
import talos.bot.commands.CommandsContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

    private static final HashMap<String, Boolean> channelWhitelist = new HashMap<>();

    private final HashMap<String, Pair<String, String>> regexResponseMap = new HashMap<>();

    public RegexHelper() {

        // REPLACE WITH READ FROM FILE
        channelWhitelist.put(Config.get("MAIN_TEXT_CHANNEL"), true);

        /* Insert all regex commands into dictionary */
        try {
            File regexTextFile = new File(Config.get("REGEX_PATH"));
            Scanner scanner = new Scanner(regexTextFile);

            while (scanner.hasNext()) {
                String[] sections = scanner.nextLine().split(";");

                if (sections.length == 3) {
                    regexResponseMap.put(sections[0], Pair.of(sections[1], sections[2]));
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void setChannelWhitelistEntry(String id, Boolean flag) {

        channelWhitelist.replace(id, flag);
    }

    public static Boolean getChannelWhiteListEntry(String id) {

        /* Check if entry exists */
        if (channelWhitelist.containsKey(id)) {

            return channelWhitelist.get(id);
        }
        else {
            channelWhitelist.put(id, Boolean.FALSE);
            return Boolean.FALSE;
        }

    }

    public static void whitelistAll(CommandsContext commandsContext) {
        List<TextChannel> allChannels = commandsContext.getJDA().getTextChannels();

        for (TextChannel channel: allChannels
             ) {
            if (getChannelWhiteListEntry(channel.getId()) == Boolean.FALSE) {
                RegexHelper.setChannelWhitelistEntry(channel.getId(), Boolean.TRUE);
            }
        }
    }

    public static void blacklistAll(CommandsContext commandsContext) {
        List<TextChannel> allChannels = commandsContext.getJDA().getTextChannels();

        for (TextChannel channel: allChannels
        ) {
            RegexHelper.setChannelWhitelistEntry(channel.getId(), Boolean.FALSE);
        }
    }

    /* Search dictionary for pattern match and return response(s) */
    public List<String> regexSearch(String message) {

        ArrayList<String> responses = new ArrayList<>();

        for (Map.Entry<String, Pair<String, String>> set: regexResponseMap.entrySet()
             ) {
            // Add any matches to the results

            String regexString = set.getValue().getLeft();
            Pattern regexPattern = Pattern.compile(regexString);
            Matcher regexMatcher = regexPattern.matcher(message);

            if (regexMatcher.find()) {
                responses.add(set.getValue().getRight());
            }
        }

        return responses;
    }

    public void handle(GuildMessageReceivedEvent event, String response) {

        GuildChannel channel = event.getChannel();

        if (channelWhitelist.get(channel.getId())) {

            event.getChannel().sendMessage(response).queue();
        }
    }
}
