package MakeItFit.activities.types;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import MakeItFit.utils.MakeItFitDate;

public class RepetitionsTest {
    private TestRepetitions activity;
    private UUID            userCode;
    private MakeItFitDate   date;

    private MakeItFitDate createDate(int year, int month, int day) {
        return MakeItFitDate.of(year, month, day);
    }

    @BeforeEach
    void setUp() {
        userCode = UUID.randomUUID();
        date     = createDate(2025, 10, 5);
        activity = new TestRepetitions(userCode, date, 5, "Abs", "Plank", 15, 4);
    }

    @Test
    void testGetters() {
        assertEquals(15, activity.getRepetitions());
        assertEquals(4, activity.getSeries());
        assertEquals("Abs", activity.getDesignation());
    }

    @Test
    void testSetters() {
        activity.setRepetitions(20);
        activity.setSeries(5);
        assertEquals(20, activity.getRepetitions());
        assertEquals(5, activity.getSeries());
    }

    @Test
    void testConstructors() {
        TestRepetitions defaultR = new TestRepetitions();
        assertEquals(0, defaultR.getRepetitions());
        assertEquals(0, defaultR.getSeries());

        TestRepetitions copyD = new TestRepetitions(activity);
        assertEquals(activity.getName(), copyD.getName());
        assertEquals(activity.getRepetitions(), copyD.getRepetitions());
        assertEquals(activity.getSeries(), copyD.getSeries());
    }

    @Test
    void testCalculateCaloricWaste() {
        activity.calculateCaloricWaste(1.5f);
        int expected = (int) (15 * 4 * 1.5f * 2);
        assertEquals(expected, activity.getCaloricWaste());
    }

    @Test
    void testCaloricWasteReturn() {
        int expected = (int) (15 * 4 * 1.2f * 2);
        assertEquals(expected, activity.caloricWaste(1.2f));
    }

    @Test
    void testEquals() {
        TestRepetitions same = new TestRepetitions(userCode, date, 5, "Abs", "Plank", 15, 4);
        assertTrue(activity.equals(same));
        assertTrue(activity.equals(activity));

        TestRepetitions diff = new TestRepetitions(userCode, date, 5, "Abs", "Plank", 12, 4);
        assertFalse(activity.equals(diff));
        assertFalse(activity.equals(null));

        TestRepetitions diff2 = new TestRepetitions(userCode, date, 5, "Abs", "Plank", 15, 5);
        assertFalse(activity.equals(diff2));
    }

    @Test
    void testToString() {
        String str = activity.toString();
        assertTrue(str.contains("Repetitions: 15"));
        assertTrue(str.contains("Series: 4"));
    }

    @Test
    void testClone() {
        Repetitions cloned = activity.clone();
        assertNotSame(activity, cloned);
        assertEquals(activity.getRepetitions(), cloned.getRepetitions());
        assertEquals(activity.getSeries(), cloned.getSeries());
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

    public TestRepetitions(Repetitions r) {
        super(r);
    }

    @Override
    public void calculateCaloricWaste(float index) {
        int waste = (int) (getRepetitions() * getSeries() * index * 2);
        setCaloricWaste(waste);
    }

    @Override
    public int caloricWaste(float index) {
        return (int) (getRepetitions() * getSeries() * index * 2);
    }

    @Override
    public Repetitions clone() {
        return new TestRepetitions(this);
    }
}
