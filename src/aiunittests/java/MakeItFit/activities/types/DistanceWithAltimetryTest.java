package MakeItFit.activities.types;

import java.util.UUID;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DistanceWithAltimetryTest {
    private TestDistanceWithAltimetry activity;
    private UUID                      testUserCode;
    private MakeItFitDate             testDate;

    @BeforeEach
    void setUp() {
        testUserCode = UUID.randomUUID();
        testDate     = MakeItFitDate.of(12023, 1, 1);
        activity     = new TestDistanceWithAltimetry(testUserCode,
                                                 testDate,
                                                 120,
                                                 "Trail",
                                                 "Mountain Trail",
                                                 15.0,
                                                 800.0,
                                                 750.0);
    }

    @Test
    void testConstructorWithParameters() {
        assertEquals(testUserCode, activity.getUserCode());
        assertEquals(testDate, activity.getRealizationDate());
        assertEquals(120, activity.getExpectedDuration());
        assertEquals("Trail", activity.getDesignation());
        assertEquals("Mountain Trail", activity.getName());
        assertEquals(15.0, activity.getDistance(), 0.001);
        assertEquals(800.0, activity.getElevationGain(), 0.001);
        assertEquals(750.0, activity.getElevationLoss(), 0.001);
        assertEquals(0, activity.getCaloricWaste());
    }

    @Test
    void testDefaultConstructor() {
        TestDistanceWithAltimetry defaultActivity = new TestDistanceWithAltimetry();
        assertEquals(0.0, defaultActivity.getDistance(), 0.001);
        assertEquals(0.0, defaultActivity.getElevationGain(), 0.001);
        assertEquals(0.0, defaultActivity.getElevationLoss(), 0.001);
        assertEquals(0, defaultActivity.getExpectedDuration());
        assertEquals("", defaultActivity.getDesignation());
        assertEquals("", defaultActivity.getName());
    }

    @Test
    void testCopyConstructor() {
        activity.setDuration(150);
        activity.setCaloricWaste(1200);

        TestDistanceWithAltimetry copiedActivity = new TestDistanceWithAltimetry(activity);
        assertEquals(activity.getUserCode(), copiedActivity.getUserCode());
        assertEquals(activity.getRealizationDate(), copiedActivity.getRealizationDate());
        assertEquals(activity.getExpectedDuration(), copiedActivity.getExpectedDuration());
        assertEquals(activity.getDesignation(), copiedActivity.getDesignation());
        assertEquals(activity.getName(), copiedActivity.getName());
        assertEquals(activity.getDistance(), copiedActivity.getDistance(), 0.001);
        assertEquals(activity.getElevationGain(), copiedActivity.getElevationGain(), 0.001);
        assertEquals(activity.getElevationLoss(), copiedActivity.getElevationLoss(), 0.001);
        assertEquals(activity.getDuration(), copiedActivity.getDuration());
        assertEquals(activity.getCaloricWaste(), copiedActivity.getCaloricWaste());
    }

    @Test
    void testSettersAndGetters() {
        activity.setElevationGain(1000.0);
        assertEquals(1000.0, activity.getElevationGain(), 0.001);

        activity.setElevationLoss(900.0);
        assertEquals(900.0, activity.getElevationLoss(), 0.001);
    }

    @Test
    void testCalculateCaloricWaste() {
        activity.setDistance(20.0);
        activity.setElevationGain(1000.0);
        activity.setElevationLoss(800.0);
        activity.calculateCaloricWaste(1.5f);

        // Cálculo esperado:
        // Base: 20.0 * 1.5 * 0.1 = 3
        // Elevation: (1000*0.5 + 800*0.2)*1.5 = (500 + 160)*1.5 = 990
        // Total: 3 + 990 = 993
        assertEquals(993, activity.getCaloricWaste());
    }

    @Test
    void testCaloricWasteMethod() {
        activity.setDistance(10.0);
        activity.setElevationGain(500.0);
        activity.setElevationLoss(400.0);
        int waste = activity.caloricWaste(2.0f);

        // Cálculo esperado:
        // Base: 10.0 * 2.0 * 0.1 = 2
        // Elevation: (500*0.5 + 400*0.2)*2.0 = (250 + 80)*2.0 = 660
        // Total: 2 + 660 = 662
        assertEquals(662, waste);
    }

    @Test
    void testEquals() {
        TestDistanceWithAltimetry sameActivity = new TestDistanceWithAltimetry(testUserCode,
                                                                               testDate,
                                                                               120,
                                                                               "Trail",
                                                                               "Mountain Trail",
                                                                               15.0,
                                                                               800.0,
                                                                               750.0);

        TestDistanceWithAltimetry differentActivity =
            new TestDistanceWithAltimetry(testUserCode,
                                          testDate,
                                          120,
                                          "Trail",
                                          "Mountain Trail",
                                          15.0,
                                          700.0,
                                          750.0);

        assertTrue(activity.equals(sameActivity));
        assertFalse(activity.equals(differentActivity));
        assertFalse(activity.equals(null));
        assertTrue(activity.equals(activity));
    }

    @Test
    void testClone() {
        activity.setDuration(180);
        activity.setCaloricWaste(1500);
        activity.setDistance(18.0);
        activity.setElevationGain(900.0);
        activity.setElevationLoss(850.0);

        TestDistanceWithAltimetry clonedActivity = (TestDistanceWithAltimetry) activity.clone();
        assertEquals(activity.getUserCode(), clonedActivity.getUserCode());
        assertEquals(activity.getRealizationDate(), clonedActivity.getRealizationDate());
        assertEquals(activity.getExpectedDuration(), clonedActivity.getExpectedDuration());
        assertEquals(activity.getDesignation(), clonedActivity.getDesignation());
        assertEquals(activity.getName(), clonedActivity.getName());
        assertEquals(activity.getDistance(), clonedActivity.getDistance(), 0.001);
        assertEquals(activity.getElevationGain(), clonedActivity.getElevationGain(), 0.001);
        assertEquals(activity.getElevationLoss(), clonedActivity.getElevationLoss(), 0.001);
        assertEquals(activity.getDuration(), clonedActivity.getDuration());
        assertEquals(activity.getCaloricWaste(), clonedActivity.getCaloricWaste());
    }

    @Test
    void testToString() {
        String str = activity.toString();
        assertTrue(str.contains("Activity: Mountain Trail"));
        assertTrue(str.contains("Designation: Trail"));
        assertTrue(str.contains("Expected Duration: 120 minutes"));
        assertTrue(str.contains("Distance: 15.0 meters"));
        assertTrue(str.contains("Elevation Gain: 800.0 meters"));
        assertTrue(str.contains("Elevation Loss: 750.0 meters"));
    }
}

class TestDistanceWithAltimetry extends DistanceWithAltimetry {
    public TestDistanceWithAltimetry(UUID          userCode,
                                     MakeItFitDate realizationDate,
                                     int           expectedDuration,
                                     String        designation,
                                     String        name,
                                     double        distance,
                                     double        elevationGain,
                                     double        elevationLoss) {
        super(userCode,
              realizationDate,
              expectedDuration,
              designation,
              name,
              distance,
              elevationGain,
              elevationLoss);
    }

    public TestDistanceWithAltimetry() {
        super();
    }

    public TestDistanceWithAltimetry(TestDistanceWithAltimetry a) {
        super(a);
    }

    @Override
    public void calculateCaloricWaste(float index) {
        // Cálculo que considera distância e altimetria
        int baseCalories = (int) (getDistance() * index * 0.1);
        int elevationCalories =
            (int) ((getElevationGain() * 0.5 + getElevationLoss() * 0.2) * index);
        setCaloricWaste(baseCalories + elevationCalories);
    }

    @Override
    public int caloricWaste(float index) {
        int baseCalories = (int) (getDistance() * index * 0.1);
        int elevationCalories =
            (int) ((getElevationGain() * 0.5 + getElevationLoss() * 0.2) * index);
        return baseCalories + elevationCalories;
    }

    @Override
    public DistanceWithAltimetry clone() {
        return new TestDistanceWithAltimetry(this);
    }
}
