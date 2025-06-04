package MakeItFit.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import MakeItFit.utils.MakeItFitDate;

class TimeManagerTest {

    private MakeItFitDate date;

    @BeforeEach
    void setup() {
        date = MakeItFitDate.of(2024, 5, 10);
    }

    @Test
    void testDefaultConstructorInitializesWithCurrentDate() {
        TimeManager manager = new TimeManager();
        assertNotNull(manager.getCurrentDate());
    }

    @Test
    void testConstructorWithCustomDate() {
        TimeManager manager = new TimeManager(date);
        assertEquals(date, manager.getCurrentDate());
    }

    @Test
    void testGetCurrentDateReturnsCorrectDate() {
        TimeManager manager = new TimeManager(date);
        MakeItFitDate current = manager.getCurrentDate();
        assertEquals(date, current);
    }

    @Test
    void testAdvanceTimeWithPositiveDays() {
        TimeManager manager = new TimeManager(date);
        MakeItFitDate advancedDate = manager.advanceTime(5);

        MakeItFitDate expectedDate = date.plusDays(5);
        assertEquals(expectedDate, advancedDate);
        assertEquals(expectedDate, manager.getCurrentDate());
    }

    @Test
    void testAdvanceTimeWithZeroDays() {
        TimeManager manager = new TimeManager(date);
        MakeItFitDate advancedDate = manager.advanceTime(0);

        assertEquals(date, advancedDate);
    }

    @Test
    void testAdvanceTimeWithNegativeDaysThrowsException() {
        TimeManager manager = new TimeManager(date);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.advanceTime(-1);
        });
        assertEquals("Days must be a positive number.", exception.getMessage());
    }
}
