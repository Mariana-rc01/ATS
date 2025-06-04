package MakeItFit.users;

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
import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {
    private UserManager referenceManager;
    private User        referenceUser1, referenceUser2, referenceUser3;
    private PushUp      referenceActivity;

    @BeforeEach
    public void initializeReferenceManager() {
        this.referenceManager = new UserManager();
        this.referenceUser1   = this.referenceManager.createUser("João",
                                                               30,
                                                               Gender.Male,
                                                               80.5f,
                                                               180,
                                                               70,
                                                               0,
                                                               "Casa",
                                                               "+351 123",
                                                               "abc@def.com",
                                                               0,
                                                               "Amateur");
        this.referenceUser2   = this.referenceManager.createUser("Maria",
                                                               25,
                                                               Gender.Female,
                                                               60.2f,
                                                               165,
                                                               74,
                                                               1,
                                                               "Mi casa tu casa",
                                                               "+351 456",
                                                               "ghi@jkl.com",
                                                               10,
                                                               "Occasional");
        this.referenceUser3 =
            this.referenceManager.createUser("Alex",
                                             40,
                                             Gender.Other,
                                             65.0f,
                                             170,
                                             68,
                                             1,
                                             "Morada",
                                             "+351 789",
                                             "MNO@PQR.com", // Test upper case emails
                                             7,
                                             "Professional");

        this.referenceManager.insertUser(this.referenceUser1);
        this.referenceManager.insertUser(this.referenceUser2);
        this.referenceManager.insertUser(this.referenceUser3);

        this.referenceActivity =
            new PushUp(this.referenceUser1.getCode(), new MakeItFitDate(), 1, "", "", 10, 10);
    }

    @Test
    void testConstructor() {
        UserManager manager = new UserManager();
        assertEquals(List.of(), manager.getAllUsers());
    }

    @Test
    public void testCreateUser1() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.referenceManager.createUser(null,
                                             30,
                                             Gender.Male,
                                             70.5f,
                                             180,
                                             70,
                                             1,
                                             "123 Main St",
                                             "123-456-7890",
                                             "john.doe@example.com",
                                             0,
                                             "Amateur");
        });
    }

    @Test
    public void testCreateUser2() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.referenceManager.createUser("John Doe",
                                             30,
                                             Gender.Male,
                                             70.5f,
                                             180,
                                             70,
                                             1,
                                             null,
                                             "123-456-7890",
                                             "john.doe@example.com",
                                             0,
                                             "Amateur");
        });
    }

    @Test
    public void testCreateUser3() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.referenceManager.createUser("John Doe",
                                             30,
                                             Gender.Male,
                                             70.5f,
                                             180,
                                             70,
                                             1,
                                             "123 Main St",
                                             null,
                                             "john.doe@example.com",
                                             0,
                                             "Amateur");
        });
    }

    @Test
    public void testCreateUser4() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.referenceManager.createUser("John Doe",
                                             30,
                                             Gender.Male,
                                             70.5f,
                                             180,
                                             70,
                                             1,
                                             "123 Main St",
                                             "123-456-7890",
                                             null,
                                             0,
                                             "Amateur");
        });
    }

    @Test
    public void testCreateUser5() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.referenceManager.createUser("John Doe",
                                             -1,
                                             Gender.Male,
                                             70.5f,
                                             180,
                                             70,
                                             1,
                                             "123 Main St",
                                             "123-456-7890",
                                             "john.doe@example.com",
                                             0,
                                             "Amateur");
        });
    }

    @Test
    public void testCreateUser6() {
        User u = this.referenceManager.createUser("John Doe",
                                                  0,
                                                  Gender.Male,
                                                  70.5f,
                                                  180,
                                                  70,
                                                  1,
                                                  "123 Main St",
                                                  "123-456-7890",
                                                  "john.doe@example.com",
                                                  0,
                                                  "Amateur");
        assertTrue(u instanceof Amateur);
    }

    @Test
    public void testCreateUser7() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.referenceManager.createUser("John Doe",
                                             30,
                                             Gender.Male,
                                             -1.0f,
                                             180,
                                             70,
                                             1,
                                             "123 Main St",
                                             "123-456-7890",
                                             "john.doe@example.com",
                                             0,
                                             "Amateur");
        });
    }

    @Test
    public void testCreateUser8() {
        User u = this.referenceManager.createUser("John Doe",
                                                  0,
                                                  Gender.Male,
                                                  0.0f,
                                                  180,
                                                  70,
                                                  1,
                                                  "123 Main St",
                                                  "123-456-7890",
                                                  "john.doe@example.com",
                                                  0,
                                                  "Amateur");
        assertTrue(u instanceof Amateur);
    }

    @Test
    public void testCreateUser9() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.referenceManager.createUser("John Doe",
                                             30,
                                             Gender.Male,
                                             70.5f,
                                             -1,
                                             70,
                                             1,
                                             "123 Main St",
                                             "123-456-7890",
                                             "john.doe@example.com",
                                             0,
                                             "Amateur");
        });
    }

    @Test
    public void testCreateUser10() {
        User u = this.referenceManager.createUser("John Doe",
                                                  30,
                                                  Gender.Male,
                                                  70.5f,
                                                  0,
                                                  70,
                                                  1,
                                                  "123 Main St",
                                                  "123-456-7890",
                                                  "john.doe@example.com",
                                                  0,
                                                  "Amateur");
        assertTrue(u instanceof Amateur);
    }

    @Test
    public void testCreateUser11() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.referenceManager.createUser("John Doe",
                                             30,
                                             Gender.Male,
                                             70.5f,
                                             180,
                                             -1,
                                             1,
                                             "123 Main St",
                                             "123-456-7890",
                                             "john.doe@example.com",
                                             0,
                                             "Amateur");
        });
    }

    @Test
    public void testCreateUser12() {
        User u = this.referenceManager.createUser("John Doe",
                                                  30,
                                                  Gender.Male,
                                                  70.5f,
                                                  180,
                                                  0,
                                                  1,
                                                  "123 Main St",
                                                  "123-456-7890",
                                                  "john.doe@example.com",
                                                  0,
                                                  "Amateur");
        assertTrue(u instanceof Amateur);
    }

    @Test
    public void testCreateUser13() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.referenceManager.createUser("John Doe",
                                             30,
                                             Gender.Male,
                                             70.5f,
                                             180,
                                             70,
                                             -1,
                                             "123 Main St",
                                             "123-456-7890",
                                             "john.doe@example.com",
                                             0,
                                             "Amateur");
        });
    }

    @Test
    public void testCreateUser14() {
        User u = this.referenceManager.createUser("John Doe",
                                                  30,
                                                  Gender.Male,
                                                  70.5f,
                                                  180,
                                                  70,
                                                  0,
                                                  "123 Main St",
                                                  "123-456-7890",
                                                  "john.doe@example.com",
                                                  0,
                                                  "Amateur");
        assertTrue(u instanceof Amateur);
    }

    @Test
    public void testCreateUser15() {
        assertThrows(InvalidTypeException.class, () -> {
            this.referenceManager.createUser("John Doe",
                                             30,
                                             Gender.Male,
                                             70.5f,
                                             180,
                                             70,
                                             1,
                                             "123 Main St",
                                             "123-456-7890",
                                             "john.doe@example.com",
                                             0,
                                             "InvalidType");
        });
    }

    @Test
    void testInsertUser1() {
        UserManager manager = new UserManager();
        manager.insertUser(this.referenceUser1);
        manager.insertUser(this.referenceUser2);

        List<User> users = manager.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(this.referenceUser1));
        assertTrue(users.contains(this.referenceUser2));
    }

    @Test
    void testInsertUser2() {
        assertThrows(ExistingEntityConflictException.class,
                     () -> { this.referenceManager.insertUser(this.referenceUser2); });
    }

    @Test
    void testInsertUser3() {
        UserManager manager = new UserManager();
        manager.insertUser(this.referenceUser1);

        this.referenceUser2.setEmail(this.referenceUser1.getEmail());
        assertThrows(ExistingEntityConflictException.class,
                     () -> { this.referenceManager.insertUser(this.referenceUser2); });
    }

    @Test
    void testInsertUser4() {
        UserManager manager = new UserManager();
        manager.insertUser(this.referenceUser3);

        User copy = this.referenceUser3.clone();
        copy.setEmail("novo-email@gmail.com");

        assertThrows(ExistingEntityConflictException.class,
                     () -> { this.referenceManager.insertUser(copy); });
    }

    @Test
    void testRemoveUserByCode1() {
        this.referenceManager.removeUserByCode(referenceUser2.getCode());

        List<User> users = this.referenceManager.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(this.referenceUser1));
        assertTrue(users.contains(this.referenceUser3));
    }

    @Test
    void testRemoveUserByCode2() {
        this.referenceManager.removeUserByCode(referenceUser2.getCode());

        assertTrue(this.referenceManager.existsUserWithEmail(this.referenceUser1.getEmail()));
        assertFalse(this.referenceManager.existsUserWithEmail(this.referenceUser2.getEmail()));
        assertTrue(this.referenceManager.existsUserWithEmail(this.referenceUser3.getEmail()));
    }

    @Test
    void testRemoveUserByCode3() {
        this.referenceManager.removeUserByCode(referenceUser3.getCode());

        assertSame(this.referenceUser1,
                   this.referenceManager.getUserByCode(this.referenceUser1.getCode()));
        assertSame(this.referenceUser2,
                   this.referenceManager.getUserByCode(this.referenceUser2.getCode()));
        assertThrows(EntityDoesNotExistException.class,
                     () -> { this.referenceManager.getUserByCode(this.referenceUser3.getCode()); });
    }

    @Test
    void testRemoveUserByCode4() {
        UserManager manager = new UserManager();
        manager.insertUser(this.referenceUser1);
        manager.insertUser(this.referenceUser2);

        assertThrows(EntityDoesNotExistException.class,
                     () -> { manager.removeUserByCode(referenceUser3.getCode()); });
    }

    @Test
    void testRemoveUserByEmail1() {
        this.referenceManager.removeUserByEmail(referenceUser2.getEmail());

        List<User> users = this.referenceManager.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(this.referenceUser1));
        assertTrue(users.contains(this.referenceUser3));
    }

    @Test
    void testRemoveUserByEmail2() {
        this.referenceManager.removeUserByEmail(referenceUser2.getEmail());

        assertTrue(this.referenceManager.existsUserWithEmail(this.referenceUser1.getEmail()));
        assertFalse(this.referenceManager.existsUserWithEmail(this.referenceUser2.getEmail()));
        assertTrue(this.referenceManager.existsUserWithEmail(this.referenceUser3.getEmail()));
    }

    @Test
    void testRemoveUserByEmail3() {
        this.referenceManager.removeUserByEmail(referenceUser3.getEmail());

        assertSame(this.referenceUser1,
                   this.referenceManager.getUserByCode(this.referenceUser1.getCode()));
        assertSame(this.referenceUser2,
                   this.referenceManager.getUserByCode(this.referenceUser2.getCode()));
        assertThrows(EntityDoesNotExistException.class,
                     () -> { this.referenceManager.getUserByCode(this.referenceUser3.getCode()); });
    }

    @Test
    void testRemoveUserByEmail4() {
        this.referenceManager.removeUserByEmail(referenceUser2.getEmail().toUpperCase());

        List<User> users = this.referenceManager.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(this.referenceUser1));
        assertTrue(users.contains(this.referenceUser3));
    }

    @Test
    void testRemoveUserByEmail5() {
        UserManager manager = new UserManager();
        manager.insertUser(this.referenceUser1);
        manager.insertUser(this.referenceUser2);

        assertThrows(EntityDoesNotExistException.class,
                     () -> { manager.removeUserByEmail(referenceUser3.getEmail()); });
    }

    @Test
    void testExistsUserWithEmail1() {
        assertTrue(this.referenceManager.existsUserWithEmail(this.referenceUser3.getEmail()));
    }

    @Test
    void testExistsUserWithEmail2() {
        assertTrue(this.referenceManager.existsUserWithEmail(this.referenceUser3.getEmail()));
    }

    @Test
    void testExistsUserWithEmail3() {
        assertTrue(this.referenceManager.existsUserWithEmail(
            this.referenceUser1.getEmail().toUpperCase()));
    }

    @Test
    void testExistsUserWithEmail4() {
        assertFalse(this.referenceManager.existsUserWithEmail("inexistente@ats.pt"));
    }

    @Test
    void testGetUserByCode1() {
        assertSame(this.referenceUser1,
                   this.referenceManager.getUserByCode(this.referenceUser1.getCode()));
    }

    @Test
    void testGetUserByCode2() {
        UserManager manager = new UserManager();
        manager.insertUser(this.referenceUser3);

        assertThrows(EntityDoesNotExistException.class,
                     () -> { manager.getUserByCode(this.referenceUser2.getCode()); });
    }

    @Test
    void testGetUserByEmail1() {
        assertSame(this.referenceUser1,
                   this.referenceManager.getUserByEmail(this.referenceUser1.getEmail()));
    }

    @Test
    void testGetUserByEmail2() {
        assertSame(
            this.referenceUser1,
            this.referenceManager.getUserByEmail(this.referenceUser1.getEmail().toUpperCase()));
    }

    @Test
    void testGetUserByEmail3() {
        UserManager manager = new UserManager();
        manager.insertUser(this.referenceUser3);

        assertThrows(EntityDoesNotExistException.class,
                     () -> { manager.getUserByEmail(this.referenceUser2.getEmail()); });
    }

    @Test
    void testUpdateUser1() {
        this.referenceUser1.setName("Joaquina");
        this.referenceManager.updateUser(this.referenceUser1);
        assertSame(this.referenceUser1,
                   this.referenceManager.getUserByCode(this.referenceUser1.getCode()));
    }

    @Test
    void testUpdateUser2() {
        UserManager manager = new UserManager();
        manager.insertUser(this.referenceUser1);
        assertThrows(EntityDoesNotExistException.class,
                     () -> { manager.updateUser(this.referenceUser2); });
    }

    @Test
    void testUpdateUser3() {
        this.referenceUser1.setEmail("novo-email@proton.me");
        assertThrows(EntityDoesNotExistException.class,
                     () -> { this.referenceManager.updateUser(this.referenceUser1); });
    }

    @Test
    void testUpdateUser4() {
        this.referenceUser1.setEmail(this.referenceUser3.getEmail());
        assertThrows(EntityDoesNotExistException.class,
                     () -> { this.referenceManager.updateUser(this.referenceUser1); });
    }

    @Test
    void testGetActivitiesFromUser1() {
        assertEquals(List.of(),
                     this.referenceManager.getActivitiesFromUser(this.referenceUser1.getEmail()));
    }

    @Test
    void testGetActivitiesFromUser2() {
        this.referenceManager.addActivityToUser(this.referenceUser1.getEmail(),
                                                this.referenceActivity);
        assertEquals(List.of(this.referenceActivity),
                     this.referenceManager.getActivitiesFromUser(this.referenceUser1.getEmail()));
    }

    @Test
    void testGetActivitiesFromUser3() {
        assertThrows(EntityDoesNotExistException.class,
                     () -> { this.referenceManager.getActivitiesFromUser("EMAIL-INVALIDO@1234"); });
    }

    @Test
    void testAddActivityToUser1() {
        this.referenceManager.addActivityToUser(this.referenceUser3.getEmail(),
                                                this.referenceActivity);

        assertEquals(0, this.referenceUser1.getListActivities().size());
        assertEquals(0, this.referenceUser2.getListActivities().size());
        assertEquals(1, this.referenceUser3.getListActivities().size());
    }

    @Test
    void testAddActivityToUser2() {
        this.referenceManager.addActivityToUser(this.referenceUser1.getEmail(),
                                                this.referenceActivity);
        this.referenceManager.addActivityToUser(this.referenceUser1.getEmail(),
                                                this.referenceActivity);

        assertEquals(2, this.referenceUser1.getListActivities().size());
        assertEquals(0, this.referenceUser2.getListActivities().size());
        assertEquals(0, this.referenceUser3.getListActivities().size());
    }

    @Test
    void testAddActivityToUser3() {
        assertThrows(EntityDoesNotExistException.class, () -> {
            this.referenceManager.addActivityToUser("", this.referenceActivity);
        });
    }

    @Test
    void testRemoveActivityFromUser1() {
        assertThrows(EntityDoesNotExistException.class, () -> {
            this.referenceManager.removeActivityFromUser("", this.referenceActivity.getCode());
        });
    }

    @Test
    void testRemoveActivityFromUser2() {
        this.referenceManager.removeActivityFromUser(this.referenceUser3.getEmail(),
                                                     this.referenceActivity.getCode());

        assertEquals(0, this.referenceUser1.getListActivities().size());
        assertEquals(0, this.referenceUser2.getListActivities().size());
        assertEquals(0, this.referenceUser3.getListActivities().size());
    }

    @Test
    void testRemoveActivityFromUser3() {
        this.referenceManager.addActivityToUser(this.referenceUser2.getEmail(),
                                                this.referenceActivity);
        this.referenceManager.removeActivityFromUser(this.referenceUser2.getEmail(),
                                                     this.referenceActivity.getCode());

        assertEquals(0, this.referenceUser1.getListActivities().size());
        assertEquals(0, this.referenceUser2.getListActivities().size());
        assertEquals(0, this.referenceUser3.getListActivities().size());
    }

    @Test
    void testRemoveActivityFromUser4() {
        this.referenceManager.addActivityToUser(this.referenceUser1.getEmail(),
                                                this.referenceActivity);
        this.referenceManager.removeActivityFromUser(this.referenceUser1.getEmail(),
                                                     UUID.randomUUID());

        assertEquals(1, this.referenceUser1.getListActivities().size());
        assertEquals(0, this.referenceUser2.getListActivities().size());
        assertEquals(0, this.referenceUser3.getListActivities().size());
    }

    @Test
    void testAddActivitiesToUser1() {
        this.referenceManager.addActivitiesToUser(
            this.referenceUser3.getCode(),
            List.of(this.referenceActivity, this.referenceActivity));

        assertEquals(0, this.referenceUser1.getListActivities().size());
        assertEquals(0, this.referenceUser2.getListActivities().size());
        assertEquals(2, this.referenceUser3.getListActivities().size());
    }

    @Test
    void testAddActivitiesToUser2() {
        assertThrows(EntityDoesNotExistException.class, () -> {
            this.referenceManager.addActivitiesToUser(UUID.randomUUID(), List.of());
        });
    }

    @Test
    void testAddActivitiesToUser3() {
        this.referenceManager.addActivitiesToUser(this.referenceUser2.getCode(), List.of());

        assertEquals(0, this.referenceUser1.getListActivities().size());
        assertEquals(0, this.referenceUser2.getListActivities().size());
        assertEquals(0, this.referenceUser3.getListActivities().size());
    }

    @Test
    void testAddActivitiesToUser4() {
        this.referenceManager.addActivitiesToUser(
            this.referenceUser2.getCode(),
            List.of(this.referenceActivity, this.referenceActivity));
        this.referenceManager.addActivitiesToUser(
            this.referenceUser2.getCode(),
            List.of(this.referenceActivity, this.referenceActivity));

        assertEquals(0, this.referenceUser1.getListActivities().size());
        assertEquals(4, this.referenceUser2.getListActivities().size());
        assertEquals(0, this.referenceUser3.getListActivities().size());
    }

    @Test
    void testUpdateSystem() {
        assertTrue(this.referenceActivity.getCaloricWaste() == 0);

        this.referenceManager.addActivityToUser(this.referenceUser3.getEmail(),
                                                this.referenceActivity);
        this.referenceManager.updateSystem();

        Activity newActivity =
            this.referenceManager.getActivitiesFromUser(this.referenceUser3.getEmail()).get(0);

        assertEquals("PushUp", ((Professional) this.referenceUser3).getSpecialization());
        assertTrue(newActivity.getCaloricWaste() > 0);
    }
}
