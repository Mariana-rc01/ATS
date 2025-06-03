package MakeItFit.queries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.activities.implementation.Running;
import MakeItFit.activities.implementation.Trail;
import MakeItFit.activities.implementation.WeightSquat;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.users.UserManager;
import MakeItFit.users.types.Professional;
import MakeItFit.utils.MakeItFitDate;

public class MostDoneActivityTest {

    private MostDoneActivity query;
    private UserManager userManager;

    private PushUp pushUp;
    private Running running;
    private Trail trail;
    private WeightSquat weightSquat;

    private User createUser(String email, List<Activity> activities) {
        User user = new Professional(
            "JohnDoe",
            21,
            Gender.Male,
            59,
            150,
            60,
            6,
            "Lloyd",
            "987 123 432",
            email,
            7
        );

        user.addActivities(activities);
        return user;
    }

    @BeforeEach
    void testSetup() {
        this.query = new MostDoneActivity();
        this.userManager = new UserManager();

        this.pushUp = new PushUp(
            UUID.randomUUID(),
            MakeItFitDate.of(2000, 1, 1),
            60,
            "PushUp",
            "DO IT",
            10,
            100
        );
        this.running = new Running(
            UUID.randomUUID(),
            MakeItFitDate.of(2000, 1, 1),
            70,
            "Running",
            "For my life",
            100,
            1
        );
        this.trail = new Trail(
            UUID.randomUUID(),
            MakeItFitDate.of(2000, 1, 1),
            120,
            "Trail",
            "RunningUpThatHill",
            100,
            123,
            321,
            2
        );
        this.weightSquat = new WeightSquat(
            UUID.randomUUID(),
            MakeItFitDate.of(2000, 1, 1),
            45,
            "WeightSquat",
            "PumpItUp",
            10,
            100,
            46
        );
    }

    @Test
    void testExecuteQueryNoUsers() {
        assertEquals("DistanceWithAltimetry", this.query.executeQuery(this.userManager));
    }

    @Test
    void testExecuteQuerySingleUserReturnsRepetitions() {
        User user = createUser("jd@uminho.pt", Arrays.asList(
            this.pushUp,
            this.pushUp,
            this.running,
            this.trail,
            this.weightSquat
        ));
        this.userManager.insertUser(user);

        assertEquals("Repetitions", this.query.executeQuery(this.userManager));
    }

    @Test
    void testExecuteQuerySingleUserReturnsDistance() {
        User user = createUser("jd@uminho.pt", Arrays.asList(
            this.pushUp,
            this.running,
            this.running,
            this.trail,
            this.weightSquat
        ));
        this.userManager.insertUser(user);

        assertEquals("Distance", this.query.executeQuery(this.userManager));
    }

    @Test
    void testExecuteQuerySingleUserReturnsDistanceWithAltimetry() {
        User user = createUser("jd@uminho.pt", Arrays.asList(
            this.pushUp,
            this.running,
            this.trail,
            this.trail,
            this.weightSquat
        ));
        this.userManager.insertUser(user);

        assertEquals("DistanceWithAltimetry", this.query.executeQuery(this.userManager));
    }

    @Test
    void testExecuteQuerySingleUserReturnsRepetitionsWithWeights() {
        User user = createUser("jd@uminho.pt", Arrays.asList(
            this.pushUp,
            this.running,
            this.trail,
            this.weightSquat,
            this.weightSquat
        ));
        this.userManager.insertUser(user);

        assertEquals("RepetitionsWithWeights", this.query.executeQuery(this.userManager));
    }

    @Test
    void testExecuteQueryMultipleUsersReturnsDistance() {
        User user1 = createUser("jd@suspect.pt", Arrays.asList(this.pushUp, this.pushUp));
        User user2 = createUser("jd@uminho.pt", Arrays.asList(this.trail));
        User user3 = createUser("jd@eb23.pt", Arrays.asList(this.weightSquat, this.weightSquat));
        User user4 = createUser(
            "jd@vice.pt",
            Arrays.asList(this.running, this.running, this.running)
        );

        this.userManager.insertUser(user1);
        this.userManager.insertUser(user2);
        this.userManager.insertUser(user3);
        this.userManager.insertUser(user4);

        assertEquals("Distance", this.query.executeQuery(this.userManager));
    }
}
