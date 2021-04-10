package be.pxl.auctions.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorIsValidTest {

	@Test
	public void returnsTrueWhenValidEmail() {
		assertTrue(EmailValidator.isValid("ine.menten@pxl.be"));
	}

	@Test
	public void returnsFalseWhenAtSignMissing() {
		assertFalse(EmailValidator.isValid("ine.menten#pxl.be"));
	}
}
