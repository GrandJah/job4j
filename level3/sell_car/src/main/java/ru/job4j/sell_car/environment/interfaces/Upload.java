package ru.job4j.sell_car.environment.interfaces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public interface Upload {

   boolean saveFile(File file);

   boolean getFile(File file);

   @AllArgsConstructor
   @Getter
   @Setter
   class File {
      private String path;
      private byte[] content;
   }
}
