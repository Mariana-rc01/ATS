package MakeItFit.activities.implementation;

import java.util.UUID;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeightSquatTest {

    private UUID          userCode;
    private MakeItFitDate date;
    private WeightSquat   squat;

    private MakeItFitDate createDate(int year, int month, int day) {
        return MakeItFitDate.of(year, month, day);
    }

    @BeforeEach
    void setUp() {
        userCode = UUID.randomUUID();
        date     = createDate(2025, 12, 25);
        squat    = new WeightSquat(userCode, date, 30, "Get ready for summer!", "Squat", 10, 3, 50);
    }

    @Test
    void testGetters() {
        assertEquals(userCode, squat.getUserCode());
        assertEquals(date, squat.getRealizationDate());
        assertEquals(30, squat.getExpectedDuration());
        assertEquals("Get ready for summer!", squat.getDesignation());
        assertEquals("Squat", squat.getName());
        assertEquals(10, squat.getRepetitions());
        assertEquals(3, squat.getSeries());
        assertEquals(50, squat.getWeight());
        assertEquals("WeightSquat", squat.getSpecialization());
    }

    @Test
    void testConstructors() {
        WeightSquat defaultSquat = new WeightSquat();
        assertEquals("WeightSquat", defaultSquat.getSpecialization());
        assertEquals(0, defaultSquat.getRepetitions());
        assertEquals(0, defaultSquat.getSeries());
        assertEquals(0, defaultSquat.getWeight());

        WeightSquat copy = new WeightSquat(squat);
        assertEquals(squat, copy);
        assertNotSame(squat, copy);
    }

    @Test
    void testCaloricWasteCalculation() {
        float index    = 0f;
        int   expected = 0;
        assertEquals(expected, squat.caloricWaste(index));

        index    = 1f;
        expected = (int) (10 * 3 * 50 * 0.2 * index * 0.5);
        assertEquals(expected, squat.caloricWaste(index));

        index    = -1f;
        expected = (int) (10 * 3 * 50 * 0.2 * index * 0.5);
        assertEquals(expected, squat.caloricWaste(index));

        index    = Float.MAX_VALUE;
        expected = (int) (10 * 3 * 50 * 0.2 * index * 0.5);
        assertEquals(expected, squat.caloricWaste(index));

        index    = Float.MIN_VALUE;
        expected = (int) (10 * 3 * 50 * 0.2 * index * 0.5);
        assertEquals(expected, squat.caloricWaste(index));
    }

    @Test
    void testCalculateCaloricWasteSetsValue() {
        float index = 1f;
        squat.calculateCaloricWaste(index);
        assertEquals(squat.caloricWaste(index), squat.getCaloricWaste());
    }

    @Test
    void testToString() {
        assertNotNull(squat.toString());
        assertNotEquals("", squat.toString());
    }

    @Test
    void testEquals() {
        WeightSquat equalSquat =
            new WeightSquat(userCode, date, 31, "Get ready for summer!", "Squat", 10, 3, 50);
        assertTrue(squat.equals(squat));

        assertFalse(squat.equals(equalSquat));
        assertFalse(squat.equals(null));
        assertNotEquals(squat, new Object());
    }

    @Test
    void testClone() {
        WeightSquat clone = squat.clone();
        assertEquals(squat, clone);
        assertNotSame(squat, clone);
    }
}
