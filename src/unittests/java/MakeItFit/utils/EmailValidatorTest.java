package MakeItFit.utils;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {
    @Test
    public void testValid() {
        assertTrue(EmailValidator.isValidEmail("jonh@mail.com"));
    }
}
