package ru.job4j.sell_car.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ru.job4j.sell_car.environment.Environment;
import ru.job4j.sell_car.environment.interfaces.Upload;

@Log4j
public class Files extends HttpServlet {
   private final Environment env = Environment.inst(this);

   private final Upload upload = env.get(Upload.class);

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      JSONObject answer = new JSONObject();
      resp.setHeader("Access-Control-Allow-Origin", "*");
      //      resp.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Accept,X-Requested-With");
      answer.put("success", false);
      if (ServletFileUpload.isMultipartContent(req)) {
         List<String> list = new ArrayList<>();
         ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
         try {
            for (FileItem item : upload.parseRequest(req)) {
               if (item.getContentType().startsWith("image")) {
                  Upload.File file = new Upload.File(null, item.getContentType(), item.get());
                  if (this.upload.saveFile(file)) {
                     list.add(file.getPath());
                  }
               }
            }
         } catch (FileUploadException e) {
            e.printStackTrace();
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            answer.put("success", false);
            answer.put("error", "error parse data!");
         }
         if (list.size() != 0) {
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            answer.put("success", true);
            answer.put("files", list);
            resp.getWriter().write(answer.toString());
         }
      }
   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      resp.setHeader("Access-Control-Allow-Origin", "*");
      //      resp.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Accept,X-Requested-With");
      Upload.File file = new Upload.File(req.getPathInfo().substring(1), null, null);
      if (this.upload.getFile(file)) {
         resp.setContentType(file.getType());
         resp.getOutputStream().write(file.getContent());
      } else {
         resp.sendError(404);
      }
   }
}
