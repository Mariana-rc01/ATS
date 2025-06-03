package MakeItFit.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyTupleTest {

    @Test
    void testTupleCreationAndGetters() {
        MyTuple<String, Integer> tuple = new MyTuple<>("Test", 42);
        assertEquals("Test", tuple.getItem1());
        assertEquals(42, tuple.getItem2());
    }

    @Test
    void testToString() {
        MyTuple<String, Double> tuple = new MyTuple<>("Price", 19.99);
        assertEquals("(Price, 19.99)", tuple.toString());
    }

    @Test
    void testEquals() {
        MyTuple<Integer, String> tuple1 = new MyTuple<>(1, "One");
        MyTuple<Integer, String> tuple2 = new MyTuple<>(1, "One");
        MyTuple<Integer, String> tuple3 = new MyTuple<>(2, "Two");

        assertEquals(tuple1, tuple2);
        assertNotEquals(tuple1, tuple3);
        assertNotEquals(tuple1, null);
        assertNotEquals(tuple1, "Not a tuple");
    }

    @Test
    void testCompareTo() {
        MyTuple<String, Integer> tuple1 = new MyTuple<>("A", 1);
        MyTuple<String, Integer> tuple2 = new MyTuple<>("A", 2);
        MyTuple<String, Integer> tuple3 = new MyTuple<>("B", 1);

        assertTrue(tuple1.compareTo(tuple2) < 0);
        assertTrue(tuple2.compareTo(tuple1) > 0);
        assertTrue(tuple1.compareTo(tuple3) < 0);
        assertEquals(0, tuple1.compareTo(new MyTuple<>("A", 1)));
    }

    @Test
    void testClone() {
        MyTuple<String, String> original = new MyTuple<>("Key", "Value");
        MyTuple<String, String> cloned   = original.clone();

        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    void testWithDifferentTypes() {
        MyTuple<Integer, Boolean> tuple = new MyTuple<>(123, true);
        assertEquals(123, tuple.getItem1());
        assertTrue(tuple.getItem2());
    }
}
