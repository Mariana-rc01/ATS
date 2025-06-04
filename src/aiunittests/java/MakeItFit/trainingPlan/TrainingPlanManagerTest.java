package MakeItFit.trainingPlan;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.exceptions.EntityDoesNotExistException;
import MakeItFit.utils.MakeItFitDate;

class TrainingPlanManagerTest {
    private TrainingPlanManager manager;
    private UUID                userCode;
    private MakeItFitDate       startDate;
    private TrainingPlan        trainingPlan;
    private Activity            testActivity;

    @BeforeEach
    void setUp() {
        manager      = new TrainingPlanManager();
        userCode     = UUID.randomUUID();
        startDate    = MakeItFitDate.of(2024, 5, 1);
        trainingPlan = manager.createTrainingPlan(userCode, startDate);
        testActivity = new PushUp();
    }

    @Test
    void testCreateTrainingPlan() {
        assertNotNull(trainingPlan);
        assertEquals(userCode, trainingPlan.getUserCode());
        assertEquals(startDate, trainingPlan.getStartDate());
    }

    @Test
    void testCreateTrainingPlan_InvalidArguments() {
        assertThrows(IllegalArgumentException.class,
                     () -> { manager.createTrainingPlan(null, null); });
    }

    @Test
    void testConstructTrainingPlanByObjectives() {
        TrainingPlan plan =
            manager.constructTrainingPlanByObjectives(trainingPlan, 1.0f, false, 2, 3, 4, 1000);

        assertNotNull(plan);
        assertFalse(plan.getActivities().isEmpty());
    }

    @Test
    void testConstructTrainingPlanByObjectives_InvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.constructTrainingPlanByObjectives(trainingPlan, 1.0f, false, -1, 3, 4, 1000);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            manager.constructTrainingPlanByObjectives(trainingPlan, 1.0f, false, 2, 3, 8, 1000);
        });
    }

    @Test
    void testInsertTrainingPlan() {
        manager.insertTrainingPlan(trainingPlan);
        assertTrue(manager.getAllTrainingPlans().contains(trainingPlan));
    }

    @Test
    void testRemoveTrainingPlan() {
        manager.insertTrainingPlan(trainingPlan);
        manager.removeTrainingPlan(trainingPlan.getCode());
        assertFalse(manager.getAllTrainingPlans().contains(trainingPlan));
    }

    @Test
    void testGetTrainingPlan() {
        manager.insertTrainingPlan(trainingPlan);
        TrainingPlan retrieved = manager.getTrainingPlan(trainingPlan.getCode());
        assertEquals(trainingPlan, retrieved);
    }

    @Test
    void testUpdateTrainingPlan() throws EntityDoesNotExistException {
        manager.insertTrainingPlan(trainingPlan);

        MakeItFitDate newDate = MakeItFitDate.of(2024, 6, 1);
        trainingPlan.setStartDate(newDate);
        manager.updateTrainingPlan(trainingPlan);

        TrainingPlan updated = manager.getTrainingPlan(trainingPlan.getCode());
        assertEquals(newDate, updated.getStartDate());
    }

    @Test
    void testUpdateTrainingPlan_NonExistent() {
        assertThrows(EntityDoesNotExistException.class,
                     () -> { manager.updateTrainingPlan(trainingPlan); });
    }

    @Test
    void testGetAllTrainingPlans() {
        manager.insertTrainingPlan(trainingPlan);
        List<TrainingPlan> plans = manager.getAllTrainingPlans();
        assertEquals(1, plans.size());
        assertEquals(trainingPlan, plans.get(0));
    }

    @Test
    void testAddActivity() {
        manager.insertTrainingPlan(trainingPlan);
        manager.addActivity(trainingPlan.getCode(), 3, testActivity);
        assertEquals(1, trainingPlan.getActivities().size());
    }

    @Test
    void testRemoveActivity() {
        manager.insertTrainingPlan(trainingPlan);
        manager.addActivity(trainingPlan.getCode(), 3, testActivity);
        manager.removeActivity(trainingPlan.getCode(), testActivity.getCode());
        assertTrue(trainingPlan.getActivities().isEmpty());
    }

    @Test
    void testGetTrainingPlansFromUser() {
        UUID         otherUser = UUID.randomUUID();
        TrainingPlan otherPlan = manager.createTrainingPlan(otherUser, startDate);

        manager.insertTrainingPlan(trainingPlan);
        manager.insertTrainingPlan(otherPlan);

        List<TrainingPlan> userPlans = manager.getTrainingPlansFromUser(userCode);
        assertEquals(1, userPlans.size());
        assertEquals(trainingPlan, userPlans.get(0));
    }

    @Test
    void testUpdateActivities() {
        manager.insertTrainingPlan(trainingPlan);
        manager.addActivity(trainingPlan.getCode(), 3, testActivity);

        MakeItFitDate currentDate = MakeItFitDate.of(2024, 6, 1);
        manager.updateActivities(currentDate, 1.0f);

        // Verifica se a atualização foi realizada (depende da implementação de Activity)
        // Este teste pode precisar ser ajustado conforme a lógica de updateActivities
    }

    @Test
    void testExtractActivities_NoActivities() {
        MakeItFitDate  currentDate = MakeItFitDate.of(2024, 6, 1);
        List<Activity> activities  = manager.extractActivities(currentDate, userCode);
        assertTrue(activities.isEmpty());
    }
}
