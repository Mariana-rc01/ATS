package MakeItFit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.exceptions.EntityDoesNotExistException;
import MakeItFit.exceptions.ExistingEntityConflictException;
import MakeItFit.trainingPlan.TrainingPlan;
import MakeItFit.users.Gender;
import MakeItFit.users.User;
import MakeItFit.utils.MakeItFitDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MakeItFitTest {

    private MakeItFit     mif;
    private String        testEmail = "test@example.com";
    private String        email1    = "user1@example.com";
    private String        email2    = "user2@example.com";
    private MakeItFitDate testDate1 = MakeItFitDate.of(2023, 5, 1);
    private MakeItFitDate testDate2 = MakeItFitDate.of(2023, 5, 31);
    private String        fileName  = "makeitfit_test.dat";
    private UUID          testUserId;

    @Before
    public void setUp() throws Exception {
        mif = new MakeItFit();

        // Setup for general user tests
        mif.createUser("Test User",
                       30,
                       Gender.Male,
                       70.5f,
                       175,
                       72,
                       5,
                       "Test Address",
                       "123456789",
                       testEmail,
                       3,
                       "Amateur");
        testUserId = mif.getUser(testEmail).getCode();

        // Setup for query tests
        mif.createUser("Active User",
                       30,
                       Gender.Male,
                       75.0f,
                       180,
                       70,
                       8,
                       "Active St",
                       "111111111",
                       email1,
                       5,
                       "Professional");
        mif.createUser("Casual User",
                       35,
                       Gender.Female,
                       60.0f,
                       165,
                       65,
                       5,
                       "Casual St",
                       "222222222",
                       email2,
                       2,
                       "Amateur");

        // Add activities for query tests
        Activity a1 = new PushUp(); // In date range
        Activity a2 = new PushUp(); // In date range
        Activity a3 = new PushUp(); // Outside date range
        Activity a4 = new PushUp(); // In date range
        Activity a5 = new PushUp(); // Outside date range

        mif.addActivityToUser(email1, a1);
        mif.addActivityToUser(email1, a2);
        mif.addActivityToUser(email1, a3);
        mif.addActivityToUser(email2, a4);
        mif.addActivityToUser(email2, a5);
    }

    // User Management Tests
    @Test
    public void testCreateAndGetUser() throws Exception {
        User user = mif.getUser(testEmail);
        assertNotNull(user);
        assertEquals("Test User", user.getName());
        assertEquals(testEmail, user.getEmail());
    }

    @Test(expected = ExistingEntityConflictException.class)
    public void testCreateDuplicateUser() throws Exception {
        mif.createUser("Test User",
                       30,
                       Gender.Male,
                       70.5f,
                       175,
                       72,
                       5,
                       "Test Address",
                       "123456789",
                       testEmail,
                       3,
                       "Amateur");
    }

    @Test
    public void testRemoveUserByEmail() throws Exception {
        assertTrue(mif.existsUserWithEmail(testEmail));
        mif.removeUser(testEmail);
        assertFalse(mif.existsUserWithEmail(testEmail));
    }

    @Test
    public void testRemoveUserByUUID() throws Exception {
        User user   = mif.getUser(testEmail);
        UUID userId = user.getCode();

        assertTrue(mif.existsUserWithEmail(testEmail));
        mif.removeUser(userId);
        assertFalse(mif.existsUserWithEmail(testEmail));
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void testRemoveNonExistentUser() throws Exception {
        mif.removeUser(UUID.randomUUID());
    }

    @Test
    public void testUpdateUserDetails() throws Exception {
        mif.updateUserName("Updated Name", testEmail);
        mif.updateUserAge(31, testEmail);
        mif.updateUserWeight(71.0f, testEmail);
        mif.updateUserHeight(176, testEmail);

        User updatedUser = mif.getUser("test@example.com");
        assertEquals("Updated Name", updatedUser.getName());
        assertEquals(31, updatedUser.getAge());
        assertEquals(71.0f, updatedUser.getWeight(), 0.001);
        assertEquals(176, updatedUser.getHeight());
    }

    @Test
    public void testUserActivitiesManagement() throws Exception {
        Activity activity = new PushUp();
        mif.addActivityToUser(testEmail, activity);

        List<Activity> activities = mif.getActivitiesFromUser(testEmail);
        assertEquals(1, activities.size());

        mif.removeActivityFromUser(testEmail, activity.getCode());
        activities = mif.getActivitiesFromUser(testEmail);
        assertEquals(0, activities.size());
    }

    // Training Plan Tests
    @Test
    public void testCreateAndGetTrainingPlan() throws Exception {
        MakeItFitDate startDate = MakeItFitDate.of(2023, 6, 1);
        UUID          planId    = mif.createTrainingPlan(testUserId, startDate);

        TrainingPlan plan = mif.getTrainingPlan(planId);
        assertNotNull(plan);
        assertEquals(testUserId, plan.getUserCode());
        assertEquals(startDate, plan.getStartDate());
    }

    @Test
    public void testRemoveTrainingPlan() throws Exception {
        MakeItFitDate startDate = MakeItFitDate.of(2023, 6, 1);
        UUID          planId    = mif.createTrainingPlan(testUserId, startDate);

        assertNotNull(mif.getTrainingPlan(planId));
        mif.removeTrainingPlan(planId);

        try {
            mif.getTrainingPlan(planId);
            fail("Expected exception not thrown");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testGetTrainingPlansFromUser() throws Exception {
        MakeItFitDate date1 = MakeItFitDate.of(2023, 6, 1);
        MakeItFitDate date2 = MakeItFitDate.of(2023, 7, 1);

        UUID planId1 = mif.createTrainingPlan(testUserId, date1);
        UUID planId2 = mif.createTrainingPlan(testUserId, date2);

        List<TrainingPlan> plans = mif.getTrainingPlansFromUser(testUserId);
        assertEquals(2, plans.size());
        assertTrue(plans.stream().anyMatch(p -> p.getCode().equals(planId1)));
        assertTrue(plans.stream().anyMatch(p -> p.getCode().equals(planId2)));
    }

    @Test
    public void testConstructTrainingPlanByObjectives() throws Exception {
        MakeItFitDate startDate = MakeItFitDate.of(2023, 6, 1);
        UUID          planId    = mif.createTrainingPlan(testUserId, startDate);
        TrainingPlan  plan      = mif.getTrainingPlan(planId);

        mif.constructTrainingPlanByObjectives(plan, true, 3, 5, 2, 500);
        assertFalse(plan.getActivities().isEmpty());
    }

    @Test
    public void testTrainingPlanActivitiesManagement() throws Exception {
        MakeItFitDate startDate = MakeItFitDate.of(2023, 6, 1);
        UUID          planId    = mif.createTrainingPlan(testUserId, startDate);

        Activity activity = new PushUp();
        mif.addActivityToTrainingPlan(planId, activity, 10);

        TrainingPlan plan = mif.getTrainingPlan(planId);
        assertEquals(1, plan.getActivities().size());

        mif.removeActivityFromTrainingPlan(planId, activity.getCode());
        assertEquals(0, plan.getActivities().size());
    }

    @Test
    public void testExecuteQueryMostDemandingTrainingPlan() throws Exception {
        UUID user1Id = mif.getUser(email1).getCode();
        UUID user2Id = mif.getUser(email2).getCode();

        UUID planId1 = mif.createTrainingPlan(user1Id, testDate1);
        UUID planId2 = mif.createTrainingPlan(user2Id, testDate1);

        TrainingPlan plan2 = mif.getTrainingPlan(planId2);

        mif.constructTrainingPlanByObjectives(plan2, false, 3, 3, 2, 500);

        TrainingPlan mostDemanding = mif.executeQueryMostDemandingTrainingPlan();
        assertEquals(planId2, mostDemanding.getCode());
    }

    @Test
    public void testExecuteQueryMostDoneActivity() {
        String mostDone = mif.executeQueryMostDoneActivity();
        assertEquals("RepetitionsWithWeights", mostDone);
    }

    // Persistence Tests
    @Test
    public void testSaveAndLoadSystem() throws Exception {
        // Save system
        mif.saveSystem(fileName);

        // Create new instance and load
        MakeItFit loadedMif = new MakeItFit();
        loadedMif.loadSystem(fileName);

        // Verify data was loaded correctly
        User loadedUser = loadedMif.getUser(testEmail);
        assertNotNull(loadedUser);
        assertEquals("Test User", loadedUser.getName());
    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadNonExistentFile() throws Exception {
        mif.loadSystem("nonexistent_file.dat");
    }

    // System Update Tests
    @Test
    public void testUpdateSystemWithTrainingPlan() throws Exception {
        MakeItFitDate startDate   = MakeItFitDate.of(2023, 6, 1);
        MakeItFitDate currentDate = MakeItFitDate.of(2023, 6, 2);

        UUID         planId = mif.createTrainingPlan(testUserId, startDate);
        TrainingPlan plan   = mif.getTrainingPlan(planId);
        mif.constructTrainingPlanByObjectives(plan, false, 2, 3, 1, 300);

        assertEquals(0, mif.getActivitiesFromUser(testEmail).size());
        mif.updateSystem(currentDate, testUserId);
        assertTrue(mif.getActivitiesFromUser(testEmail).size() > 0);
    }

    @Test
    public void testUpdateSystemWithMultipleDays() throws Exception {
        MakeItFitDate startDate = MakeItFitDate.of(2023, 6, 1);
        MakeItFitDate day1      = MakeItFitDate.of(2023, 6, 1);
        MakeItFitDate day2      = MakeItFitDate.of(2023, 6, 2);

        UUID         planId = mif.createTrainingPlan(testUserId, startDate);
        TrainingPlan plan   = mif.getTrainingPlan(planId);
        mif.constructTrainingPlanByObjectives(plan, false, 2, 3, 1, 300);

        mif.updateSystem(day1, testUserId);
        int day1Activities = mif.getActivitiesFromUser(testEmail).size();

        mif.updateSystem(day2, testUserId);
        int day2Activities = mif.getActivitiesFromUser(testEmail).size();

        assertTrue(day2Activities > day1Activities);
    }

    // Clean up after tests
    @After
    public void tearDown() {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
