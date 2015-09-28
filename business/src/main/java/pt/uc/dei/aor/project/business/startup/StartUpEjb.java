package pt.uc.dei.aor.project.business.startup;

<<<<<<< HEAD
import javax.ejb.Singleton;
=======
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
>>>>>>> branch 'master' of https://github.com/kwakiutlCS/jobapplication.git
import javax.ejb.Startup;
<<<<<<< HEAD


=======
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.service.IColorBusinessService;
import pt.uc.dei.aor.project.business.service.IPublicationChannelBusService;
import pt.uc.dei.aor.project.business.service.IQualificationBusinessService;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.business.util.Role;
>>>>>>> branch 'master' of https://github.com/kwakiutlCS/jobapplication.git

@Singleton
@Startup
public class StartUpEjb {
<<<<<<< HEAD
=======
	
	private static Logger logger = LoggerFactory.getLogger(StartUpEjb.class);
	
	@Inject
	private IUserBusinessService userEjb;
	
	@Inject
	private IPublicationChannelBusService channelEjb;
	
	@Inject
	private IQualificationBusinessService qualificationEjb;
	
>>>>>>> branch 'master' of https://github.com/kwakiutlCS/jobapplication.git

<<<<<<< HEAD
=======
	@Inject
	private IColorBusinessService colorEjb;
	

	@PostConstruct
	public void init() {
		
		logger.info("initializing startup script");
		
		// Admin user
	
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ADMIN);
		try {
			userEjb.createNewUser("admin", "admin", "admin", "admin@admin.pt", roles);
				
			logger.info("Admin added with sucess.");
		} catch (DuplicatedUserException e) {
			logger.info("Admin already exists. Nothing to be done here.");
		}
		catch (Exception e) {
			logger.error("An error occurred adding admin user.");
		}
				
		// color

		colorEjb.save("admin", "red", "white", "black", "green", "white", "grey");
		colorEjb.save("manager", "red", "white", "black", "green", "white", "grey");
		colorEjb.save("interview", "red", "white", "black", "green", "white", "grey");
		colorEjb.save("public", "red", "white", "black", "green", "white", "grey");

	
>>>>>>> branch 'master' of https://github.com/kwakiutlCS/jobapplication.git
}
}
