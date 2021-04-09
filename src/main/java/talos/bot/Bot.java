package talos.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Bot {

    private Bot() throws LoginException {

        JDABuilder.createLight(System.getenv("TOKEN"), GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new DiscordListener())
                .setActivity(Activity.watching(Config.get("DEFAULT_STATUS")))
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }


}