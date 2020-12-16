package ru.job4j.sell_car.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import ru.job4j.sell_car.entity.ImageFile;

//todo добавить настройку пути корневого хранилища
public class FileStorage extends HbmStorage {
   //todo реализовать сохранение в БД
   public String addFile(String name, String contentType, long size, byte[] content) {
      String path = UUID.randomUUID().toString();
      try {
         FileOutputStream out = new FileOutputStream(path);
         out.write(content);
         out.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return path;
   }

   //todo добавить проверку полноты загрузки файла и установки типа файла
   public ImageFile getFile(String filepath) {
      ImageFile image = new ImageFile();
      image.setFilepath(filepath);
      image.setType("image/png");
      byte[] buf = new byte[(int) new File(filepath).length()];
      image.setContent(buf);
      try (FileInputStream in = new FileInputStream(filepath)) {
         in.read(buf);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return image;
   }
}
