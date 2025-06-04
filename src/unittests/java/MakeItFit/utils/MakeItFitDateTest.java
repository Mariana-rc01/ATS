package MakeItFit.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MakeItFitDateTest {

    @Test
    void testFromStringValidStandardFormat() {
        MakeItFitDate date = MakeItFitDate.fromString("04/06/2025");
        assertEquals(4, date.getDayOfMonth());
        assertEquals(6, date.getMonth());
        assertEquals(2025, date.getYear());
        assertEquals(4, date.getDayOfWeek());
    }

    @Test
    void testFromStringValidBoundary() {
        MakeItFitDate d = MakeItFitDate.fromString("31/12/1999");
        assertEquals(31, d.getDayOfMonth());
        assertEquals(12, d.getMonth());
        assertEquals(1999, d.getYear());
    }

    @Test
    void testFromStringInvalidDelimiter() {
        assertThrows(
            IllegalArgumentException.class,
            () -> MakeItFitDate.fromString("04-06-2025")
        );
    }

    @Test
    void testFromStringInvalidPartsCount() {
        assertThrows(
            IllegalArgumentException.class,
            () -> MakeItFitDate.fromString("01/01"));
    }

    @Test
    void testFromStringInvalidNonNumeric() {
        assertThrows(
            IllegalArgumentException.class,
            () -> MakeItFitDate.fromString("aa/bb/cccc"
        ));
    }

    /*
        @Test
        @DisplayName("Invalid day (32/01/2020)")
        void invalidDayOutOfRange() {
            // LocalDate.of throws DateTimeException when day = 32—MakeItFitDate wraps it in IllegalArgumentException—turn1search6
            assertThrows(IllegalArgumentException.class,
                () -> MakeItFitDate.fromString("32/01/2020"));
        }

        @Test
        @DisplayName("Invalid month (15/13/2020)")
        void invalidMonthOutOfRange() {
            assertThrows(IllegalArgumentException.class,
                () -> MakeItFitDate.fromString("15/13/2020"));
        }

        @Test
        @DisplayName("Non‐existent leap date (29/02/2019)")
        void invalidNonLeapYear() {
            // 2019 is not a leap year, so LocalDate.of(2019, 2, 29) throws DateTimeException—wrapped as IllegalArgumentException—turn1search6
            assertThrows(IllegalArgumentException.class,
                () -> MakeItFitDate.fromString("29/02/2019"));
        }

        @Test
        @DisplayName("Empty string")
        void invalidEmptyString() {
            assertThrows(IllegalArgumentException.class,
                () -> MakeItFitDate.fromString(""),
                "Empty string");
        }
    */
}
