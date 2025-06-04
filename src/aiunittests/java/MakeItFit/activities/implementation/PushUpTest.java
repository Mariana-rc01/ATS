package MakeItFit.activities.implementation;

import java.util.UUID;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PushUpTest {

    @Test
    void testConstructorWithParameters() {
        UUID          userCode = UUID.randomUUID();
        MakeItFitDate date     = new MakeItFitDate();
        PushUp        pushUp   = new PushUp(userCode, date, 15, "Strength", "Push Up", 15, 3);

        assertEquals(userCode, pushUp.getUserCode());
        assertEquals(date, pushUp.getRealizationDate());
        assertEquals(15, pushUp.getExpectedDuration());
        assertEquals("Strength", pushUp.getDesignation());
        assertEquals("Push Up", pushUp.getName());
        assertEquals(15, pushUp.getRepetitions());
        assertEquals(3, pushUp.getSeries());
    }

    @Test
    void testDefaultConstructor() {
        PushUp pushUp = new PushUp();

        assertNotNull(pushUp.getUserCode());
        assertNotNull(pushUp.getRealizationDate());
        assertEquals(0, pushUp.getExpectedDuration());
        assertEquals(0, pushUp.getRepetitions());
        assertEquals(0, pushUp.getSeries());
    }

    @Test
    void testCopyConstructor() {
        PushUp original =
            new PushUp(UUID.randomUUID(), new MakeItFitDate(), 10, "Test", "Push Up", 10, 4);

        PushUp copy = new PushUp(original);

        assertEquals(original.getUserCode(), copy.getUserCode());
        assertEquals(original.getRealizationDate(), copy.getRealizationDate());
        assertEquals(original.getExpectedDuration(), copy.getExpectedDuration());
        assertEquals(original.getDesignation(), copy.getDesignation());
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getRepetitions(), copy.getRepetitions());
        assertEquals(original.getSeries(), copy.getSeries());
    }

    @Test
    void testCaloricWaste() {
        PushUp pushUp = new PushUp();
        pushUp.setRepetitions(20);
        pushUp.setSeries(3);

        float index    = 1.0f;
        int   expected = (int) (20 * 3 * 1.0 * 0.1);
        assertEquals(expected, pushUp.caloricWaste(index));

        index    = 1.5f;
        expected = (int) (20 * 3 * 1.5 * 0.1);
        assertEquals(expected, pushUp.caloricWaste(index));
    }

    @Test
    void testCalculateCaloricWaste() {
        PushUp pushUp = new PushUp();
        pushUp.setRepetitions(15);
        pushUp.setSeries(4);

        float index = 1.2f;
        pushUp.calculateCaloricWaste(index);

        int expected = (int) (15 * 4 * 1.2 * 0.1);
        assertEquals(expected, pushUp.getCaloricWaste());
    }

    @Test
    void testToString() {
        PushUp pushUp = new PushUp();
        pushUp.setRepetitions(12);
        pushUp.setSeries(5);

        String result = pushUp.toString();
        assertTrue(result.contains("12"));
        assertTrue(result.contains("5"));
    }

    @Test
    void testEquals() {
        UUID          userCode = UUID.randomUUID();
        MakeItFitDate date     = new MakeItFitDate();
        PushUp        pushUp1  = new PushUp(userCode, date, 20, "Workout", "Push Up", 15, 3);
        PushUp        pushUp2  = new PushUp(userCode, date, 20, "Workout", "Push Up", 15, 3);
        PushUp        pushUp3  = new PushUp(userCode, date, 20, "Workout", "Push Up", 12, 3);

        assertEquals(pushUp1, pushUp2);
        assertNotEquals(pushUp1, pushUp3);
        assertNotEquals(pushUp1, null);
        assertNotEquals(pushUp1, new Object());
    }

    @Test
    void testClone() {
        PushUp original =
            new PushUp(UUID.randomUUID(), new MakeItFitDate(), 15, "Exercise", "Push Up", 10, 4);

        PushUp clone = original.clone();

        assertEquals(original, clone);
        assertNotSame(original, clone);
    }
}
