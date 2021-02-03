import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        // Create New Bot
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        config bot = new config();
        builder.setToken(bot.getToken());

        // Create Ping Pong Listener
        builder.addEventListener(new Main());
        builder.buildAsync();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Restrict bot to bot communication (for now)
        if (event.getAuthor().isBot()){
            return;
        }

        System.out.println("Received message from " +
                event.getAuthor().getName() +
                ": " +
                event.getMessage().getContentDisplay()
        );

        // Ping Pong
        if (event.getMessage().getContentRaw().equals("!ping")) {
            event.getChannel().sendMessage("pong!").queue();
        }
    }
}
