package ru.job4j.sell_car.json_action;

import org.json.JSONObject;
import ru.job4j.sell_car.Hibernate;
import ru.job4j.sell_car.models.Announcement;

import javax.servlet.http.HttpSession;

/**
 * Get action.
 */
public class Get extends JsonAction {

    @Override
    public void action(JSONObject json, HttpSession session) {
        try (Hibernate hibernate = new Hibernate()) {
            getJSON().put("head", new String[]{"", ""});
            getJSON().put("data", hibernate.createQuery("from Announcement", Announcement.class).list());
            success();
        }
    }
}
