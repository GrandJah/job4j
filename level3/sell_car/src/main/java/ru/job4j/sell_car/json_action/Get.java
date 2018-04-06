package ru.job4j.sell_car.json_action;

import org.json.JSONObject;
import ru.job4j.sell_car.Hibernate;
import ru.job4j.sell_car.models.Announcement;

/**
 * Get action.
 */
public class Get extends JsonAction {

    @Override
    public boolean action(JSONObject json) {
        try (Hibernate hibernate = new Hibernate()) {
            getJSON().put("head", new String[]{"", ""});
            getJSON().put("data", Announcement.toJSON(hibernate.getAll("from Announcement", Announcement.class)));
        }
        return true;
    }
}
