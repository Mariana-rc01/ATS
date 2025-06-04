package MakeItFit.users.types;
import MakeItFit.users.Gender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OccasionalTest {

    @Test
    void testConstructorWithParameters() {
        Occasional occ = new Occasional("John Doe",
                                        30,
                                        Gender.Male,
                                        75.5f,
                                        180,
                                        72,
                                        2,
                                        "123 Main St",
                                        "555-1234",
                                        "john@example.com",
                                        2);

        assertEquals("John Doe", occ.getName());
        assertEquals(30, occ.getAge());
        assertEquals(Gender.Male, occ.getGender());
        assertEquals(75.5f, occ.getWeight());
        assertEquals(180, occ.getHeight());
        assertEquals(72, occ.getBpm());
        assertEquals(2, occ.getLevel());
        assertEquals("123 Main St", occ.getAddress());
        assertEquals("555-1234", occ.getPhone());
        assertEquals("john@example.com", occ.getEmail());
        assertEquals(2, occ.getFrequency());
    }

    @Test
    void testConstructorWithNegativeFrequency() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Occasional("John Doe",
                           30,
                           Gender.Male,
                           75.5f,
                           180,
                           72,
                           2,
                           "123 Main St",
                           "555-1234",
                           "john@example.com",
                           -1);
        });
    }

    @Test
    void testCopyConstructor() {
        Occasional original = new Occasional("Jane Doe",
                                             25,
                                             Gender.Female,
                                             60.0f,
                                             165,
                                             68,
                                             1,
                                             "456 Oak St",
                                             "555-5678",
                                             "jane@example.com",
                                             1);

        Occasional copy = new Occasional(original);

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
        assertEquals(original.getFrequency(), copy.getFrequency());
    }

    @Test
    void testFrequencyManagement() {
        Occasional occ = new Occasional("Alice",
                                        28,
                                        Gender.Female,
                                        58.5f,
                                        170,
                                        65,
                                        3,
                                        "789 Pine St",
                                        "555-9012",
                                        "alice@example.com",
                                        1);

        // Test valid frequency
        occ.setFrequency(3);
        assertEquals(3, occ.getFrequency());

        // Test invalid frequency
        assertThrows(IllegalArgumentException.class, () -> occ.setFrequency(-1));
    }

    @Test
    void testClone() {
        Occasional original = new Occasional("Bob",
                                             35,
                                             Gender.Male,
                                             80.0f,
                                             185,
                                             75,
                                             2,
                                             "101 Maple St",
                                             "555-3456",
                                             "bob@example.com",
                                             2);

        Occasional clone = original.clone();

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
        assertEquals(original.getFrequency(), clone.getFrequency());
    }

    @Test
    void testToString() {
        Occasional occ = new Occasional("Charlie",
                                        40,
                                        Gender.Male,
                                        85.0f,
                                        190,
                                        80,
                                        1,
                                        "202 Elm St",
                                        "555-7890",
                                        "charlie@example.com",
                                        1);

        String str = occ.toString();

        assertTrue(str.contains("Charlie"));
        assertTrue(str.contains("40"));
        assertTrue(str.contains("Male"));
        assertTrue(str.contains("190"));
        assertTrue(str.contains("80"));
        assertTrue(str.contains("1"));
        assertTrue(str.contains("202 Elm St"));
        assertTrue(str.contains("555-7890"));
        assertTrue(str.contains("charlie@example.com"));
        assertTrue(str.contains("Frequency: 1"));
        assertTrue(str.contains("===================="));
    }
}
