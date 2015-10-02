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
import pt.uc.dei.aor.project.business.service.IColorBusinessService;
import pt.uc.dei.aor.project.business.service.IPublicationChannelBusService;
import pt.uc.dei.aor.project.business.service.IQualificationBusinessService;
import pt.uc.dei.aor.project.business.service.IUserBusinessService;
import pt.uc.dei.aor.project.business.util.Role;



@Startup
@Singleton
public class InitialScript {

	private static final Logger logger = LoggerFactory.getLogger(InitialScript.class);
	
	@Inject
	private IUserBusinessService userService;
	
	@Inject
	private IColorBusinessService colorService;
	
	@Inject
	private IQualificationBusinessService qualificationService;
	
	@Inject
	private IPublicationChannelBusService channelService;
	
	

	@PostConstruct
	public void initialize() {
		logger.info("initializing startup script");
		
		// Admin user
		logger.info("Adding admin user...");
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ADMIN);
		try {
			userService.createNewUser("admin", "admin", "admin", "admin@admin.pt", roles);
				
			logger.info("Admin added with sucess.");
		} catch (DuplicatedUserException e) {
			logger.info("Admin already exists. Nothing to be done here.");
		}
		catch (Exception e) {
			logger.error("An error occurred adding admin user.");
		}
		
		
		// color scheme
		logger.info("Initializing color scheme...");
		colorService.save("admin", "#6A943F", "#FFFFFF", "#000000", "#E47D00", "#FFFFFF", "#51504D");
		colorService.save("manager", "#6A943F", "#FFFFFF", "#000000", "#E47D00", "#FFFFFF", "#51504D");
		colorService.save("interview", "#6A943F", "#FFFFFF", "#000000", "#E47D00", "#FFFFFF", "#51504D");
		colorService.save("public", "red", "white", "black", "green", "white", "grey");
	
		
		// channel information
		logger.info("Adding pre-defined publication channels...");
		channelService.createNewPublicationChannel("Critical Software website");
		channelService.createNewPublicationChannel("Linkedin");
		channelService.createNewPublicationChannel("Facebook");
		channelService.createNewPublicationChannel("Glassdoor");
		
		
		// file structure
		logger.info("Creating file structure...");
		try {
			createFileSystemStructure();
		} catch (IOException e1) {
			logger.error("Error creating the file structure");
		}

		// load data
		logger.info("Populating database with schools information...");
		if (!qualificationService.isPopulated()) {
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
							qualificationService.addQualification(line);
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
		
		tmp = directory.resolve(Paths.get("uploads/images"));
		if (!Files.exists(tmp))
			Files.createDirectory(tmp);
			
		tmp = directory.resolve(Paths.get("uploads/cv"));
		if (!Files.exists(tmp))
			Files.createDirectory(tmp);
		
		tmp = directory.resolve(Paths.get("uploads/cv/users"));
		if (!Files.exists(tmp))
			Files.createDirectory(tmp);
		
		tmp = directory.resolve(Paths.get("uploads/cv/applications"));
		if (!Files.exists(tmp))
			Files.createDirectory(tmp);
		
		
			
		tmp = directory.resolve(Paths.get("uploads/letter"));
		if (!Files.exists(tmp))
			Files.createDirectory(tmp);
		
		tmp = directory.resolve(Paths.get("uploads/letter/temp"));
		if (!Files.exists(tmp))
			Files.createDirectory(tmp);
		
		tmp = directory.resolve(Paths.get("data"));
		if (!Files.exists(tmp))
			Files.createDirectory(tmp);
	}
	
}