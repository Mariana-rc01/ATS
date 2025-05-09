package MakeItFit.activities.implementation;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PushUpTest {
    private PushUp pushUp;
    private UUID userCode;
    private MakeItFitDate date;

    private MakeItFitDate createDate(int year, int month, int day) {
        return MakeItFitDate.of(year, month, day);
    }

    @BeforeEach
    void setUp() {
        userCode = UUID.randomUUID();
        date = createDate(2025,10,5);
        pushUp = new PushUp(userCode, date, 30, "You got this!", "Push-Ups", 5, 4);
    }

    @Test
    void testGetters() {
        assertEquals(5, pushUp.getRepetitions());
        assertEquals(4, pushUp.getSeries());
        assertEquals("PushUp", pushUp.getSpecialization());
        assertEquals("Push-Ups", pushUp.getName());
        assertEquals("You got this!", pushUp.getDesignation());
        assertEquals(30, pushUp.getExpectedDuration());
        assertEquals(date, pushUp.getRealizationDate());
        assertEquals(userCode, pushUp.getUserCode());
    }

    @Test
    void testConstructor() {
        PushUp defaultP = new PushUp();
        assertEquals(0, defaultP.getRepetitions());
        assertEquals(0, defaultP.getSeries());
        assertEquals("PushUp", defaultP.getSpecialization());

        PushUp copyP = new PushUp(pushUp);
        assertEquals(pushUp, copyP);
        assertNotSame(pushUp, copyP);
    }

    @Test
    void testCalculateCaloricWaste() {
        float index = 2f;
        int expected = (int) (5 * 4 * 2 * 0.1);
        pushUp.calculateCaloricWaste(index);
        assertEquals(expected, pushUp.getCaloricWaste());
    }

    @Test
    void testCaloricWasteMethod() {
        float index = 1.5f;
        int expected = (int) (5 * 4 * 1.5 * 0.1);
        assertEquals(expected, pushUp.caloricWaste(index));
    }

    @Test
    void testToString() {
        String output = pushUp.toString();
        assertTrue(output.contains("Repetitions: 5"));
        assertTrue(output.contains("Series: 4"));
    }

    @Test
    void testEquals() {
        PushUp same = new PushUp(userCode, date, 30, "You got this!", "Push-Ups", 5, 4);
        assertTrue(pushUp.equals(same));
        assertTrue(pushUp.equals(pushUp));

        PushUp diff = new PushUp(userCode, date, 30, "You got this!", "Push-Ups", 15, 4);
        assertFalse(pushUp.equals(diff));
        assertFalse(pushUp.equals(null));
    }

    @Test
    void testClone() {
        PushUp cloned = pushUp.clone();
        assertNotSame(pushUp, cloned);
        assertEquals(pushUp, cloned);
    }
}
