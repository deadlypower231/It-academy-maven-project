package com.itAcademy.services;


import com.itAcademy.api.dao.IAnimalDao;
import com.itAcademy.api.service.IAnimalService;
import com.itAcademy.dao.AnimalDao;
import com.itAcademy.entities.Animal;
import com.itAcademy.entities.Cat;
import com.itAcademy.entities.Dog;
import com.itAcademy.utils.SetStatsNextLevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AnimalService implements IAnimalService {

    private static final String TITLE = "~=~=~=~=~=~=~=~=~=~=~=~=~=~=~\n";
    private static final String ENTER_YOUR_NAME = "Enter your name: ";

    static IAnimalDao animalDao = new AnimalDao();

    private boolean checkName(String name) {
        Pattern pattern = Pattern.compile("[A-Z][a-z]{2,9}");
        Matcher matcher = pattern.matcher(name);
        boolean isCheckName = matcher.matches();
        if (isCheckName) {
            try {
                return animalDao.getAllEntities().stream().noneMatch(x -> x.equals(name));

            } catch (SQLException e) {
                System.out.println("No connection to MySQL!");
            }catch (IOException e){
                System.out.println("Properties file is not found!");
            }

        }
        return isCheckName;
    }

    @Override
    public Animal loadOrCreate() {
        Animal animal = null;
        System.out.println("\nLoad a hero or create a new hero?\n1-Load.\n2-Create.");
        int i = getIntReader();
        if (i == 1) {
            System.out.println(ENTER_YOUR_NAME);
            String name;
            boolean isCheckName = true;
            while (isCheckName) {
                name = getName();
                if (!checkName(name)) {
                    animal = loadFromMySQL(name);
                    isCheckName = false;
                } else {
                    System.out.println(ENTER_YOUR_NAME);
                }
            }
        } else if (i == 2) {
            animal = chooseRace();
            System.out.println(ENTER_YOUR_NAME);
            String name;
            boolean isCheckName = true;
            while (isCheckName) {
                name = getName();
                if (checkName(name)) {
                    animal.setName(name);
                    isCheckName = false;
                } else {
                    System.out.println(ENTER_YOUR_NAME);
                }
            }
            createHero(animal);
        } else {
            return loadOrCreate();
        }
        return animal;
    }

    @Override
    public Animal chooseRace() {
        Animal animal;
        System.out.println("Choose:\n1-Cat.\n2-Dog.");
        int i;
        i = getIntReader();
        if (i > 0 && i < 3) {
            animal = ((i == 1) ? new Cat() : new Dog());
        } else {
            return chooseRace();
        }
        return animal;
    }

    @Override
    public Animal createComputerAnimal(Animal animal) {
        Animal computer = null;
        if (animal instanceof Cat) {
            computer = new Cat();
            computer.setName("ComputerCat");
            createHero(computer);
            computer.setLevel(animal.getLevel());
            if (animal.getLevel() > 1) {
                SetStatsNextLevel.setStatsNextLevel(computer);
            }
        } else if (animal instanceof Dog) {
            computer = new Dog();
            computer.setName("ComputerDog");
            createHero(computer);
            computer.setLevel(animal.getLevel());
            if (animal.getLevel() > 1) {
                SetStatsNextLevel.setStatsNextLevel(computer);
            }
        }
        return computer;
    }

    private void createHero(Animal animal) {
        if (animal instanceof Cat) {
            animal.setLevel(1);
            animal.setType("cat");
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
            animal.setLevel(1);
            animal.setType("dog");
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
    }

    static String getName() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String string = null;
        try {
            string = reader.readLine();
        } catch (IOException e) {
            System.out.println("IOError");
        }
        return string;
    }

    static int getIntReader() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int number = 0;
        boolean isReader = true;
        while (isReader) {
            try {
                number = Integer.parseInt(reader.readLine());
                isReader = false;
            } catch (NumberFormatException | IOException e) {
                System.out.println("Enter number: ");
            }
        }
        return number;
    }

    public static void saveToMySQL(Animal animal) {
        try {
            animalDao.saveToMySQL(animal);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            System.out.println("No connection!");
        }
    }

    @Override
    public Animal loadFromMySQL(String name) {
        Animal animal = null;
        try {
            animal = animalDao.loadFromMySQL(name);
        } catch (SQLException | IOException throwables) {
            System.out.println("No connection!");
        }
        return animal;
    }

    public static void levelUp(Animal animal) {
        int level = (int) animal.getLevel();
        int levelUp = 100 * (level * (1 + level));
        if (animal.getExperience() >= levelUp) {
            animal.setLevel(animal.getLevel() + 1);
            SetStatsNextLevel.setStatsNextLevel(animal);
        }
    }

    @Override
    public StringBuilder start() {
        return new StringBuilder(TITLE).append("\t\tWelcome to FA\n").append(TITLE);
    }

    @Override
    public boolean exit() {
        System.out.println("Quit the game?\n1-Yes.\n2-No.\n");
        int i = getIntReader();
        if (i == 1) {
            return true;
        } else if (i == 2) {
            return false;
        } else {
            return exit();
        }
    }

}
