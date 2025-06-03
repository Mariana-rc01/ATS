package MakeItFit.trainingPlan;

import java.util.UUID;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.utils.MakeItFitDate;
import MakeItFit.utils.MyTuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainingPlanTest {
    private TrainingPlan  trainingPlan;
    private UUID          userCode;
    private MakeItFitDate startDate;
    private Activity      activity1;
    private Activity      activity2;

    @BeforeEach
    void setUp() {
        userCode     = UUID.randomUUID();
        startDate    = MakeItFitDate.of(2024, 5, 1);
        trainingPlan = new TrainingPlan(userCode, startDate);

        activity1 = new PushUp();
        activity2 = new PushUp();
    }

    @Test
    void testConstructorWithParameters() {
        assertNotNull(trainingPlan);
        assertEquals(userCode, trainingPlan.getUserCode());
        assertEquals(startDate, trainingPlan.getStartDate());
        assertTrue(trainingPlan.getActivities().isEmpty());
    }

    @Test
    void testDefaultConstructor() {
        TrainingPlan defaultPlan = new TrainingPlan();
        assertNotNull(defaultPlan);
        assertEquals(new UUID(0L, 0L), defaultPlan.getUserCode());
        assertNotNull(defaultPlan.getStartDate());
        assertTrue(defaultPlan.getActivities().isEmpty());
    }

    @Test
    void testCopyConstructor() {
        trainingPlan.addActivity(3, activity1);
        trainingPlan.addActivity(5, activity2);

        TrainingPlan copy = new TrainingPlan(trainingPlan);

        assertEquals(trainingPlan.getUserCode(), copy.getUserCode());
        assertEquals(trainingPlan.getStartDate(), copy.getStartDate());
        assertEquals(trainingPlan.getActivities().size(), copy.getActivities().size());
        assertEquals(trainingPlan.getCode(), copy.getCode());
    }

    @Test
    void testGetUserCode() {
        assertEquals(userCode, trainingPlan.getUserCode());
    }

    @Test
    void testGetCode() {
        assertNotNull(trainingPlan.getCode());
    }

    @Test
    void testGetStartDate() {
        assertEquals(startDate, trainingPlan.getStartDate());
    }

    @Test
    void testGetActivities() {
        assertTrue(trainingPlan.getActivities().isEmpty());
    }

    @Test
    void testSetStartDate() {
        MakeItFitDate newDate = MakeItFitDate.of(2024, 6, 1);
        trainingPlan.setStartDate(newDate);
        assertEquals(newDate, trainingPlan.getStartDate());
    }

    @Test
    void testAddActivity() {
        trainingPlan.addActivity(3, activity1);
        assertEquals(1, trainingPlan.getActivities().size());

        MyTuple<Integer, Activity> tuple = trainingPlan.getActivities().get(0);
        assertEquals(3, tuple.getItem1().intValue());
        assertEquals(activity1, tuple.getItem2());
    }

    @Test
    void testRemoveActivity() {
        trainingPlan.addActivity(3, activity1);
        trainingPlan.addActivity(5, activity2);

        assertEquals(2, trainingPlan.getActivities().size());

        trainingPlan.removeActivity(activity1.getCode());
        assertEquals(1, trainingPlan.getActivities().size());
        assertEquals(activity2, trainingPlan.getActivities().get(0).getItem2());
    }

    @Test
    void testRemoveActivity_NonExistent() {
        trainingPlan.addActivity(3, activity1);
        trainingPlan.removeActivity(UUID.randomUUID()); // Should not throw exception
        assertEquals(1, trainingPlan.getActivities().size());
    }

    @Test
    void testUpdateActivities() {
        MakeItFitDate pastDate = MakeItFitDate.of(2024, 4, 1);

        trainingPlan.addActivity(3, activity1);
        trainingPlan.addActivity(5, activity2); // activity2 has future date by default

        trainingPlan.updateActivities(MakeItFitDate.of(2024, 5, 15), 1.1f);

        // Only activity1 should be updated as it's in the past
        // Note: This assumes Activity has some way to verify it was updated
        // You might need to add getters for updated values in Activity class
    }

    @Test
    void testToString() {
        trainingPlan.addActivity(3, activity1);
        String result = trainingPlan.toString();

        assertTrue(result.contains("Training Plan: " + trainingPlan.getCode()));
        assertTrue(result.contains("User Code: " + userCode));
        assertTrue(result.contains("Start Date: " + startDate));
        assertTrue(result.contains("Activities(Iterations / Activity):"));
    }

    @Test
    void testEquals() {
        TrainingPlan samePlan = new TrainingPlan(userCode, startDate);
        samePlan.addActivity(3, activity1);
        trainingPlan.addActivity(3, activity1);

        TrainingPlan differentPlan =
            new TrainingPlan(UUID.randomUUID(), MakeItFitDate.of(2024, 6, 1));

        assertEquals(trainingPlan, trainingPlan); // reflexivity
        assertEquals(trainingPlan, samePlan);
        assertNotEquals(trainingPlan, differentPlan);
        assertNotEquals(trainingPlan, null);
        assertNotEquals(trainingPlan, new Object());
    }

    @Test
    void testCompareTo() {
        TrainingPlan earlierPlan = new TrainingPlan(userCode, MakeItFitDate.of(2024, 4, 1));
        TrainingPlan laterPlan   = new TrainingPlan(userCode, MakeItFitDate.of(2024, 6, 1));

        assertTrue(trainingPlan.compareTo(earlierPlan) > 0);
        assertTrue(trainingPlan.compareTo(laterPlan) < 0);
        assertEquals(0, trainingPlan.compareTo(new TrainingPlan(trainingPlan)));

        // Test with same date but different number of activities
        TrainingPlan sameDateMoreActivities = new TrainingPlan(userCode, startDate);
        sameDateMoreActivities.addActivity(3, activity1);
        sameDateMoreActivities.addActivity(5, activity2);

        trainingPlan.addActivity(3, activity1);
        assertTrue(trainingPlan.compareTo(sameDateMoreActivities) < 0);
    }

    @Test
    void testClone() {
        trainingPlan.addActivity(3, activity1);
        TrainingPlan clone = trainingPlan.clone();

        assertEquals(trainingPlan.getUserCode(), clone.getUserCode());
        assertEquals(trainingPlan.getStartDate(), clone.getStartDate());
        assertEquals(trainingPlan.getActivities().size(), clone.getActivities().size());
        assertEquals(trainingPlan.getCode(), clone.getCode());
    }
}
