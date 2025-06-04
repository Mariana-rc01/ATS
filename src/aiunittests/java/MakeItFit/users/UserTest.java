package MakeItFit.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestUser extends User {

    public TestUser() {
        super();
    }

    public TestUser(String name,
                    int    age,
                    Gender gender,
                    float  weight,
                    int    height,
                    int    bpm,
                    int    level,
                    String address,
                    String phone,
                    String email) {
        super(name, age, gender, weight, height, bpm, level, address, phone, email);
    }

    public TestUser(User u) {
        super(u);
    }

    @Override
    public User clone() {
        return new TestUser(this);
    }
}

public class UserTest {

    @Test
    void testConstructorWithParameters() {
        TestUser user = new TestUser("John Doe",
                                     30,
                                     Gender.Male,
                                     75.5f,
                                     180,
                                     72,
                                     2,
                                     "123 Main St",
                                     "555-1234",
                                     "john@example.com");

        assertEquals("John Doe", user.getName());
        assertEquals(30, user.getAge());
        assertEquals(Gender.Male, user.getGender());
        assertEquals(75.5f, user.getWeight());
        assertEquals(180, user.getHeight());
        assertEquals(72, user.getBpm());
        assertEquals(2, user.getLevel());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("555-1234", user.getPhone());
        assertEquals("john@example.com", user.getEmail());
        assertNotNull(user.getCode());
        assertTrue(user.getListActivities().isEmpty());
    }

    @Test
    void testDefaultConstructor() {
        TestUser user = new TestUser();

        assertEquals("", user.getName());
        assertEquals(0, user.getAge());
        assertEquals(Gender.Other, user.getGender());
        assertEquals(1f, user.getWeight());
        assertEquals(1, user.getHeight());
        assertEquals(1, user.getBpm());
        assertEquals(0, user.getLevel());
        assertEquals("", user.getAddress());
        assertEquals("", user.getPhone());
        assertEquals("", user.getEmail());
        assertNotNull(user.getCode());
        assertTrue(user.getListActivities().isEmpty());
    }

    @Test
    void testCopyConstructor() {
        TestUser original = new TestUser("Jane Doe",
                                         25,
                                         Gender.Female,
                                         60.0f,
                                         165,
                                         68,
                                         1,
                                         "456 Oak St",
                                         "555-5678",
                                         "jane@example.com");
        original.addActivity(new PushUp());

        TestUser copy = new TestUser(original);

        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getAge(), copy.getAge());
        assertEquals(original.getGender(), copy.getGender());
        assertEquals(original.getWeight(), copy.getWeight());
        assertEquals(original.getHeight(), copy.getHeight());
        assertEquals(original.getBpm(), copy.getBpm());
        assertEquals(original.getLevel(), copy.getLevel());
        assertEquals(original.getAddress(), copy.getAddress());
        assertEquals(original.getPhone(), copy.getPhone());
        assertEquals(original.getEmail(), copy.getEmail());
        assertEquals(original.getCode(), copy.getCode());
        assertEquals(original.getListActivities().size(), copy.getListActivities().size());
    }

    @Test
    void testSettersAndGetters() {
        TestUser user = new TestUser();

        user.setName("Alice");
        user.setAge(28);
        user.setGender(Gender.Female);
        user.setWeight(58.5f);
        user.setHeight(170);
        user.setBpm(65);
        user.setLevel(3);
        user.setAddress("789 Pine St");
        user.setPhone("555-9012");
        user.setEmail("alice@example.com");

        assertEquals("Alice", user.getName());
        assertEquals(28, user.getAge());
        assertEquals(Gender.Female, user.getGender());
        assertEquals(58.5f, user.getWeight());
        assertEquals(170, user.getHeight());
        assertEquals(65, user.getBpm());
        assertEquals(3, user.getLevel());
        assertEquals("789 Pine St", user.getAddress());
        assertEquals("555-9012", user.getPhone());
        assertEquals("alice@example.com", user.getEmail());
    }

    @Test
    void testActivityManagement() {
        TestUser user      = new TestUser();
        Activity activity1 = new PushUp();
        Activity activity2 = new PushUp();

        // Test adding single activity
        user.addActivity(activity1);
        assertEquals(1, user.getListActivities().size());

        // Test adding list of activities
        List<Activity> activities = new ArrayList<>();
        activities.add(activity2);
        user.addActivities(activities);
        assertEquals(2, user.getListActivities().size());

        // Test removing activity
        UUID activityId = activity1.getCode();
        user.removeActivity(activityId);
        assertEquals(1, user.getListActivities().size());
        assertNotEquals(activityId, user.getListActivities().get(0).getCode());
    }

    @Test
    void testEquals() {
        TestUser user1 = new TestUser("John Doe",
                                      30,
                                      Gender.Male,
                                      75.5f,
                                      180,
                                      72,
                                      2,
                                      "123 Main St",
                                      "555-1234",
                                      "john@example.com");

        TestUser user2 = new TestUser("John Doe",
                                      30,
                                      Gender.Male,
                                      75.5f,
                                      180,
                                      72,
                                      2,
                                      "123 Main St",
                                      "555-1234",
                                      "john@example.com");

        TestUser user3 = new TestUser("Jane Doe",
                                      25,
                                      Gender.Female,
                                      60.0f,
                                      165,
                                      68,
                                      1,
                                      "456 Oak St",
                                      "555-5678",
                                      "jane@example.com");

        // Reflexivity
        assertEquals(user1, user1);

        // Symmetry
        assertEquals(user1, user2);
        assertEquals(user2, user1);

        // Transitivity
        TestUser user4 = new TestUser(user1);
        assertEquals(user1, user2);
        assertEquals(user2, user4);
        assertEquals(user1, user4);

        // Inequality
        assertNotEquals(user1, user3);
        assertNotEquals(user1, null);
        assertNotEquals(user1, new Object());
    }

    @Test
    void testCompareTo() {
        TestUser user1 = new TestUser("Alice",
                                      25,
                                      Gender.Female,
                                      60.0f,
                                      165,
                                      68,
                                      1,
                                      "456 Oak St",
                                      "555-5678",
                                      "alice@example.com");

        TestUser user2 = new TestUser("Bob",
                                      30,
                                      Gender.Male,
                                      75.5f,
                                      180,
                                      72,
                                      2,
                                      "123 Main St",
                                      "555-1234",
                                      "bob@example.com");

        TestUser user3 = new TestUser("Alice",
                                      30,
                                      Gender.Female,
                                      65.0f,
                                      170,
                                      70,
                                      2,
                                      "789 Pine St",
                                      "555-9012",
                                      "alice2@example.com");

        // Name comparison
        assertTrue(user1.compareTo(user2) < 0);
        assertTrue(user2.compareTo(user1) > 0);

        // Same name, compare by age
        assertTrue(user1.compareTo(user3) < 0);
        assertTrue(user3.compareTo(user1) > 0);

        // Same name and age
        TestUser user4 = new TestUser(user1);
        assertEquals(0, user1.compareTo(user4));
    }

    @Test
    void testToString() {
        TestUser user = new TestUser("John Doe",
                                     30,
                                     Gender.Male,
                                     75.5f,
                                     180,
                                     72,
                                     2,
                                     "123 Main St",
                                     "555-1234",
                                     "john@example.com");

        String str = user.toString();

        assertTrue(str.contains("John Doe"));
        assertTrue(str.contains("30"));
        assertTrue(str.contains("Male"));
        assertTrue(str.contains("180"));
        assertTrue(str.contains("72"));
        assertTrue(str.contains("2"));
        assertTrue(str.contains("123 Main St"));
        assertTrue(str.contains("555-1234"));
        assertTrue(str.contains("john@example.com"));
    }

    @Test
    void testClone() {
        TestUser original = new TestUser("John Doe",
                                         30,
                                         Gender.Male,
                                         75.5f,
                                         180,
                                         72,
                                         2,
                                         "123 Main St",
                                         "555-1234",
                                         "john@example.com");
        original.addActivity(new PushUp());

        TestUser clone = (TestUser) original.clone();

        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getAge(), clone.getAge());
        assertEquals(original.getGender(), clone.getGender());
        assertEquals(original.getWeight(), clone.getWeight());
        assertEquals(original.getHeight(), clone.getHeight());
        assertEquals(original.getBpm(), clone.getBpm());
        assertEquals(original.getLevel(), clone.getLevel());
        assertEquals(original.getAddress(), clone.getAddress());
        assertEquals(original.getPhone(), clone.getPhone());
        assertEquals(original.getEmail(), clone.getEmail());
        assertEquals(original.getCode(), clone.getCode());
        assertEquals(original.getListActivities().size(), clone.getListActivities().size());
    }
}
