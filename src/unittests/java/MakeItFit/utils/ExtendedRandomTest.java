package MakeItFit.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class ExtendedRandomTest {
    private int SAMPLE_SIZE = 1000;

    @Test
    void testRangeBoundariesUnseededRandom() {
        ExtendedRandom unseededRandom = new ExtendedRandom();
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            int expected = unseededRandom.nextInt(5, 15);
            assertTrue(expected >= 5 && expected < 15);
        }
    }

    @Test
    void testDeterministicOutputSeededRandom() {
        ExtendedRandom seededRandom = new ExtendedRandom(123456L);
        int[] firstRun = new int[5];
        for (int i = 0; i < firstRun.length; i++) {
            firstRun[i] = seededRandom.nextInt(5, 10);
        }

        ExtendedRandom repeatRandom = new ExtendedRandom(123456L);
        for (int i = 0; i < firstRun.length; i++) {
            assertEquals(firstRun[i], repeatRandom.nextInt(5, 10));
        }
    }

    @Test
    void testOriginEqualThrows() {
        ExtendedRandom unseededRandom = new ExtendedRandom();
        assertThrows(
            IllegalArgumentException.class,
            () -> unseededRandom.nextInt(10, 10)
        );
    }

    @Test
    void testOriginGreaterThrows() {
        ExtendedRandom unseededRandom = new ExtendedRandom();
        assertThrows(
            IllegalArgumentException.class,
            () -> unseededRandom.nextInt(15, 5)
        );
    }

    @Test
    void testSingleValueRange() {
        ExtendedRandom unseededRandom = new ExtendedRandom();
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            assertEquals(5, unseededRandom.nextInt(5, 6));
        }
    }

    @Test
    void testNegativeOriginPositiveBound() {
        ExtendedRandom unseededRandom = new ExtendedRandom();
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            int expected = unseededRandom.nextInt(-5, 5);
            assertTrue(expected >= -5 && expected < 5);
        }
    }
}
