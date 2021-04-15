package talos.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Bot {

    private Bot() throws LoginException {

        JDABuilder.createLight(Config.get("TOKEN"),
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES)
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(new DiscordListener())
                .setActivity(Activity.watching(Config.get("DEFAULT_STATUS")))
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }


}