package pt.uc.dei.aor.project.business.startup;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.exception.DuplicatedUserException;
import pt.uc.dei.aor.project.business.exception.NoRoleException;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.service.IPublicationChannelBusService;
import pt.uc.dei.aor.project.business.service.IQualificationBusinessService;
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
	
	@Inject
	private IQualificationBusinessService qualificationEjb;
	
	
	@PostConstruct
	public void init() {
		
		logger.info("initializing startup script");
		
		// Admin user
		logger.info("Adding admin user...");
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ADMIN);
		try {
			workerEjb.createNewWorker("admin", "admin", "admin", "admin@admin.pt", roles);
				
			logger.info("Admin added with sucess.");
		} catch (DuplicatedUserException e) {
			logger.info("Admin already exists. Nothing to be done here.");
		}
		catch (Exception e) {
			logger.error("An error occurred adding admin user.");
		}
		
		// database populate
		optionalPopulate();
				
				
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

//		System.out.println("Adding Channels...");
//		
//		try { channelEjb.createNewPublicationChannel("Critical Software website"); } 
//		catch (Exception e) {System.out.println(e.getMessage());}
//		
//		try {channelEjb.createNewPublicationChannel("Linkedin"); }
//		catch (Exception e) {System.out.println(e.getMessage());}
//		
//		try {channelEjb.createNewPublicationChannel("Glassdoor"); } 
//		catch (Exception e) {System.out.println(e.getMessage());}
//		
//		try {channelEjb.createNewPublicationChannel("Facebook"); } 
//		catch (Exception e) {System.out.println(e.getMessage());}

		
		// color
//		colorEjb.save("admin", "red", "white", "black", "green", "white", "grey");
//		colorEjb.save("manager", "red", "white", "black", "green", "white", "grey");
//		colorEjb.save("interview", "red", "white", "black", "green", "white", "grey");
//		colorEjb.save("public", "red", "white", "black", "green", "white", "grey");
		 
		try {
			createFileSystemStructure();
		} catch (IOException e1) {
			logger.error("Error creating the file structure");
		}

		// load data
		if (!qualificationEjb.isPopulated()) {
			Path l = Paths.get(System.getProperty("jboss.server.data.dir"));
			l = l.resolve(Paths.get("jobapplication")).normalize();
			
			String[] files = new String[]{"lpu", "lpr", "mpu", "mpr", "dpu", "dpr"};

			for (String s : files) {
				Path filePath = l.resolve(Paths.get(s+".csv"));
				
				if (Files.exists(filePath.toAbsolutePath())) {
					BufferedReader reader = null;
					try {
						reader = Files.newBufferedReader(filePath);
						String line = null;
						while ((line = reader.readLine()) != null) {
							qualificationEjb.addQualification(line);
						}
					} catch (IOException x) {
						System.err.println(x);
					} finally {
						try {
							reader.close();
						} catch (IOException e) {

						}
					}
				}
			}
		}
		
		

	}
	
	
	private void createFileSystemStructure() throws IOException {
		Path directory = Paths.get(System.getProperty("jboss.server.data.dir"))
				.resolve(Paths.get("jobapplication"));
		
		if (!Files.exists(directory)) {
			Files.createDirectory(directory);
		}	
		
		Path tmp = directory.resolve(Paths.get("uploads"));
		if (!Files.exists(tmp))
			Files.createDirectory(tmp);
			
		tmp = directory.resolve(Paths.get("uploads/usersImport"));
		if (!Files.exists(tmp))
			Files.createDirectory(tmp);
			
		tmp = directory.resolve(Paths.get("uploads/cv"));
		if (!Files.exists(tmp))
			Files.createDirectory(tmp);
			
		tmp = directory.resolve(Paths.get("uploads/letter"));
		if (!Files.exists(tmp))
			Files.createDirectory(tmp);
		
		tmp = directory.resolve(Paths.get("data"));
		if (!Files.exists(tmp))
			Files.createDirectory(tmp);
	}
	
	
	private void optionalPopulate() {
		BufferedReader reader = null;
		Path directory = Paths.get(System.getProperty("jboss.server.data.dir"))
				.resolve(Paths.get("jobapplication/data"));
		
		// users
		Path file = directory.resolve(Paths.get("users.csv"));
		
		if (Files.exists(file)) {
			logger.info("populating users database...");
			try {
				reader = Files.newBufferedReader(file);
				String line = null;
				while ((line = reader.readLine()) != null) {
					String[] f = line.split(",");
					List<Role> roles = new ArrayList<>();
					if (f[4].indexOf("admin") != -1) roles.add(Role.ADMIN);
					if (f[4].indexOf("manager") != -1) roles.add(Role.MANAGER);
					if (f[4].indexOf("interviewer") != -1) roles.add(Role.INTERVIEWER);
					
					workerEjb.createNewWorker(f[0], f[1], f[2], f[3], roles);
				}
			} catch (IOException x) {
				System.err.println(x);
			} catch (NoRoleException e) {
				
			} catch (DuplicatedUserException e) {
				
			} finally {
				try {
					reader.close();
				} catch (IOException e) {

				}
			}
		}
		
	}
}