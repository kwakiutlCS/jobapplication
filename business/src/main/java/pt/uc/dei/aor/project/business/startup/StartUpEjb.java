package pt.uc.dei.aor.project.business.startup;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IPublicationChannelBusService;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;
import pt.uc.dei.aor.project.business.util.Role;

@Singleton
@Startup
public class StartUpEjb {

	@Inject
	private IWorkerBusinessService workerEjb;
	
	@Inject
	private IPublicationChannelBusService channelEjb;
	
	@PostConstruct
	public void init() {
		
		System.out.println();
		System.out.println();
		System.out.println("Initializing startup script");
		System.out.println();
		
		
		// Admin user
		System.out.println("Adding admin user...");
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ADMIN);
		try {
			IWorker worker = workerEjb.createNewWorker("admin", "admin", "admin", "admin@admin", 
					Encryptor.encrypt("admin"), roles);
				
			System.out.println("Admin added with sucess.");
		} catch (DuplicatedUserException e) {
			System.out.println("Admin already exists. Nothing to be done here.");
		}
		catch (Exception e) {
			System.out.println("An error occurred adding admin user.");
		}
		System.out.println();
		
		
		// super user
		System.out.println("Adding super user...");
		IWorker su = workerEjb.createSuperUser();
		if (su == null) {
			System.out.println("Super user already exists. Nothing to be done here.");
		}
		else {
			System.out.println("Super user created successfully.");
		}
		
		// publication channels
		//channelEjb.createNewPublicationChannel("sadfdadsf");
		//System.out.println("Created channel qq coisa");
	}
}
