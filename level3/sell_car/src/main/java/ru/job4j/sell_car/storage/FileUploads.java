package ru.job4j.sell_car.storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
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
      ImageFile image = this.fileStorage.getFile(file.getPath().trim().toLowerCase());
      if (image != null) {
         byte[] buf = new byte[image.getSize()];
         String path = String.format("%s/%s", rootStorage, image.getFilepath());
         try (FileInputStream in = new FileInputStream(path)) {
            int n = 0;
            do {
               n += in.read(buf, n, image.getSize() - n);
            } while (n < image.getSize());
            file.setContent(buf);
            return true;
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      return false;
   }

   @Override
   public boolean saveFile(File file) {
      byte[] content = file.getContent();
      String pathId = this.fileStorage.addFile(file.getType(), content.length);
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
