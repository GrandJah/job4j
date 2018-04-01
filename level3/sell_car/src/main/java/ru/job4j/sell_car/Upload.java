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
import java.util.Iterator;
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
        resp.setContentType("application/json");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        JSONObject answer = new JSONObject();
        if (ServletFileUpload.isMultipartContent(req)) {
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List items = upload.parseRequest(req);
                Iterator it = items.iterator();
                while (it.hasNext()) {
                    FileItem item = (FileItem) it.next();
                    String name = item.getName();
                    long size = item.getSize();
                    String cct = item.getContentType();
                    System.out.println(name + " - " + size + " - " + cct);
                    System.out.println(item.get().length); //byte[]
                }
                answer.put("success", true);
            } catch (FileUploadException e) {
                e.printStackTrace();
                answer.put("success", false);
            }
        } else {
            answer.put("success", false);
        }
        resp.getWriter().write(answer.toString());
    }
}
