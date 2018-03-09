package ru.job4j.mvc_servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import ru.job4j.data_base.store.IRoleStore;
import ru.job4j.data_base.store.IUserStore;
import ru.job4j.data_base.store.RoleStore;
import ru.job4j.data_base.store.UserStore;
import ru.job4j.data_base.model.Role;
import ru.job4j.data_base.model.User;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 15.01.2018
 */
public class Controller extends HttpServlet {
    /**
     * User data_base.
     */
    private IRoleStore roles = new RoleStore();

    /**
     * Role data_base.
     */
    private IUserStore users = new UserStore();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User useUser = (User) req.getSession().getAttribute("user");
        Map<User, Role> users = new TreeMap<>();

        for (User user: this.users.getUsers()) {
            users.put(user, this.roles.getUserRole(user.getLogin()));
        }

        req.setAttribute("users", users);
        req.setAttribute("useUser", useUser);
        req.setAttribute("roles", roles.getRoles());

        if (this.roles.getUserRole(useUser.getLogin()) ==  Role.ADMINISTRATOR) {
            req.getRequestDispatcher("/WEB-INF/views/forAdmins.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/forDefaultUsers.jsp").forward(req, resp);
        }
    }
}
