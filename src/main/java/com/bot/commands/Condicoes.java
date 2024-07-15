package com.bot.commands;

import ch.qos.logback.core.util.StringUtil;
import com.bot.RequestManager.ClassParser;
import com.bot.RequestManager.RequestManager;
import com.bot.mappers.ConditionMap;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Condicoes implements ICommand {
    private String condicao = "";
    private String[] words = {"blinded", "charmed", "deafened", "exhaustion", "frightened", "grappled", "incapacitated", "invisible", "paralyzed", "petrified", "poisoned", "prone", "restrained", "stunned", "unconscious"};


    @Override
    public String getName() {
        return "condicoes";
    }

    @Override
    public String getDescription() {
        return "Nome e descrição das condições";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "nome", "Nome da condição", true, true)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping optionMapping = event.getOption("nome");
        if (optionMapping != null) {
            condicao = optionMapping.getAsString();
        }
        try {
            event.deferReply().queue();
            String url = String.format("https://www.dnd5eapi.co/api/conditions/%s", condicao);
            ConditionMap conditionMap = ClassParser.fromJson(url, new ConditionMap());

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(StringUtil.capitalizeFirstLetter(condicao));
            String desc = "";
            embedBuilder.addField("Descrição" ,"",false);
            for (String string : conditionMap.getDesc()) {
                if(string.length() + desc.length() < 1023){
                    desc = desc.concat(string).concat("\n");
                }
                else {
                    embedBuilder.addField("", desc, false);
                    desc = "";
                }
            }
            embedBuilder.addField("", desc, false);
            embedBuilder.setColor(Color.BLUE);

            event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
        } catch (Exception e) {
            event.reply("Não encontrado").queue();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasAutoComplete() {
        return true;
    }

    @Override
    public void executeAutocomplete(CommandAutoCompleteInteractionEvent event) {
        if (event.getName().equals(getName()) && event.getFocusedOption().getName().equals("nome")) {
            List<Command.Choice> options = Stream.of(words)
                    .filter(word -> word.startsWith(event.getFocusedOption().getValue()))
                    .map(word -> new Command.Choice(word, word))
                    .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }

}