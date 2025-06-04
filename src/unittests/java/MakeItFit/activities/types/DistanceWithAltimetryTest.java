package MakeItFit.activities.types;

import java.util.UUID;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DistanceWithAltimetryTest {
    private TestDistanceWithAltimetry activity;
    private UUID                      userCode;
    private MakeItFitDate             date;

    private MakeItFitDate createDate(int year, int month, int day) {
        return MakeItFitDate.of(year, month, day);
    }

    @BeforeEach
    void setUp() {
        userCode = UUID.randomUUID();
        date     = createDate(2024, 5, 15);
        activity = new TestDistanceWithAltimetry(userCode,
                                                 date,
                                                 90,
                                                 "Biking",
                                                 "Bom Jesus Biking",
                                                 12345.0,
                                                 234.0,
                                                 200.0);
    }

    @Test
    void testGetters() {
        assertEquals(12345.0, activity.getDistance());
        assertEquals(234.0, activity.getElevationGain());
        assertEquals(200.0, activity.getElevationLoss());
        assertEquals("Biking", activity.getDesignation());
    }

    @Test
    void testSetElevationGainLoss() {
        activity.setElevationGain(1000.0);
        activity.setElevationLoss(300.0);
        assertEquals(1000.0, activity.getElevationGain());
        assertEquals(300.0, activity.getElevationLoss());
    }

    @Test
    void testConstructors() {
        TestDistanceWithAltimetry defaultDA = new TestDistanceWithAltimetry();
        assertEquals(0.0, defaultDA.getDistance());
        assertEquals(0.0, defaultDA.getElevationGain());
        assertEquals(0.0, defaultDA.getElevationLoss());

        TestDistanceWithAltimetry copyA = new TestDistanceWithAltimetry(activity);
        assertEquals(activity.getElevationGain(), copyA.getElevationGain());
        assertEquals(activity.getElevationLoss(), copyA.getElevationLoss());
        assertEquals(activity.getDesignation(), copyA.getDesignation());
    }

    @Test
    void testCalculateCaloricWaste() {
        activity.calculateCaloricWaste(0.1f);
        int expected = (int) ((12345 + 234 * 2 - 200 * 0.5) * 0.1f);
        assertEquals(expected, activity.getCaloricWaste());
    }

    @Test
    void testCaloricWasteReturn() {
        int expected = (int) ((12345 + 234 * 2 - 200 * 0.5) * 0.2f);
        assertEquals(expected, activity.caloricWaste(0.2f));
    }

    @Test
    void testEquals() {
        TestDistanceWithAltimetry same = new TestDistanceWithAltimetry(userCode,
                                                                       date,
                                                                       90,
                                                                       "Biking",
                                                                       "Bom Jesus Biking",
                                                                       12345.0,
                                                                       234.0,
                                                                       200.0);
        assertTrue(activity.equals(same));
        assertTrue(activity.equals(activity));

        TestDistanceWithAltimetry different = new TestDistanceWithAltimetry(userCode,
                                                                            date,
                                                                            90,
                                                                            "Biking",
                                                                            "Bom Jesus Biking",
                                                                            12345.0,
                                                                            700.0,
                                                                            200.0);
        assertFalse(activity.equals(null));
        assertFalse(activity.equals(different));

        TestDistanceWithAltimetry diff2 = new TestDistanceWithAltimetry(userCode,
                                                                        date,
                                                                        91,
                                                                        "Biking",
                                                                        "Bom Jesus Biking",
                                                                        12345.0,
                                                                        234.0,
                                                                        200.0);
        assertFalse(activity.equals(diff2));

        TestDistanceWithAltimetry diff3 = new TestDistanceWithAltimetry(userCode,
                                                                        date,
                                                                        90,
                                                                        "Biking",
                                                                        "Bom Jesus Biking",
                                                                        12345.0,
                                                                        234.0,
                                                                        210.0);
        assertFalse(activity.equals(diff3));
    }

    @Test
    void testToString() {
        String output = activity.toString();
        assertTrue(output.contains("Elevation Gain: 234.0"));
        assertTrue(output.contains("Elevation Loss: 200.0"));
    }

    @Test
    void testClone() {
        DistanceWithAltimetry cloned = activity.clone();
        assertNotSame(activity, cloned);
        assertEquals(activity.getElevationGain(), cloned.getElevationGain());
        assertEquals(activity.getElevationLoss(), cloned.getElevationLoss());
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

    public TestDistanceWithAltimetry(DistanceWithAltimetry other) {
        super(other);
    }

    @Override
    public void calculateCaloricWaste(float index) {
        int waste =
            (int) ((getDistance() + getElevationGain() * 2 - getElevationLoss() * 0.5) * index);
        setCaloricWaste(waste);
    }

    @Override
    public int caloricWaste(float index) {
        return (int) ((getDistance() + getElevationGain() * 2 - getElevationLoss() * 0.5) * index);
    }

    @Override
    public DistanceWithAltimetry clone() {
        return new TestDistanceWithAltimetry(this);
    }
}
