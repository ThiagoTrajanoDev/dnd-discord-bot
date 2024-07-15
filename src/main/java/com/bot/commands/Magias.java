package com.bot.commands;

import com.bot.RequestManager.ClassParser;
import com.bot.mappers.SpellListMap;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Magias implements ICommand {
    private final String[] words = {"barbarian","bard","cleric","druid","fighter","monk","paladin","ranger","rogue","sorcerer","warlock","wizard"};
    private String classe = "";
    private String level = "";

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping classeOption = event.getOption("classe");
        OptionMapping levelOption = event.getOption("level");
        if (classeOption != null && levelOption!= null) {
            classe = classeOption.getAsString();
            level = levelOption.getAsString();
        }
        try {
            String url = String.format("https://www.dnd5eapi.co/api/classes/%s/levels/%s/spells",classe, level);
            SpellListMap spells = ClassParser.fromJson(url, new SpellListMap());

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(String.format("Magias de %s - Level %s", classe, level));
            embed.setDescription("Lista de magias de uma classe em determinado level");
            embed.setColor(Color.BLUE);

            for(HashMap<String,String>spell : spells.getResults()) {
                    embed.addField(spell.get("name"),"",false);
            }
            if (!spells.getResults().isEmpty()){
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                return;
            }
            event.reply("Não encontrado").queue();
        } catch (Exception e) {
            event.reply("Ocorre um erro, tente novamente!").queue();
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean hasAutoComplete() {
        return true;
    }

    @Override
    public void executeAutocomplete(CommandAutoCompleteInteractionEvent event) {
        if(event.getName().equals("magias") && event.getFocusedOption().getName().equals("classe")) {
            List<Command.Choice> options = Stream.of(words)
                    .filter(word -> word.startsWith(event.getFocusedOption().getValue()))
                    .map(word -> new Command.Choice(word, word))
                    .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }

    @Override
    public String getName() {
        return "magias";
    }

    @Override
    public String getDescription() {
        return "Retorna as magias de uma classe por level";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING , "classe", "Classe da magia", true,true),
                new OptionData(OptionType.STRING , "level", "Level da magia", true)
        );
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
