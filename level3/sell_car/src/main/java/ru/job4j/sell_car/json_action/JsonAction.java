package ru.job4j.sell_car.json_action;

import org.json.JSONObject;

import javax.servlet.http.HttpSession;

/**
 * JSON action object.
 */
public abstract class JsonAction {
    /**
     * json answer object.
     */
    private JSONObject answer = new JSONObject();
    /**
     * success.
     */
    private boolean success = false;

    /**
     * @return json answer object.
     */
    protected JSONObject getJSON() {
        return this.answer;
    }

    /**
     * set success true.
     */
    protected void success() {
        this.success = true;
    }

    /** abstract method action.
     * @param json json
     * @param session session
     */
    public abstract void action(JSONObject json, HttpSession session);

    @Override
    public String toString() {
        this.answer.put("success", this.success);
        success = false;
        return this.answer.toString();
    }
}
