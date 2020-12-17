package ru.job4j.sell_car.environment.interfaces;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface Upload {

   boolean isUploaded(HttpServletRequest req);

   void upload(HttpServletRequest req, List<String> list);
}
