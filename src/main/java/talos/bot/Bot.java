package talos.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Bot {

    private Bot() throws LoginException {
        //Config config = new Config();

        JDABuilder.createLight(Config.get("TOKEN"), GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new MessageHandler())
                .setActivity(Activity.playing("Always Watching"))
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }


}