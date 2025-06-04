package MakeItFit.activities;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActivityManagerTest {

    private static final List<String> EXPECTED_ACTIVITIES =
        Arrays.asList("PushUp", "Running", "Trail", "WeightSquat");

    @Test
    void testGetRandomActivityReturnsValidActivity() {
        ActivityManager manager = new ActivityManager();

        // Test multiple times to account for randomness
        for (int i = 0; i < 100; i++) {
            String activity = manager.getRandomActivity();
            assertTrue(EXPECTED_ACTIVITIES.contains(activity),
                       "Returned activity should be one of: " + EXPECTED_ACTIVITIES);
        }
    }

    @Test
    void testAllActivitiesAreEventuallyReturned() {
        ActivityManager manager         = new ActivityManager();
        boolean[]       foundActivities = new boolean[EXPECTED_ACTIVITIES.size()];

        // Run enough times to likely get all activities
        for (int i = 0; i < 1000; i++) {
            String activity        = manager.getRandomActivity();
            int    index           = EXPECTED_ACTIVITIES.indexOf(activity);
            foundActivities[index] = true;
        }

        // Verify all activities were returned at least once
        for (boolean found : foundActivities) {
            assertTrue(found, "All activities should be returned eventually");
        }
    }

    @Test
    void testActivityDistributionIsReasonable() {
        ActivityManager manager   = new ActivityManager();
        int[]           counts    = new int[EXPECTED_ACTIVITIES.size()];
        int             totalRuns = 10000;

        // Count how many times each activity appears
        for (int i = 0; i < totalRuns; i++) {
            String activity = manager.getRandomActivity();
            int    index    = EXPECTED_ACTIVITIES.indexOf(activity);
            counts[index]++;
        }

        // Check that each activity appears roughly 25% of the time (with some tolerance)
        double expectedPercentage = 0.25;
        double tolerance          = 0.05; // ±5%

        for (int count : counts) {
            double actualPercentage = (double) count / totalRuns;
            assertEquals(expectedPercentage,
                         actualPercentage,
                         tolerance,
                         "Activities should be roughly evenly distributed");
        }
    }
}
