package pt.uc.dei.aor.project.business.startup;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.service.IColorBusinessService;
import pt.uc.dei.aor.project.business.service.IPublicationChannelBusService;
import pt.uc.dei.aor.project.business.service.IQualificationBusinessService;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.business.util.Role;


@Singleton
@Startup
public class StartUpEjb {

	
	private static Logger logger = LoggerFactory.getLogger(StartUpEjb.class);
	
	@Inject
	private IUserBusinessService userEjb;
	
	@Inject
	private IPublicationChannelBusService channelEjb;
	
	@Inject
	private IQualificationBusinessService qualificationEjb;
	

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

	
}
}
