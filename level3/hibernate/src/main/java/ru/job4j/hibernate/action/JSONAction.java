package ru.job4j.hibernate.action;

import org.json.JSONObject;

/**
 * Json Action interface.
 */
public interface JSONAction {
    /**
     * @param json json
     * @param answer answer
     */
    void action(JSONObject json, JSONObject answer);
}
