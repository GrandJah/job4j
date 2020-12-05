package ru.job4j.todolist.controller;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Properties;
import java.util.function.Function;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import ru.job4j.todolist.models.Item;
import ru.job4j.todolist.storage.Storage;

/**
 * web hibernate application.
 */
public class Ajax extends HttpServlet {
   private static Storage<Item> STORAGE;
   {
      Properties properties = new Properties();
      ClassLoader loader = getClass().getClassLoader();
      try {
         properties.load(loader.getResourceAsStream("application.properties"));
         STORAGE = (Storage<Item>) loader.loadClass(properties.getProperty("item_storage")).newInstance();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      resp.setContentType("application/json");
      req.setCharacterEncoding("UTF-8");
      resp.setCharacterEncoding("UTF-8");
      resp.getWriter().write(execute(req.getReader().readLine()));
   }

   /**
    * Action table.
    */
   private static final HashMap<String,
    Function<JSONObject, JSONObject>> ACTIONS = new HashMap<>();

   static {
      ACTIONS.put("get", Ajax::get);
      ACTIONS.put("create", Ajax::create);
      ACTIONS.put("done", Ajax::done);
   }

   /**
    * @param jsonString jsonString
    * @return json string answer
    */
   public static String execute(String jsonString) {
      JSONObject json = new JSONObject(jsonString);
      return ACTIONS
       .get(json.getString("action"))
       .apply(json).toString();
   }

   /**
    * Action "get".
    */
   private static JSONObject get(JSONObject json) {
      JSONObject answer = new JSONObject();
      answer.put("data", STORAGE.getAll());
      return answer;
   }

   /**
    * Action create.
    */
   private static JSONObject create(JSONObject json) {
      JSONObject answer = new JSONObject();
      Item item = new Item();
      item.setTask(json.getString("task"));
      item.setDescription(json.getString("description"));
      if (STORAGE.create(item) != null) {
         answer.put("success", true);
      } else {
         answer.put("success", false);
         answer.put("error", "X3");
      }
      return answer;
   }

   /**
    * Action Done task.
    */
   private static JSONObject done(JSONObject json) {
      JSONObject answer = new JSONObject();
      Integer id = json.getInt("id");
      Item item = STORAGE.getById(id);
      item.setDone(json.getBoolean("done"));
      if (STORAGE.update(id, item)) {
         answer.put("success", true);
      } else {
         answer.put("success", false);
         answer.put("error", "X3");
      }
      return answer;
   }
}
