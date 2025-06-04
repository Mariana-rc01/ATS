package MakeItFit.users.types;

import java.util.List;

import MakeItFit.users.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AmateurTest {
    Amateur referenceUser;

    @BeforeEach
    public void initializeReferenceUser() {
        this.referenceUser = new Amateur("João",
                                         30,
                                         Gender.Male,
                                         80.5f,
                                         180,
                                         70,
                                         0,
                                         "Casa",
                                         "+351 123",
                                         "abc@def.com");
    }

    @Test
    public void testConstructorSimpleFields() {
        assertEquals("João", this.referenceUser.getName());
        assertEquals(30, this.referenceUser.getAge());
        assertEquals(Gender.Male, this.referenceUser.getGender());
        assertEquals(80.5f, this.referenceUser.getWeight());
        assertEquals(180, this.referenceUser.getHeight());
        assertEquals(70, this.referenceUser.getBpm());
        assertEquals(0, this.referenceUser.getLevel());
        assertEquals("Casa", this.referenceUser.getAddress());
        assertEquals("+351 123", this.referenceUser.getPhone());
        assertEquals("abc@def.com", this.referenceUser.getEmail());
    }

    @Test
    public void testConstructorActivities() {
        assertEquals(List.of(), this.referenceUser.getListActivities());
    }

    @Test
    public void testConstructorInvalidFields1() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Amateur("João",
                        -1,
                        Gender.Male,
                        65.0f,
                        180,
                        70,
                        0,
                        "Casa",
                        "+351 123",
                        "abc@def.com");
        });
    }

    @Test
    public void testConstructorInvalidFields2() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Amateur("João",
                        30,
                        Gender.Male,
                        -0.1f,
                        180,
                        70,
                        0,
                        "Casa",
                        "+351 123",
                        "abc@def.com");
        });
    }

    @Test
    public void testConstructorInvalidFields3() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Amateur("João",
                        30,
                        Gender.Male,
                        1.0f,
                        -1,
                        70,
                        0,
                        "Casa",
                        "+351 123",
                        "abc@def.com");
        });
    }

    @Test
    public void testConstructorInvalidFields4() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Amateur("João", 30, Gender.Male, 1.0f, 1, -1, 0, "Casa", "+351 123", "abc@def.com");
        });
    }

    @Test
    public void testConstructorInvalidFields5() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Amateur("João",
                        30,
                        Gender.Male,
                        1.0f,
                        1,
                        70,
                        -1,
                        "Casa",
                        "+351 123",
                        "abc@def.com");
        });
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
        Amateur user1 = new Amateur("João",
                                    30,
                                    Gender.Male,
                                    80.5f,
                                    180,
                                    70,
                                    0,
                                    "Casa",
                                    "+351 123",
                                    "abc@def.com");

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
                                              0);

        assertNotEquals(user1, user2);
    }

    @Test
    public void testToString() {
        // clang-format off
        String format = """
                        == (User details) ==
                        Code: %s
                        Name: João
                        Age: 30
                        Gender: Male
                        Weight: 80.50 kg
                        Height: 180 cm
                        Bpm: 70
                        Level: 0
                        Address: Casa
                        Phone: +351 123
                        Email: abc@def.com
                        Activities: []
                        ====================
                        """;

        assertEquals(String.format(format, this.referenceUser.getCode()),
                     this.referenceUser.toString());
        // clang-format on
    }
}
