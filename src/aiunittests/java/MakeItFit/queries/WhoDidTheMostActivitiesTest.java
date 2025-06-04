package MakeItFit.queries;

import java.util.UUID;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.users.UserManager;
import MakeItFit.users.types.Amateur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WhoDidTheMostActivitiesTest {

    @Test
    void testExecuteQueryWithoutDates() {
        // Setup
        UserManager userManager = new UserManager();

        // Create users
        User user1 = new Amateur("José Fernandes",
                                 45,
                                 Gender.Male,
                                 75,
                                 175,
                                 67,
                                 6,
                                 "Braga",
                                 "990000000",
                                 "jose@mail.com");

        User user2 = new Amateur("Maria Silva",
                                 30,
                                 Gender.Female,
                                 60,
                                 165,
                                 55,
                                 8,
                                 "Porto",
                                 "910000000",
                                 "maria@mail.com");

        userManager.insertUser(user1);
        userManager.insertUser(user2);

        // Add activities (user1 has more activities overall)
        UUID user1Code = user1.getCode();
        UUID user2Code = user2.getCode();

        Activity a1 = new PushUp();
        Activity a2 = new PushUp();

        Activity a3 = new PushUp();

        user1.addActivity(a1);
        user1.addActivity(a2);
        user2.addActivity(a3);

        WhoDidTheMostActivities query = new WhoDidTheMostActivities();

        // Test
        User result = query.executeQuery(userManager);

        // Verify
        assertEquals(user1, result);
    }

    @Test
    void testTieBetweenUsers() {
        UserManager userManager = new UserManager();

        // Create users with same number of activities
        User user1 = new Amateur("User1",
                                 30,
                                 Gender.Male,
                                 70,
                                 170,
                                 60,
                                 5,
                                 "City",
                                 "111111111",
                                 "user1@mail.com");
        User user2 = new Amateur("User2",
                                 35,
                                 Gender.Female,
                                 65,
                                 165,
                                 55,
                                 6,
                                 "City",
                                 "222222222",
                                 "user2@mail.com");

        userManager.insertUser(user1);
        userManager.insertUser(user2);

        // Add one activity for each
        user1.addActivity(new PushUp());
        user2.addActivity(new PushUp());

        WhoDidTheMostActivities query = new WhoDidTheMostActivities();

        // Test - should return the first user with max activities
        User result = query.executeQuery(userManager);
        assertNotNull(result);
    }

    @Test
    void testWithNoUsers() {
        UserManager             userManager = new UserManager();
        WhoDidTheMostActivities query       = new WhoDidTheMostActivities();

        User result = query.executeQuery(userManager);
        assertNull(result);
    }
}
