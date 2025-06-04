package MakeItFit.utils;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EmailValidatorTest {
    @Test
    void testQuotedAndDoubleDot() {
        assertTrue(EmailValidator.isValidEmail("\"john..doe\"@uminho.pt"));
    }

    @Test
    void testIPv4LiteralDomain() {
        assertTrue(EmailValidator.isValidEmail("user@[192.168.0.1]"));
    }

    @Test
    void testLocalBangAndPercent() {
        assertTrue(EmailValidator.isValidEmail("user%example.com@example.org"));
    }

    @Test
    void testSingleLetters() {
        assertTrue(EmailValidator.isValidEmail("x@y.co"));
    }

    @Test
    void testLocalQuotedSingleLetter() {
        assertTrue(EmailValidator.isValidEmail("\"a\"@b.co"));
    }

    @Test
    void testLocalPlusTag() {
        assertTrue(EmailValidator.isValidEmail("first.last+tag@example.com"));
    }

    @Test
    void testLocalAndDomainHyphenOrUnderscore() {
        assertTrue(EmailValidator.isValidEmail("first_last-123@sub-domain.example.co.uk"));
    }

    @Test
    void testDomainStartsWithHyphen() {
        assertFalse(EmailValidator.isValidEmail("user@-example.com"));
    }

    @Test
    void testDomainEndsWithHyphen() {
        assertFalse(EmailValidator.isValidEmail("user@example-.com"));
    }

    @Test
    void testDomainConsecutiveDots() {
        assertFalse(EmailValidator.isValidEmail("user@sub..example.com"));
    }

    @Test
    void testLocalTrailingDot() {
        assertFalse(EmailValidator.isValidEmail("user.@example.com"));
    }

    @Test
    void testLocalStartsWithDot() {
        assertFalse(EmailValidator.isValidEmail(".user@example.com"));
    }

    @Test
    void testDomainSpace() {
        assertFalse(EmailValidator.isValidEmail("user@exa mple.com"));
    }

    @Test
    void testTLDMissing() {
        assertFalse(EmailValidator.isValidEmail("user@example"));
    }

    @Test
    void testQuotedAndSpace() {
        assertFalse(EmailValidator.isValidEmail("\"john doe\"@uminho.pt"));
    }

    @Test
    void testEmpty() {
        assertFalse(EmailValidator.isValidEmail(""));
    }

    @Test
    void testToForceCoverage() {
        new EmailValidator();
    }
}
