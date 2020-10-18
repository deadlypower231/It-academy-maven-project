package com.itAcademy.utils;

import com.itAcademy.entities.Animal;
import com.itAcademy.entities.Cat;
import com.itAcademy.entities.Dog;

public class SetStatsNextLevel {

    private SetStatsNextLevel(){

    }

    public static void setStatsNextLevel(Animal animal) {
        if (animal instanceof Cat) {
            animal.setHealth(100);
            animal.setMana(25);
            animal.setDamage(10);
            animal.setDefence(2);
            animal.setStrength(3);
            animal.setAgility(5);
            animal.setIntelligence(2);
            animal.setCriticalChance(3);
            animal.setCriticalStrikeMultiplier(2);
        } else if (animal instanceof Dog) {
            animal.setHealth(100);
            animal.setMana(25);
            animal.setDamage(10);
            animal.setDefence(2);
            animal.setStrength(5);
            animal.setAgility(3);
            animal.setIntelligence(2);
            animal.setCriticalChance(3);
            animal.setCriticalStrikeMultiplier(2);
        }

        int level = (int) animal.getLevel();
        if (animal instanceof Cat) {
            animal.setStrength(animal.getStrength() + ((level * 1.2) - 1.2));
            animal.setAgility(animal.getAgility() + ((level * 2) - 2));
            animal.setIntelligence(animal.getIntelligence() + ((level * 0.9) - 0.9));
            animal.setHealth(animal.getHealth() + (10 + level * 2) + (animal.getStrength() * 1.3));
            animal.setMana(animal.getMana() + (5 + level));
            animal.setDamage(animal.getDamage() + (animal.getAgility() * 1.2));


        } else if (animal instanceof Dog) {

            animal.setStrength(animal.getStrength() + ((level * 2.2) - 2.2));
            animal.setAgility(animal.getAgility() + ((level * 1.1) - 1.1));
            animal.setIntelligence(animal.getIntelligence() + ((level * 0.9) - 0.9));
            animal.setHealth(animal.getHealth() + (12 + level * 2) + (animal.getStrength() * 1.4));
            animal.setMana(animal.getMana() + (4 + level));
            animal.setDamage(animal.getDamage() + (animal.getStrength() * 1.3));
        }
    }
}
