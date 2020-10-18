package com.itAcademy.services;


import com.itAcademy.api.dao.IAnimalDao;
import com.itAcademy.api.service.IAnimalService;
import com.itAcademy.dao.AnimalDao;
import com.itAcademy.entities.Animal;
import com.itAcademy.entities.Cat;
import com.itAcademy.entities.Dog;
import com.itAcademy.utils.SetStatsNextLevel;

import java.io.*;
import java.util.Map;
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
            File file = new File("src\\FightingAnimals\\save\\" + name + ".txt");
            if (file.exists()) {
                System.out.println("This name exists!");
                return false;
            }
        }
        return isCheckName;
    }

    @Override
    public Animal loadOrCreate() {
        Animal animal;
        System.out.println("\nLoad a hero or create a new hero?\n1-Load.\n2-Create.");
        int i = getIntReader();
        if (i == 1) {
            System.out.println(ENTER_YOUR_NAME);
            animal = loadFromFile(exists(getName()));
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


    public static void saveToFile(Animal animal) {
        StringBuilder stats = new StringBuilder()
                .append("id0: ").append(animal.getType())
                .append("\nid1: ").append(animal.getName())
                .append("\nid2: ").append(animal.getLevel())
                .append("\nid3: ").append(animal.getExperience())
                .append("\nid4: ").append(animal.getHealth())
                .append("\nid5: ").append(animal.getMana())
                .append("\nid6: ").append(animal.getDamage())
                .append("\nid7: ").append(animal.getDefence())
                .append("\nid8: ").append(animal.getStrength())
                .append("\nid9: ").append(animal.getAgility())
                .append("\nid10: ").append(animal.getIntelligence())
                .append("\nid11: ").append(animal.getCriticalChance())
                .append("\nid12: ").append(animal.getCriticalStrikeMultiplier());
        try {
            animalDao.saveToFile(stats, animal.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Animal loadFromFile(String name) {
        Animal animal = null;
        try {
            Map<String, String> stats = animalDao.loadFromFile(name);
            animal = ((stats.get("id0:").equals("cat")) ? new Cat() : new Dog());
            for (Map.Entry<String, String> s :
                    stats.entrySet()) {
                switch (s.getKey()) {
                    case "id0:":
                        animal.setType(s.getValue());
                        break;
                    case "id1:":
                        animal.setName(s.getValue());
                        break;
                    case "id2:":
                        animal.setLevel(Double.parseDouble(s.getValue()));
                        break;
                    case "id3:":
                        animal.setExperience(Double.parseDouble(s.getValue()));
                        break;
                    case "id4:":
                        animal.setHealth(Double.parseDouble(s.getValue()));
                        break;
                    case "id5:":
                        animal.setMana(Double.parseDouble(s.getValue()));
                        break;
                    case "id6:":
                        animal.setDamage(Double.parseDouble(s.getValue()));
                        break;
                    case "id7:":
                        animal.setDefence(Double.parseDouble(s.getValue()));
                        break;
                    case "id8:":
                        animal.setStrength(Double.parseDouble(s.getValue()));
                        break;
                    case "id9:":
                        animal.setAgility(Double.parseDouble(s.getValue()));
                        break;
                    case "id10:":
                        animal.setIntelligence(Double.parseDouble(s.getValue()));
                        break;
                    case "id11:":
                        animal.setCriticalChance(Double.parseDouble(s.getValue()));
                        break;
                    case "id12:":
                        animal.setCriticalStrikeMultiplier(Double.parseDouble(s.getValue()));
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Exception");
        }
        return animal;
    }

    private String exists(String fileName) {
        String fullName = "save-module\\src\\main\\java\\com\\itAcademy\\save\\" + fileName + ".txt";
        File file = new File(fullName);
        if (!file.exists()) {
            System.out.println("File does not exists!");
            System.out.println(ENTER_YOUR_NAME);
            return exists(getName());
        }
        return fileName;
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
        StringBuilder stringBuilder = new StringBuilder(TITLE);
        stringBuilder.append("\t\tWelcome to FA\n").append(TITLE);
        return stringBuilder;
    }

    /**
     * Метод exit запрашивает у пользователя, продолжить покинуть игру или нет.
     *
     * @return boolean
     */
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
