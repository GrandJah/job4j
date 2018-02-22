package ru.job4j.crud_server;

import ru.job4j.store.IUserStore;
import ru.job4j.store.UserStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 28.12.2017
 */
public class UserServlet extends HttpServlet {
    /**
     * User store.
     */
    private IUserStore users = new UserStore();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter print = new PrintWriter(resp.getOutputStream());
        String login = req.getParameter("login");
        print.append(this.users.getUser(login).toString());
        print.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (this.users.addUser(req.getParameter("login"),
                req.getParameter("name"), req.getParameter("email"))) {
            resp.sendError(201);
        } else {
            resp.sendError(501);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.users.deleteUser(req.getParameter("login"));
        resp.sendError(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.users.updateUser(req.getParameter("login"), req.getParameter("name"), req.getParameter("email"));
        resp.sendError(200);
    }
}
