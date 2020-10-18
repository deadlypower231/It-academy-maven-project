package com.itAcademy.services;


import com.itAcademy.api.service.IBattle;
import com.itAcademy.entities.Animal;
import com.itAcademy.utils.GetStats;
import com.serialization.SerializationUtils;

public class Battle implements IBattle {

    @Override
    public Animal battle(Animal animal1, Animal animal2) {
        boolean isBattle = true;
        boolean isChance = true;
        int counterRound = 1;
        double startHpAnimal1 = animal1.getHealth();
        double startHpAnimal2 = animal2.getHealth();
        while (isBattle) {
            while ((isChance && counterRound % 2 != 0 && animal1.getHealth() <= startHpAnimal1 / 2) ||
                    (isChance && counterRound % 2 != 0 && animal2.getHealth() <= startHpAnimal2 / 2)) {
                isChance = false;
                SerializationUtils.serialization(animal1, animal1.getName());
                SerializationUtils.serialization(animal2, animal2.getName());
            }
            if (counterRound % 2 != 0) {
                System.out.println("Round " + counterRound + "!\n");
                int hp = hit(animal1, animal2);
                counterRound++;
                printHealth(animal1, animal2);
                if (hp <= 0) {
                    System.out.println("Congratulation " + animal1.getName() + " won!\n");
                    System.out.println("Lucky chance! Let`s try? 1(Yes) or 2(No)");
                    int x = AnimalService.getIntReader();
                    if (x == 1) {
                        animal1 = (Animal) SerializationUtils.deserialization(animal1.getName());
                        animal2 = (Animal) SerializationUtils.deserialization(animal2.getName());
                        System.out.println(animal1);
                        System.out.println(animal2);
                        luckyBattle(animal1, animal2, startHpAnimal1, startHpAnimal2);
                    } else if (x == 2) {
                        endOfBattle(animal1, animal2, startHpAnimal1, startHpAnimal2);
                    }
                    isBattle = false;
                }
            } else {
                System.out.println("Round " + counterRound + "!\n");
                int hp = hit(animal2, animal1);
                counterRound++;
                printHealth(animal1, animal2);
                if (hp <= 0) {
                    System.out.println("Congratulation " + animal2.getName() + " won!\n");
                    System.out.println("Lucky chance! Let`s try? 1(Yes) or 2(No)");
                    int x = AnimalService.getIntReader();
                    if (x == 1) {
                        animal1 = (Animal) SerializationUtils.deserialization(animal1.getName());
                        animal2 = (Animal) SerializationUtils.deserialization(animal2.getName());
                        System.out.println(animal1);
                        System.out.println(animal2);
                        luckyBattle(animal2, animal1, startHpAnimal2, startHpAnimal1);
                    } else {
                        endOfBattle(animal2, animal1, startHpAnimal2, startHpAnimal1);
                    }
                    isBattle = false;
                }
            }
            System.out.println("Press enter!");
            AnimalService.getName();
        }
        return animal1;
    }

    private void luckyBattle(Animal animal1, Animal animal2, double animalHP1, double animalHP2) {
        int counterRound = 1;
        boolean isLuckyBattle = true;
        while (isLuckyBattle) {
            if (counterRound % 2 != 0) {
                System.out.println("Round " + counterRound + "!\n");
                counterRound++;
                int hp = hit(animal1, animal2);
                printHealth(animal1, animal2);
                if (hp <= 0) {
                    System.out.println("Congratulation " + animal1.getName() + " won!");
                    endOfBattle(animal1, animal2, animalHP1, animalHP2);
                    isLuckyBattle = false;
                }
            } else {

                System.out.println("Round " + counterRound + "!\n");
                counterRound++;
                int hp = hit(animal2, animal1);
                printHealth(animal1, animal2);
                if (hp <= 0) {
                    System.out.println("Congratulation " + animal2.getName() + " won!");
                    endOfBattle(animal2, animal1, animalHP2, animalHP1);
                    isLuckyBattle = false;
                }
            }
            System.out.println("Press enter!");
            AnimalService.getName();
        }


    }

    private void printHealth(Animal animal1, Animal animal2) {
        System.out.println(animal1.getName() + " health: " + (int) animal1.getHealth() + "\n" +
                animal2.getName() + " health: " + (int) animal2.getHealth());
    }

    private void endOfBattle(Animal animal1, Animal animal2, double animal1HP, double animal2HP) {
        animal1.setHealth(animal1HP);
        animal2.setHealth(animal2HP);
        animal1.setExperience(animal1.getExperience() + (50 * animal1.getLevel() / 2));
        animal2.setExperience(animal2.getExperience() + (25 * animal2.getLevel() / 2));


    }

    private double randomDamage(Animal animal) {
        double random = Math.random() * 3;
        return (animal.getDamage() - 1) + random;
    }

    private int hit(Animal animal1, Animal animal2) {

        if (GetStats.getEvasion(animal2)) {
            System.out.println(animal2.getName() + " dodges!");
        } else if (GetStats.getCriticalChance(animal1)) {
            double criticalDamage = randomDamage(animal1) * animal1.getCriticalStrikeMultiplier();
            double criticalDamageWithDefence = criticalDamage - criticalDamage / 100 * GetStats.getDefencePercent(animal2);
            System.out.println(animal1.getName() + " deal " + (int) criticalDamageWithDefence + " critical damage!");
            animal2.setHealth(animal2.getHealth() - (int) criticalDamageWithDefence);
        } else {
            double randomDamage = randomDamage(animal1);
            double damage = randomDamage - randomDamage / 100 * GetStats.getDefencePercent(animal2);
            System.out.println(animal1.getName() + " deal " + (int) damage + " damage!");
            animal2.setHealth(animal2.getHealth() - (int) damage);
        }
        return (int) animal2.getHealth();
    }

}