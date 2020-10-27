package com.itAcademy.api.dao;

import com.itAcademy.entities.Animal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IAnimalDao {

    void saveToMySQL(Animal animal) throws SQLException, ClassNotFoundException, IOException;

    Animal loadFromMySQL(String name) throws SQLException, IOException;

    List<String> getAllEntities() throws SQLException, IOException;


}
