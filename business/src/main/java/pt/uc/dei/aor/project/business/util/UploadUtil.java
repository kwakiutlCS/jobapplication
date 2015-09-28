package pt.uc.dei.aor.project.business.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class UploadUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadUtil.class);
	private static final Path UPLOADS = Paths.get(System.getProperty("jboss.server.data.dir")+
			"/jobapplication/uploads");
	
	public Path upload(String path, InputStream in) {
		Path folder = UPLOADS.resolve(Paths.get(path));
				
		try {
			Path file = Files.createTempFile(folder, "f", ".csv");
			Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
			return file;
		} catch (IOException e) {
			return null;
		}
		
	}
		
	
	public Path upload(String path, String filename, InputStream in) throws IOException {
		Path dir = UPLOADS.resolve(Paths.get(path));
		
		if (Files.notExists(dir))
			Files.createDirectories(dir);
		
		Path file = dir.resolve(Paths.get(filename));
		
		Files.createFile(file);
		Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
		return file;
	}
	
	
	public Path mv(String from, String to, String filename) throws IOException {
		Path fromFile = UPLOADS.resolve(Paths.get(from+"/"+filename));
		Path toFile = UPLOADS.resolve(Paths.get(to));
		
		if (Files.notExists(toFile))
			Files.createDirectories(toFile);
		
		toFile = toFile.resolve(filename);
		
		Files.createFile(toFile);
		Files.copy(fromFile, toFile);
		
		return toFile;
	}
	
	
	public void delete(Path extra) throws IOException {
		Path path = UPLOADS.resolve(extra);
		
		if (Files.isDirectory(path)) {
			DirectoryStream<Path> stream = Files.newDirectoryStream(path);
			List<Path> files = new ArrayList<>();
			for (Path file : stream) {
				files.add(file);
			}
			for (int i = files.size()-1; i >= 0; i--) {
				Files.delete(files.get(i));
			}
		}
		else {
			Files.deleteIfExists(path);
		}
	}
	
	public void delete(String path) throws IOException {
		delete(Paths.get(path));
	}
}	