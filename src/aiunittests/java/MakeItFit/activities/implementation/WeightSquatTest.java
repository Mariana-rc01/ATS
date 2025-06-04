package MakeItFit.activities.implementation;

import java.util.UUID;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeightSquatTest {

    @Test
    void testConstructorWithParameters() {
        UUID          userCode = UUID.randomUUID();
        MakeItFitDate date     = new MakeItFitDate();
        WeightSquat   squat =
            new WeightSquat(userCode, date, 30, "Leg Workout", "Barbell Squat", 12, 4, 60.0);

        assertEquals(userCode, squat.getUserCode());
        assertEquals(date, squat.getRealizationDate());
        assertEquals(30, squat.getExpectedDuration());
        assertEquals("Leg Workout", squat.getDesignation());
        assertEquals("Barbell Squat", squat.getName());
        assertEquals(12, squat.getRepetitions());
        assertEquals(4, squat.getSeries());
        assertEquals(60.0, squat.getWeight());
    }

    @Test
    void testDefaultConstructor() {
        WeightSquat squat = new WeightSquat();

        assertNotNull(squat.getUserCode());
        assertNotNull(squat.getRealizationDate());
        assertEquals(0, squat.getExpectedDuration());
        assertEquals(0, squat.getRepetitions());
        assertEquals(0, squat.getSeries());
        assertEquals(0, squat.getWeight());
    }

    @Test
    void testCopyConstructor() {
        UUID          userCode = UUID.randomUUID();
        MakeItFitDate date     = new MakeItFitDate();
        WeightSquat   original =
            new WeightSquat(userCode, date, 30, "Leg Workout", "Barbell Squat", 12, 4, 60.0);

        WeightSquat copy = new WeightSquat(original);

        assertEquals(original.getUserCode(), copy.getUserCode());
        assertEquals(original.getRealizationDate(), copy.getRealizationDate());
        assertEquals(original.getExpectedDuration(), copy.getExpectedDuration());
        assertEquals(original.getDesignation(), copy.getDesignation());
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getRepetitions(), copy.getRepetitions());
        assertEquals(original.getSeries(), copy.getSeries());
        assertEquals(original.getWeight(), copy.getWeight());
    }

    @Test
    void testCaloricWasteCalculation() {
        WeightSquat squat = new WeightSquat();
        squat.setRepetitions(10);
        squat.setSeries(3);
        squat.setWeight(50.0);

        float index            = 1.0f;
        int   expectedCalories = (int) (10 * 3 * 50.0 * 0.2 * 1.0 * 0.5);
        assertEquals(expectedCalories, squat.caloricWaste(index));

        // Test with different index
        index            = 1.5f;
        expectedCalories = (int) (10 * 3 * 50.0 * 0.2 * 1.5 * 0.5);
        assertEquals(expectedCalories, squat.caloricWaste(index));
    }

    @Test
    void testCalculateCaloricWasteMethod() {
        WeightSquat squat = new WeightSquat();
        squat.setRepetitions(8);
        squat.setSeries(4);
        squat.setWeight(70.0);

        float index = 1.2f;
        squat.calculateCaloricWaste(index);

        int expectedCalories = (int) (8 * 4 * 70.0 * 0.2 * 1.2 * 0.5);
        assertEquals(expectedCalories, squat.getCaloricWaste());
    }

    @Test
    void testToString() {
        WeightSquat squat = new WeightSquat();
        squat.setRepetitions(8);
        squat.setSeries(3);
        squat.setWeight(40.0);

        String result = squat.toString();
        assertTrue(result.contains("8"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("40.0 Kg"));
    }

    @Test
    void testEquals() {
        UUID          userCode = UUID.randomUUID();
        MakeItFitDate date     = new MakeItFitDate();
        WeightSquat   squat1 =
            new WeightSquat(userCode, date, 30, "Leg Day", "Back Squat", 10, 3, 80.0);
        WeightSquat squat2 =
            new WeightSquat(userCode, date, 30, "Leg Day", "Back Squat", 10, 3, 80.0);
        WeightSquat squat3 =
            new WeightSquat(userCode, date, 30, "Leg Day", "Back Squat", 10, 3, 85.0);

        // Test equality with same values
        assertEquals(squat1, squat2);

        // Test inequality with different weight
        assertNotEquals(squat1, squat3);

        // Test inequality with null
        assertNotEquals(squat1, null);

        // Test inequality with different class
        assertNotEquals(squat1, new Object());

        // Test reflexivity
        assertEquals(squat1, squat1);
    }

    @Test
    void testClone() {
        UUID          userCode = UUID.randomUUID();
        MakeItFitDate date     = new MakeItFitDate();
        WeightSquat   original =
            new WeightSquat(userCode, date, 45, "Heavy Legs", "Overhead Squat", 6, 5, 30.0);

        WeightSquat clone = original.clone();

        // Test equality
        assertEquals(original, clone);

        // Test that it's a different object
        assertNotSame(original, clone);

        // Test that changes to clone don't affect original
        clone.setWeight(35.0);
        assertNotEquals(original.getWeight(), clone.getWeight());
    }
}
