package ru.job4j.pretty_interface;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.job4j.data_base.JSONConvert;
import ru.job4j.pretty_interface.action.AjaxAction;
import ru.job4j.pretty_interface.action.CreateUser;
import ru.job4j.pretty_interface.action.FillTable;
import ru.job4j.pretty_interface.action.UseUser;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.Map;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 08.03.2018
 */
public class RequestJSON implements JSONConvert {
    /**
     * Action Table.
     */
    private static final Hashtable<String, AjaxAction> TABLE = new Hashtable<String, AjaxAction>() { {
        put("table", new FillTable());
        put("login", new UseUser());
        put("createUser", new CreateUser());
    } };

    /**
     * action.
     */
    private AjaxAction action;

    /**
     * data for action.
     */
    private JSONObject data = new JSONObject();

    @Override
    public String toJSON() {
        String nameAction = "error";
        for (Map.Entry<String, AjaxAction> entry : TABLE.entrySet()) {
            if (entry.getValue() == this.action) {
                nameAction = entry.getKey();
            }
        }
        return String.format("{\"action\":\"%s\",\"data\":%s}", nameAction, this.data.toJSONString());
    }

    @Override
    public void fromJSON(String string) {
        JSONObject obj;
        try {
            obj = (JSONObject) new JSONParser().parse(string);
            String action = (String) obj.get("action");
            this.action = action == null ? null : TABLE.get(action);
            this.data = (JSONObject) obj.get("data");
        } catch (ParseException e) {
            this.action = null;
        }
        if (this.action == null || this.data == null) {
            this.action = (data, session) -> JSONConvert.EMPTY;
        }
    }

    /**
     * @return response
     */
    JSONConvert action(HttpSession session) {
        return this.action.action(this.data, session);
    }
}
