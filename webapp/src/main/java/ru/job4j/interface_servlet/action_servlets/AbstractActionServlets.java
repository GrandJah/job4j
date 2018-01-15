package ru.job4j.interface_servlet.action_servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 15.01.2018
 */
public abstract class AbstractActionServlets extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        doAction(req.getParameter("login"), req.getParameter("name"), req.getParameter("email"));
        resp.sendRedirect(req.getHeader("Referer"));
    }

    /** Action.
     * @param login login
     * @param name name
     * @param email email
     */
    abstract void doAction(String login, String name, String email);
}
