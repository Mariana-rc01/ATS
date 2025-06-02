package MakeItFit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.activities.implementation.Running;
import MakeItFit.activities.implementation.Trail;
import MakeItFit.exceptions.EntityDoesNotExistException;
import MakeItFit.exceptions.ExistingEntityConflictException;
import MakeItFit.exceptions.InvalidTypeException;
import MakeItFit.trainingPlan.TrainingPlan;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.utils.MakeItFitDate;

public class MakeItFitTest {

    private MakeItFit model;

    @BeforeEach
    void setUp() {
        model = new MakeItFit();
    }

    @Test
    void testCreateAndGetUser() throws Exception {
        model.createUser("João",
                         25,
                         Gender.Male,
                         75.5f,
                         180,
                         70,
                         2,
                         "Rua do João",
                         "911111111",
                         "joao@gmail.com",
                         3,
                         "Amateur");

        assertTrue(model.existsUserWithEmail("joao@gmail.com"));
        User user = model.getUser("joao@gmail.com");
        assertEquals("João", user.getName());
        assertEquals(25, user.getAge());
    }

    @Test
    void testRemoveUserByEmail() throws Exception {
        model.createUser("Maria",
                         30,
                         Gender.Female,
                         65.0f,
                         170,
                         72,
                         3,
                         "Rua da Maria",
                         "922222222",
                         "maria@gmail.com",
                         2,
                         "Amateur");

        model.removeUser("maria@gmail.com");
        assertFalse(model.existsUserWithEmail("maria@gmail.com"));
    }

    @Test
    void testRemoveUserByUUID() throws Exception {
        model.createUser("Carlos",
                         22,
                         Gender.Male,
                         80.0f,
                         185,
                         60,
                         1,
                         "Rua do Carlos",
                         "933333333",
                         "carlos@gmail.com",
                         4,
                         "Amateur");

        UUID id = model.getUser("carlos@gmail.com").getCode();
        model.removeUser(id);
        assertFalse(model.existsUserWithEmail("carlos@gmail.com"));
    }

    @Test
    void testRemoveUserInvalidType() {
        assertThrows(InvalidTypeException.class, () -> model.removeUser(123));
    }

    @Test
    void testGetUserInvalidType() {
        assertThrows(InvalidTypeException.class, () -> model.getUser(123));
    }

    @Test
    void testRemoveNonExistentUserThrowsException() {
        assertThrows(EntityDoesNotExistException.class,
                     () -> model.removeUser("doesntexist@gmail.com"));
    }

    @Test
    void testUpdateUser() throws Exception {
        model.createUser("Ana",
                         28,
                         Gender.Female,
                         58.0f,
                         165,
                         75,
                         2,
                         "Rua da Margarida",
                         "944444444",
                         "margarida@gmail.com",
                         3,
                         "Amateur");

        model.updateUserName("Margarida", "margarida@gmail.com");
        model.updateUserAge(29, "margarida@gmail.com");
        model.updateUserGender(Gender.Male, "margarida@gmail.com");
        model.updateUserWeight(60.0f, "margarida@gmail.com");
        model.updateUserHeight(167, "margarida@gmail.com");
        model.updateUserBpm(78, "margarida@gmail.com");
        model.updateUserLevel(3, "margarida@gmail.com");
        model.updateUserAddress("Rua Nova da Margarida", "margarida@gmail.com");
        model.updateUserPhone("919999999", "margarida@gmail.com");

        User updated = model.getUser("margarida@gmail.com");
        UUID id      = updated.getCode();
        User u       = model.getUser(id);

        assertEquals("Margarida", u.getName());
        assertEquals("Margarida", updated.getName());
        assertEquals(29, updated.getAge());
        assertEquals(Gender.Male, updated.getGender());
        assertEquals(60.0f, updated.getWeight());
        assertEquals(167, updated.getHeight());
        assertEquals(78, updated.getBpm());
        assertEquals(3, updated.getLevel());
        assertEquals("Rua Nova da Margarida", updated.getAddress());
        assertEquals("919999999", updated.getPhone());
    }

    @Test
    void testUpdateEmail() throws Exception {
        model.createUser("Luís",
                         32,
                         Gender.Male,
                         90.0f,
                         190,
                         65,
                         2,
                         "Rua do Luís",
                         "955555555",
                         "luis@gmail.com",
                         2,
                         "Amateur");

        model.updateUserEmail("luis@gmail.com", "luis.silva@gmail.com");
        assertFalse(model.existsUserWithEmail("luis@gmail.com"));
        assertTrue(model.existsUserWithEmail("luis.silva@gmail.com"));
        User user = model.getUser("luis.silva@gmail.com");
        assertEquals("Luís", user.getName());
    }

    @Test
    void testDuplicateUser() throws Exception {
        model.createUser("Pedro",
                         27,
                         Gender.Male,
                         70.0f,
                         175,
                         70,
                         2,
                         "Rua do Pedro",
                         "966666666",
                         "pedro@gmail.com",
                         2,
                         "Amateur");

        assertThrows(ExistingEntityConflictException.class,
                     ()
                         -> model.createUser("Pedro",
                                             27,
                                             Gender.Male,
                                             70.0f,
                                             175,
                                             70,
                                             2,
                                             "Rua do Pedro",
                                             "966666666",
                                             "pedro@gmail.com",
                                             2,
                                             "Amateur"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<User> allUsers = model.getAllUsers();
        assertNotNull(allUsers);
        assertTrue(allUsers.isEmpty());

        model.createUser("Olivia",
                         25,
                         Gender.Male,
                         70,
                         175,
                         70,
                         1,
                         "Rua da Olívia",
                         "977777777",
                         "olivia@outlook.com",
                         1,
                         "Amateur");

        allUsers = model.getAllUsers();
        assertEquals(1, allUsers.size());
        List<String> emails = allUsers.stream().map(User::getEmail).toList();
        assertTrue(emails.contains("olivia@outlook.com"));
    }

    @Test
    void testGetActivitiesFromUser() throws Exception {
        model.createUser("Tiago",
                         29,
                         Gender.Male,
                         72.0f,
                         178,
                         70,
                         2,
                         "Rua do Tiago",
                         "988888888",
                         "tiago@outlook.com",
                         2,
                         "Amateur");

        List<Activity> activities = model.getActivitiesFromUser("tiago@outlook.com");
        assertNotNull(activities);
        assertTrue(activities.isEmpty());

        Activity activity1 = new PushUp();
        Activity activity2 = new Trail();

        User user = model.getUser("tiago@outlook.com");
        user.addActivity(activity1);
        user.addActivity(activity2);

        activities = model.getActivitiesFromUser("tiago@outlook.com");

        assertEquals(2, activities.size());
        assertTrue(activities.contains(activity1));
        assertTrue(activities.contains(activity2));
    }

    @Test
    void testAddActivityToUser() throws Exception {
        model.createUser("Diana",
                         31,
                         Gender.Female,
                         60.0f,
                         168,
                         75,
                         3,
                         "Rua da Diana",
                         "9999999999",
                         "diana@outlook.com",
                         2,
                         "Amateur");

        Activity activity = new Trail();
        model.addActivityToUser("diana@outlook.com", activity);

        List<Activity> activities = model.getActivitiesFromUser("diana@outlook.com");

        assertEquals(1, activities.size());
        assertEquals("", activities.get(0).getName());
        assertEquals(0, activities.get(0).getDuration());
    }

    @Test
    void testRemoveActivityFromUser() throws Exception {
        model.createUser("Renata",
                         30,
                         Gender.Female,
                         75.0f,
                         180,
                         70,
                         3,
                         "Rua da Renata",
                         "912222222",
                         "renata@outlook.com",
                         2,
                         "Amateur");

        Activity activity = new PushUp();
        model.addActivityToUser("renata@outlook.com", activity);
        UUID activityId = activity.getCode();

        assertEquals(1, model.getActivitiesFromUser("renata@outlook.com").size());

        model.removeActivityFromUser("renata@outlook.com", activityId);

        assertTrue(model.getActivitiesFromUser("renata@outlook.com").isEmpty());
    }

    @Test
    void testCreateTrainingPlan() throws Exception {
        model.createUser("Carlota Cambalhota",
                         25,
                         Gender.Female,
                         75.0f,
                         180,
                         70,
                         3,
                         "Rua da Carlota",
                         "913333333",
                         "carlotaCambalhota@outlook.com",
                         2,
                         "Amateur");

        UUID          userCode  = model.getUser("carlotaCambalhota@outlook.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2025, 01, 13);
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);

        assertNotNull(planCode);
    }

    @Test
    void testCreateTrainingPlanException() throws Exception {
        model.createUser("Maria do Céu",
                         32,
                         Gender.Female,
                         60.0f,
                         170,
                         72,
                         3,
                         "Rua da Maria do Céu",
                         "914444444",
                         "maria@outlook.com",
                         2,
                         "Amateur");

        UUID userCode = model.getUser("maria@outlook.com").getCode();

        assertThrows(IllegalArgumentException.class,
                     () -> { model.createTrainingPlan(userCode, null); });
    }

    @Test
    void testConstructTrainingPlan() throws Exception {
        model.createUser("José",
                         30,
                         Gender.Male,
                         80.0f,
                         185,
                         70,
                         3,
                         "Rua do José",
                         "915555555",
                         "jose@outlook.com",
                         2,
                         "Amateur");

        UUID          userCode  = model.getUser("jose@outlook.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2025, 01, 13);
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);
        TrainingPlan  plan      = model.getTrainingPlan(planCode);

        assertDoesNotThrow(
            () -> { model.constructTrainingPlanByObjectives(plan, true, 2, 3, 4, 500); });
    }

    @Test
    void testConstructTrainingPlanException() {
        UUID          fakeUserCode = UUID.randomUUID();
        MakeItFitDate startDate    = MakeItFitDate.of(2025, 01, 13);
        TrainingPlan  fakePlan     = new TrainingPlan(fakeUserCode, startDate);

        assertThrows(EntityDoesNotExistException.class, () -> {
            model.constructTrainingPlanByObjectives(fakePlan, true, 2, 3, 4, 500);
        });
    }

    @Test
    void testRemoveTrainingPlan() throws Exception {
        model.createUser("Mariana",
                         27,
                         Gender.Female,
                         60.0f,
                         170,
                         68,
                         2,
                         "Rua da Mariana",
                         "916666666",
                         "mariana@outlook.com",
                         3,
                         "Amateur");

        UUID          userCode  = model.getUser("mariana@outlook.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2025, 01, 13);
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);

        model.removeTrainingPlan(planCode);
        assertThrows(IllegalArgumentException.class, () -> { model.getTrainingPlan(planCode); });
    }

    @Test
    void testGetTrainingPlan() throws Exception {
        model.createUser("Humberto",
                         29,
                         Gender.Male,
                         75.0f,
                         178,
                         70,
                         3,
                         "Rua do Humberto",
                         "917777777",
                         "humberto@outlook.com",
                         2,
                         "Amateur");

        UUID          userCode  = model.getUser("humberto@outlook.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2025, 01, 13);
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);

        TrainingPlan retrievedPlan = model.getTrainingPlan(planCode);
        assertNotNull(retrievedPlan);
        assertEquals(planCode, retrievedPlan.getCode());
    }

    @Test
    void testGetTrainingPlanException() {
        UUID invalidCode = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> { model.getTrainingPlan(invalidCode); });
    }

    @Test
    void testUpdateTrainingPlan() throws Exception {
        model.createUser("Eva",
                         26,
                         Gender.Female,
                         62.0f,
                         165,
                         75,
                         2,
                         "Rua da Eva",
                         "918888888",
                         "eva@outlook.com",
                         2,
                         "Amateur");

        UUID          userCode  = model.getUser("eva@outlook.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2025, 01, 13);
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);
        TrainingPlan  plan      = model.getTrainingPlan(planCode);
        plan.setStartDate(MakeItFitDate.of(2025, 02, 13));

        assertDoesNotThrow(() -> model.updateTrainingPlan(plan));

        TrainingPlan updated = model.getTrainingPlan(planCode);
        assertEquals(MakeItFitDate.of(2025, 02, 13), updated.getStartDate());
    }

    @Test
    void testUpdateTrainingPlanException() {
        MakeItFitDate startDate = MakeItFitDate.of(2025, 01, 13);
        TrainingPlan  fakePlan  = new TrainingPlan(UUID.randomUUID(), startDate);

        assertThrows(EntityDoesNotExistException.class,
                     () -> { model.updateTrainingPlan(fakePlan); });
    }

    @Test
    void testGetAllTrainingPlans() {
        model.createUser("Vitória",
                         31,
                         Gender.Female,
                         65.0f,
                         168,
                         70,
                         2,
                         "Rua da Vitória",
                         "919999999",
                         "vitoria@outlook.com",
                         2,
                         "Amateur");

        UUID          userCode  = model.getUser("vitoria@outlook.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2025, 01, 13);

        model.createTrainingPlan(userCode, startDate);
        model.createTrainingPlan(userCode, startDate.plusDays(7));

        List<TrainingPlan> plans = model.getAllTrainingPlans();
        assertEquals(2, plans.size());
    }

    @Test
    void testAddActivityToTrainingPlan() {
        model.createUser("Hugo",
                         34,
                         Gender.Male,
                         82.0f,
                         180,
                         75,
                         3,
                         "Rua do Hugo",
                         "921111111",
                         "hugo@outlook.com",
                         3,
                         "Amateur");

        UUID          userCode  = model.getUser("hugo@outlook.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2025, 01, 13);
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);
        Activity      activity  = new PushUp();

        assertDoesNotThrow(() -> { model.addActivityToTrainingPlan(planCode, activity, 3); });

        TrainingPlan plan = model.getTrainingPlan(planCode);
        assertTrue(plan.getActivities().stream().anyMatch(a -> a.getItem2().equals(activity)));
    }

    @Test
    void testRemoveActivityFromTrainingPlan() throws Exception {
        model.createUser("Cândida",
                         29,
                         Gender.Female,
                         60.0f,
                         165,
                         68,
                         2,
                         "Rua da Cândida",
                         "923333333",
                         "candida@outlook.com",
                         3,
                         "Amateur");

        UUID          userCode  = model.getUser("candida@outlook.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2025, 01, 13);
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);

        Activity activity = new PushUp();
        model.addActivityToTrainingPlan(planCode, activity, 2);
        TrainingPlan plan         = model.getTrainingPlan(planCode);
        UUID         activityCode = plan.getActivities().get(0).getItem2().getCode();

        model.removeActivityFromTrainingPlan(planCode, activityCode);

        TrainingPlan updated = model.getTrainingPlan(planCode);
        assertTrue(updated.getActivities().stream().noneMatch(
            a -> a.getItem2().getCode().equals(activityCode)));
    }

    @Test
    void testGetTrainingPlansFromUser() throws Exception {
        model.createUser("Henrique",
                         40,
                         Gender.Male,
                         88.0f,
                         182,
                         72,
                         1,
                         "Rua do Henrique",
                         "924444444",
                         "henrique@outlook.com",
                         2,
                         "Amateur");

        UUID          userCode  = model.getUser("henrique@outlook.com").getCode();
        MakeItFitDate startDate = MakeItFitDate.of(2025, 01, 13);
        model.createTrainingPlan(userCode, startDate);
        model.createTrainingPlan(userCode, startDate.plusDays(7));

        List<TrainingPlan> userPlans = model.getTrainingPlansFromUser(userCode);

        assertEquals(2, userPlans.size());
        for (TrainingPlan plan : userPlans) {
            assertEquals(userCode, plan.getUserCode());
        }
    }

    @Test
    void testUpdateSystem() throws Exception {
        model.createUser("Gabriel",
                         31,
                         Gender.Male,
                         85,
                         180,
                         70,
                         2,
                         "Rua do Gabriel",
                         "926666666",
                         "gabriel@outlook.com",
                         3,
                         "Amateur");

        UUID          userCode  = model.getUser("gabriel@outlook.com").getCode();
        MakeItFitDate startDate = new MakeItFitDate();
        UUID          planCode  = model.createTrainingPlan(userCode, startDate);

        Activity activity = new Running(userCode,
                                        MakeItFitDate.of(2025, 5, 15),
                                        90,
                                        "Let's run",
                                        "Running a lot",
                                        25000,
                                        25.0);

        model.addActivityToTrainingPlan(planCode, activity, 1);
        model.addActivityToUser("gabriel@outlook.com", activity);

        assertEquals(27.98457, model.getUser(userCode).getIndex(), 0.001);

        MakeItFitDate newDate = startDate.plusDays(7);
        model.updateSystem(newDate, userCode);

        User           userAfter  = model.getUser(userCode);
        List<Activity> activities = userAfter.getListActivities();

        assertFalse(activities.isEmpty());

        Activity addedActivity1 = activities.get(0);
        Activity addedActivity2 = activities.get(1);
        assertEquals(87451, addedActivity1.getCaloricWaste());
        assertEquals(87451, addedActivity2.getCaloricWaste());
        assertEquals(27.98457, userAfter.getIndex(), 0.001);
    }

    @Test
    void testSaveSystem() throws Exception {
        String fileName = "makeitfit.txt";

        model.createUser("Domingos",
                         28,
                         Gender.Male,
                         72.0f,
                         175,
                         75,
                         2,
                         "Rua dp Domingos",
                         "927777777",
                         "domingos@outlook.com",
                         2,
                         "Amateur");

        model.saveSystem(fileName);

        File file = new File(fileName);
        assertTrue(file.exists());

        file.delete();
    }

    @Test
    void testSaveSystemException() {
        ByteArrayOutputStream outContent  = new ByteArrayOutputStream();
        PrintStream           originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        String invalidPath = "/invalid_path/file.txt";

        assertThrows(FileNotFoundException.class, () -> { model.saveSystem(invalidPath); });

        System.setOut(originalOut);

        String output = outContent.toString();
        assertTrue(output.contains("MakeItFit.saveSystem:"));
    }

    @Test
    void testLoadSystem() throws Exception {
        String fileName = "makeitfit.txt";

        model.createUser("Filipa",
                         33,
                         Gender.Female,
                         68.0f,
                         170,
                         70,
                         3,
                         "Rua da Filipa",
                         "928888888",
                         "filipa@outlook.com",
                         1,
                         "Amateur");
        model.saveSystem(fileName);

        MakeItFit loadedModel = new MakeItFit();
        loadedModel.loadSystem(fileName);

        assertTrue(loadedModel.existsUserWithEmail("filipa@outlook.com"));
        User loadedUser = loadedModel.getUser("filipa@outlook.com");
        assertEquals("Filipa", loadedUser.getName());

        new File(fileName).delete();
    }

    @Test
    void testLoadSystemException() {
        String invalidFilePath = "filedoesnotexist.txt";

        assertThrows(FileNotFoundException.class, () -> { model.loadSystem(invalidFilePath); });
    }

    @Test
    void testQueryHowManyKMsDoneWithDates() throws Exception {
        model.createUser("Selma",
                         25,
                         Gender.Female,
                         60,
                         165,
                         65,
                         2,
                         "Rua da Selma",
                         "929999999",
                         "selma@outlook.com",
                         1,
                         "Amateur");
        UUID userCode = model.getUser("selma@outlook.com").getCode();

        Activity running = new Running(userCode,
                                       MakeItFitDate.of(2025, 5, 15),
                                       60,
                                       "Running Session",
                                       "Running a lot",
                                       10000,
                                       10.0);
        model.createTrainingPlan(userCode, MakeItFitDate.of(2025, 5, 15));
        model.addActivityToUser("selma@outlook.com", running);

        double kms = model.executeQueryHowManyKMsDone("selma@outlook.com",
                                                      MakeItFitDate.of(2025, 5, 10),
                                                      MakeItFitDate.of(2025, 5, 20));
        assertEquals(10.0, kms, 0.001);
    }

    @Test
    void testQueryHowManyKMsDoneTotal() throws Exception {
        model.createUser("Alberto Caneco",
                         30,
                         Gender.Male,
                         75,
                         180,
                         70,
                         3,
                         "Rua do Alberto",
                         "931111111",
                         "alberto@outlook.com",
                         1,
                         "Amateur");
        UUID userCode = model.getUser("alberto@outlook.com").getCode();

        Activity running = new Running(userCode,
                                       MakeItFitDate.of(2025, 5, 15),
                                       90,
                                       "Running Session",
                                       "Run a lot",
                                       25000,
                                       25.0);
        model.addActivityToUser("alberto@outlook.com", running);

        double totalKms = model.executeQueryHowManyKMsDone("alberto@outlook.com");
        assertEquals(25.0, totalKms, 0.001);
    }

    @Test
    void testExecuteQueryMostDemandingTrainingPlan() {
        model.createUser("Lolita Pirulita",
                         26,
                         Gender.Female,
                         55.0f,
                         160,
                         70,
                         2,
                         "Rua da Lola",
                         "932222222",
                         "lola@outlook.com",
                         3,
                         "Amateur");

        UUID          userCode1 = model.getUser("lola@outlook.com").getCode();
        MakeItFitDate startDate = new MakeItFitDate();
        UUID          planCode1 = model.createTrainingPlan(userCode1, startDate);

        Activity activity = new PushUp();
        model.addActivityToTrainingPlan(planCode1, activity, 1);

        model.createUser("Jorge Bode",
                         26,
                         Gender.Male,
                         55.0f,
                         160,
                         70,
                         2,
                         "Rua do Jorge",
                         "934444444",
                         "jorge@outlook.com",
                         3,
                         "Amateur");

        UUID userCode2 = model.getUser("jorge@outlook.com").getCode();
        UUID planCode2 = model.createTrainingPlan(userCode2, startDate);

        Activity activity2 = new Running(userCode2,
                                         MakeItFitDate.of(2025, 5, 15),
                                         90,
                                         "Let's run",
                                         "Running a lot",
                                         25000,
                                         25.0);
        model.addActivityToTrainingPlan(planCode2, activity, 1);
        model.addActivityToTrainingPlan(planCode2, activity2, 2);

        List<TrainingPlan> t = model.getAllTrainingPlans();
        assertEquals(2, t.size());

        TrainingPlan result = model.executeQueryMostDemandingTrainingPlan();

        assertEquals(planCode2, result.getCode());
    }

    @Test
    void testQueryHowManyAltimetryWithDates() throws Exception {
        model.createUser("Gabriel Pastel",
                         27,
                         Gender.Male,
                         70,
                         175,
                         70,
                         2,
                         "Rua do Gabriel",
                         "934444444",
                         "gabriel@outlook.com",
                         2,
                         "Amateur");
        UUID userCode = model.getUser("gabriel@outlook.com").getCode();

        Trail trail = new Trail(userCode,
                                MakeItFitDate.of(2025, 5, 15),
                                45,
                                "Trail Serra",
                                "Trail",
                                10000,
                                300,
                                0,
                                Trail.TRAIL_TYPE_EASY);
        model.addActivityToUser("gabriel@outlook.com", trail);

        double alt = model.executeQueryHowManyAltimetryDone("gabriel@outlook.com",
                                                            MakeItFitDate.of(2025, 5, 1),
                                                            MakeItFitDate.of(2025, 5, 31));
        assertEquals(300.0, alt, 0.001);
    }

    @Test
    void testQueryHowManyAltimetryTotal() throws Exception {
        model.createUser("Diana Tiana",
                         32,
                         Gender.Female,
                         68,
                         160,
                         65,
                         3,
                         "Rua da Diana",
                         "935555555",
                         "diana@outlook.com",
                         1,
                         "Amateur");
        UUID userCode = model.getUser("diana@outlook.com").getCode();

        Trail hiking = new Trail(userCode,
                                 MakeItFitDate.of(2025, 5, 20),
                                 60,
                                 "Caminhada",
                                 "Caminhada",
                                 8000,
                                 200,
                                 0,
                                 Trail.TRAIL_TYPE_EASY);
        model.addActivityToUser("diana@outlook.com", hiking);

        double alt = model.executeQueryHowManyAltimetryDone("diana@outlook.com");
        assertEquals(200.0, alt, 0.001);
    }

    @Test
    void testQueryMostDoneActivity() throws Exception {
        model.createUser("Marta Prata",
                         26,
                         Gender.Female,
                         55,
                         165,
                         60,
                         2,
                         "Rua da Marta",
                         "936666666",
                         "marta@outlook.com",
                         2,
                         "Amateur");
        UUID userCode = model.getUser("marta@outlook.com").getCode();

        Activity activity1 = new PushUp(userCode,
                                        MakeItFitDate.of(2025, 5, 10),
                                        30,
                                        "Some push ups",
                                        "Let's train",
                                        10,
                                        3);
        Activity activity2 = new PushUp(userCode,
                                        MakeItFitDate.of(2025, 5, 11),
                                        30,
                                        "Some push ups",
                                        "Let's train",
                                        10,
                                        3);

        model.addActivityToUser("marta@outlook.com", activity1);
        model.addActivityToUser("marta@outlook.com", activity2);

        String mostDone = model.executeQueryMostDoneActivity();
        assertEquals("RepetitionsWithWeights", mostDone);
    }

    @Test
    void testQueryUserWhoBurnsMostCaloriesWithDates() throws Exception {
        model.createUser("Marco Macaco",
                         33,
                         Gender.Male,
                         85,
                         185,
                         80,
                         2,
                         "Rua do Marco",
                         "937777777",
                         "marco@outlook.com",
                         1,
                         "Amateur");
        model.createUser("Helena Hiena",
                         29,
                         Gender.Female,
                         60,
                         170,
                         65,
                         2,
                         "Rua H",
                         "938888888",
                         "helena@outlook.com",
                         1,
                         "Professional");
        UUID userCodeG = model.getUser("marco@outlook.com").getCode();
        UUID userCodeH = model.getUser("helena@outlook.com").getCode();

        Activity activity1 = new PushUp(userCodeG,
                                        MakeItFitDate.of(2025, 5, 10),
                                        30,
                                        "Some push ups",
                                        "Let's train",
                                        10,
                                        3);
        Activity activity2 = new PushUp(userCodeH,
                                        MakeItFitDate.of(2025, 5, 11),
                                        30,
                                        "Some push ups",
                                        "Let's train",
                                        10,
                                        3);

        model.addActivityToUser("marco@outlook.com", activity1);
        model.addActivityToUser("helena@outlook.com", activity2);

        User top = model.executeQuerywhoBurnsMoreCalories(MakeItFitDate.of(2020, 5, 10),
                                                          MakeItFitDate.of(2026, 5, 10));
        assertEquals("marco@outlook.com", top.getEmail());
    }

    @Test
    void testQueryUserWhoBurnsMostCalories() throws Exception {
        model.createUser("Amadeu Coliseu",
                         33,
                         Gender.Male,
                         85,
                         185,
                         80,
                         2,
                         "Rua do Amadeu",
                         "939999999",
                         "amadeu@outlook.com",
                         1,
                         "Amateur");
        model.createUser("Sara Vara",
                         29,
                         Gender.Female,
                         60,
                         170,
                         65,
                         2,
                         "Rua da Sara",
                         "941111111",
                         "sara@outlook.com",
                         1,
                         "Professional");
        UUID userCodeG = model.getUser("amadeu@outlook.com").getCode();
        UUID userCodeH = model.getUser("sara@outlook.com").getCode();

        Activity activity1 = new PushUp(userCodeG,
                                        MakeItFitDate.of(2025, 5, 10),
                                        30,
                                        "Some push ups",
                                        "Let's train",
                                        10,
                                        3);
        Activity activity2 = new PushUp(userCodeH,
                                        MakeItFitDate.of(2025, 5, 11),
                                        30,
                                        "Some push ups",
                                        "Let's train",
                                        10,
                                        3);

        model.addActivityToUser("amadeu@outlook.com", activity1);
        model.addActivityToUser("sara@outlook.com", activity2);

        User top = model.executeQuerywhoBurnsMoreCalories();
        assertEquals("amadeu@outlook.com", top.getEmail());
    }

    @Test
    void testQueryUserWhoDidMostActivitiesWithDates() throws Exception {
        model.createUser("Tiago Trago",
                         35,
                         Gender.Male,
                         78,
                         178,
                         70,
                         2,
                         "Rua do Tiago",
                         "94222222",
                         "tiago@outlook.com",
                         1,
                         "Amateur");
        model.createUser("Joana Catana",
                         31,
                         Gender.Female,
                         62,
                         168,
                         60,
                         2,
                         "Rua da Joana",
                         "943333333",
                         "joana@outlook.com",
                         1,
                         "Amateur");

        model.addActivityToUser("tiago@outlook.com", new PushUp());
        model.addActivityToUser("joana@outlook.com", new PushUp());
        model.addActivityToUser("joana@outlook.com", new PushUp());

        User top = model.executeQueryWhoDidTheMostActivities(MakeItFitDate.of(2020, 5, 15),
                                                             MakeItFitDate.of(2025, 10, 15));
        assertEquals("joana@outlook.com", top.getEmail());
    }

    @Test
    void testQueryUserWhoDidMostActivities() throws Exception {
        model.createUser("João Jajao",
                         35,
                         Gender.Male,
                         78,
                         178,
                         70,
                         2,
                         "Rua do João",
                         "945555555",
                         "joao@outlook.com",
                         1,
                         "Amateur");
        model.createUser("Bloom Winx",
                         31,
                         Gender.Female,
                         62,
                         168,
                         60,
                         2,
                         "Rua da Bloom",
                         "946666666",
                         "bloom@outlook.com",
                         1,
                         "Amateur");

        model.addActivityToUser("joao@outlook.com", new PushUp());
        model.addActivityToUser("bloom@outlook.com", new PushUp());
        model.addActivityToUser("bloom@outlook.com", new PushUp());

        User top = model.executeQueryWhoDidTheMostActivities();
        assertEquals("bloom@outlook.com", top.getEmail());
    }
}
