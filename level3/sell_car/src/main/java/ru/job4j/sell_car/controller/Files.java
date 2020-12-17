package ru.job4j.sell_car.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.job4j.sell_car.entity.ImageFile;
import ru.job4j.sell_car.storage.FileStorage;

public class Files extends HttpServlet {

   private DiskFileItemFactory factory;

   private FileStorage fileStorage;

   @Override
   public void init() throws ServletException {
      super.init();
      this.factory = new DiskFileItemFactory();
      this.fileStorage = new FileStorage();
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      JSONObject answer = new JSONObject();
      answer.put("success", false);
      if (ServletFileUpload.isMultipartContent(req)) {
         List<String> list = new ArrayList<>();
         ServletFileUpload upload = new ServletFileUpload(this.factory);
         try {
            for (FileItem item : upload.parseRequest(req)) {
               if (item.getContentType().startsWith("image")) {
                  String path = this.fileStorage
                   .addFile(item.getContentType(), item.get());
                  if (path != null) {
                     list.add(path);
                  }
               }
            }
         } catch (FileUploadException e) {
            e.printStackTrace();
         }
         if (list.size() != 0) {
            answer.put("success", true);
            answer.put("files", list);
         }
      }
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.getWriter().write(answer.toString());
   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      ImageFile image = this.fileStorage.getFile(req.getPathInfo().trim().toLowerCase());
      if (image != null) {
         resp.setContentType(image.getType());
         resp.getOutputStream().write(image.getContent());
      }
   }
}
