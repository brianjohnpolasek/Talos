package talos.bot;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import talos.bot.commands.CommandHandler;
import talos.bot.helpers.RegexHelper;

import javax.annotation.Nonnull;
import java.util.List;


public class DiscordListener extends ListenerAdapter {

    private final CommandHandler handler = new CommandHandler();
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordListener.class);

    public RegexHelper regexHelper;

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        this.regexHelper = new RegexHelper();

        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event)
    {
        String prefix = Config.get("PREFIX");
        User user = event.getAuthor();
        String message = event.getMessage().getContentRaw();
        MessageChannel channel = event.getChannel();

        //IGNORE TEST SERVER
        //if (event.getGuild().getId().equals(Config.get("SERVER_ID"))) {
        //    return;
        //}

        //SHUTDOWN COMMAND
        if (message.equalsIgnoreCase(prefix + "shutdown")
                && user.getId().equals(Config.get("OWNER_ID"))) {
            channel.sendMessage("Goodbye cruel world!").queue();
            LOGGER.info("Shutdown.");
            event.getJDA().shutdown();

            return;
        }

        //OTHER COMMAND
        if (message.startsWith(prefix)) {

            handler.handle(event);
        }

        //REGEX SEARCH
        List<String> regexSearch = regexHelper.regexSearch(message);

        if (regexSearch.size() > 0) {

            for (String response: regexSearch
                 ) {
                regexHelper.handle(event, response);
            }
        }
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {

        if (event.getMember().getId().equals(Config.get("OWNER_ID"))) {

            String nickname = event.getMember().getNickname();

            event.getJDA()
                    .getTextChannelById(Config.get("MAIN_TEXT_CHANNEL"))
                    .sendMessage("HELLO THERE!" + nickname).queue()
            ;
        }

    }
}
