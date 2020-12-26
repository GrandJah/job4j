package ru.job4j.sell_car.controller;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ru.job4j.sell_car.environment.Environment;
import ru.job4j.sell_car.environment.interfaces.AdvStorage;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.environment.interfaces.Shadows;
import ru.job4j.sell_car.environment.interfaces.UserStorage;
import ru.job4j.sell_car.models.Advert;
import ru.job4j.sell_car.models.Shadow;
import ru.job4j.sell_car.models.User;
import ru.job4j.sell_car.models.categories.CarType;
import ru.job4j.sell_car.models.categories.FuelType;
import ru.job4j.sell_car.models.categories.GearType;
import ru.job4j.sell_car.models.categories.WheelDriveType;

@Log4j
public class Ajax extends HttpServlet {

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
      actions.put("create", this::createAdv);
      actions.put("changeStatus", this::changeStatus);
      actions.put("getCategories", this::getCategories);
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

   private Map o(String key, Object value, Map... maps) {
      Map o = (maps.length == 0 || maps[0] == null) ? new HashMap() : maps[0];
      o.put(key, value);
      return o;
   }

   private List<Object> a(Object... values) {
      return new ArrayList<>(Arrays.asList(values));
   }

   private JSONObject ok(Map data) {
      log.trace(data.toString());
      JSONObject answer = new JSONObject(data);
      log.debug(answer.toString());
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
                   .reduce(e.getMessage(), (a, b) -> String.format("%s%s%s", a, System.lineSeparator(), b));
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
         log.error(e);
         log.trace(exToStr(e));
         return error("errorUser");
      }
   }

   private JSONObject register(JSONObject json) {
      try {
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
      } catch (JSONException e) {
         log.error(e);
         log.trace(exToStr(e));
         return error("unknown error");
      }
   }

   private JSONObject createAdv(JSONObject json) {
      try {
         String token = json.getString("token");
         User user = shadows.findByToken(token);
         if (user == null) {
            return error("not authentication");
         }
         return ok(o("id_adv", parseAdvert(json.getJSONObject("advert"), user)));
      } catch (JSONException e) {
         log.error(e);
         log.trace(exToStr(e));
         return error("create - unknown error");
      }
   }

   private long parseAdvert(JSONObject advert, User user) { //todo parse adverts
      Advert adv = new Advert();
      adv.setUser(user);
      return advStorage.save(adv);
   }

   private JSONObject changeStatus(JSONObject json) {
      try {
         Integer id = json.getInt("id");
         Advert advert = advStorage.getById(id);
         if (advert == null) {
            return error("invalid ID");
         }
         String token = json.getString("token");
         if (!Objects.equals(shadows.findByToken(token), advert.getUser())) {
            return error("not authentication");
         }
         boolean status = advert.getStatus();
         advert.setStatus(!status);
         advStorage.save(advert);
         return ok(o("status", !status));
      } catch (JSONException e) {
         log.error(e);
         log.trace(exToStr(e));
         return error("create - unknown error");
      }
   }

   private JSONObject getCategories(JSONObject json) {
      Map categories = o("car_type", CarType.values());
      o("fuel_type", FuelType.values(), categories);
      o("gear_type", GearType.values(), categories);
      o("wd_type", WheelDriveType.values(), categories);


      //      System.out.println(pack);
      //      for (String p : categories){
      //         log.debug(p);
      //      }


      //      System.out.println();
      //      System.out.println(pack);

      return ok(o("categories", categories));
   }
}
