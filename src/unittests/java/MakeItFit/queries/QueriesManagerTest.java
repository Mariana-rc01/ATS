package MakeItFit.queries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import MakeItFit.MakeItFit;
import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.Trail;
import MakeItFit.trainingPlan.TrainingPlan;
import MakeItFit.trainingPlan.TrainingPlanManager;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.users.UserManager;
import MakeItFit.users.types.Professional;
import MakeItFit.utils.MakeItFitDate;
import MakeItFit.utils.MyTuple;

public class QueriesManagerTest {

    QueriesManager queriesManager;
    UserManager userManager;
    TrainingPlanManager TPManager;
    Trail trail;
    User user;

    @BeforeEach
    void testSetup() {
        this.queriesManager = new QueriesManager();
        this.userManager = new UserManager();
        this.TPManager = new TrainingPlanManager();
        this.user = new Professional(
            "JohnDoe",
            21,
            Gender.Male,
            59,
            150,
            60,
            6,
            "Lloyd",
            "987 123 432",
            "jd@suspect.pt",
            7
        );

        this.trail = new Trail(
            this.user.getCode(),
            MakeItFitDate.of(2000, 1, 1),
            90,
            "Hiking",
            "Subir ao Bom Jesus",
            2000.0,
            12432.3,
            12341.2,
            1
        );
    }

    @Test
    void testExecuteQueryHowManyAltimetryDoneWithDates() {
        HowManyAltimetryDone query = new HowManyAltimetryDone();

        user.addActivities(Arrays.asList(this.trail, this.trail));
        userManager.insertUser(user);

        assertEquals(
            query.executeQuery(
                userManager,
                "jd@suspect.pt",
                MakeItFitDate.of(1999, 12, 30),
                MakeItFitDate.of(2000, 1 , 2)
            ),
            queriesManager.executeQueryHowManyAltimetryDone(
                userManager,
                "jd@suspect.pt",
                MakeItFitDate.of(1999, 12, 30),
                MakeItFitDate.of(2000, 1 , 2)
            )
        );
    }

    @Test
    void testExecuteQueryHowManyAltimetryDone() {
        HowManyAltimetryDone query = new HowManyAltimetryDone();

        this.user.addActivities(Arrays.asList(this.trail, this.trail));
        this.userManager.insertUser(this.user);

        assertEquals(
            query.executeQuery(this.userManager, "jd@suspect.pt"),
            this.queriesManager.executeQueryHowManyAltimetryDone(
                this.userManager,
                "jd@suspect.pt"
            )
        );
    }

    @Test
    void testExecuteQueryHowManyKMsDoneWithDates() {
        HowManyKMsDone query = new HowManyKMsDone();

        this.user.addActivities(Arrays.asList(this.trail, this.trail));
        this.userManager.insertUser(this.user);

        assertEquals(
            query.executeQuery(
                userManager,
                "jd@suspect.pt",
                MakeItFitDate.of(1999, 2, 8),
                MakeItFitDate.of(2000, 1, 2)
            ),
            this.queriesManager.executeQueryHowManyKMsDone(
                userManager,
                "jd@suspect.pt",
                MakeItFitDate.of(1999, 2, 8),
                MakeItFitDate.of(2000, 1, 2)
            ),
            0.0001
        );
    }

    @Test
    void testExecuteQueryHowManyKMsDone() {
        HowManyKMsDone query = new HowManyKMsDone();

        this.user.addActivities(Arrays.asList(this.trail, this.trail));
        this.userManager.insertUser(this.user);

        assertEquals(
            query.executeQuery(
                userManager,
                "jd@suspect.pt"
            ),
            this.queriesManager.executeQueryHowManyKMsDone(
                userManager,
                "jd@suspect.pt"
            ),
            0.0001
        );
    }

    @Test
    void testExecuteQueryMostDemandingTrainingPlan() {
        MostDemandingTrainingPlan query = new MostDemandingTrainingPlan();

        TrainingPlan tp = new TrainingPlan(
            this.user.getCode(),
            MakeItFitDate.of(2000, 1, 1)
        );

        ArrayList<MyTuple<Integer, Activity>> activities =
            new ArrayList<MyTuple<Integer, Activity>>(
                Arrays.asList(
                    new MyTuple<Integer, Activity>(1, this.trail),
                    new MyTuple<Integer, Activity>(2, this.trail)
                )
            );

        for (MyTuple<Integer, Activity> activity : activities) {
            tp.addActivity(activity.getItem1(), activity.getItem2());
        }

        this.userManager.insertUser(this.user);
        this.TPManager.insertTrainingPlan(tp);

        assertSame(
            query.executeQuery(this.TPManager, this.userManager),
            this.queriesManager.executeQueryMostDemandingTrainingPlan(
                this.TPManager, this.userManager
            )
        );
    }

    @Test
    void testExecuteQueryMostDoneActivity() {
        MostDoneActivity query = new MostDoneActivity();

        this.user.addActivities(Arrays.asList(this.trail, this.trail));
        this.userManager.insertUser(this.user);

        assertEquals(
            query.executeQuery(this.userManager),
            this.queriesManager.executeQueryMostDoneActivity(this.userManager)
        );
    }

    @Test
    void testExecuteQueryWhoBurnsMoreCaloresWithDates() {
        WhoBurnsMoreCalories query = new WhoBurnsMoreCalories();

        this.user.addActivities(Arrays.asList(this.trail, this.trail));
        User user1 = this.user.clone();
        user1.setEmail("something@diferent.com");

        this.userManager.insertUser(user);
        this.userManager.insertUser(user1);

        assertSame(
            query.executeQuery(
                this.userManager,
                MakeItFitDate.of(1999, 2, 8),
                MakeItFitDate.of(2000, 1, 2)
            ),
            this.queriesManager.executeQuerywhoBurnsMoreCalories(
                this.userManager,
                MakeItFitDate.of(1999, 2, 8),
                MakeItFitDate.of(2000, 1, 2)
            )
        );
    }

    @Test
    void testExecuteQueryWhoBurnsMoreCalores() {
        WhoBurnsMoreCalories query = new WhoBurnsMoreCalories();

        this.user.addActivities(Arrays.asList(this.trail, this.trail));
        User user1 = this.user.clone();
        user1.setEmail("something@diferent.com");
        user1.addActivity(this.trail);

        this.userManager.insertUser(user);
        this.userManager.insertUser(user1);

        assertSame(
            query.executeQuery(this.userManager),
            this.queriesManager.executeQuerywhoBurnsMoreCalories(this.userManager)
        );
    }

    @Test
    void testWhoDidTheMostActivitiesWithDates() {
        WhoDidTheMostActivities query = new WhoDidTheMostActivities();

        this.user.addActivities(Arrays.asList(this.trail, this.trail));
        User user1 = this.user.clone();
        user1.setEmail("something@diferent.com");
        user1.addActivity(this.trail);

        this.userManager.insertUser(user);
        this.userManager.insertUser(user1);

        assertSame(
            query.executeQuery(
                this.userManager,
                MakeItFitDate.of(1999, 2, 8),
                MakeItFitDate.of(2000, 1, 2)
            ),
            this.queriesManager.executeQueryWhoDidTheMostActivities(
                this.userManager,
                MakeItFitDate.of(1999, 2, 8),
                MakeItFitDate.of(2000, 1, 2)
            )
        );
    }

    @Test
    void testWhoDidTheMostActivities() {
        WhoDidTheMostActivities query = new WhoDidTheMostActivities();

        this.user.addActivities(Arrays.asList(this.trail, this.trail));
        User user1 = this.user.clone();
        user1.setEmail("something@diferent.com");
        user1.addActivity(this.trail);

        this.userManager.insertUser(user);
        this.userManager.insertUser(user1);

        assertSame(
            query.executeQuery(this.userManager),
            this.queriesManager.executeQueryWhoDidTheMostActivities(this.userManager)
        );
    }
}
