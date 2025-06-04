package MakeItFit.activities.implementation;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import MakeItFit.utils.MakeItFitDate;

public class RunningTest {
    private Running       running;
    private UUID          userCode;
    private MakeItFitDate date;

    private MakeItFitDate createDate(int year, int month, int day) {
        return MakeItFitDate.of(year, month, day);
    }

    @BeforeEach
    void setUp() {
        userCode = UUID.randomUUID();
        date     = createDate(2025, 12, 25);
        running  = new Running(userCode, date, 45, "Warming", "Just for 45 minutes", 5000.0, 10.0);
    }

    @Test
    void testGetters() {
        assertEquals(5000.0, running.getDistance());
        assertEquals(10.0, running.getSpeed());
        assertEquals("Running", running.getSpecialization());
        assertEquals("Just for 45 minutes", running.getName());
        assertEquals("Warming", running.getDesignation());
        assertEquals(45, running.getExpectedDuration());
        assertEquals(date, running.getRealizationDate());
        assertEquals(userCode, running.getUserCode());
    }

    @Test
    void testConstructors() {
        Running defaultRunning = new Running();
        assertEquals(0.0, defaultRunning.getDistance());
        defaultRunning.setSpeed(0.0);
        assertEquals(0.0, defaultRunning.getSpeed());
        assertEquals("Running", defaultRunning.getSpecialization());

        Running copy = new Running(running);
        assertEquals(running, copy);
        assertNotSame(running, copy);
    }

    @Test
    void testCalculateCaloricWaste() {
        float index    = 1.5f;
        int   expected = (int) (10.0 * 5000.0 * 1.5 * 0.005);
        running.calculateCaloricWaste(index);
        assertEquals(expected, running.getCaloricWaste());
    }

    @Test
    void testCaloricWasteMethod() {
        float index    = 2.0f;
        int   expected = (int) (10.0 * 5000.0 * 2.0 * 0.005);
        assertEquals(expected, running.caloricWaste(index));
    }

    @Test
    void testToString() {
        String output = running.toString();
        assertTrue(output.contains("Speed: 10.0 Km/h"));
    }

    @Test
    void testEquals() {
        Running same =
            new Running(userCode, date, 45, "Warming", "Just for 45 minutes", 5000.0, 10.0);
        assertTrue(running.equals(same));
        assertTrue(running.equals(running));

        Running diff =
            new Running(userCode, date, 45, "Warming", "Just for 45 minutes", 5000.0, 12.0);
        assertFalse(running.equals(diff));
        assertFalse(running.equals(null));
        Running diffSpeed = new Running(userCode, date, 45, "Warming", "Just for 45 minutes", 5000.0, 13.0);
        assertFalse(running.equals(diffSpeed));

        Running diffInSpeed =
            new Running(userCode, date, 50, "Warming", "Just for 45 minutes", 5000.0, 10.0);
        assertFalse(running.equals(diffInSpeed));
    }

    @Test
    void testClone() {
        Running cloned = running.clone();
        assertNotSame(running, cloned);
        assertEquals(running, cloned);
    }
}
