package ru.job4j.pretty_interface;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 19.02.2018
 */
public class ControllerAJAX extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(String.format("%s/resources/html/index.html", req.getContextPath()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        RequestJSON requestJSON = new RequestJSON();
        String request = req.getReader().readLine();
        requestJSON.fromJSON(request);
        resp.getWriter().write(requestJSON.action(req.getSession()).toJSON());
    }
}
