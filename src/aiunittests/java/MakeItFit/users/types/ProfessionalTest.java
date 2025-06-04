package MakeItFit.users.types;

import MakeItFit.users.Gender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfessionalTest {

    @Test
    void testConstructorWithParameters() {
        Professional pro = new Professional("John Doe",
                                            30,
                                            Gender.Male,
                                            75.5f,
                                            180,
                                            72,
                                            2,
                                            "123 Main St",
                                            "555-1234",
                                            "john@example.com",
                                            5);

        assertEquals("John Doe", pro.getName());
        assertEquals(30, pro.getAge());
        assertEquals(Gender.Male, pro.getGender());
        assertEquals(75.5f, pro.getWeight());
        assertEquals(180, pro.getHeight());
        assertEquals(72, pro.getBpm());
        assertEquals(2, pro.getLevel());
        assertEquals("123 Main St", pro.getAddress());
        assertEquals("555-1234", pro.getPhone());
        assertEquals("john@example.com", pro.getEmail());
        assertEquals("No specialization", pro.getSpecialization());
        assertEquals(5, pro.getFrequency());
    }

    @Test
    void testCopyConstructor() {
        Professional original = new Professional("Jane Doe",
                                                 25,
                                                 Gender.Female,
                                                 60.0f,
                                                 165,
                                                 68,
                                                 1,
                                                 "456 Oak St",
                                                 "555-5678",
                                                 "jane@example.com",
                                                 3);
        original.setSpecialization("Running");

        Professional copy = new Professional(original);

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
        assertEquals(original.getSpecialization(), copy.getSpecialization());
        assertEquals(original.getFrequency(), copy.getFrequency());
    }

    @Test
    void testSpecializationManagement() {
        Professional pro = new Professional("Athlete",
                                            28,
                                            Gender.Male,
                                            70.0f,
                                            175,
                                            65,
                                            3,
                                            "Sports Ave",
                                            "555-9999",
                                            "athlete@example.com",
                                            5);

        // Test setting specialization
        pro.setSpecialization("Swimming");
        assertEquals("Swimming", pro.getSpecialization());

        // Test updateSpecialization with no activities (should remain unchanged)
        pro.updateSpecialization();
        assertEquals("Swimming", pro.getSpecialization());
    }

    @Test
    void testFrequencyManagement() {
        Professional pro = new Professional("Athlete",
                                            28,
                                            Gender.Male,
                                            70.0f,
                                            175,
                                            65,
                                            3,
                                            "Sports Ave",
                                            "555-9999",
                                            "athlete@example.com",
                                            5);

        // Test setting frequency
        pro.setFrequency(7);
        assertEquals(7, pro.getFrequency());
    }

    @Test
    void testClone() {
        Professional original = new Professional("Alice",
                                                 28,
                                                 Gender.Female,
                                                 58.5f,
                                                 170,
                                                 65,
                                                 3,
                                                 "789 Pine St",
                                                 "555-9012",
                                                 "alice@example.com",
                                                 4);
        original.setSpecialization("Cycling");

        Professional clone = original.clone();

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
        assertEquals(original.getSpecialization(), clone.getSpecialization());
        assertEquals(original.getFrequency(), clone.getFrequency());
    }

    @Test
    void testToString() {
        Professional pro = new Professional("Bob",
                                            35,
                                            Gender.Male,
                                            80.0f,
                                            185,
                                            75,
                                            2,
                                            "101 Maple St",
                                            "555-3456",
                                            "bob@example.com",
                                            6);
        pro.setSpecialization("Running");

        String str = pro.toString();

        assertTrue(str.contains("Bob"));
        assertTrue(str.contains("35"));
        assertTrue(str.contains("Male"));
        assertTrue(str.contains("185"));
        assertTrue(str.contains("75"));
        assertTrue(str.contains("2"));
        assertTrue(str.contains("101 Maple St"));
        assertTrue(str.contains("555-3456"));
        assertTrue(str.contains("bob@example.com"));
        assertTrue(str.contains("Specialization: Running"));
        assertTrue(str.contains("Frequency: 6"));
        assertTrue(str.contains("===================="));
    }
}
