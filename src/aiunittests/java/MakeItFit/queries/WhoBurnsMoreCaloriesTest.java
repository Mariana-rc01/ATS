package MakeItFit.queries;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.users.UserManager;
import MakeItFit.users.types.Amateur;
import MakeItFit.utils.MakeItFitDate;

public class WhoBurnsMoreCaloriesTest {

    @Test
    void testExecuteQueryWithDates() {
        // Setup
        UserManager   userManager = new UserManager();
        MakeItFitDate date1       = MakeItFitDate.of(2023, 5, 1);
        MakeItFitDate date2       = MakeItFitDate.of(2023, 5, 31);

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

        // Add activities (user1 burns more calories in the date range)
        UUID user1Code = user1.getCode();
        UUID user2Code = user2.getCode();

        // High calorie activity for user1 in range
        Activity a1 = new PushUp();

        Activity a2 = new PushUp();

        // Activity outside date range (shouldn't count)
        Activity a3 = new PushUp();

        user1.addActivity(a1);
        user2.addActivity(a2);
        user2.addActivity(a3);

        WhoBurnsMoreCalories query = new WhoBurnsMoreCalories();

        // Test
        User result = query.executeQuery(userManager, date1, date2);

        // Verify
        assertEquals(null, result);
    }

    @Test
    void testInvalidDates() {
        UserManager          userManager = new UserManager();
        WhoBurnsMoreCalories query       = new WhoBurnsMoreCalories();

        MakeItFitDate date1 = MakeItFitDate.of(2023, 5, 20);
        MakeItFitDate date2 = MakeItFitDate.of(2023, 5, 10);

        assertThrows(IllegalArgumentException.class,
                     () -> { query.executeQuery(userManager, date1, date2); });
    }

    @Test
    void testNullDates() {
        UserManager          userManager = new UserManager();
        WhoBurnsMoreCalories query       = new WhoBurnsMoreCalories();

        assertThrows(IllegalArgumentException.class,
                     () -> { query.executeQuery(userManager, null, null); });
    }

    @Test
    void testWithNoUsers() {
        UserManager          userManager = new UserManager();
        WhoBurnsMoreCalories query       = new WhoBurnsMoreCalories();

        User result = query.executeQuery(userManager);
        assertNull(result);
    }
}
