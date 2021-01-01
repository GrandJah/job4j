package ru.job4j.sell_car.controller.stub;

import java.util.HashMap;
import java.util.Map;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.models.ImageFile;

public class StabFileStorage implements FileStorage {
   private static final Map<String, ImageFile> FILES = new HashMap<>();
   private static int fileCount = 0;

   @Override
   public String createNewFileName() {
      String filepath = Integer.toString(fileCount++);
      ImageFile file = new ImageFile();
      file.setFilepath(filepath);
      FILES.put(filepath, file);
      return filepath;
   }

   @Override
   public ImageFile getFile(String filepath) {
      return FILES.get(filepath);
   }
}
