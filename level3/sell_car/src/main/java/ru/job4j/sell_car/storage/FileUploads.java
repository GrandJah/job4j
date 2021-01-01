package ru.job4j.sell_car.storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import ru.job4j.sell_car.environment.Environment;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.environment.interfaces.Upload;
import ru.job4j.sell_car.models.ImageFile;

public class FileUploads implements Upload {

   private final Environment env = Environment.inst(this);

   private final FileStorage fileStorage = env.get(FileStorage.class);

   private final String rootStorage = env.option("file.storage");

   @Override
   public boolean getFile(File file) {
      ImageFile image = null;
      try {
         if (!"NoPhoto.png".equals(file.getPath())) {
            image = this.fileStorage.getFile(file.getPath().trim().toLowerCase());
         }
         Path path = null;
         if (image != null) {
            path = Paths.get(String.format("%s/%s", rootStorage, image.getFilepath()));
            if (!Files.exists(path)) {
               image = null;
            }
         }
         if (image == null) {
            path = Paths.get(
             URI.create(String.valueOf(getClass().getClassLoader().getResource("NoPhoto.png"))));
         }
         if (Files.exists(path)) {
            file.setContent(Files.readAllBytes(path));
            return true;
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return false;
   }

   @Override
   public boolean saveFile(File file) {
      byte[] content = file.getContent();
      String pathId = this.fileStorage.createNewFileName();
      if (pathId != null) {
         String path = String.format("%s/%s", rootStorage, pathId);
         try {
            Files.createFile(Paths.get(path));
            try (FileOutputStream out = new FileOutputStream(path)) {
               out.write(content);
               file.setPath(pathId);
               return true;
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      return false;
   }
}
