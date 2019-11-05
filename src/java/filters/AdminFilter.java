/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;

/**
 *
 * @author 745507
 */
public class AdminFilter implements Filter {

    public AdminFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        try {
            HttpServletRequest hsr = (HttpServletRequest) request;
            HttpSession session = hsr.getSession();
            UserService us = new UserService();

            String email = (String) session.getAttribute("email");
            User user = us.get(email);

            if (user.getRole().getRoleID() == 1 || user.getRole().getRoleID() == 3) {
                chain.doFilter(request, response);
                return;
            }

            HttpServletResponse hsre = (HttpServletResponse) response;
            hsre.sendRedirect("home");
        } catch (Exception ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
    }
}
