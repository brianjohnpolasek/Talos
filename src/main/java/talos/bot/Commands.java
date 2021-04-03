package talos.bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class Commands extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Commands.class);

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event)
    {

        String prefix = Config.get("PREFIX");

        String msg = event.getMessage().getContentRaw();

        //PING PONG
        if (msg.equals(prefix + "ping"))
        {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                    .queue(response /* => Message */ -> response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue());
        }

        //SHUTDOWN
        if (msg.equalsIgnoreCase(prefix + "shutdown") && event.getAuthor().getId().equals(Config.get("OWNER_ID"))) {
            LOGGER.info("Shutdown.");
            event.getJDA().shutdown();
        }
    }
}
