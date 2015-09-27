package pt.uc.dei.aor.project.business.startup;

import javax.ejb.Startup;
import javax.inject.Singleton;

@Singleton
@Startup
public class StartUpEjb {
	
//	private static Logger logger = LoggerFactory.getLogger(StartUpEjb.class);
//	
//	@Inject
//	private IUserBusinessService userEjb;
//	
//	@Inject
//	private IPublicationChannelBusService channelEjb;
//	
//	@Inject
//	private IQualificationBusinessService qualificationEjb;
//	
//
//	@Inject
//	private IColorBusinessService colorEjb;
//	
//
//	@PostConstruct
//	public void init() {
//		
//		logger.info("initializing startup script");
//		
//		// Admin user
//		logger.info("Adding admin user...");
//		List<Role> roles = new ArrayList<>();
//		roles.add(Role.ADMIN);
//		try {
//			userEjb.createNewUser("admin", "admin", "admin", "admin@admin.pt", roles);
//				
//			logger.info("Admin added with sucess.");
//		} catch (DuplicatedUserException e) {
//			logger.info("Admin already exists. Nothing to be done here.");
//		}
//		catch (Exception e) {
//			logger.error("An error occurred adding admin user.");
//		}
//		
//				
//		
//		// publication channels
//
////		System.out.println("Adding Channels...");
////		
////		try { channelEjb.createNewPublicationChannel("Critical Software website"); } 
////		catch (Exception e) {System.out.println(e.getMessage());}
////		
////		try {channelEjb.createNewPublicationChannel("Linkedin"); }
////		catch (Exception e) {System.out.println(e.getMessage());}
////		
////		try {channelEjb.createNewPublicationChannel("Glassdoor"); } 
////		catch (Exception e) {System.out.println(e.getMessage());}
////		
////		try {channelEjb.createNewPublicationChannel("Facebook"); } 
////		catch (Exception e) {System.out.println(e.getMessage());}
//
//		
//		// color
//
//		colorEjb.save("admin", "red", "white", "black", "green", "white", "grey");
//		colorEjb.save("manager", "red", "white", "black", "green", "white", "grey");
//		colorEjb.save("interview", "red", "white", "black", "green", "white", "grey");
//		colorEjb.save("public", "red", "white", "black", "green", "white", "grey");
//
//		try {
//			createFileSystemStructure();
//		} catch (IOException e1) {
//			logger.error("Error creating the file structure");
//		}
//
//		// load data
//		if (!qualificationEjb.isPopulated()) {
//			Path l = Paths.get(System.getProperty("jboss.server.data.dir"));
//			l = l.resolve(Paths.get("jobapplication")).normalize();
//			
//			String[] files = new String[]{"lpu", "lpr", "mpu", "mpr", "dpu", "dpr"};
//
//			for (String s : files) {
//				Path filePath = l.resolve(Paths.get(s+".csv"));
//				
//				if (Files.exists(filePath.toAbsolutePath())) {
//					BufferedReader reader = null;
//					try {
//						reader = Files.newBufferedReader(filePath);
//						String line = null;
//						while ((line = reader.readLine()) != null) {
//							qualificationEjb.addQualification(line);
//						}
//					} catch (IOException x) {
//						System.err.println(x);
//					} finally {
//						try {
//							reader.close();
//						} catch (IOException e) {
//
//						}
//					}
//				}
//			}
//		}
//		
//		
//
//	}
//	
//	
//	private void createFileSystemStructure() throws IOException {
//		Path directory = Paths.get(System.getProperty("jboss.server.data.dir"))
//				.resolve(Paths.get("jobapplication"));
//		
//		if (!Files.exists(directory)) {
//			Files.createDirectory(directory);
//		}	
//		
//		Path tmp = directory.resolve(Paths.get("uploads"));
//		if (!Files.exists(tmp))
//			Files.createDirectory(tmp);
//			
//		tmp = directory.resolve(Paths.get("uploads/usersImport"));
//		if (!Files.exists(tmp))
//			Files.createDirectory(tmp);
//			
//		tmp = directory.resolve(Paths.get("uploads/cv"));
//		if (!Files.exists(tmp))
//			Files.createDirectory(tmp);
//			
//		tmp = directory.resolve(Paths.get("uploads/letter"));
//		if (!Files.exists(tmp))
//			Files.createDirectory(tmp);
//		
//		tmp = directory.resolve(Paths.get("data"));
//		if (!Files.exists(tmp))
//			Files.createDirectory(tmp);
//	}
//	
	
}
