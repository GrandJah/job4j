package ru.job4j.sell_car.environment.interfaces;

import ru.job4j.sell_car.models.ImageFile;

public interface FileStorage {

   String createNewFileName();

   ImageFile getFile(String filepath);
}
