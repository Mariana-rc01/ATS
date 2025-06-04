package MakeItFit.users.types;
import MakeItFit.users.Gender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AmateurTest {

    @Test
    void testConstructorWithParameters() {
        Amateur amateur = new Amateur("John Doe",
                                      30,
                                      Gender.Male,
                                      75.5f,
                                      180,
                                      72,
                                      2,
                                      "123 Main St",
                                      "555-1234",
                                      "john@example.com");

        assertEquals("John Doe", amateur.getName());
        assertEquals(30, amateur.getAge());
        assertEquals(Gender.Male, amateur.getGender());
        assertEquals(75.5f, amateur.getWeight());
        assertEquals(180, amateur.getHeight());
        assertEquals(72, amateur.getBpm());
        assertEquals(2, amateur.getLevel());
        assertEquals("123 Main St", amateur.getAddress());
        assertEquals("555-1234", amateur.getPhone());
        assertEquals("john@example.com", amateur.getEmail());
    }

    @Test
    void testCopyConstructor() {
        Amateur original = new Amateur("Jane Doe",
                                       25,
                                       Gender.Female,
                                       60.0f,
                                       165,
                                       68,
                                       1,
                                       "456 Oak St",
                                       "555-5678",
                                       "jane@example.com");

        Amateur copy = new Amateur(original);

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
    }

    @Test
    void testClone() {
        Amateur original = new Amateur("Alice",
                                       28,
                                       Gender.Female,
                                       58.5f,
                                       170,
                                       65,
                                       3,
                                       "789 Pine St",
                                       "555-9012",
                                       "alice@example.com");

        Amateur clone = original.clone();

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
    }

    @Test
    void testToString() {
        Amateur amateur = new Amateur("Bob",
                                      35,
                                      Gender.Male,
                                      80.0f,
                                      185,
                                      75,
                                      2,
                                      "101 Maple St",
                                      "555-3456",
                                      "bob@example.com");

        String str = amateur.toString();

        assertTrue(str.contains("Bob"));
        assertTrue(str.contains("35"));
        assertTrue(str.contains("Male"));
        assertTrue(str.contains("185"));
        assertTrue(str.contains("75"));
        assertTrue(str.contains("2"));
        assertTrue(str.contains("101 Maple St"));
        assertTrue(str.contains("555-3456"));
        assertTrue(str.contains("bob@example.com"));
        assertTrue(str.contains("===================="));
    }
}
