package pt.uc.dei.aor.project.business.startup;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

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
				
			if (worker != null) System.out.println("Admin added with sucess.");
			else System.out.println("Admin already exists. Nothing to be done here.");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
		System.out.println("Adding Channels...");
		
		try { channelEjb.createNewPublicationChannel("Critical Software website"); } 
		catch (Exception e) {System.out.println(e.getMessage());}
		
		try {channelEjb.createNewPublicationChannel("Linkedin"); }
		catch (Exception e) {System.out.println(e.getMessage());}
		
		try {channelEjb.createNewPublicationChannel("Glassdoor"); } 
		catch (Exception e) {System.out.println(e.getMessage());}
		
		try {channelEjb.createNewPublicationChannel("Facebook"); } 
		catch (Exception e) {System.out.println(e.getMessage());}
		
		 
	}
}
