package ru.job4j.pretty_interface.action;

import org.json.simple.JSONObject;
import ru.job4j.data_base.JSONConvert;
import ru.job4j.data_base.model.FullFieldUser;
import ru.job4j.data_base.model.User;
import ru.job4j.data_base.store.UserStore;

import javax.servlet.http.HttpSession;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.03.2018
 */
public class FillTable implements AjaxAction {
    @Override
    public JSONConvert action(JSONObject data, HttpSession session) {
        String tableName = "Таблица пользователей"; //!
        String tableId = "table_id"; //!
        String tableFill = fullTable(); //!
        return () -> String.format("{\"table\":\"%s\",\"table_id\":\"%s\",\"rows\":%s}",
                tableName, tableId, tableFill);
    }

    /**
     * @return json table.
     */
    private String fullTable() {
        StringBuilder builder = new StringBuilder("[");
        for (User user : new UserStore().getUsers()) {
            if (user != User.UNKNOWN) {
                builder.append(new FullFieldUser(user).toJSON());
            }
            builder.append(",");
        }
        int len = builder.length();
        return builder.delete(len - 1, len).append("]").toString();
    }
}
