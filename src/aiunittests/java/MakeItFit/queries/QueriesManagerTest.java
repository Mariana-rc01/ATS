package MakeItFit.queries;

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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class QueriesManagerTest {

    @Test
    void testAllQueryMethods() {
        // Setup managers
        UserManager         userManager    = new UserManager();
        TrainingPlanManager tpManager      = new TrainingPlanManager();
        QueriesManager      queriesManager = new QueriesManager(userManager, tpManager);

        // Create test data
        User user = new Amateur("José Fernandes",
                                45,
                                Gender.Male,
                                75,
                                175,
                                67,
                                6,
                                "Braga",
                                "990000000",
                                "jose@mail.com");
        userManager.insertUser(user);

        // Add activities
        UUID          userCode = user.getCode();
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
        user.addActivity(a1);

        // Add training plan
        TrainingPlan plan = new TrainingPlan(userCode, MakeItFitDate.of(2023, 5, 1));
        tpManager.insertTrainingPlan(plan);

        // Test all query methods
        MakeItFitDate date1 = MakeItFitDate.of(2023, 5, 1);
        MakeItFitDate date2 = MakeItFitDate.of(2023, 5, 31);

        // HowManyAltimetryDone
        double altimetry = queriesManager.executeQueryHowManyAltimetryDone(userManager,
                                                                           "jose@mail.com",
                                                                           date1,
                                                                           date2);
        assertEquals(1400.0, altimetry); // 800 + 600

        // HowManyKMsDone
        double kms =
            queriesManager.executeQueryHowManyKMsDone(userManager, "jose@mail.com", date1, date2);
        assertEquals(0.0155, kms);

        // MostDoneActivity
        String mostDone = queriesManager.executeQueryMostDoneActivity(userManager);
        assertEquals("DistanceWithAltimetry", mostDone);

        // MostDemandingTrainingPlan
        TrainingPlan demandingPlan =
            queriesManager.executeQueryMostDemandingTrainingPlan(tpManager, userManager);

        // WhoBurnsMoreCalories
        User topCalorieBurner = queriesManager.executeQuerywhoBurnsMoreCalories(userManager);
        assertEquals(user, topCalorieBurner);

        // WhoDidTheMostActivities
        User mostActive = queriesManager.executeQueryWhoDidTheMostActivities(userManager);
        assertEquals(user, mostActive);
    }

    @Test
    void testWithEmptyData() {
        UserManager         userManager    = new UserManager();
        TrainingPlanManager tpManager      = new TrainingPlanManager();
        QueriesManager      queriesManager = new QueriesManager(userManager, tpManager);

        // MostDoneActivity with no users
        String mostDone = queriesManager.executeQueryMostDoneActivity(userManager);

        // WhoBurnsMoreCalories with no users
        User topCalorieBurner = queriesManager.executeQuerywhoBurnsMoreCalories(userManager);
        assertNull(topCalorieBurner);

        // WhoDidTheMostActivities with no users
        User mostActive = queriesManager.executeQueryWhoDidTheMostActivities(userManager);
        assertNull(mostActive);
    }
}
