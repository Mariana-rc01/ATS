package MakeItFit.activities.implementation;

import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static MakeItFit.activities.implementation.Trail.*;
import static org.junit.jupiter.api.Assertions.*;

public class TrailTest {
    private Trail trail;
    private UUID userCode;
    private MakeItFitDate date;

    private MakeItFitDate createDate(int year, int month, int day) {
        return MakeItFitDate.of(year, month, day);
    }

    @BeforeEach
    void setUp() {
        userCode = UUID.randomUUID();
        date = createDate(2025, 12, 25);
        trail = new Trail(userCode, date, 90, "Easy peasy lemon squeezy", "Trail", 10000, 500, 200, TRAIL_TYPE_EASY);
    }

    @Test
    void testGetters() {
        assertEquals(10000, trail.getDistance());
        assertEquals(500, trail.getElevationGain());
        assertEquals(200, trail.getElevationLoss());
        assertEquals(TRAIL_TYPE_EASY, trail.getTrailType());
        assertEquals("Trail", trail.getSpecialization());
    }

    @Test
    void testConstructors() {
        Trail defaultTrail = new Trail();
        assertEquals(0, defaultTrail.getDistance());
        assertEquals(TRAIL_TYPE_EASY, defaultTrail.getTrailType());
        assertEquals("Trail", defaultTrail.getSpecialization());

        Trail copy = new Trail(trail);
        assertEquals(trail, copy);
        assertNotSame(trail, copy);
    }

    @Test
    void testSetTrailType() {
        trail.setTrailType(-10);
        assertEquals(TRAIL_TYPE_EASY, trail.getTrailType());

        trail.setTrailType(99);
        assertEquals(TRAIL_TYPE_HARD, trail.getTrailType());

        trail.setTrailType(TRAIL_TYPE_EASY);
        assertEquals(TRAIL_TYPE_EASY, trail.getTrailType());

        trail.setTrailType(TRAIL_TYPE_MEDIUM);
        assertEquals(TRAIL_TYPE_MEDIUM, trail.getTrailType());

        trail.setTrailType(TRAIL_TYPE_HARD);
        assertEquals(TRAIL_TYPE_HARD, trail.getTrailType());
    }

    @Test
    void testCalculateCaloricWaste() {
        float index = 2.0f;
        int expected = (int) ((10000 * 0.5 + 500 * 0.1 - 200 * 0.1) * index * 0.01);
        trail.calculateCaloricWaste(index);
        assertEquals(expected, trail.getCaloricWaste());
    }

    @Test
    void testCaloricWasteMethod() {
        float index = 1.5f;
        int expected = (int) ((10000 * 0.5 + 500 * 0.1 - 200 * 0.1) * index * 0.01);
        assertEquals(expected, trail.caloricWaste(index));
    }

    @Test
    void testToStringIncludesTrailType() {
        String output = trail.toString();
        assertTrue(output.contains("Trail Type: " + TRAIL_TYPE_EASY));
    }

    @Test
    void testEquals() {
        Trail same = new Trail(userCode, date, 90, "Easy peasy lemon squeezy", "Trail", 10000, 500, 200, TRAIL_TYPE_EASY);
        assertTrue(trail.equals(same));
        assertTrue(trail.equals(trail));

        Trail diff = new Trail(userCode, date, 90, "Easy peasy lemon squeezy", "Trail", 10000, 500, 200, TRAIL_TYPE_HARD);
        assertFalse(trail.equals(diff));
        assertFalse(trail.equals(null));
    }

    @Test
    void testClone() {
        Trail cloned = trail.clone();
        assertNotSame(trail, cloned);
        assertEquals(trail, cloned);
    }
}
