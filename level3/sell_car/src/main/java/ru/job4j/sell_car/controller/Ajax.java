package ru.job4j.sell_car.controller;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.job4j.sell_car.environment.Environment;
import ru.job4j.sell_car.environment.interfaces.AdvStorage;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.environment.interfaces.Shadows;
import ru.job4j.sell_car.environment.interfaces.UserStorage;
import ru.job4j.sell_car.models.Advert;
import ru.job4j.sell_car.models.Shadow;
import ru.job4j.sell_car.models.User;

public class Ajax extends HttpServlet {
   private static final Logger LOG = Logger.getLogger(Ajax.class);

   private final Environment env = Environment.inst(this);

   private final FileStorage fileStorage = env.get(FileStorage.class);

   private final AdvStorage advStorage = env.get(AdvStorage.class);
   private final UserStorage userStorage = env.get(UserStorage.class);
   private final Shadows shadows = env.get(Shadows.class);

   private final HashMap<String, Function<JSONObject, JSONObject>> actions = new HashMap<>();

   {
      actions.put("list_ad", this::get);
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

   private String execute(String jsonString) {
      JSONObject json = new JSONObject(jsonString);
      String action = json.getString("action");
      if (action == null || actions.get(action) == null) {
         throw new UnsupportedOperationException(action);
      }
      return actions.get(action).apply(json).toString();
   }

   private Map o(String key, Object value) {
      Map o = new HashMap();
      o.put(key, value);
      return o;
   }

   private List a(Object... values) {
      List a = new ArrayList();
      for (Object o : values) {
         a.add(o);
      }
      return a;
   }

   private JSONObject ok(Map data) {
      LOG.error(data.toString());
      JSONObject answer = new JSONObject(data);
      LOG.error(answer.toString());
      answer.put("success", true);
      return answer;
   }

   private JSONObject error(String error) {
      JSONObject answer = new JSONObject();
      answer.put("success", false);
      answer.put("error", error);
      return answer;
   }

   private String exToStr(Exception e) {
      return Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString)
                   .reduce(e.getMessage(),
                    (a, b) -> String.format("%s%s%s", a, System.lineSeparator(), b));
   }

   private JSONObject get(JSONObject json) {
      List<Advert> data = Optional.ofNullable(this.advStorage.getAll()).orElse(new ArrayList<>());
      return ok(o("data", data));
   }

   private JSONObject login(JSONObject json) {
      try {
         String name = json.getString("username");
         String pass = json.getString("pass");
         User user = userStorage.findByName(name);
         if (user == null || !shadows.checkPass(user, pass)) {
            return error("errorUser");
         }
         String token = shadows.createToken(user);
         return ok(o("token", token));
      } catch (JSONException e) {
         LOG.error(e);
         LOG.trace(exToStr(e));
         return error("errorUser");
      }
   }

   private JSONObject register(JSONObject json) {
      String name = json.getString("username");
      String pass = json.getString("pass");
      User user = userStorage.findByName(name);
      if (user != null) {
         return error("errorName");
      }
      user = User.of(name);
      Shadow shadow = Shadow.of(user, pass);
      shadows.save(shadow);
      user = userStorage.save(user);
      String token = shadows.createToken(user);
      return ok(o("token", token));
   }
}




