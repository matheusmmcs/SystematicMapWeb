package br.com.ufpi.systematicmap.utils;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

public class GenerateHashPasswordUtil {
	private Object salt;

	public String generateHash(String password) {
		MessageDigestPasswordEncoder digestPasswordEncoder = getInstanceMessageDisterPassword();
		String encodePassword = digestPasswordEncoder.encodePassword(password, salt);
		return encodePassword;
	}

	private MessageDigestPasswordEncoder getInstanceMessageDisterPassword() {
		// informo tipo de enconding que desejo
		MessageDigestPasswordEncoder digestPasswordEncoder = new MessageDigestPasswordEncoder("MD5");
		return digestPasswordEncoder;
	}

	// método que faz a validação  como não usamos salt deixei em null
	public boolean isPasswordValid(String password, String hashPassword) {
		MessageDigestPasswordEncoder digestPasswordEncoder = getInstanceMessageDisterPassword();
		return digestPasswordEncoder.isPasswordValid(hashPassword, password, salt);
	}
	
	//
	public String generateCodeRecovery(String code){
		return generateHash(code);
	}
}