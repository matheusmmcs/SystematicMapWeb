package br.com.caelum.vraptor.musicjungle.util;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.ufpi.systematicmap.utils.GenerateHashPasswordUtil;

public class GenerateHashPasswordUtilTest {
	@Test
	public void testHashWasGenerateWithSuccess() {
		String password = "1234";
		assertNotNull(GenerateHashPasswordUtil.generateHash(password));

	}

	@Test
	public void testValidIfPasswordIsValidAfterHashed() {
		String password = "brazil";
		String hashPassword = GenerateHashPasswordUtil.generateHash(password);
		boolean expectedValidPassword = GenerateHashPasswordUtil
				.isPasswordValid(password, hashPassword);
		assertTrue(expectedValidPassword);
	}

	@Test
	public void testPassWordIsNotEqualToHashCodeGenerated() {
		String password = "XPto";
		String passwordhash = GenerateHashPasswordUtil.generateHash(password);
		String passwordTyped = "xPto";
		boolean expectedInvalidPassword = GenerateHashPasswordUtil
				.isPasswordValid(passwordTyped, passwordhash);
		assertFalse(expectedInvalidPassword);
	}

}