package com.itAcademy.api.service;

import com.itAcademy.entities.Animal;

public interface IAnimalService {

    Animal loadOrCreate();

    Animal loadFromMySQL(String name);

    Animal chooseRace();

    Animal createComputerAnimal(Animal animal);

    StringBuilder start();

    boolean exit();

}
