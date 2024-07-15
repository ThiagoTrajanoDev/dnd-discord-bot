package com.bot.commands;

import com.bot.RequestManager.ClassParser;
import com.bot.mappers.RuleMap;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Regras implements  ICommand{

    String[] words = new String[] {
            "Ability Scores and Modifiers", "Actions in Combat",
            "Activating an Item", "Advantage and Disadvantage", "Attunement",
            "Cover", "Damage and Healing",
            "Diseases", "Fantasy Historical Pantheons", "Madness",
            "Mounted Combat", "Movement", "Objects", "Poisons",
            "Proficiency Bonus", "Resting", "Saving Throws", "Sentient Magic Items",
            "Standard Exchange Rates", "The Order of Combat",
            "The Planes of Existence", "Traps", "Underwater Combat",
            "Using Each Ability"
    };

    private String regra = "";

    @Override
    public String getName() {
        return "regras";
    }

    @Override
    public String getDescription() {
        return "Algumas regras do jogo";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "regra", "Descrição da regra escolhida", true, true)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        OptionMapping optionMapping = event.getOption("regra");
        String ruleName = "";
        if(optionMapping != null){
            regra = optionMapping.getAsString().replace(" ", "-").toLowerCase();
            ruleName = optionMapping.getAsString();
        }
        String url = String.format("https://www.dnd5eapi.co/api/rule-sections/%s", regra);
        try {
            RuleMap ruleMap  = ClassParser.fromJson(url, new RuleMap());
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(ruleName);
            if(ruleMap.getDesc().length() <1024){
                eb.addField("Descrição",ruleMap.getDesc(), false);
            }
            else {
                eb.addField("Paginação a ser implementada em breve","", false);
                eb.setImage("https://i.kym-cdn.com/entries/icons/original/000/035/976/mucho-texto.jpg");
            }
            eb.setColor(Color.CYAN);

            event.getHook().sendMessageEmbeds(eb.build()).queue();
        } catch (Exception e) {
            event.reply("Nâo encontrado").queue();
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean hasAutoComplete() {
        return true;
    }

    @Override
    public void executeAutocomplete(CommandAutoCompleteInteractionEvent event) {
        if(event.getName().equals(getName()) && event.getFocusedOption().getName().equals("regra")) {
            List<Command.Choice> options = Stream.of(words)
                    .filter(word -> word.startsWith(event.getFocusedOption().getValue()))
                    .map(word -> new Command.Choice(word, word))
                    .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }
}
