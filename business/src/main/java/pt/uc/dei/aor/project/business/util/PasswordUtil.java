package pt.uc.dei.aor.project.business.util;

import java.util.Random;

import javax.ejb.Stateless;

@Stateless
public class PasswordUtil {
	
	private static Random rand = new Random();
	
	public static String generate(int size) {
		StringBuilder s = new StringBuilder("");
		
		for (int i = 0; i < size; i++) {
			s.append(getRandomChar());
		}
		
		return s.toString();
	}
	
	
	
	private static String getRandomChar() {
		char c = (char) (rand.nextInt(90)+33);
		return String.valueOf(c);
	}
}
