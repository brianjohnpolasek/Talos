package talos.bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import talos.bot.Config;
import talos.bot.helpers.RegexHelper;

import javax.annotation.Nonnull;

public class MessageHandler extends ListenerAdapter {

    private final CommandHandler handler = new CommandHandler();
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);

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
        String regexSearch = regexHelper.regexSearch(message);

        if (regexSearch != "") {
            regexHelper.handle(event, regexSearch);
        }

        return;
    }
}
