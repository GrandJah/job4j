package ru.job4j.todolist.controller;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import ru.job4j.todolist.models.Item;
import ru.job4j.todolist.models.User;
import ru.job4j.todolist.storage.Storage;
import ru.job4j.todolist.storage.StorageInstance;

/**
 * web hibernate application.
 */
public class Ajax extends HttpServlet {
   public Ajax() {
      this(
       StorageInstance.instance(Item.class),
       StorageInstance.instance(User.class)
      );
   }

   private final Storage<Item> items;
   private final Storage<User> users;

   public Ajax(Storage<Item> items, Storage<User> users) {
      this.items = items;
      this.users = users;
   }

   /**
    * Action table.
    */
   private final HashMap<String, Function<JSONObject, JSONObject>> actions = new HashMap<>();

   {
      actions.put("get", this::get);
      actions.put("create", this::create);
      actions.put("done", this::done);
      actions.put("login", this::login);
      actions.put("register", this::register);
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
   private String execute(String jsonString) {
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
      List<Item> data = this.items.getAll().stream().peek((
       item -> {
          User user = item.getAuthor();
          if (user != null) {
             user.setToken(null);
             user.setPassword(null);
             user.setId(null);
          }
       })).collect(Collectors.toList());
      answer.put("data", data);
      return answer;
   }

   /**
    * Action create.
    */
   private JSONObject create(JSONObject json) {
      JSONObject answer = new JSONObject();
      if (json.has("token")) {
         List<User> userList = users.findByField("token", json.getString("token"));
         if (!userList.isEmpty()) {
            User user = userList.get(0);
            if (user != null) {
               Item item = new Item();
               item.setTask(json.getString("task"));
               item.setDescription(json.getString("description"));
               item.setAuthor(user);
               if (this.items.create(item) != null) {
                  answer.put("success", true);
                  return answer;
               }
               answer.put("success", false);
               answer.put("error", "X3");
               return answer;
            }
         }
      }
      answer.put("success", false);
      answer.put("error", "errorLogin");
      return answer;
   }

   /**
    * Action Done task.
    */
   private JSONObject done(JSONObject json) {
      JSONObject answer = new JSONObject();
      Integer id = json.getInt("id");
      Item item = this.items.getById(id);
      item.setDone(json.getBoolean("done"));
      if (this.items.update(id, item)) {
         answer.put("success", true);
      } else {
         answer.put("success", false);
         answer.put("error", "X3");
      }
      return answer;
   }

   private JSONObject login(JSONObject json) {
      JSONObject answer = new JSONObject();
      String name = json.getString("username");
      String pass = json.getString("pass");
      List<User> userList = users.findByField("name", name);
      if (!userList.isEmpty()) {
         User user = userList.get(0);
         if (Objects.equals(pass, user.getPassword())) {
            answer.put("success", true);
            answer.put("token", createToken(user));
            return answer;
         }
      }
      answer.put("success", false);
      answer.put("error", "errorUser");
      return answer;
   }

   private JSONObject register(JSONObject json) {
      JSONObject answer = new JSONObject();
      String name = json.getString("username");
      String pass = json.getString("pass");

      if (users.findByField("name", name).isEmpty()) {
         User user = new User();
         user.setName(name);
         user.setPassword(pass);
         user = users.create(user);
         if (user != null) {
            answer.put("success", true);
            answer.put("token", createToken(user));
         } else {
            answer.put("success", false);
            answer.put("error", "Error user registration");
         }
      } else {
         answer.put("success", false);
         answer.put("error", "errorName");
      }
      return answer;
   }

   private String createToken(User user) {
      String token = UUID.randomUUID().toString();
      user.setToken(token);
      users.update(user.getId(), user);
      return token;
   }
}
