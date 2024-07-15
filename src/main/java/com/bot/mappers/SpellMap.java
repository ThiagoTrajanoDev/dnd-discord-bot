package com.bot.mappers;

import java.util.ArrayList;
import java.util.HashMap;


public class SpellMap {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDesc() {
        return desc;
    }

    public void setDesc(ArrayList<String> desc) {
        this.desc = desc;
    }

    public ArrayList<String> getHigherLevel() {
        return higher_level;
    }

    public void setHigherLevel(ArrayList<String> higherLevel) {
        this.higher_level = higherLevel;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getCastingTime() {
        return casting_time;
    }

    public void setCastingTime(String castingTime) {
        this.casting_time = castingTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public HashMap<String, String> getAreaOfEffect() {
        return area_of_effect;
    }

    public void setAreaOfEffect(HashMap<String,String> areaOfEffect) {
        this.area_of_effect = areaOfEffect;
    }

    String name;
    ArrayList<String> desc;
    ArrayList<String> higher_level;
    String range;
    String duration;
    String concentration;
    String casting_time;
    String level;
     HashMap<String,String> area_of_effect;


}
