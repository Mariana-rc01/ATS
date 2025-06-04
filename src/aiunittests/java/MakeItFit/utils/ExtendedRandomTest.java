package MakeItFit.utils;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtendedRandomTest {

    @Test
    void testNextIntWithRange() {
        ExtendedRandom random = new ExtendedRandom(12345);
        int            origin = 10;
        int            bound  = 20;

        // Test multiple times to ensure consistency
        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(origin, bound);
            assertTrue(value >= origin && value < bound,
                       "Value should be between " + origin + " (inclusive) and " + bound +
                           " (exclusive)");
        }
    }

    @Test
    void testNextIntWithRangeDistribution() {
        ExtendedRandom random = new ExtendedRandom(67890);
        int            origin = 5;
        int            bound  = 15;
        Set<Integer>   values = new HashSet<>();

        // Test if we get a reasonable distribution of values
        for (int i = 0; i < 1000; i++) {
            values.add(random.nextInt(origin, bound));
        }

        assertEquals(bound - origin, values.size(), "Should get all possible values in the range");
    }

    @Test
    void testNextIntWithNegativeRange() {
        ExtendedRandom random = new ExtendedRandom();
        int            value  = random.nextInt(-10, 10);
        assertTrue(value >= -10 && value < 10);
    }

    @Test
    void testNextIntWithSameBounds() {
        ExtendedRandom random = new ExtendedRandom();
        assertThrows(IllegalArgumentException.class, () -> { random.nextInt(10, 10); });
    }

    @Test
    void testNextIntWithInvalidBounds() {
        ExtendedRandom random = new ExtendedRandom();
        assertThrows(IllegalArgumentException.class, () -> { random.nextInt(20, 10); });
    }
}
