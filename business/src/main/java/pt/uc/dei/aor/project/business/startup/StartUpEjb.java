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
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IPublicationChannelBusService;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;
import pt.uc.dei.aor.project.business.util.Role;

@Singleton
@Startup
public class StartUpEjb {
	
	private static Logger logger = LoggerFactory.getLogger(StartUpEjb.class);
	
	@Inject
	private IWorkerBusinessService workerEjb;
	
	@Inject
	private IPublicationChannelBusService channelEjb;
	
	@PostConstruct
	public void init() {
		
		logger.info("initializing startup script");
		
		// Admin user
		logger.info("Adding admin user...");
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ADMIN);
		try {
			workerEjb.createNewWorker("admin", "admin", "admin", "admin@admin", roles);
				
			logger.info("Admin added with sucess.");
		} catch (DuplicatedUserException e) {
			logger.info("Admin already exists. Nothing to be done here.");
		}
		catch (Exception e) {
			logger.error("An error occurred adding admin user.");
		}
		
		
		// super user
		logger.info("Adding super user...");
		IWorker su = workerEjb.createSuperUser();
		if (su == null) {
			logger.info("Super user already exists. Nothing to be done here.");
		}
		else {
			logger.info("Super user created successfully.");
		}
		
		// publication channels
		//channelEjb.createNewPublicationChannel("sadfdadsf");
		//System.out.println("Created channel qq coisa");
	}
}
