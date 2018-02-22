package ru.job4j.pretty_interface.action;

import org.json.simple.JSONObject;
import ru.job4j.data_base.JSONConvert;
import ru.job4j.data_base.model.Location;

import javax.servlet.http.HttpSession;
import java.util.Iterator;

/**
 * Ajax action for get Location.
 */
public class AjaxLocation implements AjaxAction {
    /**
     * @param data    data object
     * @param session session
     * @return answer
     */
    @Override
    public JSONConvert action(JSONObject data, HttpSession session) {
        return () -> {
            StringBuilder builder = new StringBuilder("{");
            Iterator<String> it = null;
            switch (String.valueOf(data.get("type"))) {
                case "country":
                    builder.append("\"countries\":[");
                    it = Location.getCountries();
                    break;
                case "city":
                    String country = (String) data.get("country");
                    if (country != null) {
                        builder.append(String.format("\"country\":\"%s\",\"cities\":[", country));
                        it = Location.getCities(country);
                        break;
                    }
                default:
                    builder.append("\"data\":[");
            }
            if (it != null) {
                while (it.hasNext()) {
                    builder.append(String.format("\"%s\"", it.next()))
                            .append(it.hasNext() ? "," : "");
                }
            }
            return builder.append("]}").toString();
        };
    }
}
