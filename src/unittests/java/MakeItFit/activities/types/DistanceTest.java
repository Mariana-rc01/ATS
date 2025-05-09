package MakeItFit.activities.types;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DistanceTest {

    private TestDistance distanceActivity;
    private UUID userCode;
    private MakeItFitDate date;

    private MakeItFitDate createDate(int year, int month, int day) {
        return MakeItFitDate.of(year, month, day);
    }

    @BeforeEach
    void setUp() {
        userCode = UUID.randomUUID();
        date = createDate(2024, 5, 15);
        distanceActivity = new TestDistance(userCode, date, 45, "Running", "Running 45 minutes", 5000.0);
    }

    @Test
    void testGetters() {
        assertEquals(userCode, distanceActivity.getUserCode());
        assertEquals(date, distanceActivity.getRealizationDate());
        assertEquals(45, distanceActivity.getExpectedDuration());
        assertEquals("Running", distanceActivity.getDesignation());
        assertEquals("Running 45 minutes", distanceActivity.getName());
        assertEquals(5000.0, distanceActivity.getDistance());
    }

    @Test
    void testConstructors() {
        TestDistance defaultD = new TestDistance();
        assertEquals(0.0, defaultD.getDistance());
        assertEquals(0, defaultD.getExpectedDuration());

        TestDistance copyD = new TestDistance(distanceActivity);
        assertEquals(distanceActivity.getDistance(), copyD.getDistance());
        assertEquals(distanceActivity.getDesignation(), copyD.getDesignation());
    }

    @Test
    void testSetDistance() {
        distanceActivity.setDistance(10);
        assertEquals(10, distanceActivity.getDistance());
    }

    @Test
    void testCalculateCaloricWaste() {
        distanceActivity.calculateCaloricWaste(0.1f);
        assertEquals(500, distanceActivity.getCaloricWaste());
    }

    @Test
    void testCaloricWasteReturn() {
        int waste = distanceActivity.caloricWaste(0.2f);
        assertEquals(1000, waste);
    }

    @Test
    void testEquals() {
        TestDistance other = new TestDistance(userCode, date, 45, "Running", "Running for 45 minutes", 5000.0);
        assertTrue(distanceActivity.equals(other));
        assertTrue(distanceActivity.equals(distanceActivity));

        TestDistance different = new TestDistance(userCode, date, 45, "Running", "Running for 45 minutes", 4000.0);
        assertFalse(distanceActivity.equals(different));
        assertFalse(distanceActivity.equals(null));
    }

    @Test
    void testToString() {
        String output = distanceActivity.toString();
        assertTrue(output.contains("Running"));
        assertTrue(output.contains("Distance: 5000.0"));
    }

    @Test
    void testClone() {
        Distance cloned = distanceActivity.clone();
        assertNotSame(distanceActivity, cloned);
        assertEquals(distanceActivity.getDistance(), cloned.getDistance());
    }
}

class TestDistance extends Distance {
    public TestDistance(UUID userCode, MakeItFitDate realizationDate, int expectedDuration, String designation, String name, double distance) {
        super(userCode, realizationDate, expectedDuration, designation, name, distance);
    }

    public TestDistance() {
        super();
    }

    public TestDistance(Distance d) {
        super(d);
    }

    @Override
    public void calculateCaloricWaste(float index) {
        setCaloricWaste((int) (getDistance() * index));
    }

    @Override
    public int caloricWaste(float index) {
        return (int) (getDistance() * index);
    }

    @Override
    public Distance clone() {
        return new TestDistance(this);
    }
}
