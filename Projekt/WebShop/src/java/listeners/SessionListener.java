/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web application lifecycle listener.
 *
 * @author Nina
 */
public class SessionListener implements HttpSessionListener {

    ServletContext ctx = null;
    HttpServletRequest req;
    HttpServletResponse resp;
    static int current = 0;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        current++;

        ctx = se.getSession().getServletContext();
        ctx.setAttribute("currentusers", current);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        current--;
        ctx.setAttribute("currentusers", current);
    }
}
