package MakeItFit.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class EmailValidatorTest {

    @Test
    void testValidEmails() {
        assertTrue(EmailValidator.isValidEmail("user@example.com"));
        assertTrue(EmailValidator.isValidEmail("firstname.lastname@example.com"));
        assertTrue(EmailValidator.isValidEmail("email@subdomain.example.com"));
        assertTrue(EmailValidator.isValidEmail("1234567890@example.com"));
        assertTrue(EmailValidator.isValidEmail("email@example-one.com"));
        assertTrue(EmailValidator.isValidEmail("_______@example.com"));
        assertTrue(EmailValidator.isValidEmail("email@example.name"));
        assertTrue(EmailValidator.isValidEmail("email@example.museum"));
        assertTrue(EmailValidator.isValidEmail("email@example.co.jp"));
        assertTrue(EmailValidator.isValidEmail("firstname-lastname@example.com"));
    }

    @Test
    void testInvalidEmails() {
        assertFalse(EmailValidator.isValidEmail("plainaddress"));
        assertFalse(EmailValidator.isValidEmail("@missingusername.com"));
        assertFalse(EmailValidator.isValidEmail("username@.com"));
        assertFalse(EmailValidator.isValidEmail("username@example.com."));
        assertFalse(EmailValidator.isValidEmail("username@example_com"));
        assertFalse(EmailValidator.isValidEmail("username@example:com"));
        assertFalse(EmailValidator.isValidEmail("too@many@at@signs.com"));
        assertFalse(EmailValidator.isValidEmail("username@.com."));
        assertFalse(EmailValidator.isValidEmail("username@.com"));
    }

    @Test
    void testDomainParts() {
        assertTrue(EmailValidator.isValidEmail("email@example.com"));
        assertTrue(EmailValidator.isValidEmail("email@example.co.uk"));
        assertTrue(EmailValidator.isValidEmail("email@sub.domain.example.com"));
        assertFalse(EmailValidator.isValidEmail("email@.com"));
        assertFalse(EmailValidator.isValidEmail("email@example."));
    }

}
