package MakeItFit.activities.implementation;

import java.util.UUID;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RunningTest {

    @Test
    void testConstructorWithParameters() {
        UUID          userCode = UUID.randomUUID();
        MakeItFitDate date     = new MakeItFitDate();
        Running       running  = new Running(userCode, date, 45, "Morning Run", "5K", 5.0, 10.5);

        assertEquals(userCode, running.getUserCode());
        assertEquals(date, running.getRealizationDate());
        assertEquals(45, running.getExpectedDuration());
        assertEquals("Morning Run", running.getDesignation());
        assertEquals("5K", running.getName());
        assertEquals(5.0, running.getDistance());
        assertEquals(10.5, running.getSpeed());
    }

    @Test
    void testDefaultConstructor() {
        Running running = new Running();

        assertNotNull(running.getUserCode());
        assertNotNull(running.getRealizationDate());
        assertEquals(0, running.getExpectedDuration());
        assertEquals(0.0, running.getDistance());
        assertEquals(0.0, running.getSpeed());
    }

    @Test
    void testCopyConstructor() {
        Running original = new Running(UUID.randomUUID(),
                                       new MakeItFitDate(),
                                       30,
                                       "Sprint",
                                       "Interval",
                                       3.0,
                                       12.0);

        Running copy = new Running(original);

        assertEquals(original.getUserCode(), copy.getUserCode());
        assertEquals(original.getRealizationDate(), copy.getRealizationDate());
        assertEquals(original.getExpectedDuration(), copy.getExpectedDuration());
        assertEquals(original.getDesignation(), copy.getDesignation());
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getDistance(), copy.getDistance());
        assertEquals(original.getSpeed(), copy.getSpeed());
    }

    @Test
    void testSetSpeed() {
        Running running = new Running();

        running.setSpeed(10.5);
        assertEquals(10.5, running.getSpeed());

        running.setSpeed(0);
        assertEquals(0, running.getSpeed());

        running.setSpeed(-5.0);
        assertEquals(-5.0, running.getSpeed());
    }

    @Test
    void testCaloricWaste() {
        Running running = new Running();
        running.setDistance(10.0);
        running.setSpeed(12.0);

        float index    = 1.0f;
        int   expected = (int) (12.0 * 10.0 * 1.0 * 0.005);
        assertEquals(expected, running.caloricWaste(index));

        index    = 1.2f;
        expected = (int) (12.0 * 10.0 * 1.2 * 0.005);
        assertEquals(expected, running.caloricWaste(index));
    }

    @Test
    void testCalculateCaloricWaste() {
        Running running = new Running();
        running.setDistance(8.0);
        running.setSpeed(10.0);

        float index = 1.5f;
        running.calculateCaloricWaste(index);

        int expected = (int) (10.0 * 8.0 * 1.5 * 0.005);
        assertEquals(expected, running.getCaloricWaste());
    }

    @Test
    void testToString() {
        Running running = new Running();
        running.setDistance(21.1);
        running.setSpeed(11.5);

        String result = running.toString();
        assertTrue(result.contains("21.1"));
        assertTrue(result.contains("11.5 Km/h"));
    }

    @Test
    void testEquals() {
        UUID          userCode = UUID.randomUUID();
        MakeItFitDate date     = new MakeItFitDate();
        Running running1 = new Running(userCode, date, 60, "Long Run", "Half Marathon", 21.1, 10.5);
        Running running2 = new Running(userCode, date, 60, "Long Run", "Half Marathon", 21.1, 10.5);
        Running running3 = new Running(userCode, date, 60, "Long Run", "Half Marathon", 21.1, 11.0);

        assertEquals(running1, running2);
        assertNotEquals(running1, running3);
        assertNotEquals(running1, null);
        assertNotEquals(running1, new Object());
    }

    @Test
    void testClone() {
        Running original = new Running(UUID.randomUUID(),
                                       new MakeItFitDate(),
                                       45,
                                       "Recovery",
                                       "Easy Run",
                                       5.0,
                                       9.5);

        Running clone = original.clone();

        assertEquals(original, clone);
        assertNotSame(original, clone);
    }
}
