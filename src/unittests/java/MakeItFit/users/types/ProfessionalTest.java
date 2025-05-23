package MakeItFit.users.types;

import java.util.List;

import MakeItFit.activities.implementation.PushUp;
import MakeItFit.activities.implementation.Running;
import MakeItFit.users.Gender;
import MakeItFit.utils.MakeItFitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProfessionalTest {
    Professional referenceUser;
    PushUp       referenceActivity;

    @BeforeEach
    public void initializeReferenceUser() {
        this.referenceUser = new Professional("Alex",
                                              40,
                                              Gender.Other,
                                              65.0f,
                                              170,
                                              68,
                                              1,
                                              "Morada",
                                              "+351 789",
                                              "mno@pqr.com",
                                              7);
        this.referenceActivity =
            new PushUp(this.referenceUser.getCode(), new MakeItFitDate(), 1, "", "", 1, 1);
    }

    @Test
    public void testConstructorSimpleFields() {
        assertEquals("Alex", this.referenceUser.getName());
        assertEquals(40, this.referenceUser.getAge());
        assertEquals(Gender.Other, this.referenceUser.getGender());
        assertEquals(65.0f, this.referenceUser.getWeight());
        assertEquals(170, this.referenceUser.getHeight());
        assertEquals(68, this.referenceUser.getBpm());
        assertEquals(1, this.referenceUser.getLevel());
        assertEquals("Morada", this.referenceUser.getAddress());
        assertEquals("+351 789", this.referenceUser.getPhone());
        assertEquals("mno@pqr.com", this.referenceUser.getEmail());
        assertEquals(7, this.referenceUser.getFrequency());
        assertEquals("No specialization", this.referenceUser.getSpecialization());
    }

    @Test
    public void testConstructorActivities() {
        assertEquals(List.of(), this.referenceUser.getListActivities());
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
    public void testSetFrequency3() {
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
    public void testUpdateSpecialization1() {
        this.referenceUser.updateSpecialization();
        assertEquals("No specialization", this.referenceUser.getSpecialization());
    }

    @Test
    public void testUpdateSpecialization2() {
        this.referenceUser.addActivity(this.referenceActivity);
        this.referenceUser.updateSpecialization();
        assertEquals("PushUp", this.referenceUser.getSpecialization());
    }

    @Test
    public void testUpdateSpecialization3() {
        this.referenceUser.addActivity(this.referenceActivity);
        this.referenceUser.updateSpecialization();
        this.referenceUser.removeActivity(this.referenceActivity.getCode());
        this.referenceUser.updateSpecialization();

        assertEquals("No specialization", this.referenceUser.getSpecialization());
    }

    @Test
    public void testUpdateSpecialization4() {
        this.referenceUser.addActivity(this.referenceActivity);
        this.referenceUser.addActivity(this.referenceActivity);
        this.referenceUser.updateSpecialization();

        assertEquals("PushUp", this.referenceUser.getSpecialization());
    }

    @Test
    public void testUpdateSpecialization5() {
        Running newActivity =
            new Running(this.referenceUser.getCode(), new MakeItFitDate(), 1, "", "", 1, 1);

        this.referenceUser.addActivity(this.referenceActivity);
        this.referenceUser.addActivity(this.referenceActivity);
        this.referenceUser.addActivity(newActivity);
        this.referenceUser.updateSpecialization();

        assertEquals("PushUp", this.referenceUser.getSpecialization());
    }

    @Test
    public void testUpdateSpecialization6() {
        Running newActivity =
            new Running(this.referenceUser.getCode(), new MakeItFitDate(), 1, "", "", 1, 1);

        this.referenceUser.addActivity(this.referenceActivity);
        this.referenceUser.addActivity(newActivity);
        this.referenceUser.addActivity(this.referenceActivity);
        this.referenceUser.addActivity(newActivity);
        this.referenceUser.addActivity(newActivity);
        this.referenceUser.updateSpecialization();

        assertEquals("Running", this.referenceUser.getSpecialization());
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
        Professional user1 = new Professional("João",
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

        Amateur user2 = new Amateur("João",
                                    30,
                                    Gender.Male,
                                    80.5f,
                                    180,
                                    70,
                                    0,
                                    "Casa",
                                    "+351 123",
                                    "abc@def.com");

        assertNotEquals(user1, user2);
    }

    @Test
    public void testEquals4() {
        Professional copy = new Professional(this.referenceUser);
        copy.setFrequency(1);
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testEquals5() {
        Professional copy = new Professional(this.referenceUser);
        copy.setSpecialization("Sedentarismo");
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testEquals6() {
        Professional copy = new Professional(this.referenceUser);
        copy.setName("Alexa");
        assertNotEquals(this.referenceUser, copy);
    }

    @Test
    public void testToString1() {
        // clang-format off
        String format = """
                        == (User details) ==
                        Code: %s
                        Name: Alex
                        Age: 40
                        Gender: Other
                        Weight: 65.00 kg
                        Height: 170 cm
                        Bpm: 68
                        Level: 1
                        Address: Morada
                        Phone: +351 789
                        Email: mno@pqr.com
                        Activities: []
                        Specialization: No specialization
                        Frequency: 7
                        ====================
                        """;

        assertEquals(String.format(format, this.referenceUser.getCode()),
                     this.referenceUser.toString());
        // clang-format on
    }

    @Test
    public void testToString2() {
        this.referenceUser.addActivity(this.referenceActivity);

        // clang-format off
        String format = """
                        == (User details) ==
                        Code: %s
                        Name: Alex
                        Age: 40
                        Gender: Other
                        Weight: 65.00 kg
                        Height: 170 cm
                        Bpm: 68
                        Level: 1
                        Address: Morada
                        Phone: +351 789
                        Email: mno@pqr.com
                        Activities: [%s]
                        Specialization: No specialization
                        Frequency: 7
                        ====================
                        """;

        assertEquals(String.format(format, this.referenceUser.getCode(), this.referenceActivity),
                     this.referenceUser.toString());
        // clang-format on
    }
}
