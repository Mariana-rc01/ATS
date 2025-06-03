package MakeItFit.queries;

import java.util.Arrays;

import MakeItFit.activities.implementation.PushUp;
import MakeItFit.activities.implementation.Running;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.users.UserManager;
import MakeItFit.users.types.Professional;
import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HowManyKMsDoneTest {
    private HowManyKMsDone query;
    private UserManager    userManager;
    private User           user;

    private Running createRunning(MakeItFitDate date, double distance) {
        return new Running(user.getCode(),
                           date,
                           84,
                           "StreetRace",
                           "Corrida de Viana",
                           distance,
                           12);
    }

    private PushUp createPushUp(MakeItFitDate date) {
        return new PushUp(user.getCode(), date, 90, "PushUps", "100x", 10, 100);
    }

    @BeforeEach
    void testSetup() {
        this.query       = new HowManyKMsDone();
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
    }

    @Test
    void testExecuteQueryWithDatesReturnsTrueDistance() {
        user.addActivities(Arrays.asList(createRunning(MakeItFitDate.of(1999, 2, 9), 20000.0),
                                         createRunning(MakeItFitDate.of(1999, 3, 9), 4567.231)));
        userManager.insertUser(user);

        assertEquals((20000.0 + 4567.231) / 1000,
                     query.executeQuery(userManager,
                                        "jd@suspect.pt",
                                        MakeItFitDate.of(1999, 2, 8),
                                        MakeItFitDate.of(1999, 3, 10)),
                     0.0001);
    }

    @Test
    void testExecuteQueryWithDatesThrows() {
        assertThrows(IllegalArgumentException.class,
                     ()
                         -> query.executeQuery(userManager,
                                               "jd@suspect.pt",
                                               MakeItFitDate.of(1999, 2, 9),
                                               MakeItFitDate.of(1999, 2, 9)));
    }

    @Test
    void testExecuteQueryWithDatesNoValidActivities() {
        user.addActivities(Arrays.asList(createPushUp(MakeItFitDate.of(1999, 2, 9))));
        userManager.insertUser(user);

        assertEquals(0,
                     query.executeQuery(userManager,
                                        "jd@suspect.pt",
                                        MakeItFitDate.of(1999, 2, 9),
                                        MakeItFitDate.of(1999, 3, 1)));
    }

    @Test
    void testExecuteQueryWithDatesActivitiesOutsideTimeWindow() {
        user.addActivities(Arrays.asList(createRunning(MakeItFitDate.of(1999, 2, 9), 123.143)));
        userManager.insertUser(user);

        assertEquals(0,
                     query.executeQuery(userManager,
                                        "jd@suspect.pt",
                                        MakeItFitDate.of(1999, 2, 9),
                                        MakeItFitDate.of(1999, 4, 10)));
    }

    @Test
    void testExecuteQueryReturnsTrueDistance() {
        user.addActivities(Arrays.asList(createRunning(MakeItFitDate.of(1999, 2, 9), 2122.12),
                                         createRunning(MakeItFitDate.of(1999, 2, 14), 233.231)));
        userManager.insertUser(user);

        assertEquals((2122.12 + 233.231) / 1000,
                     query.executeQuery(userManager, "jd@suspect.pt"),
                     0.0001);
    }

    @Test
    void testExecuteQueryNoValidActivities() {
        user.addActivities(Arrays.asList(createPushUp(MakeItFitDate.of(1999, 2, 9))));
        userManager.insertUser(user);

        assertEquals(0, query.executeQuery(userManager, "jd@suspect.pt"));
    }
}
