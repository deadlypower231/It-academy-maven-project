package com.itAcademy.api.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface IAnimalDao {

    Map<String, String> loadFromFile(String name) throws IOException;

    void saveToFile(StringBuilder stats, String name) throws FileNotFoundException;


}
