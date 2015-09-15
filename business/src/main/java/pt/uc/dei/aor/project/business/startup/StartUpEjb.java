package pt.uc.dei.aor.project.business.startup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IQualificationPersistenceService;
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
		
		
		// super user
		logger.info("Adding super user...");
		IWorker su = workerEjb.createSuperUser();
		if (su == null) {
			logger.info("Super user already exists. Nothing to be done here.");
		}
		else {
			logger.info("Super user created successfully.");
		}
		
		System.out.println(System.getProperty("user.dir"));
		
		// publication channels

//		System.out.println("Adding Channels...");
		
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

		try {
			createFileSystemStructure();
		} catch (IOException e1) {
			logger.error("Error creating the file structure");
		}

		// load data
		if (!qualificationEjb.isPopulated()) {
			Path l = Paths.get(System.getProperty("java.class.path"));
			l = l.resolve(Paths.get("../jobapplication")).normalize();
			
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
			Files.createDirectory(directory.resolve(Paths.get("uploads")));
			Files.createDirectory(directory.resolve(Paths.get("uploads/usersImport")));
		}
	}
}
