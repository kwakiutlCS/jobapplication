package pt.uc.dei.aor.project.presentationservlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pt.uc.dei.aor.project.business.model.IUser;

@WebFilter("/index.xhtml")
public class LoggedInFilter implements Filter {

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException { 
    	HttpServletRequest req = (HttpServletRequest) request;
    
        IUser user = (IUser) req.getSession().getAttribute("user");
        
        if (user != null) {
            // User is logged in, so just continue request.
        	HttpServletResponse res = (HttpServletResponse) response;
        	if (user.isAdmin())
        		res.sendRedirect(req.getContextPath() + "/admin/index.xhtml");
        	else if (user.isManager()) 
        		res.sendRedirect(req.getContextPath() + "/manager/index.xhtml");
        	else 
        		res.sendRedirect(req.getContextPath() + "/interview/index.xhtml");
            
        } else {
            // User is not logged in, so redirect to index.
        	chain.doFilter(request, response);
        }
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}