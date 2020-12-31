package ru.job4j.sell_car.storage.hibernate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.models.ImageFile;

public class HbmFileStorage extends HbmStorage implements FileStorage {
   //todo move to config enviroment
   private static final String ROOT_PATH = "image_sell_car";

   public String addFile(String contentType, byte[] content) {
      ImageFile file = new ImageFile();
      String path = String
       .format("%s/%s", ROOT_PATH, UUID.randomUUID().toString().trim().toLowerCase());
      file.setFilepath(path);
      file.setType(contentType);
      file.setSize(content.length);
      try {
         FileOutputStream out = new FileOutputStream(path);
         out.write(content);
         out.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
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
