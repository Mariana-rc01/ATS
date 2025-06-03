package MakeItFit.time;

import java.time.LocalDate;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeManagerTest {

    @Test
    public void testDefaultConstructor() {
        TimeManager tm    = new TimeManager();
        LocalDate   today = LocalDate.now();
        assertEquals(today.getDayOfMonth(), tm.getCurrentDate().getDayOfMonth());
        assertEquals(today.getMonthValue(), tm.getCurrentDate().getMonth());
        assertEquals(today.getYear(), tm.getCurrentDate().getYear());
    }

    @Test
    public void testParameterizedConstructor() {
        MakeItFitDate testDate = MakeItFitDate.of(2024, 5, 15);
        TimeManager   tm       = new TimeManager(testDate);
        assertEquals(testDate, tm.getCurrentDate());
    }

    @Test
    public void testGetCurrentDate() {
        MakeItFitDate expectedDate = MakeItFitDate.of(2023, 1, 1);
        TimeManager   tm           = new TimeManager(expectedDate);
        assertEquals(expectedDate, tm.getCurrentDate());
    }

    @Test
    public void testAdvanceTimePositiveDays() {
        MakeItFitDate initialDate = MakeItFitDate.of(2023, 1, 1);
        TimeManager   tm          = new TimeManager(initialDate);
        MakeItFitDate newDate     = tm.advanceTime(5);
        assertEquals(6, newDate.getDayOfMonth());
        assertEquals(1, newDate.getMonth());
        assertEquals(2023, newDate.getYear());
        assertEquals(newDate, tm.getCurrentDate());
    }

    @Test
    public void testAdvanceTimeZeroDays() {
        MakeItFitDate initialDate = MakeItFitDate.of(2023, 1, 1);
        TimeManager   tm          = new TimeManager(initialDate);
        MakeItFitDate newDate     = tm.advanceTime(0);
        assertEquals(initialDate, newDate);
        assertEquals(initialDate, tm.getCurrentDate());
    }

    @Test
    public void testAdvanceTimeNegativeDays() {
        MakeItFitDate initialDate = MakeItFitDate.of(2023, 1, 1);
        TimeManager   tm          = new TimeManager(initialDate);
        assertThrows(IllegalArgumentException.class, () -> tm.advanceTime(-1));
    }

    @Test
    public void testAdvanceTimeAcrossMonthBoundary() {
        MakeItFitDate initialDate = MakeItFitDate.of(2023, 1, 30);
        TimeManager   tm          = new TimeManager(initialDate);
        MakeItFitDate newDate     = tm.advanceTime(2);
        assertEquals(1, newDate.getDayOfMonth());
        assertEquals(2, newDate.getMonth());
        assertEquals(2023, newDate.getYear());
    }

    @Test
    public void testAdvanceTimeAcrossYearBoundary() {
        MakeItFitDate initialDate = MakeItFitDate.of(2023, 12, 31);
        TimeManager   tm          = new TimeManager(initialDate);
        MakeItFitDate newDate     = tm.advanceTime(1);
        assertEquals(1, newDate.getDayOfMonth());
        assertEquals(1, newDate.getMonth());
        assertEquals(2024, newDate.getYear());
    }

    @Test
    public void testAdvanceTimeLeapYear() {
        // 2024 is a leap year
        MakeItFitDate initialDate = MakeItFitDate.of(2024, 2, 28);
        TimeManager   tm          = new TimeManager(initialDate);

        // Advance 1 day - should be Feb 29
        MakeItFitDate newDate = tm.advanceTime(1);
        assertEquals(29, newDate.getDayOfMonth());
        assertEquals(2, newDate.getMonth());
        assertEquals(2024, newDate.getYear());

        // Advance 1 more day - should be Mar 1
        newDate = tm.advanceTime(1);
        assertEquals(1, newDate.getDayOfMonth());
        assertEquals(3, newDate.getMonth());
        assertEquals(2024, newDate.getYear());
    }
}
