package MakeItFit.queries;

import java.util.ArrayList;
import java.util.UUID;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.Trail;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.users.UserManager;
import MakeItFit.users.types.Amateur;
import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HowManyKMsDoneTest {

    @Test
    void testExecuteQueryWithoutDates() {
        // Setup
        UserManager userManager = new UserManager();
        User        user        = new Amateur("José Fernandes",
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
        ArrayList<Activity> activities = new ArrayList<>();
        UUID                userCode   = user.getCode();
        MakeItFitDate       date       = MakeItFitDate.of(2023, 5, 10);
        Activity            a          = new Trail(userCode,
                               date,
                               120,
                               "Mountain Trail",
                               "Hard Climb",
                               15.5,
                               800.0,
                               600.0,
                               Trail.TRAIL_TYPE_HARD);
        activities.add(a);
        user.addActivities(activities);
        userManager.insertUser(user);

        HowManyKMsDone query = new HowManyKMsDone();

        // Test
        double result = query.executeQuery(userManager, "josefernandes@mail.com");

        // Verify
        assertEquals(0.0155, result);
    }

    @Test
    void testInvalidDates() {
        UserManager    userManager = new UserManager();
        HowManyKMsDone query       = new HowManyKMsDone();

        MakeItFitDate date1 = MakeItFitDate.of(2023, 5, 20);
        MakeItFitDate date2 = MakeItFitDate.of(2023, 5, 10);

        assertThrows(IllegalArgumentException.class,
                     () -> { query.executeQuery(userManager, "test@example.com", date1, date2); });
    }

    @Test
    void testNullDates() {
        UserManager    userManager = new UserManager();
        HowManyKMsDone query       = new HowManyKMsDone();

        assertThrows(IllegalArgumentException.class,
                     () -> { query.executeQuery(userManager, "test@example.com", null, null); });
    }
}
