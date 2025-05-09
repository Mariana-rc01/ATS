package MakeItFit.activities.types;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RepetitionsWithWeightsTest {
    private TestRepetitionsWithWeights activity;
    private UUID userCode;
    private MakeItFitDate date;

    private MakeItFitDate createDate(int year, int month, int day) {
        return MakeItFitDate.of(year, month, day);
    }

    @BeforeEach
    void setUp() {
        userCode = UUID.randomUUID();
        date = createDate(2025, 10, 5);
        activity = new TestRepetitionsWithWeights(userCode, date, 45, "BodyPump", "Squats", 10, 3, 20);
    }

    @Test
    void testGetters() {
        assertEquals(10, activity.getRepetitions());
        assertEquals(3, activity.getSeries());
        assertEquals(20, activity.getWeight());
    }

    @Test
    void testSetters() {
        activity.setWeight(25.5);
        assertEquals(25.5, activity.getWeight());
    }

    @Test
    void testConstructors() {
        TestRepetitionsWithWeights defaultRW = new TestRepetitionsWithWeights();
        assertEquals(0, defaultRW.getWeight());
        assertEquals(0, defaultRW.getSeries());

        TestRepetitionsWithWeights copyRW = new TestRepetitionsWithWeights(activity);
        assertEquals(activity.getWeight(), copyRW.getWeight());
        assertEquals(activity.getRepetitions(), copyRW.getRepetitions());
    }

    @Test
    void testCalculateCaloricWaste() {
        activity.calculateCaloricWaste(2f);
        int expected = (int) (10 * 3 * 20 * 2 * 0.5);
        assertEquals(expected, activity.getCaloricWaste());
    }

    @Test
    void testCaloricWasteReturn() {
        int expected = (int) (10 * 3 * 25 * 1.5 * 0.5);
        activity.setWeight(25);
        assertEquals(expected, activity.caloricWaste(1.5f));
    }

    @Test
    void testEquals() {
        TestRepetitionsWithWeights same = new TestRepetitionsWithWeights(userCode, date, 45, "BodyPump", "Squats", 10, 3, 20);
        assertTrue(activity.equals(same));
        assertTrue(activity.equals(activity));

        TestRepetitionsWithWeights diff = new TestRepetitionsWithWeights(userCode, date, 45, "BodyPump", "Squats", 10, 3, 25);
        assertFalse(activity.equals(diff));
        assertFalse(activity.equals(null));
    }

    @Test
    void testToString() {
        String str = activity.toString();
        assertTrue(str.contains("Weight: 20.0 Kg"));
    }

    @Test
    void testClone() {
        RepetitionsWithWeights cloned = activity.clone();
        assertNotSame(activity, cloned);
        assertEquals(activity.getWeight(), cloned.getWeight());
        assertEquals(activity.getSeries(), cloned.getSeries());
    }
}

class TestRepetitionsWithWeights extends RepetitionsWithWeights {
    public TestRepetitionsWithWeights(UUID userCode, MakeItFitDate realizationDate, int expectedDuration,
                                      String designation, String name, int repetitions, int series, double weight) {
        super(userCode, realizationDate, expectedDuration, designation, name, repetitions, series, weight);
    }

    public TestRepetitionsWithWeights() {
        super();
    }

    public TestRepetitionsWithWeights(RepetitionsWithWeights r) {
        super(r);
    }

    @Override
    public void calculateCaloricWaste(float index) {
        int waste = (int) (getRepetitions() * getSeries() * getWeight() * index * 0.5);
        setCaloricWaste(waste);
    }

    @Override
    public int caloricWaste(float index) {
        return (int) (getRepetitions() * getSeries() * getWeight() * index * 0.5);
    }

    @Override
    public RepetitionsWithWeights clone() {
        return new TestRepetitionsWithWeights(this);
    }
}

