package ru.job4j.sell_car.environment.interfaces;

import ru.job4j.sell_car.models.ImageFile;

public interface FileStorage {

   String addFile(String contentType, long contentSize);

   ImageFile getFile(String filepath);
}
