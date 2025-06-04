package MakeItFit.activities;

import java.lang.reflect.Field;
import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityManagerTest {

    private static final List<String> EXPECTED_ACTIVITIES =
        Arrays.asList("PushUp", "Running", "Trail", "WeightSquat");

    @Test
    void testActivitiesListIsCorrect() throws Exception {
        Field activitiesField = ActivityManager.class.getDeclaredField("activities");
        activitiesField.setAccessible(true);
        List<String> actualActivities = (List<String>) activitiesField.get(null);

        assertEquals(EXPECTED_ACTIVITIES, actualActivities);
    }

    @Test
    void testGetRandomActivityReturnsValidActivity() {
        ActivityManager manager = new ActivityManager();
        for (int i = 0; i < 100; i++) {
            String activity = manager.getRandomActivity();
            assertTrue(EXPECTED_ACTIVITIES.contains(activity));
        }
    }

    @Test
    void testAllActivitiesAreEventuallyReturned() {
        ActivityManager manager            = new ActivityManager();
        Set<String>     returnedActivities = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            returnedActivities.add(manager.getRandomActivity());
        }

        assertEquals(EXPECTED_ACTIVITIES.size(), returnedActivities.size());
        assertTrue(returnedActivities.containsAll(EXPECTED_ACTIVITIES));
    }

    @Test
    void testControlledRandomWithReflection() throws Exception {
        ActivityManager manager = new ActivityManager();

        Random controlledRandom = new Random() {
            private int[] values = { 1, 2 };
            private int   index  = 0;

            @Override
            public int nextInt(int bound) {
                return values[index++];
            }
        };

        Field randomField = ActivityManager.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(manager, controlledRandom);

        assertEquals("Running", manager.getRandomActivity());
        assertEquals("Trail", manager.getRandomActivity());
    }
}
