package MakeItFit.trainingPlan;

import java.util.List;
import java.util.UUID;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.utils.MakeItFitDate;
import MakeItFit.utils.MyTuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainingPlanTest {
    private TrainingPlan  referencePlan;
    private UUID          referenceUserCode;
    private MakeItFitDate referenceStartDate;
    private Activity      referenceActivity1;
    private Activity      referenceActivity2;

    @BeforeEach
    void initializeReferenceObjects() {
        this.referenceUserCode = UUID.randomUUID();
        this.referencePlan = new TrainingPlan(this.referenceUserCode, MakeItFitDate.of(2024, 5, 1));

        this.referenceActivity1 =
            new PushUp(this.referenceUserCode, MakeItFitDate.of(2000, 1, 1), 30, "", "", 10, 10);
        this.referenceActivity2 =
            new PushUp(this.referenceUserCode, MakeItFitDate.of(2030, 1, 1), 1, "", "", 1, 1);
    }

    @Test
    void testEmptyConstructor() {
        TrainingPlan plan = new TrainingPlan();
        assertEquals(List.of(), plan.getActivities());
    }

    @Test
    void testCopyConstructor() {
        TrainingPlan plan = new TrainingPlan(this.referencePlan);
        assertEquals(this.referencePlan.getCode(), plan.getCode());
        assertEquals(this.referencePlan.getUserCode(), plan.getUserCode());
        assertEquals(this.referencePlan.getStartDate(), plan.getStartDate());
        assertEquals(this.referencePlan.getActivities(), plan.getActivities());
    }

    @Test
    void testGetActivities() {
        List<MyTuple<Integer, Activity>> activities = this.referencePlan.getActivities();
        activities.add(new MyTuple<Integer, Activity>(10, this.referenceActivity1));
        assertNotEquals(this.referencePlan.getActivities(), activities);
    }

    @Test
    void testSetStartDate() {
        this.referencePlan.setStartDate(MakeItFitDate.of(2024, 10, 1));
        assertEquals(MakeItFitDate.of(2024, 10, 1), this.referencePlan.getStartDate());
    }

    @Test
    void testAddActivity1() {
        this.referencePlan.addActivity(10, this.referenceActivity1);
    }

    @Test
    void testRemoveActivity1() {
        this.referencePlan.addActivity(1, this.referenceActivity1);
        this.referencePlan.addActivity(2, this.referenceActivity2);
        this.referencePlan.removeActivity(UUID.randomUUID());
        assertEquals(2, this.referencePlan.getActivities().size());
    }

    @Test
    void testRemoveActivity2() {
        this.referencePlan.addActivity(10, this.referenceActivity1);
        this.referencePlan.addActivity(2, this.referenceActivity2);
        this.referencePlan.removeActivity(this.referenceActivity2.getCode());
        assertEquals(List.of(new MyTuple<Integer, Activity>(10, this.referenceActivity1)),
                     this.referencePlan.getActivities());
    }

    @Test
    void testUpdateActivities() {
        assertEquals(0, this.referenceActivity1.getCaloricWaste());
        assertEquals(0, this.referenceActivity2.getCaloricWaste());

        this.referencePlan.addActivity(10, this.referenceActivity1);
        this.referencePlan.addActivity(2, this.referenceActivity2);
        this.referencePlan.updateActivities(MakeItFitDate.of(2020, 1, 1), 5.0f);

        List<MyTuple<Integer, Activity>> activities = this.referencePlan.getActivities();
        assertNotEquals(0, activities.get(0).getItem2().getCaloricWaste());
        assertEquals(0, activities.get(1).getItem2().getCaloricWaste());
    }

    @Test
    void testToString() {
        String got      = this.referencePlan.toString();
        String expected = "        == (Training plan details) =="
                          + "\n        Training Plan: " + this.referencePlan.getCode() +
                          "\n        User Code: " + this.referencePlan.getUserCode() +
                          "\n        Start Date: " + this.referencePlan.getStartDate() +
                          "\n        Activities(Iterations / Activity): []";

        assertEquals(expected, got);
    }

    @Test
    void testExtractActivities1() {
        assertEquals(List.of(), this.referencePlan.extractActivities(MakeItFitDate.of(2100, 1, 1)));
    }

    @Test
    void testExtractActivities2() {
        this.referencePlan.addActivity(10, this.referenceActivity1);
        this.referencePlan.addActivity(2, this.referenceActivity2);

        assertEquals(List.of(this.referenceActivity1, this.referenceActivity2),
                     this.referencePlan.extractActivities(MakeItFitDate.of(2100, 1, 1)));
    }

    @Test
    void testExtractActivities3() {
        this.referencePlan.addActivity(10, this.referenceActivity1);
        this.referencePlan.addActivity(2, this.referenceActivity2);

        assertEquals(List.of(this.referenceActivity1),
                     this.referencePlan.extractActivities(MakeItFitDate.of(2015, 1, 1)));
    }

    @Test
    void testEquals1() {
        assertEquals(this.referencePlan, this.referencePlan);
    }

    @Test
    void testEquals2() {
        assertNotEquals(this.referencePlan, null);
    }

    @Test
    void testEquals3() {
        assertEquals(this.referencePlan,
                     new TrainingPlan(this.referenceUserCode, MakeItFitDate.of(2024, 5, 1)));
    }

    @Test
    void testEquals4() {
        assertNotEquals(this.referencePlan,
                        new TrainingPlan(UUID.randomUUID(), MakeItFitDate.of(2024, 5, 1)));
    }

    @Test
    void testEquals5() {
        TrainingPlan copy = this.referencePlan.clone();
        copy.setStartDate(MakeItFitDate.of(2020, 10, 16));
        assertNotEquals(this.referencePlan, copy);
    }

    @Test
    void testEquals6() {
        TrainingPlan copy = this.referencePlan.clone();
        copy.addActivity(6, this.referenceActivity1);
        assertNotEquals(this.referencePlan, copy);
    }

    @Test
    void testCompareTo1() {
        assertEquals(0, this.referencePlan.compareTo(this.referencePlan));
    }

    @Test
    void testCompareTo2() {
        assertEquals(0, this.referencePlan.compareTo(this.referencePlan.clone()));
    }

    @Test
    void testCompareTo3() {
        TrainingPlan copy = this.referencePlan.clone();
        copy.setStartDate(MakeItFitDate.of(2020, 10, 16));
        assertTrue(this.referencePlan.compareTo(copy) > 0);
    }

    @Test
    void testCompareTo4() {
        TrainingPlan copy = this.referencePlan.clone();
        copy.addActivity(6, this.referenceActivity1);
        assertTrue(this.referencePlan.compareTo(copy) < 0);
    }

    @Test
    void testClone1() {
        TrainingPlan plan = this.referencePlan.clone();
        assertEquals(this.referencePlan.getCode(), plan.getCode());
        assertEquals(this.referencePlan.getUserCode(), plan.getUserCode());
        assertEquals(this.referencePlan.getStartDate(), plan.getStartDate());
        assertEquals(this.referencePlan.getActivities(), plan.getActivities());
    }

    @Test
    void testClone2() {
        this.referencePlan.addActivity(1, this.referenceActivity2);

        TrainingPlan plan = this.referencePlan.clone();
        assertEquals(this.referencePlan.getCode(), plan.getCode());
        assertEquals(this.referencePlan.getUserCode(), plan.getUserCode());
        assertEquals(this.referencePlan.getStartDate(), plan.getStartDate());
        assertEquals(this.referencePlan.getActivities(), plan.getActivities());
    }
}
