package MakeItFit.activities.types;

import java.util.UUID;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RepetitionsTest {
    private TestRepetitions activity;
    private UUID            testUserCode;
    private MakeItFitDate   testDate;

    @BeforeEach
    void setUp() {
        testUserCode = UUID.randomUUID();
        testDate     = MakeItFitDate.of(2023, 5, 15);
        activity = new TestRepetitions(testUserCode, testDate, 30, "Strength", "Push-ups", 15, 3);
    }

    @Test
    void testConstructorWithParameters() {
        assertEquals(testUserCode, activity.getUserCode());
        assertEquals(testDate, activity.getRealizationDate());
        assertEquals(30, activity.getExpectedDuration());
        assertEquals("Strength", activity.getDesignation());
        assertEquals("Push-ups", activity.getName());
        assertEquals(15, activity.getRepetitions());
        assertEquals(3, activity.getSeries());
        assertEquals(0, activity.getCaloricWaste());
    }

    @Test
    void testDefaultConstructor() {
        TestRepetitions defaultActivity = new TestRepetitions();
        assertEquals(0, defaultActivity.getRepetitions());
        assertEquals(0, defaultActivity.getSeries());
        assertEquals(0, defaultActivity.getExpectedDuration());
        assertEquals("", defaultActivity.getDesignation());
        assertEquals("", defaultActivity.getName());
    }

    @Test
    void testCopyConstructor() {
        activity.setDuration(25);
        activity.setCaloricWaste(150);

        TestRepetitions copiedActivity = new TestRepetitions(activity);
        assertEquals(activity.getUserCode(), copiedActivity.getUserCode());
        assertEquals(activity.getRealizationDate(), copiedActivity.getRealizationDate());
        assertEquals(activity.getExpectedDuration(), copiedActivity.getExpectedDuration());
        assertEquals(activity.getDesignation(), copiedActivity.getDesignation());
        assertEquals(activity.getName(), copiedActivity.getName());
        assertEquals(activity.getRepetitions(), copiedActivity.getRepetitions());
        assertEquals(activity.getSeries(), copiedActivity.getSeries());
        assertEquals(activity.getDuration(), copiedActivity.getDuration());
        assertEquals(activity.getCaloricWaste(), copiedActivity.getCaloricWaste());
    }

    @Test
    void testSettersAndGetters() {
        activity.setRepetitions(20);
        assertEquals(20, activity.getRepetitions());

        activity.setSeries(4);
        assertEquals(4, activity.getSeries());
    }

    @Test
    void testCalculateCaloricWaste() {
        activity.setRepetitions(10);
        activity.setSeries(3);
        activity.calculateCaloricWaste(1.2f);

        // Cálculo esperado: (10 * 3 * 0.5) * 1.2 = 15 * 1.2 = 18
        assertEquals(18, activity.getCaloricWaste());
    }

    @Test
    void testCaloricWasteMethod() {
        activity.setRepetitions(12);
        activity.setSeries(4);
        int waste = activity.caloricWaste(1.5f);

        // Cálculo esperado: (12 * 4 * 0.5) * 1.5 = 24 * 1.5 = 36
        assertEquals(36, waste);
    }

    @Test
    void testEquals() {
        TestRepetitions sameActivity =
            new TestRepetitions(testUserCode, testDate, 30, "Strength", "Push-ups", 15, 3);

        TestRepetitions differentReps =
            new TestRepetitions(testUserCode, testDate, 30, "Strength", "Push-ups", 12, 3);

        TestRepetitions differentSeries =
            new TestRepetitions(testUserCode, testDate, 30, "Strength", "Push-ups", 15, 4);

        assertTrue(activity.equals(sameActivity));
        assertFalse(activity.equals(differentReps));
        assertFalse(activity.equals(differentSeries));
        assertFalse(activity.equals(null));
        assertTrue(activity.equals(activity));
    }

    @Test
    void testClone() {
        activity.setDuration(20);
        activity.setCaloricWaste(100);
        activity.setRepetitions(18);
        activity.setSeries(3);

        TestRepetitions clonedActivity = (TestRepetitions) activity.clone();
        assertEquals(activity.getUserCode(), clonedActivity.getUserCode());
        assertEquals(activity.getRealizationDate(), clonedActivity.getRealizationDate());
        assertEquals(activity.getExpectedDuration(), clonedActivity.getExpectedDuration());
        assertEquals(activity.getDesignation(), clonedActivity.getDesignation());
        assertEquals(activity.getName(), clonedActivity.getName());
        assertEquals(activity.getRepetitions(), clonedActivity.getRepetitions());
        assertEquals(activity.getSeries(), clonedActivity.getSeries());
        assertEquals(activity.getDuration(), clonedActivity.getDuration());
        assertEquals(activity.getCaloricWaste(), clonedActivity.getCaloricWaste());
    }

    @Test
    void testToString() {
        String str = activity.toString();
        assertTrue(str.contains("Activity: Push-ups"));
        assertTrue(str.contains("Designation: Strength"));
        assertTrue(str.contains("Expected Duration: 30 minutes"));
        assertTrue(str.contains("Repetitions: 15"));
        assertTrue(str.contains("Series: 3"));
    }

    @Test
    void testEdgeCases() {
        // Teste com valores zero
        TestRepetitions zeroActivity =
            new TestRepetitions(testUserCode, testDate, 0, "Test", "Zero", 0, 0);
        zeroActivity.calculateCaloricWaste(1.0f);
        assertEquals(0, zeroActivity.getCaloricWaste());

        // Teste com valores negativos (deveria ser tratado na classe se necessário)
        TestRepetitions negativeActivity =
            new TestRepetitions(testUserCode, testDate, -10, "Test", "Negative", -5, -2);
        negativeActivity.calculateCaloricWaste(1.0f);
        // Esperado: (-5 * -2 * 0.5) * 1.0 = 5 * 1.0 = 5
        assertEquals(5, negativeActivity.getCaloricWaste());
    }
}

class TestRepetitions extends Repetitions {
    public TestRepetitions(UUID          userCode,
                           MakeItFitDate realizationDate,
                           int           expectedDuration,
                           String        designation,
                           String        name,
                           int           repetitions,
                           int           series) {
        super(userCode, realizationDate, expectedDuration, designation, name, repetitions, series);
    }

    public TestRepetitions() {
        super();
    }

    public TestRepetitions(TestRepetitions r) {
        super(r);
    }

    @Override
    public void calculateCaloricWaste(float index) {
        // Cálculo simples baseado em repetições e séries
        int calories = (int) ((getRepetitions() * getSeries() * 0.5) * index);
        setCaloricWaste(calories);
    }

    @Override
    public int caloricWaste(float index) {
        return (int) ((getRepetitions() * getSeries() * 0.5) * index);
    }

    @Override
    public Repetitions clone() {
        return new TestRepetitions(this);
    }
}
