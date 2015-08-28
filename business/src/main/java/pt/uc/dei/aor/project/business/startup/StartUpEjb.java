package pt.uc.dei.aor.project.business.startup;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.service.IWorkerBusinessService;
import pt.uc.dei.aor.project.business.util.Role;

@Singleton
@Startup
public class StartUpEjb {

	@Inject
	private IWorkerBusinessService workerEjb;
	
	@PostConstruct
	public void init() {
		
		System.out.println();
		System.out.println();
		System.out.println("Initializing startup script");
		System.out.println();
		
		
		// Admin user
		System.out.println("Adding admin user");
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ADMIN);
		try {
			if (workerEjb.getWorkerByLogin("admin") == null) {
				workerEjb.createNewWorker("admin", "admin", "admin", "admin@admin", 
					Encryptor.encrypt("admin"), roles);
				System.out.println("Admin added with sucess.");
			}
			else {
				System.out.println("Admin already exists. Nothing to be done here.");
			}
		} catch (NoRoleException e) {
			System.out.println("An error occurred adding admin user.");
		}
		System.out.println();
		
		
		// publication channels
	}
}
