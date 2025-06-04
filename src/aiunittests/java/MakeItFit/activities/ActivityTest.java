package MakeItFit.activities;

import java.util.UUID;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActivityTest {

    private TestActivity  activity;
    private UUID          testUserCode;
    private MakeItFitDate testDate;

    @BeforeEach
    void setUp() {
        testUserCode = UUID.randomUUID();
        testDate     = MakeItFitDate.of(2023, 1, 1);
        activity     = new TestActivity(testUserCode, testDate, 60, "Workout", "Running");
    }

    @Test
    void testConstructorWithParameters() {
        assertEquals(testUserCode, activity.getUserCode());
        assertEquals(testDate, activity.getRealizationDate());
        assertEquals(60, activity.getExpectedDuration());
        assertEquals("Workout", activity.getDesignation());
        assertEquals("Running", activity.getName());
        assertNotNull(activity.getCode());
        assertEquals(0, activity.getDuration());
        assertEquals(0, activity.getCaloricWaste());
    }

    @Test
    void testDefaultConstructor() {
        TestActivity defaultActivity = new TestActivity();
        assertEquals(new UUID(0L, 0L), defaultActivity.getUserCode());
        assertNotNull(defaultActivity.getCode());
        assertNotNull(defaultActivity.getRealizationDate());
        assertEquals(0, defaultActivity.getExpectedDuration());
        assertEquals("", defaultActivity.getDesignation());
        assertEquals(0, defaultActivity.getDuration());
        assertEquals(0, defaultActivity.getCaloricWaste());
        assertEquals("", defaultActivity.getName());
    }

    @Test
    void testCopyConstructor() {
        activity.setDuration(45);
        activity.setCaloricWaste(300);

        TestActivity copiedActivity = new TestActivity(activity);
        assertEquals(activity.getUserCode(), copiedActivity.getUserCode());
        assertEquals(activity.getCode(), copiedActivity.getCode());
        assertEquals(activity.getRealizationDate(), copiedActivity.getRealizationDate());
        assertEquals(activity.getExpectedDuration(), copiedActivity.getExpectedDuration());
        assertEquals(activity.getDesignation(), copiedActivity.getDesignation());
        assertEquals(activity.getDuration(), copiedActivity.getDuration());
        assertEquals(activity.getCaloricWaste(), copiedActivity.getCaloricWaste());
        assertEquals(activity.getName(), copiedActivity.getName());
    }

    @Test
    void testSettersAndGetters() {
        activity.setExpectedDuration(90);
        assertEquals(90, activity.getExpectedDuration());

        activity.setDesignation("Cardio");
        assertEquals("Cardio", activity.getDesignation());

        activity.setDuration(30);
        assertEquals(30, activity.getDuration());

        activity.setCaloricWaste(200);
        assertEquals(200, activity.getCaloricWaste());

        activity.setSpecialization("Endurance");
        assertEquals("Endurance", activity.getSpecialization());
    }

    @Test
    void testUpdateActivity() {
        activity.setDuration(40);
        activity.updateActivity(1.5f);
        assertTrue(activity.getCaloricWaste() > 0);
    }

    @Test
    void testEquals() {
        TestActivity sameActivity =
            new TestActivity(testUserCode, testDate, 60, "Workout", "Running");
        TestActivity differentActivity =
            new TestActivity(testUserCode, testDate, 30, "Cardio", "Swimming");

        assertTrue(activity.equals(sameActivity));
        assertFalse(activity.equals(differentActivity));
        assertFalse(activity.equals(null));
        assertTrue(activity.equals(activity));
    }

    @Test
    void testCompareTo() {
        MakeItFitDate earlierDate = MakeItFitDate.of(2022, 1, 1);
        MakeItFitDate laterDate   = MakeItFitDate.of(2024, 1, 1);

        TestActivity earlierActivity =
            new TestActivity(testUserCode, earlierDate, 60, "Workout", "Running");
        TestActivity laterActivity =
            new TestActivity(testUserCode, laterDate, 60, "Workout", "Running");
        TestActivity sameDateShorterActivity =
            new TestActivity(testUserCode, testDate, 30, "Workout", "Running");
        TestActivity sameDateLongerActivity =
            new TestActivity(testUserCode, testDate, 90, "Workout", "Running");

        assertTrue(activity.compareTo(earlierActivity) > 0);
        assertTrue(activity.compareTo(laterActivity) < 0);
        assertTrue(activity.compareTo(sameDateShorterActivity) > 0);
        assertTrue(activity.compareTo(sameDateLongerActivity) < 0);
        assertEquals(0, activity.compareTo(activity));
    }

    @Test
    void testToString() {
        String str = activity.toString();
        assertTrue(str.contains("Activity: Running"));
        assertTrue(str.contains("Code: " + activity.getCode()));
        assertTrue(str.contains("Designation: Workout"));
        assertTrue(str.contains("Expected Duration: 60 minutes"));
        assertTrue(str.contains("Realization Date: " + testDate));
        assertTrue(str.contains("Caloric Waste: 0 calories"));
    }

    @Test
    void testClone() {
        activity.setDuration(45);
        activity.setCaloricWaste(300);

        TestActivity clonedActivity = (TestActivity) activity.clone();
        assertEquals(activity.getUserCode(), clonedActivity.getUserCode());
        assertEquals(activity.getCode(), clonedActivity.getCode());
        assertEquals(activity.getRealizationDate(), clonedActivity.getRealizationDate());
        assertEquals(activity.getExpectedDuration(), clonedActivity.getExpectedDuration());
        assertEquals(activity.getDesignation(), clonedActivity.getDesignation());
        assertEquals(activity.getDuration(), clonedActivity.getDuration());
        assertEquals(activity.getCaloricWaste(), clonedActivity.getCaloricWaste());
        assertEquals(activity.getName(), clonedActivity.getName());
    }
}

class TestActivity extends Activity {
    private String specialization = "";

    public TestActivity(UUID          userCode,
                        MakeItFitDate realizationDate,
                        int           expectedDuration,
                        String        designation,
                        String        name) {
        super(userCode, realizationDate, expectedDuration, designation, name);
    }

    public TestActivity() {
        super();
    }

    public TestActivity(TestActivity a) {
        super(a);
        this.specialization = a.specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return this.specialization;
    }

    @Override
    public void calculateCaloricWaste(float index) {
        this.setCaloricWaste((int) (this.getDuration() * index * 0.5));
    }

    @Override
    public int caloricWaste(float index) {
        return (int) (this.getDuration() * index * 0.5);
    }

    @Override
    public Activity clone() {
        return new TestActivity(this);
    }
}
