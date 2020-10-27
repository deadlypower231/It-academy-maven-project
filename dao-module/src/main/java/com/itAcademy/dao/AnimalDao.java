package com.itAcademy.dao;

import com.itAcademy.api.dao.IAnimalDao;
import com.itAcademy.entities.Animal;
import com.itAcademy.entities.Cat;
import com.itAcademy.entities.Dog;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class AnimalDao implements IAnimalDao {

    private static final String PATH_TO_PROPERTIES = "dao-module\\src\\main\\resources\\config.properties";
    private Properties properties = new Properties();

    @Override
    public Animal loadFromMySQL(String name) throws SQLException, IOException {
        Animal animal = null;
        properties.load(new FileInputStream(PATH_TO_PROPERTIES));
        Connection connection = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("pass"));
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM animal.user");
        while (resultSet.next()) {
            if (resultSet.getString("name").equals(name)) {
                animal = (resultSet.getString("type").equals("cat")) ? new Cat() : new Dog();
                animal.setName(resultSet.getString("name"));
                animal.setType(resultSet.getString("type"));
                animal.setLevel(resultSet.getDouble("level"));
                animal.setExperience(resultSet.getDouble("experience"));
                animal.setHealth(resultSet.getDouble("health"));
                animal.setMana(resultSet.getDouble("mana"));
                animal.setDamage(resultSet.getDouble("damage"));
                animal.setDefence(resultSet.getDouble("defence"));
                animal.setStrength(resultSet.getDouble("strength"));
                animal.setAgility(resultSet.getDouble("agility"));
                animal.setIntelligence(resultSet.getDouble("intelligence"));
                animal.setCriticalStrikeMultiplier(resultSet.getDouble("criticalStrikeMultiplier"));
                animal.setCriticalChance(resultSet.getDouble("criticalChance"));
            }
        }
        statement.close();
        connection.close();
        return animal;
    }

    @Override
    public void saveToMySQL(Animal animal) throws SQLException, IOException {
        properties.load(new FileInputStream(PATH_TO_PROPERTIES));
        Connection connection = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("pass"));
        Statement statement = connection.createStatement();
        String sql = null;
        ResultSet resultSet = statement.executeQuery("SELECT * FROM animal.user");
        while (resultSet.next()) {
            if (resultSet.getString("name").equals(animal.getName())) {
                sql = String.format("UPDATE user SET `level` = %s, `experience` = %s, `health` = %s, `mana` = %s," +
                                " `damage` = %s, `defence` = %s, `strength` = %s, `agility` = %s, `intelligence` = %s," +
                                " `criticalStrikeMultiplier` = %s, `criticalChance` = %s WHERE (`name` = '%s');", animal.getLevel(),
                        animal.getExperience(), animal.getHealth(), animal.getMana(), animal.getDamage(), animal.getDefence(),
                        animal.getStrength(), animal.getAgility(), animal.getIntelligence(), animal.getCriticalStrikeMultiplier(),
                        animal.getCriticalChance(), animal.getName());
                break;
            } else {
                sql = String.format("INSERT INTO user (`name`, `type`, `level`, `experience`, `health`, `mana`, `damage`," +
                                " `defence`, `strength`, `agility`, `intelligence`, `criticalStrikeMultiplier`, `criticalChance`)" +
                                " VALUES ('%s', '%s', %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);",
                        animal.getName(), animal.getType(), animal.getLevel(), animal.getExperience(), animal.getHealth(),
                        animal.getMana(), animal.getDamage(), animal.getDefence(), animal.getStrength(), animal.getAgility(),
                        animal.getIntelligence(), animal.getCriticalStrikeMultiplier(), animal.getCriticalChance());
            }
        }
        statement.executeUpdate(sql);
        statement.close();
        connection.close();
    }

    public List<String> getAllEntities() throws SQLException, IOException {
        List<String> list = new ArrayList<>();
        properties.load(new FileInputStream(PATH_TO_PROPERTIES));
        Connection connection = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("pass"));
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM animal.user");
        while (resultSet.next()) {
            list.add(resultSet.getString("name"));
        }
        statement.close();
        connection.close();
        return list;
    }

}
