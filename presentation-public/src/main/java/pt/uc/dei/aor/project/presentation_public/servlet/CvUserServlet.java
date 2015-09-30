package pt.uc.dei.aor.project.presentation_public.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pt.uc.dei.aor.project.business.model.IUser;

@WebServlet("/cvUser/*")
public class CvUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Path DOWNLOADS = 
			Paths.get(System.getProperty("jboss.server.data.dir")+"/jobapplication/uploads/cv/users");
       
	public CvUserServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
		
		String[] fields = filename.split("/");
		if (fields.length != 2) return;
		
		IUser user = (IUser) request.getSession().getAttribute("user");
		
		if (user != null) {
			
			if (!validLink(fields, user)) return;
			
			Path path = DOWNLOADS.resolve(Paths.get(filename));
			
			if (Files.exists(path)) {
				File file = path.toFile();
				response.setHeader("Content-Type", getServletContext().getMimeType(filename));
			    response.setHeader("Content-Length", String.valueOf(file.length()));
			    response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
			    
				Files.copy(path, response.getOutputStream());
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
	}
	
	
	private boolean validLink(String[] fields, IUser user) {
		if (user.getLogin().equals(fields[0])) return true;
				
		return false;
	}

}