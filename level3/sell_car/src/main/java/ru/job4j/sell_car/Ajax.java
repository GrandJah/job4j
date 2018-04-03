package ru.job4j.sell_car;

import org.json.JSONObject;
import ru.job4j.sell_car.models.Announcement;
import ru.job4j.sell_car.models.Car;
import ru.job4j.sell_car.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Ajax servlet.
 */
public class Ajax extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        JSONObject answer = new JSONObject();
        answer.put("success", false);
        JSONObject query = new JSONObject(req.getReader().readLine());
        try (Hibernate hibernate = new Hibernate()) {
            User user = (User) req.getSession().getAttribute("user");
            switch (query.getString("action")) {
                case "login":
                    user = User.findUser(query.getString("login"), query.getString("password"));
                    if (user != null) {
                        req.getSession().setAttribute("user", user);
                        answer.put("success", true);
                    }
                    break;
                case "registration":
                    answer.put("success", User.newUser(query.getString("login"), query.getString("password")));
                    break;
                case "getUser":
                    if (user != null) {
                        answer.put("success", true);
                        answer.put("user", user.getLogin());
                    }
                    break;
                case "create":
                    if (user != null) {
                        Car car = Car.fromJSON(query.get("car"));
                        if (car != null) {
                            hibernate.save(car);
                            Announcement announcement = new Announcement();
                            announcement.setUser(user);
                            announcement.setCar(car);
                            announcement.setSell(false);
                            hibernate.save(announcement);
                            answer.put("success", true);
                        } else {
                            answer.put("error", "error format car data");
                        }
                    } else {
                        answer.put("error", "no login user");
                    }
                    break;
                case "get":
                default:
                    answer.put("head", new String[]{"", ""});
                    answer.put("data", hibernate.createQuery("from Announcement", Announcement.class).list());
            }
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(answer.toString());
    }
}
