package MakeItFit.trainingPlan;

import java.util.List;
import java.util.UUID;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.exceptions.EntityDoesNotExistException;
import MakeItFit.exceptions.ExistingEntityConflictException;
import MakeItFit.utils.MakeItFitDate;
import MakeItFit.utils.MyTuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainingPlanManagerTest {
    private TrainingPlanManager referenceManager;
    private TrainingPlan        referencePlan1;
    private TrainingPlan        referencePlan2;
    private Activity            referenceActivity1;
    private Activity            referenceActivity2;

    @BeforeEach
    void initializeReferenceObjects() {
        this.referenceManager = new TrainingPlanManager();

        this.referencePlan1 = new TrainingPlan(UUID.randomUUID(), MakeItFitDate.of(2010, 5, 1));
        this.referencePlan2 = new TrainingPlan(UUID.randomUUID(), MakeItFitDate.of(2030, 5, 1));

        this.referenceActivity1 =
            new PushUp(UUID.randomUUID(), MakeItFitDate.of(2000, 1, 1), 30, "", "", 10, 10);
        this.referenceActivity2 =
            new PushUp(UUID.randomUUID(), MakeItFitDate.of(2030, 1, 1), 1, "", "", 1, 1);

        this.referencePlan2.addActivity(1, this.referenceActivity2);
    }

    @Test
    void testConstructor() {
        assertEquals(List.of(), (new TrainingPlanManager()).getAllTrainingPlans());
    }

    /*
    @Test
    void testConstructTrainingPlanByObjectives1() {
        TrainingPlan plan = new TrainingPlan();
        TrainingPlan generated = this.referenceManager.constructTrainingPlanByObjectives(plan, 1.0f,
    false, 1, 100, 1, 10000);

        assertSame(plan, generated);
    }

    @Test
    void testConstructTrainingPlanByObjectives2() {
        TrainingPlan plan = new TrainingPlan();
        TrainingPlan generated = this.referenceManager.constructTrainingPlanByObjectives(plan, 1.0f,
    true, 1, 100, 1, 10000);

        assertSame(plan, generated);
    }
    */

    @Test
    void testCreateTrainingPlan1() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.referenceManager.createTrainingPlan(null, new MakeItFitDate());
        });
    }

    @Test
    void testCreateTrainingPlan2() {
        assertThrows(IllegalArgumentException.class,
                     () -> { this.referenceManager.createTrainingPlan(UUID.randomUUID(), null); });
    }

    @Test
    void testCreateTrainingPlan3() {
        UUID          uuid = UUID.randomUUID();
        MakeItFitDate date = new MakeItFitDate();

        assertEquals(new TrainingPlan(uuid, date),
                     this.referenceManager.createTrainingPlan(uuid, date));
    }

    @Test
    void testInsertTrainingPlan1() {
        assertThrows(IllegalArgumentException.class,
                     () -> { this.referenceManager.insertTrainingPlan(null); });
    }

    @Test
    void testInsertTrainingPlan2() {
        this.referenceManager.insertTrainingPlan(this.referencePlan2);
        assertThrows(ExistingEntityConflictException.class,
                     () -> { this.referenceManager.insertTrainingPlan(this.referencePlan2); });
    }

    @Test
    void testInsertTrainingPlan3() {
        this.referenceManager.insertTrainingPlan(this.referencePlan1);
        this.referenceManager.insertTrainingPlan(this.referencePlan2);

        List<TrainingPlan> plans = this.referenceManager.getAllTrainingPlans();
        assertTrue(plans.contains(this.referencePlan1));
        assertTrue(plans.contains(this.referencePlan2));
        assertEquals(2, plans.size());
    }

    @Test
    void testRemoveTrainingPlan1() {
        this.referenceManager.insertTrainingPlan(this.referencePlan1);
        this.referenceManager.insertTrainingPlan(this.referencePlan2);
        this.referenceManager.removeTrainingPlan(UUID.randomUUID());

        List<TrainingPlan> plans = this.referenceManager.getAllTrainingPlans();
        assertTrue(plans.contains(this.referencePlan1));
        assertTrue(plans.contains(this.referencePlan2));
        assertEquals(2, plans.size());
    }

    @Test
    void testRemoveTrainingPlan2() {
        this.referenceManager.insertTrainingPlan(this.referencePlan1);
        this.referenceManager.insertTrainingPlan(this.referencePlan2);
        this.referenceManager.removeTrainingPlan(this.referencePlan2.getCode());

        assertEquals(List.of(this.referencePlan1), this.referenceManager.getAllTrainingPlans());
    }

    @Test
    void testGetTrainingPlan1() {
        this.referenceManager.insertTrainingPlan(this.referencePlan1);
        assertSame(this.referencePlan1,
                   this.referenceManager.getTrainingPlan(this.referencePlan1.getCode()));
    }

    @Test
    void testGetTrainingPlan2() {
        assertThrows(EntityDoesNotExistException.class,
                     () -> { this.referenceManager.getTrainingPlan(UUID.randomUUID()); });
    }

    @Test
    void testUpdateTrainingPlan1() {
        this.referenceManager.insertTrainingPlan(this.referencePlan1);
        assertThrows(EntityDoesNotExistException.class,
                     () -> { this.referenceManager.updateTrainingPlan(this.referencePlan2); });
    }

    @Test
    void testUpdateTrainingPlan2() {
        this.referenceManager.insertTrainingPlan(this.referencePlan2);

        TrainingPlan copy = this.referencePlan2.clone();
        copy.setStartDate(new MakeItFitDate());
        this.referenceManager.updateTrainingPlan(copy);

        assertEquals(copy, this.referenceManager.getTrainingPlan(copy.getCode()));
    }

    @Test
    void testAddActivity1() {
        this.referenceManager.insertTrainingPlan(this.referencePlan2);
        this.referenceManager.addActivity(this.referencePlan2.getCode(),
                                          10,
                                          this.referenceActivity1);

        assertEquals(List.of(new MyTuple<Integer, Activity>(1, this.referenceActivity2),
                             new MyTuple<Integer, Activity>(10, this.referenceActivity1)),
                     referencePlan2.getActivities());
    }

    @Test
    void testAddActivity2() {
        assertThrows(NullPointerException.class, () -> {
            this.referenceManager.addActivity(this.referencePlan2.getCode(),
                                              10,
                                              this.referenceActivity1);
        });
    }

    @Test
    void testRemoveActivity1() {
        this.referenceManager.insertTrainingPlan(this.referencePlan1);
        assertThrows(NullPointerException.class, () -> {
            this.referenceManager.removeActivity(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void testRemoveActivity2() {
        this.referenceManager.insertTrainingPlan(this.referencePlan2);
        this.referenceManager.removeActivity(this.referencePlan2.getCode(),
                                             this.referenceActivity2.getCode());
        assertEquals(List.of(), this.referencePlan1.getActivities());
    }

    @Test
    void testGetTrainingPlansFromUser1() {
        assertEquals(List.of(), this.referenceManager.getTrainingPlansFromUser(UUID.randomUUID()));
    }

    @Test
    void testGetTrainingPlansFromUser2() {
        this.referenceManager.insertTrainingPlan(this.referencePlan2);
        assertEquals(
            List.of(this.referencePlan2),
            this.referenceManager.getTrainingPlansFromUser(this.referencePlan2.getUserCode()));
    }

    @Test
    void testGetTrainingPlansFromUser3() {
        UUID         uuid  = UUID.randomUUID();
        TrainingPlan plan1 = new TrainingPlan(uuid, MakeItFitDate.of(2030, 5, 1));
        TrainingPlan plan2 = new TrainingPlan(uuid, MakeItFitDate.of(2030, 5, 1));
        TrainingPlan plan3 = new TrainingPlan(UUID.randomUUID(), MakeItFitDate.of(2030, 5, 1));

        this.referenceManager.insertTrainingPlan(plan1);
        this.referenceManager.insertTrainingPlan(plan2);
        this.referenceManager.insertTrainingPlan(plan3);

        List<TrainingPlan> plans = this.referenceManager.getTrainingPlansFromUser(uuid);
        assertTrue(plans.contains(plan1));
        assertTrue(plans.contains(plan2));
        assertEquals(2, plans.size());
    }

    @Test
    void testUpdateActivities() {
        this.referencePlan2.addActivity(1, this.referenceActivity1);
        this.referenceManager.insertTrainingPlan(this.referencePlan2);

        this.referenceManager.updateActivities(MakeItFitDate.of(2020, 1, 1), 1.0f);
        assertNotEquals(0, this.referenceActivity1.getCaloricWaste());
        assertEquals(0, this.referenceActivity2.getCaloricWaste());
    }

    @Test
    void testExtractActivities() {
        UUID         uuid  = UUID.randomUUID();
        TrainingPlan plan1 = new TrainingPlan(uuid, MakeItFitDate.of(2030, 5, 1));
        TrainingPlan plan2 = new TrainingPlan(uuid, MakeItFitDate.of(2030, 5, 1));
        TrainingPlan plan3 = new TrainingPlan(UUID.randomUUID(), MakeItFitDate.of(2030, 5, 1));

        plan1.addActivity(1, this.referenceActivity1);
        plan2.addActivity(5, this.referenceActivity2);

        this.referenceManager.insertTrainingPlan(plan1);
        this.referenceManager.insertTrainingPlan(plan2);
        this.referenceManager.insertTrainingPlan(plan3);

        List<Activity> done =
            this.referenceManager.extractActivities(MakeItFitDate.of(2020, 1, 1), uuid);
        assertEquals(List.of(this.referenceActivity1), done);
    }
}
