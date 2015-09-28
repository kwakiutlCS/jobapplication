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

import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.service.IInterviewBusinessService;

@WebServlet("/interview/letter/*")
public class LetterInterviewerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Path DOWNLOADS = 
			Paths.get(System.getProperty("jboss.server.data.dir")+"/jobapplication/uploads/letter");
       
	@Inject
	private IInterviewBusinessService interviewService;
	
    public LetterInterviewerServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
		
		String[] fields = filename.split("/");
		if (fields.length != 2) return; 
	
		long id;
		try {
			id = Long.parseLong(fields[0]);
		}
		catch (Exception e) {
			return;
		}
				
		IUser user = (IUser) request.getSession().getAttribute("user");
		
		if (user != null) {
			boolean invalid = true;
			for (IInterview i : interviewService.findActiveInterviewsByUser(user)) {
				
				if (i.getApplication().getId() == id) {
					invalid = false;
					break;
				}
			}
			
			if (invalid) return;
			
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

}