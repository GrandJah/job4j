package ru.job4j.sell_car.storage.hibernate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import ru.job4j.sell_car.models.ImageFile;

public class HbmFileStorage extends HbmStorage {
   //todo move to config enviroment
   String ROOT_PATH = "image_sell_car";

   public String addFile(String contentType, byte[] content) {
      ImageFile file = new ImageFile();
      String path = UUID.randomUUID().toString().trim().toLowerCase();
      file.setFilepath(path);
      file.setType(contentType);
      file.setContent(content);
      try {
         FileOutputStream out = new FileOutputStream(ROOT_PATH + "/" + path);
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
      String path = ROOT_PATH + filepath;
      ImageFile image = query(
       sf -> sf.createQuery("from ImageFile where filepath = :path", ImageFile.class)
               .setParameter("path", ROOT_PATH + filepath).getSingleResult());
      if (image != null) {
         byte[] buf = new byte[image.getSize()];
         image.setContent(buf);
         int s = 0;
         try (FileInputStream in = new FileInputStream(filepath)) {
            for (int n = 0; s < image.getSize(); ) {
               n += in.read(buf, n, image.getSize() - n);
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      return image;
   }
}
