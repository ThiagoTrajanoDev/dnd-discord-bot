package com.bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Token {

    static String DISCORD_BOT_TOKEN;

    static {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("config.properties")){
            properties.load(fileInputStream);
            DISCORD_BOT_TOKEN = properties.getProperty("DISCORD_BOT_TOKEN");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
