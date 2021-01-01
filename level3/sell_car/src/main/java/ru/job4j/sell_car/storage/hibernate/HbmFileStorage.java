package ru.job4j.sell_car.storage.hibernate;

import java.util.UUID;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.models.ImageFile;

public class HbmFileStorage extends HbmStorage implements FileStorage {

   public String addFile(String contentType, long contentSize) {
      ImageFile file = new ImageFile();
      String path = UUID.randomUUID().toString().trim().toLowerCase();
      file.setFilepath(path);
      file.setType(contentType);
      file.setSize((int) contentSize);
      query(sf -> {
         sf.save(file);
         return null;
      });
      return path;
   }

   public ImageFile getFile(String filepath) {
      return query(sf -> sf.createQuery("from ImageFile where filepath = :path", ImageFile.class)
                           .setParameter("path", filepath).getSingleResult());
   }
}
