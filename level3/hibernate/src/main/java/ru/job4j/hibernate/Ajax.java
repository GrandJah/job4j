package ru.job4j.hibernate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * web hibernate application.
 */
public class Ajax extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        //action:get - table

        resp.getWriter().write("{\"head\":[\"id\", \"item\", \"description\",\"create\",\"done\"],"
                + "\"data\": ["
                + "[\"3\",\"fg\",\"sdccsdsd dsdcd sdcds\", \"056561\", true],"
                + "[\"5\",\"gf\",\"dcsgerb\", \"065315151\", false],"
                + "[\"19\",\"csd\", \"wev wr rw erwver\", \"065351\", true]"
                + "]}");

        //action:create
        //task: - text
        //description: -text
        //return success: boolean
        //error:
    }
}
