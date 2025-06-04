package MakeItFit.activities.implementation;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import MakeItFit.utils.MakeItFitDate;

public class TrailTest {

    @Test
    void testConstructorWithParameters() {
        UUID          userCode = UUID.randomUUID();
        MakeItFitDate date     = new MakeItFitDate();
        Trail         trail    = new Trail(userCode,
                                date,
                                120,
                                "Mountain Trail",
                                "Hard Climb",
                                15.5,
                                800.0,
                                600.0,
                                Trail.TRAIL_TYPE_HARD);

        assertEquals(userCode, trail.getUserCode());
        assertEquals(date, trail.getRealizationDate());
        assertEquals(120, trail.getExpectedDuration());
        assertEquals("Mountain Trail", trail.getDesignation());
        assertEquals("Hard Climb", trail.getName());
        assertEquals(15.5, trail.getDistance());
        assertEquals(800.0, trail.getElevationGain());
        assertEquals(600.0, trail.getElevationLoss());
        assertEquals(Trail.TRAIL_TYPE_HARD, trail.getTrailType());
    }

    @Test
    void testDefaultConstructor() {
        Trail trail = new Trail();

        assertNotNull(trail.getUserCode());
        assertNotNull(trail.getRealizationDate());
        assertEquals(0, trail.getExpectedDuration());
        assertEquals(0.0, trail.getDistance());
        assertEquals(0.0, trail.getElevationGain());
        assertEquals(0.0, trail.getElevationLoss());
        assertEquals(Trail.TRAIL_TYPE_EASY, trail.getTrailType());
    }

    @Test
    void testCopyConstructor() {
        Trail original = new Trail(UUID.randomUUID(),
                                   new MakeItFitDate(),
                                   90,
                                   "Forest",
                                   "Easy Path",
                                   10.0,
                                   300.0,
                                   300.0,
                                   Trail.TRAIL_TYPE_MEDIUM);

        Trail copy = new Trail(original);

        assertEquals(original.getUserCode(), copy.getUserCode());
        assertEquals(original.getRealizationDate(), copy.getRealizationDate());
        assertEquals(original.getExpectedDuration(), copy.getExpectedDuration());
        assertEquals(original.getDesignation(), copy.getDesignation());
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getDistance(), copy.getDistance());
        assertEquals(original.getElevationGain(), copy.getElevationGain());
        assertEquals(original.getElevationLoss(), copy.getElevationLoss());
        assertEquals(original.getTrailType(), copy.getTrailType());
    }

    @Test
    void testSetTrailType() {
        Trail trail = new Trail();

        trail.setTrailType(Trail.TRAIL_TYPE_EASY);
        assertEquals(Trail.TRAIL_TYPE_EASY, trail.getTrailType());

        trail.setTrailType(Trail.TRAIL_TYPE_MEDIUM);
        assertEquals(Trail.TRAIL_TYPE_MEDIUM, trail.getTrailType());

        trail.setTrailType(Trail.TRAIL_TYPE_HARD);
        assertEquals(Trail.TRAIL_TYPE_HARD, trail.getTrailType());

        // Test values outside range
        trail.setTrailType(-1);
        assertEquals(Trail.TRAIL_TYPE_EASY, trail.getTrailType());

        trail.setTrailType(5);
        assertEquals(Trail.TRAIL_TYPE_HARD, trail.getTrailType());
    }

    @Test
    void testCaloricWaste() {
        Trail trail = new Trail();
        trail.setDistance(10.0);
        trail.setElevationGain(500.0);
        trail.setElevationLoss(300.0);

        float index    = 1.0f;
        int   expected = (int) ((10.0 * 0.5 + 500.0 * 0.1 + 300.0 * 0.1) * 1.0 * 0.01);
        assertEquals(expected, trail.caloricWaste(index));

        index    = 1.5f;
        expected = (int) ((10.0 * 0.5 + 500.0 * 0.1 + 300.0 * 0.1) * 1.5 * 0.01);
        assertEquals(expected, trail.caloricWaste(index));
    }

    @Test
    void testCalculateCaloricWaste() {
        Trail trail = new Trail();
        trail.setDistance(8.0);
        trail.setElevationGain(400.0);
        trail.setElevationLoss(200.0);

        float index = 1.2f;
        trail.calculateCaloricWaste(index);

        int expected = (int) ((8.0 * 0.5 + 400.0 * 0.1 - 200.0 * 0.1) * 1.2 * 0.01);
        assertEquals(expected, trail.getCaloricWaste());
    }

    @Test
    void testToString() {
        Trail trail = new Trail();
        trail.setDistance(12.5);
        trail.setElevationGain(700.0);
        trail.setTrailType(Trail.TRAIL_TYPE_HARD);

        String result = trail.toString();
        assertTrue(result.contains("12.5"));
        assertTrue(result.contains("700.0"));
    }

    @Test
    void testEquals() {
        UUID          userCode = UUID.randomUUID();
        MakeItFitDate date     = new MakeItFitDate();
        Trail         trail1   = new Trail(userCode,
                                 date,
                                 120,
                                 "Trail",
                                 "Path",
                                 15.0,
                                 800.0,
                                 600.0,
                                 Trail.TRAIL_TYPE_HARD);
        Trail         trail2   = new Trail(userCode,
                                 date,
                                 120,
                                 "Trail",
                                 "Path",
                                 15.0,
                                 800.0,
                                 600.0,
                                 Trail.TRAIL_TYPE_HARD);
        Trail         trail3   = new Trail(userCode,
                                 date,
                                 120,
                                 "Trail",
                                 "Path",
                                 15.0,
                                 800.0,
                                 600.0,
                                 Trail.TRAIL_TYPE_MEDIUM);

        assertEquals(trail1, trail2);
        assertNotEquals(trail1, trail3);
        assertNotEquals(trail1, null);
        assertNotEquals(trail1, new Object());
    }

    @Test
    void testClone() {
        Trail original = new Trail(UUID.randomUUID(),
                                   new MakeItFitDate(),
                                   180,
                                   "Long",
                                   "Endurance",
                                   25.0,
                                   1200.0,
                                   1200.0,
                                   Trail.TRAIL_TYPE_MEDIUM);

        Trail clone = original.clone();

        assertEquals(original, clone);
        assertNotSame(original, clone);
    }
}
