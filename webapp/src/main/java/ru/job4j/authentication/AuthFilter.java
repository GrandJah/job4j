package ru.job4j.authentication;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 15.01.2018
 */
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String[] path = request.getServletPath().split("/");
        if (path.length < 2) {
            path = new String[]{"", ""};
        }
        if (!path[1].equals("jsp") && !path[1].equals("echo") && !path[1].equals("user")) { // delete
            if (path[1].equals("resources")) { //resource path
                if (path.length < 3 || path[path.length - 1].equals("")) {
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, servletResponse);
                    return;
                }
            } else {
                HttpSession session = request.getSession();
                if (!path[1].equals("login") && session.getAttribute("user") == null) {
                    String urlRedirect = String.format("%s/login", request.getContextPath());
                    ((HttpServletResponse) servletResponse).sendRedirect(urlRedirect);
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() { }
}
