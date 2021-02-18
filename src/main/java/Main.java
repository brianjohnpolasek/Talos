import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main {
    public static JDA jda;

    public static void main(String[] args) throws LoginException {


        // Create New Bot
        config bot = new config();
        jda = new JDABuilder(AccountType.BOT).setToken(bot.getToken()).buildAsync();

        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setGame(Game.watching("Nothing"));
        jda.addEventListener(new Commands());
    }
}
