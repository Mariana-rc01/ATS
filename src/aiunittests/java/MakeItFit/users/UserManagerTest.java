package MakeItFit.users;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.exceptions.EntityDoesNotExistException;
import MakeItFit.exceptions.ExistingEntityConflictException;
import MakeItFit.exceptions.InvalidTypeException;
import MakeItFit.users.types.Amateur;
import MakeItFit.users.types.Occasional;
import MakeItFit.users.types.Professional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserManagerTest {
    private UserManager userManager;
    private User        amateurUser;
    private User        occasionalUser;
    private User        professionalUser;

    @BeforeEach
    void setUp() throws InvalidTypeException {
        userManager = new UserManager();

        amateurUser = userManager.createUser("Amateur User",
                                             25,
                                             Gender.Male,
                                             70.5f,
                                             175,
                                             72,
                                             3,
                                             "Amateur Address",
                                             "123456789",
                                             "amateur@email.com",
                                             3,
                                             "Amateur");

        occasionalUser = userManager.createUser("Occasional User",
                                                30,
                                                Gender.Female,
                                                60.0f,
                                                165,
                                                68,
                                                2,
                                                "Occasional Address",
                                                "987654321",
                                                "occasional@email.com",
                                                2,
                                                "Occasional");

        professionalUser = userManager.createUser("Professional User",
                                                  35,
                                                  Gender.Female,
                                                  80.0f,
                                                  185,
                                                  75,
                                                  4,
                                                  "Professional Address",
                                                  "555555555",
                                                  "professional@email.com",
                                                  5,
                                                  "Professional");
    }

    @Test
    void testCreateUser_Amateur() throws InvalidTypeException {
        User user = userManager.createUser("Test Amateur",
                                           20,
                                           Gender.Male,
                                           65.0f,
                                           170,
                                           70,
                                           2,
                                           "Test Address",
                                           "111111111",
                                           "test@email.com",
                                           0,
                                           "Amateur");

        assertNotNull(user);
        assertTrue(user instanceof Amateur);
        assertEquals("Test Amateur", user.getName());
    }

    @Test
    void testCreateUser_Occasional() throws InvalidTypeException {
        User user = userManager.createUser("Test Occasional",
                                           25,
                                           Gender.Female,
                                           55.0f,
                                           160,
                                           65,
                                           1,
                                           "Test Address",
                                           "222222222",
                                           "test@email.com",
                                           2,
                                           "Occasional");

        assertNotNull(user);
        assertTrue(user instanceof Occasional);
        assertEquals("Test Occasional", user.getName());
    }

    @Test
    void testCreateUser_Professional() throws InvalidTypeException {
        User user = userManager.createUser("Test Professional",
                                           30,
                                           Gender.Male,
                                           75.0f,
                                           180,
                                           70,
                                           3,
                                           "Test Address",
                                           "333333333",
                                           "test@email.com",
                                           4,
                                           "Professional");

        assertNotNull(user);
        assertTrue(user instanceof Professional);
        assertEquals("Test Professional", user.getName());
    }

    @Test
    void testCreateUser_InvalidType() {
        assertThrows(InvalidTypeException.class, () -> {
            userManager.createUser("Invalid User",
                                   40,
                                   Gender.Male,
                                   85.0f,
                                   190,
                                   80,
                                   5,
                                   "Invalid Address",
                                   "444444444",
                                   "invalid@email.com",
                                   3,
                                   "InvalidType");
        });
    }

    @Test
    void testCreateUser_InvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            userManager
                .createUser(null, -1, null, -1.0f, -1, -1, -1, null, null, null, -1, "Amateur");
        });
    }

    @Test
    void testInsertUser() throws ExistingEntityConflictException {
        userManager.insertUser(amateurUser);
        assertTrue(userManager.existsUserWithEmail("amateur@email.com"));
    }

    @Test
    void testInsertUser_DuplicateEmail() throws ExistingEntityConflictException {
        userManager.insertUser(amateurUser);
        assertThrows(ExistingEntityConflictException.class,
                     () -> { userManager.insertUser(amateurUser); });
    }

    @Test
    void testRemoveUserByCode()
        throws ExistingEntityConflictException, EntityDoesNotExistException {
        userManager.insertUser(amateurUser);
        UUID code = amateurUser.getCode();
        userManager.removeUserByCode(code);
        assertFalse(userManager.existsUserWithEmail("amateur@email.com"));
    }

    @Test
    void testRemoveUserByCode_NonExistent() {
        assertThrows(EntityDoesNotExistException.class,
                     () -> { userManager.removeUserByCode(UUID.randomUUID()); });
    }

    @Test
    void testRemoveUserByEmail()
        throws ExistingEntityConflictException, EntityDoesNotExistException {
        userManager.insertUser(occasionalUser);
        userManager.removeUserByEmail("occasional@email.com");
        assertFalse(userManager.existsUserWithEmail("occasional@email.com"));
    }

    @Test
    void testRemoveUserByEmail_NonExistent() {
        assertThrows(EntityDoesNotExistException.class,
                     () -> { userManager.removeUserByEmail("nonexistent@email.com"); });
    }

    @Test
    void testExistsUserWithEmail() throws ExistingEntityConflictException {
        userManager.insertUser(professionalUser);
        assertTrue(userManager.existsUserWithEmail("professional@email.com"));
        assertFalse(userManager.existsUserWithEmail("nonexistent@email.com"));
    }

    @Test
    void testGetUserByCode() throws ExistingEntityConflictException, EntityDoesNotExistException {
        userManager.insertUser(amateurUser);
        UUID code      = amateurUser.getCode();
        User retrieved = userManager.getUserByCode(code);
        assertEquals(amateurUser, retrieved);
    }

    @Test
    void testGetUserByCode_NonExistent() {
        assertThrows(EntityDoesNotExistException.class,
                     () -> { userManager.getUserByCode(UUID.randomUUID()); });
    }

    @Test
    void testGetUserByEmail() throws ExistingEntityConflictException, EntityDoesNotExistException {
        userManager.insertUser(occasionalUser);
        User retrieved = userManager.getUserByEmail("occasional@email.com");
        assertEquals(occasionalUser, retrieved);
    }

    @Test
    void testGetUserByEmail_NonExistent() {
        assertThrows(EntityDoesNotExistException.class,
                     () -> { userManager.getUserByEmail("nonexistent@email.com"); });
    }

    @Test
    void testUpdateUser() throws ExistingEntityConflictException, EntityDoesNotExistException {
        userManager.insertUser(professionalUser);
        professionalUser.setName("Updated Professional");
        userManager.updateUser(professionalUser);

        User updated = userManager.getUserByCode(professionalUser.getCode());
        assertEquals("Updated Professional", updated.getName());
    }

    @Test
    void testUpdateUser_NonExistent() {
        assertThrows(EntityDoesNotExistException.class,
                     () -> { userManager.updateUser(professionalUser); });
    }

    @Test
    void testGetAllUsers() throws ExistingEntityConflictException {
        userManager.insertUser(amateurUser);
        userManager.insertUser(occasionalUser);
        userManager.insertUser(professionalUser);

        List<User> users = userManager.getAllUsers();
        assertEquals(3, users.size());
        assertTrue(users.contains(amateurUser));
        assertTrue(users.contains(occasionalUser));
        assertTrue(users.contains(professionalUser));
    }

    @Test
    void testGetActivitiesFromUser()
        throws ExistingEntityConflictException, EntityDoesNotExistException {
        userManager.insertUser(amateurUser);
        List<Activity> activities = userManager.getActivitiesFromUser("amateur@email.com");
        assertNotNull(activities);
        assertTrue(activities.isEmpty());
    }

    @Test
    void testAddActivityToUser() throws ExistingEntityConflictException {
        userManager.insertUser(occasionalUser);
        Activity activity = new PushUp();
        userManager.addActivityToUser("occasional@email.com", activity);

        assertEquals(1, occasionalUser.getListActivities().size());
    }

    @Test
    void testRemoveActivityFromUser() throws ExistingEntityConflictException {
        userManager.insertUser(professionalUser);
        Activity activity     = new PushUp();
        UUID     activityCode = activity.getCode();
        professionalUser.addActivity(activity);

        userManager.removeActivityFromUser("professional@email.com", activityCode);
        assertEquals(0, professionalUser.getListActivities().size());
    }

    @Test
    void testAddActivitiesToUser() throws ExistingEntityConflictException {
        userManager.insertUser(amateurUser);
        List<Activity> activities = Arrays.asList(new PushUp(), new PushUp());
        userManager.addActivitiesToUser(amateurUser.getCode(), activities);

        assertEquals(2, amateurUser.getListActivities().size());
    }
}
