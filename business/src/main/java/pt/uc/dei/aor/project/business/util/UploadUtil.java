package pt.uc.dei.aor.project.business.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class UploadUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadUtil.class);
	private static final Path UPLOADS = Paths.get(System.getProperty("jboss.server.data.dir")+
			"/jobapplication/uploads");
	
	public static Path upload(String path, InputStream in) {
		Path folder = UPLOADS.resolve(Paths.get(path));
				
		try {
			Path file = Files.createTempFile(folder, "f", ".csv");
			Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
			return file;
		} catch (IOException e) {
			return null;
		}
		
	}
		
	
	public static Path upload(String path, String filename, InputStream in) {
		Path file = UPLOADS.resolve(Paths.get(path)).resolve(Paths.get(filename));
				
		try {
			Files.createFile(file);
			Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
			return file;
		} catch (IOException e) {
			return null;
		}
		
	}
}	