package ru.job4j.hibernate.action;

import org.json.JSONObject;
import ru.job4j.hibernate.models.Item;

/**
 * Action create.
 */
public class Create implements JSONAction {
    @Override
    public void action(JSONObject json, JSONObject answer) {

        if (Item.create(json.getString("task"), json.getString("description")) != null) {
            answer.put("success", true);
        } else {
            answer.put("success", false);
            answer.put("error", "X3");
        }
    }
}
