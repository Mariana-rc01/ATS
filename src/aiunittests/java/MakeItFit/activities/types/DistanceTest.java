package MakeItFit.activities.types;

import java.util.UUID;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DistanceTest {
    private TestDistance  distanceActivity;
    private UUID          testUserCode;
    private MakeItFitDate testDate;

    @BeforeEach
    void setUp() {
        testUserCode = UUID.randomUUID();
        testDate     = MakeItFitDate.of(2023, 1, 1);
        distanceActivity =
            new TestDistance(testUserCode, testDate, 60, "Running", "Morning Run", 5.0);
    }

    @Test
    void testConstructorWithParameters() {
        assertEquals(testUserCode, distanceActivity.getUserCode());
        assertEquals(testDate, distanceActivity.getRealizationDate());
        assertEquals(60, distanceActivity.getExpectedDuration());
        assertEquals("Running", distanceActivity.getDesignation());
        assertEquals("Morning Run", distanceActivity.getName());
        assertEquals(5.0, distanceActivity.getDistance(), 0.001);
        assertEquals(0, distanceActivity.getCaloricWaste());
    }

    @Test
    void testDefaultConstructor() {
        TestDistance defaultDistance = new TestDistance();
        assertEquals(0.0, defaultDistance.getDistance(), 0.001);
        assertEquals(0, defaultDistance.getExpectedDuration());
        assertEquals("", defaultDistance.getDesignation());
        assertEquals("", defaultDistance.getName());
    }

    @Test
    void testCopyConstructor() {
        distanceActivity.setDuration(45);
        distanceActivity.setCaloricWaste(300);

        TestDistance copiedDistance = new TestDistance(distanceActivity);
        assertEquals(distanceActivity.getUserCode(), copiedDistance.getUserCode());
        assertEquals(distanceActivity.getRealizationDate(), copiedDistance.getRealizationDate());
        assertEquals(distanceActivity.getExpectedDuration(), copiedDistance.getExpectedDuration());
        assertEquals(distanceActivity.getDesignation(), copiedDistance.getDesignation());
        assertEquals(distanceActivity.getName(), copiedDistance.getName());
        assertEquals(distanceActivity.getDistance(), copiedDistance.getDistance(), 0.001);
        assertEquals(distanceActivity.getDuration(), copiedDistance.getDuration());
        assertEquals(distanceActivity.getCaloricWaste(), copiedDistance.getCaloricWaste());
    }

    @Test
    void testSettersAndGetters() {
        distanceActivity.setDistance(10.5);
        assertEquals(10.5, distanceActivity.getDistance(), 0.001);
    }

    @Test
    void testCalculateCaloricWaste() {
        distanceActivity.setDistance(8.0);
        distanceActivity.calculateCaloricWaste(1.5f);
        // 8.0 * 1.5 * 0.1 = 1.2 → cast to int = 1
        assertEquals(1, distanceActivity.getCaloricWaste());
    }

    @Test
    void testCaloricWasteMethod() {
        distanceActivity.setDistance(12.0);
        int waste = distanceActivity.caloricWaste(2.0f);
        // 12.0 * 2.0 * 0.1 = 2.4 → cast to int = 2
        assertEquals(2, waste);
    }

    @Test
    void testEquals() {
        TestDistance sameDistance =
            new TestDistance(testUserCode, testDate, 60, "Running", "Morning Run", 5.0);
        TestDistance differentDistance =
            new TestDistance(testUserCode, testDate, 30, "Running", "Morning Run", 3.0);

        assertTrue(distanceActivity.equals(sameDistance));
        assertFalse(distanceActivity.equals(differentDistance));
        assertFalse(distanceActivity.equals(null));
        assertTrue(distanceActivity.equals(distanceActivity));
    }

    @Test
    void testClone() {
        distanceActivity.setDuration(45);
        distanceActivity.setCaloricWaste(300);
        distanceActivity.setDistance(7.5);

        TestDistance clonedDistance = (TestDistance) distanceActivity.clone();
        assertEquals(distanceActivity.getUserCode(), clonedDistance.getUserCode());
        assertEquals(distanceActivity.getRealizationDate(), clonedDistance.getRealizationDate());
        assertEquals(distanceActivity.getExpectedDuration(), clonedDistance.getExpectedDuration());
        assertEquals(distanceActivity.getDesignation(), clonedDistance.getDesignation());
        assertEquals(distanceActivity.getName(), clonedDistance.getName());
        assertEquals(distanceActivity.getDistance(), clonedDistance.getDistance(), 0.001);
        assertEquals(distanceActivity.getDuration(), clonedDistance.getDuration());
        assertEquals(distanceActivity.getCaloricWaste(), clonedDistance.getCaloricWaste());
    }

    @Test
    void testToString() {
        String str = distanceActivity.toString();
        assertTrue(str.contains("Activity: Morning Run"));
        assertTrue(str.contains("Designation: Running"));
        assertTrue(str.contains("Expected Duration: 60 minutes"));
        assertTrue(str.contains("Distance: 5.0 meters"));
    }
}

class TestDistance extends Distance {
    public TestDistance(UUID          userCode,
                        MakeItFitDate realizationDate,
                        int           expectedDuration,
                        String        designation,
                        String        name,
                        double        distance) {
        super(userCode, realizationDate, expectedDuration, designation, name, distance);
    }

    public TestDistance() {
        super();
    }

    public TestDistance(TestDistance d) {
        super(d);
    }

    @Override
    public void calculateCaloricWaste(float index) {
        this.setCaloricWaste((int) (this.getDistance() * index * 0.1));
    }

    @Override
    public int caloricWaste(float index) {
        return (int) (this.getDistance() * index * 0.1);
    }

    @Override
    public Distance clone() {
        return new TestDistance(this);
    }
}
