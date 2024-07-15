package com.bot;

import com.bot.RequestManager.RequestManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.w3c.dom.Text;

public class Listeners extends ListenerAdapter {



//    @Override
//    public void onReady(ReadyEvent event) {
//        JDA jda = event.getJDA();
//        TextChannel textChannel = jda.getTextChannelsByName("geral", true).getFirst();
////        textChannel.sendMessage("Advogado de Regra chego, papai. Esqueça tudo.").queue();
//        Guild guild = jda.getGuildById(1260743339163123782L);
//        guild.upsertCommand("magias", "Retorna as magias de uma classe por level").addOptions(
//                new OptionData(OptionType.STRING , "classe", "Classe da magia", true),
//                new OptionData(OptionType.STRING , "level", "Level da magia", true)
//        ).queue();
//



//    }


}
