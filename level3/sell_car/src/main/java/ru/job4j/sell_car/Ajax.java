package ru.job4j.sell_car;

import org.json.JSONObject;
import ru.job4j.sell_car.json_action.Create;
import ru.job4j.sell_car.json_action.Get;
import ru.job4j.sell_car.json_action.JsonAction;
import ru.job4j.sell_car.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            public void action(JSONObject json, HttpSession session) {
                if (User.newUser(json.getString("login"), json.getString("password"))) {
                    success();
                }
            }
        });
        Ajax.ACTION.put("getUser", new JsonAction() {
            @Override
            public void action(JSONObject json, HttpSession session) {
                User user = (User) session.getAttribute("user");
                if (user != null) {
                    getJSON().put("user", user.getLogin());
                    success();
                }
            }
        });
        Ajax.ACTION.put("login", new JsonAction() {
            @Override
            public void action(JSONObject json, HttpSession session) {
                User user = User.findUser(json.getString("login"));
                if (user != null && user.checkPass(json.getString("password"))) {
                    session.setAttribute("user", user);
                    success();
                }
            }
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        JSONObject query = new JSONObject(req.getReader().readLine());
        HttpSession session = req.getSession();
        JsonAction answer = Ajax.ACTION.get(query.getString("action"));
        answer.action(query, session);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(answer.toString());
    }
}
