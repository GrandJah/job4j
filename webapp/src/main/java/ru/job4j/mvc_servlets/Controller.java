package ru.job4j.mvc_servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import ru.job4j.user_store.Role;
import ru.job4j.user_store.User;
import ru.job4j.user_store.UserStore;
/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 15.01.2018
 */
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User useUser = (User) req.getSession().getAttribute("user");
        Map<User, Role> users = new TreeMap<>();

        for (User user: UserStore.getStore().getUsers()) {
            users.put(user, Role.useRole(user.getLogin()));
        }

        req.setAttribute("users", users);
        req.setAttribute("useUser", useUser);

        switch (Role.useRole(useUser.getLogin())) {
            case Administrator:
                req.getRequestDispatcher("/WEB-INF/views/forAdmins.jsp").forward(req, resp);
                break;
            default:
                req.getRequestDispatcher("/WEB-INF/views/forDefaultUsers.jsp").forward(req, resp);
        }
    }
}
