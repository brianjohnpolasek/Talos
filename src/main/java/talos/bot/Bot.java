package talos.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Bot {

    private Bot() throws LoginException {
        config config = new config();

        JDABuilder.createLight(config.getToken(), GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new Commands())
                .setActivity(Activity.playing("Type !ping"))
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new Bot()
    }


}