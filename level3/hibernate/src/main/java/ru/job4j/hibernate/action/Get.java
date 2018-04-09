package ru.job4j.hibernate.action;

import org.json.JSONObject;
import ru.job4j.hibernate.models.Item;

/**
 * Action "get".
 */
public class Get implements JSONAction {
    @Override
    public void action(JSONObject json, JSONObject answer) {
        answer.put("head", Item.getFields());
        answer.put("data", Item.getAll());
    }
}
