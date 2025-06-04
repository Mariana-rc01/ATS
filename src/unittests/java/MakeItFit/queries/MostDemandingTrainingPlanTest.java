package MakeItFit.queries;

import java.util.Arrays;
import java.util.List;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.activities.implementation.Running;
import MakeItFit.activities.implementation.Trail;
import MakeItFit.activities.implementation.WeightSquat;
import MakeItFit.trainingPlan.TrainingPlan;
import MakeItFit.trainingPlan.TrainingPlanManager;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.users.UserManager;
import MakeItFit.users.types.Professional;
import MakeItFit.utils.MakeItFitDate;
import MakeItFit.utils.MyTuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class MostDemandingTrainingPlanTest {
    private MostDemandingTrainingPlan query;
    private TrainingPlanManager       TPManager;
    private UserManager               userManager;
    private User                      user;

    private TrainingPlan createTrainingPlan(List<MyTuple<Integer, Activity>> activities) {
        TrainingPlan tl = new TrainingPlan(user.getCode(), MakeItFitDate.of(2000, 1, 1));

        for (MyTuple<Integer, Activity> activity : activities) {
            tl.addActivity(activity.getItem1(), activity.getItem2());
        }

        return tl;
    }

    private PushUp createPushUp(int repetitions, int series) {
        return new PushUp(user.getCode(),
                          MakeItFitDate.of(2000, 1, 1),
                          60,
                          "PushUp",
                          "DO IT",
                          repetitions,
                          series);
    }

    private Running createRunning(double distance, double speed) {
        return new Running(user.getCode(),
                           MakeItFitDate.of(2000, 1, 1),
                           70,
                           "Running",
                           "For my life",
                           distance,
                           speed);
    }

    private Trail createTrail(double distance, double elevationGain, double elevationLoss) {
        return new Trail(user.getCode(),
                         MakeItFitDate.of(2000, 1, 1),
                         120,
                         "Trail",
                         "RunningUpThatHill",
                         distance,
                         elevationGain,
                         elevationLoss,
                         2);
    }

    private WeightSquat createWeightSquat(int repetitions, int series, double weight) {
        return new WeightSquat(user.getCode(),
                               MakeItFitDate.of(2000, 1, 1),
                               45,
                               "WeightSquat",
                               "PumpItUp",
                               repetitions,
                               series,
                               weight);
    }

    @BeforeEach
    void testSetup() {
        this.query       = new MostDemandingTrainingPlan();
        this.TPManager   = new TrainingPlanManager();
        this.userManager = new UserManager();

        this.user = new Professional("JohnDoe",
                                     21,
                                     Gender.Male,
                                     59,
                                     150,
                                     60,
                                     6,
                                     "Lloyd",
                                     "987 123 432",
                                     "jd@suspect.pt",
                                     7);
        userManager.insertUser(user);
    }

    @Test
    void testExecuteQueryNoPlans() {
        assertNull(query.executeQuery(this.TPManager, this.userManager));
    }

    @Test
    void testExecuteQuerySinglePlan() {
        TrainingPlan tp = createTrainingPlan(
            Arrays.asList(new MyTuple<Integer, Activity>(1, createTrail(7000, 212.23, 412.1234)),
                          new MyTuple<Integer, Activity>(2, createPushUp(10, 100))));
        this.TPManager.insertTrainingPlan(tp);

        assertSame(tp, query.executeQuery(this.TPManager, this.userManager));
    }

    @Test
    void testExecuteQueryMultiplePlans() {
        TrainingPlan tp1 = createTrainingPlan(
            Arrays.asList(new MyTuple<Integer, Activity>(1, createTrail(7000, 212.23, 412.1234)),
                          new MyTuple<Integer, Activity>(2, createPushUp(10, 100))));
        TrainingPlan tp2 = createTrainingPlan(
            Arrays.asList(new MyTuple<Integer, Activity>(2, createRunning(7000, 12)),
                          new MyTuple<Integer, Activity>(2, createWeightSquat(10, 100, 50))));

        this.TPManager.insertTrainingPlan(tp1);
        this.TPManager.insertTrainingPlan(tp2);

        /* Calculate calories for the first plan */
        double caloriesTp1 = 0;
        for (MyTuple<Integer, Activity> tuple : tp1.getActivities()) {
            caloriesTp1 += tuple.getItem1() * tuple.getItem2().caloricWaste(user.getIndex());
        }

        /* Calculate calories for the second plan */
        double caloriesTp2 = 0;
        for (MyTuple<Integer, Activity> tuple : tp2.getActivities()) {
            caloriesTp2 += tuple.getItem1() * tuple.getItem2().caloricWaste(user.getIndex());
        }

        assertSame(caloriesTp1 >= caloriesTp2 ? tp1 : tp2,
                   query.executeQuery(this.TPManager, this.userManager));
    }

    @Test
    void testExecuteQueryTieReturnsFirst() {
        TrainingPlan tp1 = createTrainingPlan(
            Arrays.asList(new MyTuple<Integer, Activity>(1, createTrail(7000, 212.23, 412.1234)),
                          new MyTuple<Integer, Activity>(2, createPushUp(10, 100))));
        TrainingPlan tp1Copy = createTrainingPlan(
            Arrays.asList(new MyTuple<Integer, Activity>(1, createTrail(7000, 212.23, 412.1234)),
                          new MyTuple<Integer, Activity>(2, createPushUp(10, 100))));

        this.TPManager.insertTrainingPlan(tp1);
        this.TPManager.insertTrainingPlan(tp1Copy);

        List<TrainingPlan> activities = this.TPManager.getAllTrainingPlans();
        assertSame(activities.get(0), query.executeQuery(this.TPManager, this.userManager));
    }
}
