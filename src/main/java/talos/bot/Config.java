package talos.bot;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    //private static final Dotenv dotenv = Dotenv.load();


    private static final boolean isRemote = System.getenv("TOKEN") == null;

    public static String get(String key) {

        if (isRemote) {
            return Dotenv.load().get(key);
        }
        else {
            return System.getenv(key);
        }
    }
}
