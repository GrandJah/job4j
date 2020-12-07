package ru.job4j.todolist.controller;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.function.Function;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import ru.job4j.todolist.models.Item;
import ru.job4j.todolist.storage.Storage;
import ru.job4j.todolist.storage.StorageInstance;

/**
 * web hibernate application.
 */
public class Ajax extends HttpServlet {
   public Ajax() {
      this(StorageInstance.instance(Item.class)); 
   }

   private final Storage<Item> item;

   public Ajax(Storage<Item> item) {
      this.item = item;
   }

   /**
    * Action table.
    */
   private final HashMap<String, Function<JSONObject, JSONObject>> actions = new HashMap<>();

   {
      actions.put("get", this::get);
      actions.put("create", this::create);
      actions.put("done", this::done);
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      resp.setContentType("application/json");
      req.setCharacterEncoding("UTF-8");
      resp.setCharacterEncoding("UTF-8");
      resp.getWriter().write(execute(req.getReader().readLine()));
   }

   /**
    * @param jsonString jsonString
    * @return json string answer
    */
   public String execute(String jsonString) {
      JSONObject json = new JSONObject(jsonString);
      return actions
       .get(json.getString("action"))
       .apply(json).toString();
   }

   /**
    * Action "get".
    */
   private JSONObject get(JSONObject json) {
      JSONObject answer = new JSONObject();
      answer.put("data", this.item.getAll());
      return answer;
   }

   /**
    * Action create.
    */
   private JSONObject create(JSONObject json) {
      JSONObject answer = new JSONObject();
      Item item = new Item();
      item.setTask(json.getString("task"));
      item.setDescription(json.getString("description"));
      if (this.item.create(item) != null) {
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
   private JSONObject done(JSONObject json) {
      JSONObject answer = new JSONObject();
      Integer id = json.getInt("id");
      Item item = this.item.getById(id);
      item.setDone(json.getBoolean("done"));
      if (this.item.update(id, item)) {
         answer.put("success", true);
      } else {
         answer.put("success", false);
         answer.put("error", "X3");
      }
      return answer;
   }
}
