package MakeItFit.activities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import MakeItFit.utils.MakeItFitDate;

import java.util.UUID;

public class ActivityTest {

    private MakeItFitDate createDate(int year, int month, int day) {
        return MakeItFitDate.of(year, month, day);
    }

    @Test
    void testDefaultConstructor() {
        TestActivity activity = new TestActivity();

        assertEquals(new UUID(0L, 0L), activity.getUserCode());
        assertNotNull(activity.getCode());
        assertNotNull(activity.getRealizationDate());
        assertEquals(0, activity.getExpectedDuration());
        assertEquals("", activity.getDesignation());
        assertEquals("", activity.getName());
        assertEquals(0, activity.getDuration());
        assertEquals(0, activity.getCaloricWaste());
    }

    @Test
    void testParameterizedConstructor() {
        UUID userCode = UUID.randomUUID();
        MakeItFitDate date = createDate(2024, 5, 15);
        TestActivity activity = new TestActivity(userCode, date, 60, "Class", "Dancing");

        assertEquals(userCode, activity.getUserCode());
        assertEquals(date, activity.getRealizationDate());
        assertEquals(60, activity.getExpectedDuration());
        assertEquals("Class", activity.getDesignation());
        assertEquals("Dancing", activity.getName());
        assertNotNull(activity.getCode());
        assertEquals(0, activity.getDuration());
        assertEquals(0, activity.getCaloricWaste());
    }

    @Test
    void testCopyConstructor() {
        TestActivity original = new TestActivity(UUID.randomUUID(), createDate(2024, 5, 15), 60, "Class", "Body Pump");
        original.setDuration(30);
        original.setCaloricWaste(200);

        TestActivity copy = new TestActivity(original);

        assertEquals(original.getUserCode(), copy.getUserCode());
        assertEquals(original.getCode(), copy.getCode());
        assertEquals(original.getRealizationDate(), copy.getRealizationDate());
        assertEquals(original.getExpectedDuration(), copy.getExpectedDuration());
        assertEquals(original.getDesignation(), copy.getDesignation());
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getDuration(), copy.getDuration());
        assertEquals(original.getCaloricWaste(), copy.getCaloricWaste());
    }

    @Test
    void testSettersAndGetters() {
        TestActivity activity = new TestActivity();

        activity.setExpectedDuration(90);
        assertEquals(90, activity.getExpectedDuration());

        activity.setDesignation("Pilates");
        assertEquals("Pilates", activity.getDesignation());

        activity.setDuration(45);
        assertEquals(45, activity.getDuration());

        activity.setCaloricWaste(300);
        assertEquals(300, activity.getCaloricWaste());
    }

    @Test
    void testEquals() {
        MakeItFitDate date = createDate(2024, 5, 15);
        TestActivity activity1 = new TestActivity(UUID.randomUUID(), date, 60, "Workout", "Pilates");
        TestActivity activity2 = new TestActivity(UUID.randomUUID(), date, 60, "Workout", "Pilates");

        assertTrue(activity1.equals(activity1));
        assertTrue(activity1.equals(activity2));

        TestActivity activity3 = new TestActivity(UUID.randomUUID(), date, 30, "Workout", "Pilates");
        assertFalse(activity1.equals(activity3));

        TestActivity activity4 = new TestActivity(UUID.randomUUID(), date, 60, "Exercise", "Pilates");
        assertFalse(activity1.equals(activity4));

        assertFalse(activity1.equals(new Object()));
    }

    @Test
    void testCompareTo() {
        MakeItFitDate date1 = createDate(2024, 5, 15);
        MakeItFitDate date2 = createDate(2024, 5, 16);
        TestActivity activity1 = new TestActivity(UUID.randomUUID(), date1, 60, "Workout", "Pilates");
        TestActivity activity2 = new TestActivity(UUID.randomUUID(), date2, 60, "Workout", "Body Pump");
        TestActivity activity3 = new TestActivity(UUID.randomUUID(), date1, 30, "Workout", "Pilates");
        TestActivity activity4 = new TestActivity(UUID.randomUUID(), date1, 60, "Workout", "Body Pump");

        assertTrue(activity1.compareTo(activity2) < 0);
        assertTrue(activity2.compareTo(activity1) > 0);
        assertTrue(activity1.compareTo(activity3) > 0);
        assertEquals(0, activity1.compareTo(activity4));
    }

    @Test
    void testToString() {
        TestActivity activity = new TestActivity(UUID.randomUUID(), createDate(2024, 5, 15), 60, "Workout", "Biking");
        activity.setCaloricWaste(300);

        String result = activity.toString();
        assertTrue(result.contains("Biking"));
        assertTrue(result.contains("Workout"));
        assertTrue(result.contains("60"));
        assertTrue(result.contains("15/05/2024"));
        assertTrue(result.contains("300"));
    }

    @Test
    void testUpdateActivity() {
        TestActivity activity = new TestActivity();
        activity.updateActivity(5.0f);
        assertEquals(500, activity.getCaloricWaste());
    }

    @Test
    void testSpecialization() {
        TestActivity activity = new TestActivity();
        activity.setSpecialization("Pilates");
        assertEquals("Pilates", activity.getSpecialization());
    }

    @Test
    void testClone() {
        TestActivity original = new TestActivity(UUID.randomUUID(),
            createDate(2024, 5, 15), 60, "Class", "Body Combat");
        TestActivity clone = original.clone();

        assertNotSame(original, clone);
        assertTrue(original.equals(clone));
        assertEquals(original.getUserCode(), clone.getUserCode());
        assertEquals(original.getCode(), clone.getCode());
        assertEquals(original.getRealizationDate(), clone.getRealizationDate());
        assertEquals(original.getExpectedDuration(), clone.getExpectedDuration());
        assertEquals(original.getDesignation(), clone.getDesignation());
        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getDuration(), clone.getDuration());
        assertEquals(original.getCaloricWaste(), clone.getCaloricWaste());
    }
}

class TestActivity extends Activity {

    public TestActivity() {
        super();
    }

    public String getSpecialization() {
        return super.getSpecialization();
    }

    public void setSpecialization(String specialization) {
        super.setSpecialization(specialization);
    }

    public TestActivity(UUID userCode, MakeItFitDate realizationDate, int expectedDuration, String designation, String name) {
        super(userCode, realizationDate, expectedDuration, designation, name);
    }

    public TestActivity(TestActivity a) {
        super(a);
    }

    @Override
    public void calculateCaloricWaste(float index) {
        setCaloricWaste((int) (index * 100));
    }

    @Override
    public TestActivity clone() {
        return new TestActivity(this);
    }

    @Override
    public int caloricWaste(float index) {
        throw new UnsupportedOperationException("Abstract method not implemented");
    }
}
