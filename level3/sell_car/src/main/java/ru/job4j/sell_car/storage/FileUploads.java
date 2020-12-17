package ru.job4j.sell_car.storage;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import ru.job4j.sell_car.environment.Environment;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.environment.interfaces.Upload;

public class FileUploads implements Upload {

   private final Environment env = Environment.inst(this);

   private final FileStorage fileStorage = env.get(FileStorage.class);

   private final DiskFileItemFactory factory = new DiskFileItemFactory();

   @Override
   public boolean isUploaded(HttpServletRequest req) {
      return ServletFileUpload.isMultipartContent(req);
   }

   @Override
   public void upload(HttpServletRequest req, List<String> list) {
      ServletFileUpload upload = new ServletFileUpload(this.factory);
      try {
         for (FileItem item : upload.parseRequest(req)) {
            if (item.getContentType().startsWith("image")) {
               String path = this.fileStorage.addFile(item.getContentType(), item.get());
               if (path != null) {
                  list.add(path);
               }
            }
         }
      } catch (FileUploadException e) {
         e.printStackTrace();
      }
   }


}
