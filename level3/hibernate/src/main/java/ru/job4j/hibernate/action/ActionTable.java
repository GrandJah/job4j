package ru.job4j.hibernate.action;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Servlet Action table.
 */
public class ActionTable {
    /**
     * Action table.
     */
    private static final HashMap<String, JSONAction> ACTIONS = new HashMap<>();
    static {
        ActionTable.ACTIONS.put("get", new Get());
        ActionTable.ACTIONS.put("create", new Create());
    }

    /**
     * @param jsonString jsonString
     * @return  json string answer
     */
    public String action(String jsonString) {
        JSONObject answer = new JSONObject();
        JSONObject json = new JSONObject(jsonString);
        ActionTable.ACTIONS.get(json.getString("action")).action(json, answer);
        return answer.toString();
    }


}
