package ru.job4j.sell_car.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import ru.job4j.sell_car.environment.Environment;
import ru.job4j.sell_car.environment.interfaces.AdvStorage;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.environment.interfaces.Shadows;
import ru.job4j.sell_car.environment.interfaces.UserStorage;
import ru.job4j.sell_car.models.Advert;
import ru.job4j.sell_car.models.Car;
import ru.job4j.sell_car.models.ImageFile;
import ru.job4j.sell_car.models.Shadow;
import ru.job4j.sell_car.models.User;
import ru.job4j.sell_car.models.categories.CarType;
import ru.job4j.sell_car.models.categories.Categories;
import ru.job4j.sell_car.models.categories.Category;
import ru.job4j.sell_car.models.categories.CategoryValue;
import ru.job4j.sell_car.models.categories.FuelType;
import ru.job4j.sell_car.models.categories.GearType;
import ru.job4j.sell_car.models.categories.WheelDriveType;

@Log4j2
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
      resp.setHeader("Access-Control-Allow-Origin", "*");
//      resp.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Accept,X-Requested-With");
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

   private JSONObject get(JSONObject json) {
      Map<String, Object> filter = Optional.ofNullable((JSONObject) json.opt("filter"))
                                           .orElse(new JSONObject()).toMap();
      List<Advert> data = Optional.ofNullable(this.advStorage.getAllWithFilter(filter))
                                  .orElse(new ArrayList<>());
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
         ErrorHandler.logError(log, e);
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
         ErrorHandler.logError(log, e);
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
      } catch (Exception e) {
         ErrorHandler.logError(log, e);
         return error("create - unknown error");
      }
   }

   private String getVar(String variable, JSONObject json) {
      try {
         return json.getString(variable);
      } catch (Exception e) {
         return "";
      }
   }

   private <T extends Enum> T getCatValue(Class<? extends Category> type, JSONObject json) {
      log.debug("getCatValue");
      log.debug(type);
      String propName = Categories.getNameProperty((Class<Category>) type);
      log.debug(propName);
      String value = getVar(propName, json);
      log.debug(value);
      Category category = Categories.getCategory(propName);
      return (T) category.getValue(value);
   }

   private long parseAdvert(JSONObject advert, User user) {
      Advert adv = new Advert();
      adv.setUser(user);
      adv.setDescription(getVar("description", advert));
      try {
         adv.setPrice(Integer.parseInt(getVar("price", advert)));
      } catch (Exception e) {
         adv.setPrice(null);
      }
      Car car = new Car();
      adv.setCar(car);
      car.setModel(getVar("model", advert));

      try {
         JSONArray photo = advert.getJSONArray("photo");
         Set<ImageFile> images = new HashSet<>();
         for (Object o : photo) {
            images.add(this.fileStorage.getFile((String) o));
         }
         car.setImages(images);
      } catch (Exception e) {
         ErrorHandler.logError(log, e);
      }

      try {
         JSONObject categories = advert.getJSONObject("categories");
         car.setCarType(getCatValue(CarType.class, categories));
         car.setDrive(getCatValue(WheelDriveType.class, categories));
         car.setFuelType(getCatValue(FuelType.class, categories));
         car.setGearbox(getCatValue(GearType.class, categories));
      } catch (Exception e) {
         ErrorHandler.logError(log, e);
      }

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
         ErrorHandler.logError(log, e);
         return error("create - unknown error");
      }
   }

   private JSONObject getCategories(JSONObject json) {
      Collection<Category> categories = Categories.categories();
      Map map = new HashMap();
      for (Category category : categories) {
         Map cat = new HashMap();
         for (CategoryValue c : category.getSetValues()) {
            o(c.name(), c.text(), cat);
         }
         Map prop = o("text", category.text());
         o("values", cat, prop);
         o(category.propertyName(), prop, map);
      }
      return ok(o("categories", map));
   }
}
