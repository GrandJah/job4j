package ru.job4j.sell_car.controller;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.job4j.sell_car.environment.Environment;
import ru.job4j.sell_car.environment.interfaces.AdvStorage;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.models.Advert;

public class Ajax extends HttpServlet {

   private final Environment env = Environment.inst(this);

   private final FileStorage fileStorage = env.get(FileStorage.class);

   private final AdvStorage advStorage = env.get(AdvStorage.class);

   private final HashMap<String, Function<JSONObject, JSONObject>> actions = new HashMap<>();

   {
      actions.put("list_ad", this::get);
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
      return actions

       .get(json.getString("action"))
       .apply(json).toString();
   }

   private JSONObject get(JSONObject json) {
      JSONObject answer = new JSONObject();
      List<Advert> data = this.advStorage.getAll();
      answer.put("data", data);
      return answer;
   }
}
