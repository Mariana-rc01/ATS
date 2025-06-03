package MakeItFit.queries;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.users.UserManager;
import MakeItFit.users.types.Professional;
import MakeItFit.utils.MakeItFitDate;

public class WhoBurnsMoreCaloriesTest {

    private WhoBurnsMoreCalories query;
    private UserManager userManager;

    private User createUser(String email, float weight, int height, int bpm) {
        return new Professional(
            "JohnDoe",
            21,
            Gender.Male,
            weight,
            height,
            bpm,
            6,
            "Lloyd",
            "987 123 432",
            email,
            7
        );
    }

    private PushUp createPushUp(UUID code, MakeItFitDate date) {
        return new PushUp(
            code,
            date,
            60,
            "PushUp",
            "DO IT",
            10,
            100
        );
    }

    @BeforeEach
    void testSetup() {
        query = new WhoBurnsMoreCalories();
        userManager = new UserManager();
    }

    @Test
    void testExecuteQueryWithDatesThrows() {
        assertThrows(
            IllegalArgumentException.class,
            () -> this.query.executeQuery(
                userManager,
                MakeItFitDate.of(2000, 2, 9),
                MakeItFitDate.of(1999, 12, 30)
            )
        );
    }

    @Test
    void testExecuteQueryWithDatesReturnsUserWithValidActivities() {
        User user1 = createUser("jd@uminho.pt", 58, 167, 80);
        user1.addActivities(Arrays.asList(
            createPushUp(user1.getCode(), MakeItFitDate.of(1999, 10, 2))
        ));

        User user2 = createUser("jd@gemail.pt", 58, 167, 80);
        user2.addActivities(Arrays.asList(
            createPushUp(user1.getCode(), MakeItFitDate.of(2004, 2, 15)),
            createPushUp(user1.getCode(), MakeItFitDate.of(2003, 2, 15)),
            createPushUp(user1.getCode(), MakeItFitDate.of(2002, 2, 15))
        ));

        userManager.insertUser(user1);
        userManager.insertUser(user2);

        assertSame(user1,
            query.executeQuery(
                userManager,
                MakeItFitDate.of(1998, 1, 1),
                MakeItFitDate.of(1999, 11, 1)
            )
        );
    }

    @Test
    void testExecuteQueryWithDatesTieReturnsFirstUser() {
        User user1 = createUser("jd@uminho.pt", 58, 167, 80);
        user1.addActivities(Arrays.asList(
            createPushUp(user1.getCode(), MakeItFitDate.of(1999, 10, 2))
        ));
        User user1Copy = createUser("jd@gemail.pt", 58, 167, 80);
        user1Copy.addActivities(Arrays.asList(
            createPushUp(user1.getCode(), MakeItFitDate.of(1999, 10, 2))
        ));

        this.userManager.insertUser(user1);
        this.userManager.insertUser(user1Copy);

        assertSame(
            this.userManager.getAllUsers().get(0),
            query.executeQuery(
                userManager,
                MakeItFitDate.of(1998, 1, 1),
                MakeItFitDate.of(1999, 11, 1)
            )
        );
    }

    @Test
    void testExecuteQueryReturnsCorrectUser() {
        User user1 = createUser("jd@uminho.pt", 58, 167, 80);
        user1.addActivities(Arrays.asList(
            createPushUp(user1.getCode(), MakeItFitDate.of(1999, 10, 2))
        ));

        User user2 = createUser("jd@gemail.pt", 58, 167, 80);
        user2.addActivities(Arrays.asList(
            createPushUp(user2.getCode(), MakeItFitDate.of(2004, 2, 15)),
            createPushUp(user2.getCode(), MakeItFitDate.of(2003, 2, 15)),
            createPushUp(user2.getCode(), MakeItFitDate.of(2002, 2, 15))
        ));

        userManager.insertUser(user1);
        userManager.insertUser(user2);

        /* Calculate calories for user1 */
        int caloriesUser1 = 0;
        for (Activity activity : user1.getListActivities()) {
            caloriesUser1 += activity.caloricWaste(user1.getIndex());
        }

        /* Calculate calories for user2 */
        int caloriesUser2 = 0;
        for (Activity activity : user2.getListActivities()) {
            caloriesUser2 += activity.caloricWaste(user1.getIndex());
        }

        assertSame(caloriesUser1 < caloriesUser2 ? user2 : user1,
            query.executeQuery(
                userManager,
                MakeItFitDate.of(1998, 1, 1),
                MakeItFitDate.of(2004, 2, 16)
            )
        );
    }

    @Test
    void testExecuteQueryTieReturnsFirstUser() {
        User user1 = createUser("jd@uminho.pt", 58, 167, 80);
        user1.addActivities(Arrays.asList(
            createPushUp(user1.getCode(), MakeItFitDate.of(1999, 10, 2))
        ));
        User user1Copy = createUser("jd@gemail.pt", 58, 167, 80);
        user1Copy.addActivities(Arrays.asList(
            createPushUp(user1.getCode(), MakeItFitDate.of(1999, 10, 2))
        ));

        this.userManager.insertUser(user1);
        this.userManager.insertUser(user1Copy);

        assertSame(this.userManager.getAllUsers().get(0), query.executeQuery(userManager));
    }

    @Test
    void testExecuteQueryWithDatesNoUsersReturnsNull() {
        assertNull(
            query.executeQuery(
                this.userManager,
                MakeItFitDate.of(2000, 1, 1),
                MakeItFitDate.of(2000, 1, 2)
            )
        );
    }

    @Test
    void testExecuteQueryNoUsersReturnsNull() {
        assertNull(query.executeQuery(this.userManager));
    }
}
