package ru.job4j.authentication;

import ru.job4j.user_store.User;
import ru.job4j.user_store.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 15.01.2018
 */
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (req.getAttribute("error") != null) {
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        } else if (session.getAttribute("user") == null) {
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = UserStore.getUserStore().getUser(req.getParameter("login"));
        if (user == User.UNKNOWN) {
            req.setAttribute("error", "Ошибочка!");
            doGet(req, resp);
        } else if (session.getAttribute("user") == null) {
            session.setAttribute("user", user);
        }
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}

