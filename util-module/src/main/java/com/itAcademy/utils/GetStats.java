package com.itAcademy.utils;

import com.itAcademy.entities.Animal;

public class GetStats {

    private GetStats(){

    }

    public static boolean getEvasion(Animal animal) {
        int random = (int) (Math.random() * 100 + 1);
        return random <= (5 + (animal.getAgility() * 0.05));

    }

    public static boolean getCriticalChance(Animal animal) {
        return (int) (Math.random() * 100 + 1) <= (animal.getCriticalChance() + (animal.getAgility() * 0.05));
    }

    public static double getDefencePercent(Animal animal) {
        return animal.getDefence() * 0.9;
    }

}
