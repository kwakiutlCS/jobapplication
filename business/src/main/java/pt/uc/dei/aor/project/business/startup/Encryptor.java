package pt.uc.dei.aor.project.business.startup;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class Encryptor {
	
	public static String encrypt(String password) {
		byte byteData[] = DigestUtils.sha256(password);
		return Base64.encodeBase64String(byteData);
	}
}
