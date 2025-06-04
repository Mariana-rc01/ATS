package MakeItFit.utils;

import java.util.Random;

/**
 * Class to implement new java features to java.util.Random.
 *
 * @author  Humberto Gomes (A104348)
 * @version (27052025)
 */
public class ExtendedRandom extends Random {
    /**
     * Creates a new random number generator with a random seed.
     */
    public ExtendedRandom() {
        super();
    }

    /**
     * Creates a new random number generator with a set seed.
     */
    public ExtendedRandom(long seed) {
        super(seed);
    }

    /**
     * Generates a random integer between origin (inclusive) and bound (exclusive)
     */
    public int nextInt(int origin, int bound) {
        return this.nextInt(bound - origin) + origin;
    }
}
