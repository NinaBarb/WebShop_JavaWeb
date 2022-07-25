/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.UserAccount;

/**
 *
 * @author Nina
 */
public class AuthorizationFilter implements Filter {
    
    private HttpServletRequest httpRequest;
    private final String ERROR_MSG = "Only admin has rights to view this page!";
    
    public AuthorizationFilter() {
    }    
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        httpRequest = (HttpServletRequest) request;
        
        if ( !isAdmin()) {
            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("error.jsp");
            request.setAttribute("errorMessage", ERROR_MSG);
            dispatcher.forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
    
    private boolean isAdmin() {
        HttpSession session = httpRequest.getSession(false);
        Optional<UserAccount> userAccount = (Optional<UserAccount>) session.getAttribute("userAccount");
        boolean isAdmin = userAccount.isPresent() && userAccount.get().isIsAdmin();
 
        return isAdmin;
    }
    
    @Override
    public void destroy() {        
    }

    @Override
    public void init(FilterConfig filterConfig) { 
    }
}
