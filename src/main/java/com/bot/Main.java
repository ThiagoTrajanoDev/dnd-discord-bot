package com.bot;

import com.bot.commands.CommandManager;
import com.bot.commands.Condicoes;
import com.bot.commands.Magias;
import com.bot.commands.Regras;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {

    public static void main(String[] args) {
        if(Token.DISCORD_BOT_TOKEN == null) {
            System.out.println("Necessário adição do token de login");
            return;
        }
        JDA jda = JDABuilder.createLight(Token.DISCORD_BOT_TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS).build();
        CommandManager commandManager = new CommandManager();
        commandManager.addCommand(new Magias());
        commandManager.addCommand(new Condicoes());
        commandManager.addCommand(new Regras());
        jda.addEventListener(commandManager);

    }
}
