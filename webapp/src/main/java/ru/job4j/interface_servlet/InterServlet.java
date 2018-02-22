package ru.job4j.interface_servlet;

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
 * @since 03.01.2018
 */
public class InterServlet extends HttpServlet {
    /**
     * User store.
     */
    private IUserStore users = new UserStore();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UI ui = new UI();
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
        String title = "Interface Servlets";
        String body = ui.getUserTable(this.users, "Создать", "Удалить", "Обновить", req.getContextPath());
        String html = ui.getHTML(title, body);
        printWriter.write(html);
        printWriter.flush();
    }
}
