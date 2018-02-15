package ru.job4j.servlets;

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
public class EchoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter print = new PrintWriter(resp.getOutputStream());
        print.append("this work");
        print.flush();
    }
}
