package ru.job4j.sell_car.json_action;

import org.json.JSONObject;
import ru.job4j.sell_car.Hibernate;
import ru.job4j.sell_car.models.Announcement;
import ru.job4j.sell_car.models.Car;
import ru.job4j.sell_car.models.User;

import javax.servlet.http.HttpSession;

/**
 * json action Create.
 */
public class Create extends JsonAction {
    @Override
    public void action(JSONObject json, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Car car = Car.fromJSON(json.get("car"));
            if (car != null) {
                try (Hibernate hibernate = new Hibernate()) {
                    hibernate.save(car);
                    hibernate.save(new Announcement(user, car));
                    success();
                }
            } else {
                getJSON().put("error", "error format car data");
            }
        } else {
            getJSON().put("error", "no login user");
        }
    }
}
