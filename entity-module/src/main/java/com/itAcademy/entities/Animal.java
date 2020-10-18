package com.itAcademy.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class Animal implements Serializable {
    protected String name;

    protected double level;

    protected double experience;

    protected double health;

    protected double mana;

    protected double damage;

    protected double defence;

    protected double strength;

    protected double agility;

    protected double intelligence;

    protected double criticalStrikeMultiplier;

    protected double criticalChance;

    protected String type;

    @Override
    public String toString() {

        if (type.equals("cat")){
            return "Cat{" +
                    "name='" + name + '\'' +
                    ", level=" + level +
                    ", health=" + health +
                    '}';
        }else {
            return "Dog{" +
                    "name='" + name + '\'' +
                    ", level=" + level +
                    ", health=" + health +
                    '}';
        }

    }
}
