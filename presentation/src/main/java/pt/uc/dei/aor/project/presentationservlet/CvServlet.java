package pt.uc.dei.aor.project.presentationservlet;

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

import pt.uc.dei.aor.project.business.model.IWorker;

@WebServlet("/cv/*")
public class CvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Path DOWNLOADS = 
			Paths.get(System.getProperty("jboss.server.data.dir")+"/jobapplication/uploads/cv");
       
	
    public CvServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
		
		IWorker user = (IWorker) request.getSession().getAttribute("user");
		
		if (user != null) {
			Path path = DOWNLOADS.resolve(Paths.get(user.getLogin())).resolve(Paths.get(filename));
			
			if (Files.exists(path)) {
				System.out.println(path.toAbsolutePath());
				Files.copy(path, response.getOutputStream());
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
	}

}