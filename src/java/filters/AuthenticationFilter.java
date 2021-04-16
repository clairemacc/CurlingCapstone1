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
 * This class is used in order to authenticate a normal player. Once a player
 * logs in using the UI this class will allow the user to view different
 * menu items.
 * @author CurlingCapstone
 */
public class AuthenticationFilter implements Filter {

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
    @Override
    public void destroy() {        

    }
    @Override
    public void init(FilterConfig filterConfig) {        

    }
}
