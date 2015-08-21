package pt.uc.dei.aor.project.business.startup;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.sun.syndication.io.impl.Base64;

public class Encryptor {
	
	public static String encrypt(String password) {
		String securedPassword = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			
			byte byteData[] = md.digest();
			byte[] data2 = Base64.encode(byteData);
			securedPassword = new String(data2);
			return securedPassword;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return securedPassword;
	}
}
