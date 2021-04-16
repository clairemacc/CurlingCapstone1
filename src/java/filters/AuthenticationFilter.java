package filters;

import java.io.IOException;
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

/**
 * This filter is used in order to authenticate a normal player. Once a player
 * logs in using the UI this class will allow the user to view different
 * menu items.
 * @author CurlingCapstone
 */
public class AuthenticationFilter implements Filter {

    /**
     * Handles the filtering functionality. 
     * 
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @throws IOException if an input/output error occurs
     * @throws ServletException if a servlet error occurs
     */    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        HttpSession session = httpRequest.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            httpResponse.sendRedirect("login");
            return;
        }
        
        int roleID = user.getRole().getRoleID();
        if (roleID != 1 && roleID != 2) {
            httpResponse.sendRedirect("home");
            return;
        }
        
        chain.doFilter(request, response);
    }
    
    /**
     * Destroy method for this filter
     */    
    @Override
    public void destroy() {        

    /**
     * Init method for this filter
     * @param filterConfig the filter configuration
     */        
    }
    @Override
    public void init(FilterConfig filterConfig) {        

    }
}
