package ru.job4j.sell_car.storage.hibernate;

import java.util.UUID;
import lombok.extern.log4j.Log4j;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.models.ImageFile;

@Log4j
public class HbmFileStorage extends HbmStorage implements FileStorage {

   @Override
   public String createNewFileName() {
      ImageFile file = new ImageFile();
      String path = UUID.randomUUID().toString().trim().toLowerCase();
      file.setFilepath(path);
      query(sf -> {
         sf.save(file);
         return null;
      });
      return path;
   }

   public ImageFile getFile(String filepath) {
      try {
         return query(sf -> sf.createQuery("from ImageFile where filepath = :path", ImageFile.class)
                              .setParameter("path", filepath).getSingleResult());
      } catch (Exception e) {
         log.error(e);
         return null;
      }
   }
}
