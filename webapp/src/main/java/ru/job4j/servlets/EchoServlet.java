package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 28.12.2017
 */
public class EchoServlet extends HttpServlet {
    /** Logger. */
    private static final Logger LOG = Logger.getLogger("Logger");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter print = new PrintWriter(resp.getOutputStream());
        print.append("this work");
        print.flush();
    }
}
