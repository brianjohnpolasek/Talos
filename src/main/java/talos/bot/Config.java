package talos.bot;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    private static final Dotenv dotenv = Dotenv.load();

    public static String get(String key) {

        String systemEnv = System.getenv().get(key);

        if (systemEnv.isEmpty()) {
            return dotenv.get(key);
        }
        else {
            return systemEnv;
        }
    }
}
