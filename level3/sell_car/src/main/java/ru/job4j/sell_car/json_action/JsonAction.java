package ru.job4j.sell_car.json_action;

import org.json.JSONObject;

import javax.servlet.http.HttpSession;

/**
 * JSON action object.
 */
public abstract class JsonAction {
    /**
     * session.
     */
    private HttpSession session;

    /**
     * @return session
     */
    protected HttpSession getSession() {
        return session;
    }

    /**
     * json answer object.
     */
    private JSONObject answer;

    /**
     * @return json answer object.
     */
    protected JSONObject getJSON() {
        return this.answer;
    }

    /** json method action.
     * @param json json
     * @param session session
     * @return JSON String
     */
    public String action(JSONObject json, HttpSession session) {
        this.answer = new JSONObject();
        this.session = session;
        this.answer.put("success", action(json));
        return this.answer.toString();
    }

    /**
     * @param json json
     * @return result action
     */
    protected abstract boolean action(JSONObject json);
}
