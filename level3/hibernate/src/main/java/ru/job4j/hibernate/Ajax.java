package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONObject;
import ru.job4j.hibernate.models.Item;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * web hibernate application.
 */
public class Ajax extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        JSONObject answer = new JSONObject();
        JSONObject json = new JSONObject(req.getReader().readLine());
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();
                switch (json.getString("action")) {
                    case "get":
                        List list = session.createQuery("from Item").list();
                        answer.put("head", Item.getFields());
                        answer.put("data", list);
                        break;
                    case "create":
                        Item item = new Item();
                        item.setTask(json.getString("task"));
                        item.setDescription(json.getString("description"));
                        item.setCreated(System.currentTimeMillis());
                        item.setDone(false);
                        if (session.save(item) != null) {
                            answer.put("success", true);
                        } else {
                            answer.put("success", false);
                            answer.put("error", "X3");
                        }
                        break;
                    default:
                        break;
                }
                List<? super Object> list = new ArrayList<>();
                list.add("Long");

                session.getTransaction().commit();
            }
        }
        resp.getWriter().write(answer.toString());
    }
}
