package MakeItFit.queries;

import MakeItFit.activities.implementation.PushUp;
import MakeItFit.activities.implementation.Trail;

import MakeItFit.users.UserManager;
import MakeItFit.users.types.Professional;
import MakeItFit.utils.MakeItFitDate;
import MakeItFit.users.User;
import MakeItFit.users.Gender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HowManyAltimetryDoneTest {
    private HowManyAltimetryDone query;
    private UserManager userManager;
    private User user;

    private Trail createTrail(MakeItFitDate date, double elevationGain, double getElevationLoss) {
        return new Trail(
            user.getCode(),
            date,
            90,
            "Hiking",
            "Subir ao Bom Jesus",
            2000.0,
            elevationGain,
            getElevationLoss,
            1
        );
    }

    private PushUp createPushUp(MakeItFitDate date) {
        return new PushUp(
            user.getCode(),
            date,
            90,
            "PushUps",
            "100x",
            10,
            100
        );
    }

    @BeforeEach
    void testSetup() {
        this.query       = new HowManyAltimetryDone();
        this.userManager = new UserManager();

        this.user = new Professional(
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
    }

    @Test
    void testExecuteQueryWithDatesReturnsTrueAltimetry() {
        user.addActivities(Arrays.asList(
            createTrail(
                MakeItFitDate.of(1999, 2, 9),
                229.12,
                142.0
            ),
            createTrail(
                MakeItFitDate.of(1999, 2, 14),
                654.129,
                532.0
            )
        ));
        userManager.insertUser(user);

        assertEquals(
            229.12 + 142.0 + 654.129 + 532.0,
            query.executeQuery(
                userManager,
                "jd@suspect.pt",
                MakeItFitDate.of(1999, 2, 8),
                MakeItFitDate.of(1999, 3, 1)
            ),
            0.0001
        );
    }

    @Test
    void testExecuteQueryWithDatesThrows() {
        assertThrows(
            IllegalArgumentException.class,
            () -> query.executeQuery(
                userManager,
                "jd@suspect.pt",
                MakeItFitDate.of(1999, 2, 9),
                MakeItFitDate.of(1999, 2, 9)
            )
        );
    }

    @Test
    void testExecuteQueryWithDatesNoValidActivities() {
        user.addActivities(Arrays.asList(
            createPushUp(
                MakeItFitDate.of(1999, 2, 9)
            )
        ));
        userManager.insertUser(user);

        assertEquals(
            0,
            query.executeQuery(
                userManager,
                "jd@suspect.pt",
                MakeItFitDate.of(1999, 2, 9),
                MakeItFitDate.of(1999, 3, 1)
            )
        );
    }

    @Test
    void testExecuteQueryWithDatesActivitiesOutsideTimeWindow() {
        user.addActivities(Arrays.asList(
            createTrail(
                MakeItFitDate.of(1999, 2, 9),
                229.12,
                142.0
            ),
            createTrail(
                MakeItFitDate.of(1999, 2, 14),
                654.129,
                532.0
            )
        ));
        userManager.insertUser(user);

        assertEquals(
            0,
            query.executeQuery(
                userManager,
                "jd@suspect.pt",
                MakeItFitDate.of(1999, 4, 9),
                MakeItFitDate.of(1999, 4, 10)
            )
        );
    }

    @Test
    void testExecuteQueryReturnsTrueAltimetry() {
        user.addActivities(Arrays.asList(
            createTrail(
                MakeItFitDate.of(1999, 2, 9),
                229.12,
                142.0
            ),
            createTrail(
                MakeItFitDate.of(1999, 2, 14),
                654.129,
                532.0
            )
        ));
        userManager.insertUser(user);

        assertEquals(
            229.12 + 142.0 + 654.129 + 532.0,
            query.executeQuery(
                userManager,
                "jd@suspect.pt"
            ),
            0.0001
        );
    }

    @Test
    void testExecuteQueryActivitiesOutsideTimeWindow() {
        user.addActivities(Arrays.asList(
            createPushUp(
                MakeItFitDate.of(1999, 2, 9)
            )
        ));
        userManager.insertUser(user);

        assertEquals(
            0,
            query.executeQuery(
                userManager,
                "jd@suspect.pt"
            )
        );
    }
}
