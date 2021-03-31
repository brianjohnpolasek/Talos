package talos.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException
    {
        config bot = new config();

        JDABuilder.createLight(bot.getToken(), GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new Commands())
                .setActivity(Activity.playing("Type !ping"))
                .build();
    }


}