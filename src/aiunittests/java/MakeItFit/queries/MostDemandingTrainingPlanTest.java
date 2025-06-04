package MakeItFit.queries;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.Trail;
import MakeItFit.trainingPlan.TrainingPlan;
import MakeItFit.trainingPlan.TrainingPlanManager;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.users.UserManager;
import MakeItFit.users.types.Amateur;
import MakeItFit.utils.MakeItFitDate;
import MakeItFit.utils.MyTuple;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MostDemandingTrainingPlanTest {

    @Test
    void testExecuteQuery() {
        // Setup
        TrainingPlanManager tpManager   = new TrainingPlanManager();
        UserManager         userManager = new UserManager();

        // Create users with different indexes
        User user1 = new Amateur("José Fernandes",
                                 45,
                                 Gender.Male,
                                 75,
                                 175,
                                 67,
                                 6,
                                 "Braga",
                                 "990 000 000",
                                 "josefernandes@mail.com");

        // Add activities with altimetry
        UUID          userCode = user1.getCode();
        MakeItFitDate date     = MakeItFitDate.of(2023, 5, 10);
        Activity      a1       = new Trail(userCode,
                                date,
                                120,
                                "Mountain Trail",
                                "Hard Climb",
                                15.5,
                                800.0,
                                600.0,
                                Trail.TRAIL_TYPE_HARD);
        user1.setIndex(1.0f);
        User user2 = new Amateur("José Fernandes",
                                 45,
                                 Gender.Male,
                                 75,
                                 175,
                                 67,
                                 6,
                                 "Braga",
                                 "990 000 000",
                                 "josefernandes1@mail.com");

        // Add activities with altimetry
        UUID     userCode2 = user2.getCode();
        Activity a2        = new Trail(userCode2,
                                date,
                                120,
                                "Mountain Trail",
                                "Hard Climb",
                                15.5,
                                800.0,
                                600.0,
                                Trail.TRAIL_TYPE_HARD);
        user2.setIndex(1.5f);
        userManager.insertUser(user1);
        userManager.insertUser(user2);

        // Create training plans
        TrainingPlan                     plan1       = new TrainingPlan(user1.getCode(), date);
        List<MyTuple<Integer, Activity>> activities1 = new ArrayList<>();
        plan1.addActivity(1, a1);

        TrainingPlan                     plan2       = new TrainingPlan(user2.getCode(), date);
        List<MyTuple<Integer, Activity>> activities2 = new ArrayList<>();
        plan2.addActivity(10, a2);

        tpManager.insertTrainingPlan(plan1);
        tpManager.insertTrainingPlan(plan2);

        MostDemandingTrainingPlan query = new MostDemandingTrainingPlan();

        // Test
        TrainingPlan result = query.executeQuery(tpManager, userManager);

        assertEquals(null, result);
    }

    @Test
    void testWithEmptyLists() {
        TrainingPlanManager tpManager   = new TrainingPlanManager();
        UserManager         userManager = new UserManager();

        MostDemandingTrainingPlan query = new MostDemandingTrainingPlan();

        TrainingPlan result = query.executeQuery(tpManager, userManager);
        assertNull(result);
    }
}
