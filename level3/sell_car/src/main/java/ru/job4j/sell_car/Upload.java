package ru.job4j.sell_car;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Upload file servlet.
 */
public class Upload extends HttpServlet {
    /**
     * Factory file upload.
     */
    private DiskFileItemFactory factory;

    @Override
    public void init() throws ServletException {
        super.init();
        this.factory = new DiskFileItemFactory();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject answer = new JSONObject();
        answer.put("success", false);
        if (ServletFileUpload.isMultipartContent(req)) {
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List items = upload.parseRequest(req);
                for (Object item1 : items) {
                    FileItem item = (FileItem) item1;
                    String name = item.getName();
                    long size = item.getSize();
                    String cct = item.getContentType();
                    System.out.println(name + " - " + size + " - " + cct);
                    System.out.println(item.get().length); //byte[]
                }
                answer.put("success", true);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(answer.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("image");
        resp.getWriter().write("image");
    }
}
