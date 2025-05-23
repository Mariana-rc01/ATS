package MakeItFit.users.types;

import java.util.List;

import MakeItFit.users.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OccasionalTest {
    Occasional referenceUser;

    @BeforeEach
    public void initializeReferenceUser() {
        this.referenceUser = new Occasional("Maria",
                                            25,
                                            Gender.Female,
                                            60.2f,
                                            165,
                                            74,
                                            1,
                                            "Mi casa tu casa",
                                            "+351 456",
                                            "ghi@jkl.com",
                                            10);
    }

    @Test
    public void testConstructorSimpleFields() {
        assertEquals("Maria", this.referenceUser.getName());
        assertEquals(25, this.referenceUser.getAge());
        assertEquals(Gender.Female, this.referenceUser.getGender());
        assertEquals(60.2f, this.referenceUser.getWeight());
        assertEquals(165, this.referenceUser.getHeight());
        assertEquals(74, this.referenceUser.getBpm());
        assertEquals(1, this.referenceUser.getLevel());
        assertEquals("Mi casa tu casa", this.referenceUser.getAddress());
        assertEquals("+351 456", this.referenceUser.getPhone());
        assertEquals("ghi@jkl.com", this.referenceUser.getEmail());
        assertEquals(10, this.referenceUser.getFrequency());
    }

    @Test
    public void testConstructorInvalidFields() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Occasional("João",
                           -1,
                           Gender.Male,
                           65.0f,
                           180,
                           70,
                           0,
                           "Casa",
                           "+351 123",
                           "abc@def.com",
                           -1);
        });
    }

    @Test
    public void testConstructorActivities() {
        assertEquals(List.of(), this.referenceUser.getListActivities());
    }

    @Test
    public void testConstructorInvalidFrequency() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Occasional("João",
                           -1,
                           Gender.Male,
                           65.0f,
                           180,
                           70,
                           0,
                           "Casa",
                           "+351 123",
                           "abc@def.com",
                           -1);
        });
    }

    @Test
    public void testSetFrequency1() {
        this.referenceUser.setFrequency(4);
        assertEquals(4, this.referenceUser.getFrequency());
    }

    @Test
    public void testSetFrequency2() {
        this.referenceUser.setFrequency(0);
        assertEquals(0, this.referenceUser.getFrequency());
    }

    @Test
    public void testSetAge3() {
        assertThrows(IllegalArgumentException.class,
                     () -> { this.referenceUser.setFrequency(-1); });
    }

    @Test
    public void testCloneEquals() {
        assertEquals(this.referenceUser, this.referenceUser.clone());
    }

    @Test
    public void testCloneCode() {
        // equals() does not test for equal code
        assertEquals(this.referenceUser.getCode(), this.referenceUser.clone().getCode());
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
    public void testEquals3() {
        Occasional user1 = new Occasional("João",
                                          30,
                                          Gender.Male,
                                          80.5f,
                                          180,
                                          70,
                                          0,
                                          "Casa",
                                          "+351 123",
                                          "abc@def.com",
                                          5);

        Professional user2 = new Professional("João",
                                              30,
                                              Gender.Male,
                                              80.5f,
                                              180,
                                              70,
                                              0,
                                              "Casa",
                                              "+351 123",
                                              "abc@def.com",
                                              5);

        assertNotEquals(user1, user2);
    }

    @Test
    public void testEquals4() {
        Occasional copy = new Occasional(this.referenceUser);
        copy.setFrequency(1);
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testEquals5() {
        Occasional copy = new Occasional(this.referenceUser);
        copy.setName("Teresa");
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testToString() {
        // clang-format off
        String format = """
                        == (User details) ==
                        Code: %s
                        Name: Maria
                        Age: 25
                        Gender: Female
                        Weight: 60.20 kg
                        Height: 165 cm
                        Bpm: 74
                        Level: 1
                        Address: Mi casa tu casa
                        Phone: +351 456
                        Email: ghi@jkl.com
                        Activities: []
                        Frequency: 10
                        ====================
                        """;

        assertEquals(String.format(format, this.referenceUser.getCode()),
                     this.referenceUser.toString());
        // clang-format on
    }
}
