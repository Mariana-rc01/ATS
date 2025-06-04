package MakeItFit.utils;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class MakeItFitDateTest {
    @Test
    void testConstructor() {
        MakeItFitDate currentDate = new MakeItFitDate();
        assertTrue(LocalDate.now().isBefore(currentDate.getDate()) ||
                   LocalDate.now().isEqual(currentDate.getDate()));
    }

    @Test
    void testFromStringValidStandardFormat() {
        MakeItFitDate date = MakeItFitDate.fromString("04/06/2025");
        assertEquals(4, date.getDayOfMonth());
        assertEquals(6, date.getMonth());
        assertEquals(2025, date.getYear());
        assertEquals(3, date.getDayOfWeek());
    }

    @Test
    void testFromStringValidBoundary() {
        MakeItFitDate date = MakeItFitDate.fromString("31/12/1999");
        assertEquals(31, date.getDayOfMonth());
        assertEquals(12, date.getMonth());
        assertEquals(1999, date.getYear());
    }

    @Test
    void testFromStringInvalidDelimiter() {
        assertThrows(IllegalArgumentException.class, () -> MakeItFitDate.fromString("04-06-2025"));
    }

    @Test
    void testFromStringInvalidPartsCount() {
        assertThrows(IllegalArgumentException.class, () -> MakeItFitDate.fromString("01/01"));
    }

    @Test
    void testFromStringInvalidNonNumeric() {
        assertThrows(IllegalArgumentException.class, () -> MakeItFitDate.fromString("aa/bb/cccc"));
    }

    @Test
    void testFromStringInvalidEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> MakeItFitDate.fromString(""));
    }

    @Test
    void testsFromStringInvalidNullString() {
        assertThrows(NullPointerException.class, () -> MakeItFitDate.fromString(null));
    }

    @Test
    void testsToStringFormat() {
        assertEquals("04/06/2025", MakeItFitDate.of(2025, 6, 4).toString());
    }

    @Test
    void testCrossMonthBoundary() {
        assertEquals("02/02/2021", MakeItFitDate.fromString("28/01/2021").plusDays(5).toString());
    }

    @Test
    void testCompareToOrdering() {
        assertTrue(MakeItFitDate.fromString("01/01/2025")
                       .compareTo(MakeItFitDate.fromString("15/06/2025")) < 0);
        assertTrue(MakeItFitDate.fromString("15/06/2025")
                       .compareTo(MakeItFitDate.fromString("01/01/2025")) > 0);
    }

    @Test
    void testIsBefore() {
        assertTrue(MakeItFitDate.fromString("01/01/2025")
                       .isBefore(MakeItFitDate.fromString("15/06/2025")));
    }

    @Test
    void testIsBefore2() {
        assertFalse(MakeItFitDate.fromString("15/06/2025")
                        .isBefore(MakeItFitDate.fromString("01/01/2025")));
    }

    @Test
    void testIsBefore3() {
        assertFalse(MakeItFitDate.fromString("01/01/2025")
                        .isBefore(MakeItFitDate.fromString("01/01/2025")));
    }

    @Test
    void testIsBeforeOrSame() {
        assertTrue(MakeItFitDate.fromString("01/01/2025")
                       .isBeforeOrSame(MakeItFitDate.fromString("15/06/2025")));
    }

    @Test
    void testIsBeforeOrSame2() {
        assertFalse(MakeItFitDate.fromString("15/06/2025")
                        .isBeforeOrSame(MakeItFitDate.fromString("01/01/2025")));
    }

    @Test
    void testIsBeforeOrSame3() {
        assertTrue(MakeItFitDate.fromString("01/01/2025")
                       .isBeforeOrSame(MakeItFitDate.fromString("01/01/2025")));
    }

    @Test
    void testIsAfter() {
        assertFalse(
            MakeItFitDate.fromString("01/01/2025").isAfter(MakeItFitDate.fromString("15/06/2025")));
    }

    @Test
    void testIsAfter2() {
        assertTrue(
            MakeItFitDate.fromString("15/06/2025").isAfter(MakeItFitDate.fromString("01/01/2025")));
    }

    @Test
    void testIsAfter3() {
        assertFalse(
            MakeItFitDate.fromString("01/01/2025").isAfter(MakeItFitDate.fromString("01/01/2025")));
    }

    @Test
    void testIsAfterOrSame() {
        assertFalse(MakeItFitDate.fromString("01/01/2025")
                        .isAfterOrSame(MakeItFitDate.fromString("15/06/2025")));
    }

    @Test
    void testIsAfterOrSame2() {
        assertTrue(MakeItFitDate.fromString("15/06/2025")
                       .isAfterOrSame(MakeItFitDate.fromString("01/01/2025")));
    }

    @Test
    void testIsAfterOrSame3() {
        assertTrue(MakeItFitDate.fromString("01/01/2025")
                       .isAfterOrSame(MakeItFitDate.fromString("01/01/2025")));
    }

    @Test
    void testDistanceReturnsZero() {
        assertEquals(0,
                     MakeItFitDate.fromString("04/06/2025")
                         .distance(MakeItFitDate.fromString("04/06/2025")));
    }

    @Test
    void testDistanceAdjacentDays() {
        assertEquals(1,
                     MakeItFitDate.fromString("10/10/2025")
                         .distance(MakeItFitDate.fromString("11/10/2025")));
    }

    @Test
    void testDistanceAcrossMonths() {
        assertEquals(5,
                     MakeItFitDate.fromString("28/01/2025")
                         .distance(MakeItFitDate.fromString("02/02/2025")));
    }

    @Test
    void testDistanceAcrossYears() {
        assertEquals(7305,
                     MakeItFitDate.fromString("01/01/2000")
                         .distance(MakeItFitDate.fromString("01/01/2020")));
    }

    @Test
    void testEqualsTrue() {
        assertTrue(
            MakeItFitDate.fromString("15/05/2025").equals(MakeItFitDate.fromString("15/05/2025")));
    }

    @Test
    void testEqualsFalse() {
        assertFalse(
            MakeItFitDate.fromString("15/05/2015").equals(MakeItFitDate.fromString("16/05/2015")));
    }

    @Test
    void testClone() {
        MakeItFitDate date      = MakeItFitDate.of(2025, 06, 04);
        MakeItFitDate dateClone = date.clone();

        assertTrue(dateClone.equals(date));
        assertNotSame(dateClone, date);
    }
}
