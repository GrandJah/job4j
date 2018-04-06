package ru.job4j.sell_car.json_action;

import org.json.JSONException;
import org.json.JSONObject;
import ru.job4j.sell_car.Hibernate;
import ru.job4j.sell_car.models.Announcement;
import ru.job4j.sell_car.models.Car;
import ru.job4j.sell_car.models.User;

/**
 * json action Create.
 */
public class Create extends JsonAction {
    @Override
    public boolean action(JSONObject json) {
        User user = (User) getSession().getAttribute("user");
        boolean success = false;
        if (user != null) {
            try {
                Car car = Car.fromJSON(json.getJSONObject("car"));
                try (Hibernate hibernate = new Hibernate()) {
                    hibernate.save(car);
                    hibernate.save(new Announcement(user, car));
                    success = true;
                }
            } catch (JSONException e) {
                getJSON().put("error", e.getMessage());
            }
        } else {
            getJSON().put("error", "no login user");
        }
        return success;
    }
}
