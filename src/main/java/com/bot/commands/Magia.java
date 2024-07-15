package com.bot.commands;

import ch.qos.logback.core.util.StringUtil;
import com.bot.RequestManager.ClassParser;
import com.bot.mappers.SpellMap;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import javax.swing.text.html.Option;
import java.awt.*;
import java.util.List;

public class Magia implements  ICommand{

    String nome = "";

    @Override
    public String getName() {
        return "magia";
    }

    @Override
    public String getDescription() {
        return "Mostra magia escolhida";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "nome", "Nome da magia", true)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping  optionMapping = event.getOption("nome");
        if (optionMapping != null) {
            nome = optionMapping.getAsString().replace(" ", "-").toLowerCase();
        }

        try {
            event.deferReply().queue();
            String url = String.format("https://www.dnd5eapi.co/api/spells/%s",nome);
            SpellMap spellMap = ClassParser.fromJson(url, new SpellMap());

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(spellMap.getName());
            String desc = "";
            String higherLvl = "";
            String separator = "-------------------";
            eb.addField("Concentração", spellMap.getConcentration(),true);
            if (spellMap.getAreaOfEffect() != null){
                eb.addField("Área de efeito", spellMap.getAreaOfEffect().get("type"),true);
            }
            eb.addField("Duração", spellMap.getDuration(),true);
            eb.addField("Range", spellMap.getRange(),true);
            eb.setColor(Color.green);
            eb.addField("Descrição: ", separator ,false);
            for(String description: spellMap.getDesc()){
                if(description.length() + desc.length() < 1024 && !description.isEmpty()){
                    desc = desc.concat(description).concat("\n");
                }
                else {
                    eb.addField("", desc, false);
                    desc = "";
                }
            }
            // Ainda falta implementar um sistema de paginação eficiente, solução provisória
            if (!desc.isEmpty() && eb.getFields().getLast().getValue().equals(separator)) {
                eb.addField("",desc,false);
            }
            for(String higherLevel: spellMap.getHigherLevel()){
                higherLvl = higherLvl.concat(higherLevel).concat("\n");
            }
            if (higherLvl.length() < 1024 && !higherLvl.isEmpty()) {
                eb.addField("Niveis acima",higherLvl,false);
            }

        event.getHook().sendMessageEmbeds(eb.build()).queue();
        } catch (Exception e) {
            event.reply("Aconteceu algum erro inesperado, verifique se os parâmetros estão corretos").queue();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasAutoComplete() {
        return false;
    }

    @Override
    public void executeAutocomplete(CommandAutoCompleteInteractionEvent event) {

    }
}
