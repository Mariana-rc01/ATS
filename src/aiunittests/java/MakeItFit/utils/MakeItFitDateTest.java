package MakeItFit.utils;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MakeItFitDateTest {

    @Test
    void testDefaultConstructor() {
        MakeItFitDate date = new MakeItFitDate();
        assertEquals(LocalDate.now(), date.getDate());
    }

    @Test
    void testOfConstructor() {
        MakeItFitDate date = MakeItFitDate.of(2023, 5, 15);
        assertEquals(15, date.getDayOfMonth());
        assertEquals(5, date.getMonth());
        assertEquals(2023, date.getYear());
    }

    @Test
    void testFromStringValid() {
        MakeItFitDate date = MakeItFitDate.fromString("15/05/2023");
        assertEquals(15, date.getDayOfMonth());
        assertEquals(5, date.getMonth());
        assertEquals(2023, date.getYear());
    }

    @Test
    void testFromStringInvalid() {
        assertThrows(IllegalArgumentException.class, () -> MakeItFitDate.fromString("15-05-2023"));
        assertThrows(IllegalArgumentException.class, () -> MakeItFitDate.fromString("15/05"));
        assertThrows(IllegalArgumentException.class, () -> MakeItFitDate.fromString("invalid"));
    }

    @Test
    void testDateComparisons() {
        MakeItFitDate date1 = MakeItFitDate.of(2023, 5, 15);
        MakeItFitDate date2 = MakeItFitDate.of(2023, 5, 16);

        assertTrue(date1.isBefore(date2));
        assertTrue(date2.isAfter(date1));
        assertTrue(date1.isBeforeOrSame(date1));
        assertTrue(date1.isAfterOrSame(date1));
    }

    @Test
    void testPlusDays() {
        MakeItFitDate date    = MakeItFitDate.of(2023, 5, 15);
        MakeItFitDate newDate = date.plusDays(5);
        assertEquals(20, newDate.getDayOfMonth());
    }

    @Test
    void testDistance() {
        MakeItFitDate date1 = MakeItFitDate.of(2023, 5, 15);
        MakeItFitDate date2 = MakeItFitDate.of(2023, 5, 20);
        assertEquals(5, date1.distance(date2));
    }

    @Test
    void testToString() {
        MakeItFitDate date = MakeItFitDate.of(2023, 5, 15);
        assertEquals("15/05/2023", date.toString());
    }

    @Test
    void testEquals() {
        MakeItFitDate date1 = MakeItFitDate.of(2023, 5, 15);
        MakeItFitDate date2 = MakeItFitDate.of(2023, 5, 15);
        MakeItFitDate date3 = MakeItFitDate.of(2023, 5, 16);

        assertEquals(date1, date2);
        assertNotEquals(date1, date3);
    }

    @Test
    void testClone() {
        MakeItFitDate original = MakeItFitDate.of(2023, 5, 15);
        MakeItFitDate cloned   = original.clone();

        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    void testDayOfWeek() {
        MakeItFitDate date = MakeItFitDate.of(2023, 5, 15); // May 15, 2023 is a Monday
        assertEquals(1, date.getDayOfWeek());
    }
}
