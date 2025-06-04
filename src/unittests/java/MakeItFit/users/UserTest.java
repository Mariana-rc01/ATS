package MakeItFit.users;

import java.util.List;
import java.util.UUID;

import MakeItFit.activities.Activity;
import MakeItFit.activities.implementation.PushUp;
import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    // A simple stub to test some unreachable code
    private class UserStub extends User {
        public UserStub() {
            super();
        }

        public UserStub(UserStub user) {
            super(user);
        }

        public User clone() {
            return new UserStub(this);
        }
    }

    UserStub referenceUser;
    PushUp   referenceActivity;

    @BeforeEach
    public void initializeReferenceUser() {
        this.referenceUser = new UserStub();
        this.referenceActivity =
            new PushUp(this.referenceUser.getCode(), new MakeItFitDate(), 1, "", "", 1, 1);
    }

    @Test
    public void testConstructorSimpleFields() {
        assertEquals("", this.referenceUser.getName());
        assertEquals(0, this.referenceUser.getAge());
        assertEquals(Gender.Other, this.referenceUser.getGender());
        assertEquals(1.0f, this.referenceUser.getWeight());
        assertEquals(1, this.referenceUser.getHeight());
        assertEquals(1, this.referenceUser.getBpm());
        assertEquals(0, this.referenceUser.getLevel());
        assertEquals("", this.referenceUser.getAddress());
        assertEquals("", this.referenceUser.getPhone());
        assertEquals("", this.referenceUser.getEmail());
    }

    @Test
    public void testConstructorActivities() {
        assertEquals(List.of(), this.referenceUser.getListActivities());
    }

    @Test
    public void testCopyConstructorEquals() {
        assertEquals(this.referenceUser, new UserStub(this.referenceUser));
    }

    @Test
    public void testCopyConstructorCode() {
        // equals() does not test for equal code
        assertEquals(this.referenceUser.getCode(), (new UserStub(this.referenceUser)).getCode());
    }

    // TODO
    // @Test(expected = IllegalArgumentException.class)
    public void testGetIndex() {
        // assertTrue(Float.isNaN(this.referenceUser.getIndex()));
    }

    @Test
    public void testSetAge1() {
        this.referenceUser.setAge(20);
        assertEquals(20, this.referenceUser.getAge());
    }

    @Test
    public void testSetAge2() {
        this.referenceUser.setAge(0);
        assertEquals(0, this.referenceUser.getAge());
    }

    @Test
    public void testSetAge3() {
        assertThrows(IllegalArgumentException.class, () -> { this.referenceUser.setAge(-1); });
    }

    @Test
    public void testSetWeight1() {
        this.referenceUser.setWeight(50.0f);
        assertEquals(50.0f, this.referenceUser.getWeight());
    }

    @Test
    public void testSetWeight2() {
        this.referenceUser.setWeight(0.0f);
        assertEquals(0.0f, this.referenceUser.getWeight());
    }

    @Test
    public void testSetWeight3() {
        assertThrows(IllegalArgumentException.class,
                     () -> { this.referenceUser.setWeight(-1.0f); });
    }

    @Test
    public void testSetHeight1() {
        this.referenceUser.setHeight(160);
        assertEquals(160, this.referenceUser.getHeight());
    }

    @Test
    public void testSetHeight2() {
        this.referenceUser.setHeight(0);
        assertEquals(0, this.referenceUser.getHeight());
    }

    @Test
    public void testSetHeight3() {
        assertThrows(IllegalArgumentException.class, () -> { this.referenceUser.setHeight(-1); });
    }

    @Test
    public void testSetBpm1() {
        this.referenceUser.setBpm(65);
        assertEquals(65, this.referenceUser.getBpm());
    }

    @Test
    public void testSetBpm2() {
        this.referenceUser.setBpm(0);
        assertEquals(0, this.referenceUser.getBpm());
    }

    @Test
    public void testSetBpm3() {
        assertThrows(IllegalArgumentException.class, () -> { this.referenceUser.setBpm(-1); });
    }

    @Test
    public void testGetActivitiesComposition1() {
        List<Activity> list1 = this.referenceUser.getListActivities();
        List<Activity> list2 = this.referenceUser.getListActivities();

        assertNotSame(list1, list2);
        assertEquals(list1, list2);
    }

    @Test
    public void testGetActivitiesComposition2() {
        this.referenceUser.addActivity(this.referenceActivity);

        List<Activity> list1 = this.referenceUser.getListActivities();
        List<Activity> list2 = this.referenceUser.getListActivities();

        assertNotSame(list1.get(0), list2.get(0));
        assertEquals(list1.get(0), list2.get(0));
    }

    @Test
    public void testAddActivity1() {
        this.referenceUser.addActivity(this.referenceActivity);

        List<Activity> activities = this.referenceUser.getListActivities();

        assertEquals(1, activities.size());
        assertEquals(activities.get(0).getCode(), this.referenceActivity.getCode());
    }

    @Test
    public void testAddActivity2() {
        this.referenceUser.addActivity(this.referenceActivity);
        this.referenceActivity.setDesignation("changed");

        List<Activity> activities = this.referenceUser.getListActivities();
        assertNotEquals(activities.get(0).getDesignation(),
                        this.referenceActivity.getDesignation());
    }

    @Test
    public void testAddActivities1() {
        this.referenceUser.addActivity(this.referenceActivity);

        List<Activity> activities = this.referenceUser.getListActivities();

        assertEquals(1, activities.size());
        assertEquals(activities.get(0).getCode(), this.referenceActivity.getCode());
    }

    @Test
    public void testAddActivities2() {
        this.referenceUser.addActivities(List.of(this.referenceActivity));
        this.referenceActivity.setDesignation("changed");

        List<Activity> activities = this.referenceUser.getListActivities();
        assertNotEquals(activities.get(0).getDesignation(),
                        this.referenceActivity.getDesignation());
    }

    @Test
    public void testRemoveActivity1() {
        assertDoesNotThrow(() -> { this.referenceUser.removeActivity(UUID.randomUUID()); });
    }

    @Test
    public void testRemoveActivity2() {
        this.referenceUser.addActivity(this.referenceActivity);
        this.referenceUser.addActivity(this.referenceActivity);

        this.referenceUser.removeActivity(this.referenceActivity.getCode());
        assertEquals(List.of(), this.referenceUser.getListActivities());
    }

    @Test
    public void testRemoveActivity3() {
        PushUp newActivity =
            new PushUp(this.referenceUser.getCode(), new MakeItFitDate(), 1, "", "", 1, 1);

        this.referenceUser.addActivity(this.referenceActivity);
        this.referenceUser.addActivity(newActivity);

        this.referenceUser.removeActivity(this.referenceActivity.getCode());
        assertEquals(List.of(newActivity), this.referenceUser.getListActivities());
    }

    @Test
    public void updateActivities() {
        this.referenceUser.setWeight(80.0f);
        this.referenceUser.setHeight(180);
        this.referenceUser.addActivity(this.referenceActivity);
        this.referenceUser.addActivity(this.referenceActivity);

        this.referenceUser.updateActivities();

        List<Activity> activities = this.referenceUser.getListActivities();
        assertNotEquals(0, activities.get(0).getCaloricWaste());
        assertNotEquals(0, activities.get(1).getCaloricWaste());
    }

    @Test
    public void testCalculateIndex1() {
        float index = (new UserStub()).calculateIndex(70.0f, 180, 90);
        assertEquals(23.85f, index, 0.01f);
    }

    @Test
    public void testCalculateIndex2() {
        float index = (new UserStub()).calculateIndex(60.0f, 150, 100);
        assertEquals(29.16f, index, 0.01f);
    }

    @Test
    public void testCalculateIndex3() {
        assertThrows(IllegalArgumentException.class,
                     () -> { (new UserStub()).calculateIndex(-0.1f, 150, 60); });
    }

    @Test
    public void testCalculateIndex4() {
        assertThrows(IllegalArgumentException.class,
                     () -> { (new UserStub()).calculateIndex(0.0f, 150, 60); });
    }

    @Test
    public void testCalculateIndex5() {
        float index = (new UserStub()).calculateIndex(0.1f, 150, 100);
        assertEquals(2.54, index, 0.01f);
    }

    @Test
    public void testCalculateIndex6() {
        assertThrows(IllegalArgumentException.class,
                     () -> { (new UserStub()).calculateIndex(100.0f, 0, 60); });
    }

    @Test
    public void testCalculateIndex7() {
        assertThrows(IllegalArgumentException.class,
                     () -> { (new UserStub()).calculateIndex(100.0f, -1, 60); });
    }

    @Test
    public void testCalculateIndex8() {
        float index = (new UserStub()).calculateIndex(60.0f, 1, 100);
        assertEquals(600002.5f, index, 0.01f);
    }

    @Test
    public void testCalculateIndex9() {
        assertThrows(IllegalArgumentException.class,
                     () -> { (new UserStub()).calculateIndex(100.0f, 150, 0); });
    }

    @Test
    public void testCalculateIndex10() {
        assertThrows(IllegalArgumentException.class,
                     () -> { (new UserStub()).calculateIndex(100.0f, 150, -1); });
    }

    @Test
    public void testCalculateIndex11() {
        float index = (new UserStub()).calculateIndex(60.0f, 150, 1);
        assertEquals(26.69f, index, 0.01f);
    }

    @Test
    public void testEquals1() {
        assertEquals(this.referenceUser, this.referenceUser);
    }

    @Test
    public void testEquals2() {
        assertNotEquals(this.referenceUser, null);
    }

    @Test
    public void testEquals4() {
        User copy = new UserStub(this.referenceUser);
        copy.setName("a");
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testEquals5() {
        User copy = new UserStub(this.referenceUser);
        copy.setAge(1);
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testEquals6() {
        User copy = new UserStub(this.referenceUser);
        copy.setGender(Gender.Female);
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testEquals7() {
        User copy = new UserStub(this.referenceUser);
        copy.setWeight(0.1f);
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testEquals8() {
        User copy = new UserStub(this.referenceUser);
        copy.setHeight(2);
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testEquals9() {
        User copy = new UserStub(this.referenceUser);
        copy.setBpm(2);
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testEquals10() {
        User copy = new UserStub(this.referenceUser);
        copy.setLevel(10);
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testEquals11() {
        User copy = new UserStub(this.referenceUser);
        copy.setAddress("Casa");
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testEquals12() {
        User copy = new UserStub(this.referenceUser);
        copy.setPhone("112");
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testEquals13() {
        User copy = new UserStub(this.referenceUser);
        copy.setEmail("abc@def.com");
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testEquals14() {
        User copy = new UserStub(this.referenceUser);
        copy.addActivity(this.referenceActivity);
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testCompareTo1() {
        this.referenceUser.setName("a");

        User copy = new UserStub(this.referenceUser);
        assertEquals(0, this.referenceUser.compareTo(copy));
    }

    @Test
    public void testCompareTo2() {
        this.referenceUser.setName("a");

        User copy = new UserStub(this.referenceUser);
        copy.setName("b");
        assertTrue(this.referenceUser.compareTo(copy) < 0);
    }

    @Test
    public void testCompareTo3() {
        this.referenceUser.setName("a");

        User copy = new UserStub(this.referenceUser);
        copy.setName("A");
        assertTrue(this.referenceUser.compareTo(copy) > 0);
    }

    @Test
    public void testCompareTo4() {
        User copy = new UserStub(this.referenceUser);
        this.referenceUser.setAge(10);
        assertTrue(this.referenceUser.compareTo(copy) > 0);
    }

    @Test
    public void testCompareTo5() {
        User copy = new UserStub(this.referenceUser);
        copy.setAge(1);
        assertTrue(this.referenceUser.compareTo(copy) < 0);
    }
}
