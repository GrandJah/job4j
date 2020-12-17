package ru.job4j.sell_car.controller;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.job4j.sell_car.models.ImageFile;
import ru.job4j.sell_car.environment.Environment;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.environment.interfaces.Upload;


public class Files extends HttpServlet {
   private final Environment env = Environment.inst(this);

   private final Upload upload = env.get(Upload.class);

   private final FileStorage fileStorage = env.get(FileStorage.class);

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      JSONObject answer = new JSONObject();
      answer.put("success", false);
      assert this.upload != null;
      if (this.upload.isUploaded(req)) {
         List<String> list = new ArrayList<>();
         this.upload.upload(req, list);
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
      assert this.fileStorage != null;
      ImageFile image = this.fileStorage.getFile(req.getPathInfo().trim().toLowerCase());
      if (image != null) {
         resp.setContentType(image.getType());
         resp.getOutputStream().write(image.getContent());
      }
   }
}
