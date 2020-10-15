package com.itAcademy.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
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
}
