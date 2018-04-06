package ru.job4j.sell_car;

import org.json.JSONObject;
import ru.job4j.sell_car.json_action.Create;
import ru.job4j.sell_car.json_action.Get;
import ru.job4j.sell_car.json_action.JsonAction;
import ru.job4j.sell_car.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Ajax servlet.
 */
public class Ajax extends HttpServlet {
    /**
     * JSON actions.
     */
    private static final HashMap<String, JsonAction> ACTION = new HashMap<>();

    static {
        Ajax.ACTION.put(null, new Get());
        Ajax.ACTION.put("get", new Get());
        Ajax.ACTION.put("create", new Create());
        Ajax.ACTION.put("registration", new JsonAction() {
            @Override
            public boolean action(JSONObject json) {
                boolean success = false;
                if (User.newUser(json.getString("login"), json.getString("password"))) {
                    success = true;
                }
                return success;
            }
        });
        Ajax.ACTION.put("getUser", new JsonAction() {
            @Override
            public boolean action(JSONObject json) {
                User user = (User) getSession().getAttribute("user");
                boolean success = false;
                if (user != null) {
                    getJSON().put("user", user.getLogin());
                    success = true;
                }
                return success;
            }
        });
        Ajax.ACTION.put("login", new JsonAction() {
            @Override
            public boolean action(JSONObject json) {
                User user = User.findUser(json.getString("login"));
                boolean success = false;
                if (user != null && user.checkPass(json.getString("password"))) {
                    getSession().setAttribute("user", user);
                    success = true;
                }
                return success;
            }
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JSONObject query = new JSONObject(req.getReader().readLine());
        resp.getWriter().write(Ajax.ACTION.get(query.getString("action")).action(query, req.getSession()));
    }
}
