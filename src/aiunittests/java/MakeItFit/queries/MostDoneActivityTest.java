package MakeItFit.queries;

import java.util.ArrayList;
import java.util.List;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.users.UserManager;
import MakeItFit.users.types.Amateur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MostDoneActivityTest {

    @Test
    void testExecuteQuery() {
        // Setup
        UserManager userManager = new UserManager();

        // User 1 with mostly Distance activities
        User           user1       = new Amateur("José Fernandes",
                                 45,
                                 Gender.Male,
                                 75,
                                 175,
                                 67,
                                 6,
                                 "Braga",
                                 "990 000 000",
                                 "josefernandes@mail.com");
        List<Activity> activities1 = new ArrayList<>();
        activities1.add(new PushUp());
        activities1.add(new PushUp());
        activities1.add(new PushUp());
        user1.addActivities(activities1);

        // User 2 with mostly RepetitionsWithWeights activities
        User           user2       = new Amateur("José Fernandes",
                                 45,
                                 Gender.Male,
                                 75,
                                 175,
                                 67,
                                 6,
                                 "Braga",
                                 "990 000 000",
                                 "josefernandes1@mail.com");
        List<Activity> activities2 = new ArrayList<>();
        activities2.add(new PushUp());
        activities2.add(new PushUp());
        activities2.add(new PushUp());
        user2.addActivities(activities2);

        userManager.insertUser(user1);
        userManager.insertUser(user2);

        MostDoneActivity query = new MostDoneActivity();

        // Test
        String result = query.executeQuery(userManager);

        // Verify (Distance appears 2 times, RepetitionsWithWeights appears 2 times,
        // but Distance comes first in the switch case)
        assertEquals("RepetitionsWithWeights", result);
    }
}
