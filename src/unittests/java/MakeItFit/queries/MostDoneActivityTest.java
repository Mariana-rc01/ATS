package MakeItFit.queries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.rmi.server.UID;
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

    private User createUser(List<Activity> activities) {
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
            "jd@suspect.pt",
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
        User user = createUser(Arrays.asList(
            this.pushUp,
            this.pushUp,
            this.pushUp,
            this.pushUp,
            this.running,
            this.running,
            this.trail,
            this.trail,
            this.trail,
            this.weightSquat,
            this.weightSquat,
            this.weightSquat,
            this.weightSquat
        ));
        this.userManager.insertUser(user);

        assertEquals("Repetitions", this.query.executeQuery(this.userManager));
    }

    @Test
    void testExecuteQueryMultipleUsersReturnsDistance() {
        User user1 = createUser(Arrays.asList(this.pushUp, this.pushUp));
        User user2 = createUser(Arrays.asList(this.trail));
        User user3 = createUser(Arrays.asList(this.running, this.running, this.running));
        User user4 = createUser(Arrays.asList(this.weightSquat, this.weightSquat));

        this.userManager.insertUser(user1);
        this.userManager.insertUser(user2);
        this.userManager.insertUser(user3);
        this.userManager.insertUser(user4);

        assertEquals("Distance", this.query.executeQuery(this.userManager));
    }

    /*
    @Test
    @DisplayName("Tie between 'Repetitions' and 'Distance' → returns 'DistanceWithAltimetry'? No: check exact index mapping")
    void testExecuteQuery_tieBetweenTwoCategories() {
        // Arrange: Build two users leading to a tie between index 1 (Distance) and index 3 (Repetitions)
        User uA = mock(User.class);
        Distance dA1 = new Distance( ... );
        Distance dA2 = new Distance( ... );
        when(uA.getListActivities()).thenReturn(Arrays.asList(dA1, dA2));                // :contentReference[oaicite:16]{index=16}

        User uB = mock(User.class);
        Repetitions rB1 = new Repetitions( ... );
        Repetitions rB2 = new Repetitions( ... );
        when(uB.getListActivities()).thenReturn(Arrays.asList(rB1, rB2));                // :contentReference[oaicite:17]{index=17}

        when(mockUserManager.getAllUsers())
            .thenReturn(Arrays.asList(uA, uB));                                          // :contentReference[oaicite:18]{index=18}

        // Counts:
        //   DistanceWithAltimetry = 0
        //   Distance = 2
        //   RepetitionsWithWeights = 0
        //   Repetitions = 2
        //   → maxIndices = {1, 3} tied at 2. Since max(...) picks the first occurrence of the maximum, index 1 (“Distance”) is returned.

        // Act
        String result = queryService.executeQuery(mockUserManager);

        // Assert: “Distance” should be returned because index 1 (Distance) appears before index 3 (Repetitions) in the tie
        assertEquals("Distance", result,
            "Tie between Distance and Repetitions → Distance (index 1) should win because it appears first"); // :contentReference[oaicite:19]{index=19}
    }
    */
}
