package MakeItFit.queries;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import MakeItFit.activities.implementation.PushUp;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.users.UserManager;
import MakeItFit.users.types.Professional;
import MakeItFit.utils.MakeItFitDate;

public class WhoDidTheMostActivitiesTest {

    private WhoDidTheMostActivities query;
    private UserManager userManager;

    private User createUser(String email) {
        return new Professional(
            "JohnDoe",
            21,
            Gender.Male,
            60,
            140,
            54,
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
        this.query       = new WhoDidTheMostActivities();
        this.userManager = new UserManager();
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
        User user1 = createUser("jd@uminho.pt");
        user1.addActivities(Arrays.asList(
            createPushUp(user1.getCode(), MakeItFitDate.of(1999, 10, 2))
        ));

        User user2 = createUser("jd@gemail.pt");
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
        User user1 = createUser("jd@uminho.pt");
        user1.addActivities(Arrays.asList(
            createPushUp(user1.getCode(), MakeItFitDate.of(1999, 10, 2))
        ));
        User user1Copy = createUser("jd@gemail.pt");
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
        User user1 = createUser("jd@uminho.pt");
        user1.addActivities(Arrays.asList(
            createPushUp(user1.getCode(), MakeItFitDate.of(1999, 10, 2))
        ));

        User user2 = createUser("jd@gemail.pt");
        user2.addActivities(Arrays.asList(
            createPushUp(user2.getCode(), MakeItFitDate.of(2004, 2, 15)),
            createPushUp(user2.getCode(), MakeItFitDate.of(2003, 2, 15)),
            createPushUp(user2.getCode(), MakeItFitDate.of(2002, 2, 15))
        ));

        userManager.insertUser(user1);
        userManager.insertUser(user2);

        assertSame(
            user1.getListActivities().size() < user2.getListActivities().size() ? user2 : user1,
            query.executeQuery(
                userManager,
                MakeItFitDate.of(1998, 1, 1),
                MakeItFitDate.of(2004, 2, 16)
            )
        );
    }

    @Test
    void testExecuteQueryTieReturnsFirstUser() {
        User user1 = createUser("jd@uminho.pt");
        user1.addActivities(Arrays.asList(
            createPushUp(user1.getCode(), MakeItFitDate.of(1999, 10, 2))
        ));
        User user1Copy = createUser("jd@gemail.pt");
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
