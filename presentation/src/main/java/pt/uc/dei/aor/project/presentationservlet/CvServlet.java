package pt.uc.dei.aor.project.presentationservlet;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.service.IApplicationBusinessService;

@WebServlet("/cv/*")
public class CvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Path DOWNLOADS = 
			Paths.get(System.getProperty("jboss.server.data.dir")+"/jobapplication/uploads/cv/");
       
	@Inject
	private IApplicationBusinessService applicationService;
	
    public CvServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
		
		String[] fields = filename.split("/");
		if (fields.length != 3) return;
		
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
		if (!(fields[0].equals("users") || fields[0].equals("applications"))) return false;
		
		if (user.isAdmin()) return true;
		
		if (fields[0].equals("users")) {
			if (user.getLogin().equals(fields[1])) return true;
		}
		else {
			long id;
			try {
				id = Long.parseLong(fields[1]);
			}
			catch(Exception e) {
				return false;
			}
			
			IApplication application = applicationService.findApplicationById(id);
			if (user.isManager() && application.getPosition().getContactPerson().equals(user)) {
				return true;
			}
			else if (user.isInterviewer()) {
				for (IInterview interview : application.getInterviews()) {
					if (interview.getInterviewers().contains(user)) return true;
				}
			}
		}
		
		return false;
	}

}