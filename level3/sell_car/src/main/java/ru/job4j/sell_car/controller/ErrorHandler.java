package ru.job4j.sell_car.controller;
// Import required java libraries

import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ErrorHandler extends HttpServlet {
  private static String exToStr(Throwable e) {
    return Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).reduce(e.getMessage(),
      (a, b) -> String.format("%s%s%s", a, System.lineSeparator(), b));
  }

  public static void logError(Logger log, Throwable e) {
    log.error(e);
    log.trace(exToStr(e));
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    doGet(request, response);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
    String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");

    logError(log, throwable);

    if (throwable instanceof UnsupportedOperationException && "Ajax".equals(servletName)) {
      response.sendError(406);
    }

    log.info("{}, {}, {}, {}", throwable, statusCode, servletName, requestUri);


    response.setContentType("text/html");

    String title = "Error/Exception Information";
    String header = String.format("<title>%s</title>", title);
    StringBuilder body = new StringBuilder();
    if (throwable == null && statusCode == null) {
      body.append("<h2>Error information is missing</h2>");
      body.append("Please return to the <a href=\"/\">Home Page</a>.");
    } else if (statusCode != null) {
      body.append("The status code : ");
      body.append(statusCode);
    } else {
      body.append("<h2>Error information</h2>");
      body.append("Servlet Name : ");
      body.append(servletName);

      body.append("</br></br>");
      body.append("Exception Type : ");
      body.append(throwable.getClass().getName());

      body.append("</br></br>");
      body.append("The request URI: ");
      body.append(requestUri);

      body.append("</br></br>");
      body.append("The exception message: ");
      body.append(throwable.getMessage());
    }
    response.getWriter().write(
      String.format("<!doctype html><html><head>%s</head><body>%s</body></html>", header, body));
  }
}
