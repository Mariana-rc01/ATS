package MakeItFit.utils;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MyTupleTest {
    @Test
    void testConstructor() {
        MyTuple<String, String> tuple = new MyTuple<>("hello", "world");
        assertEquals("hello", tuple.getItem1());
        assertEquals("world", tuple.getItem2());
    }

    @Test
    void tesConstructorNullParameters() {
        MyTuple<String, String> tuple = new MyTuple<>(null, null);
        assertNull(tuple.getItem1());
        assertNull(tuple.getItem2());
    }

    @Test
    void testToStringFormat() {
        MyTuple<String, Integer> tuple = new MyTuple<>("A", 24);
        assertEquals("(A, 24)", tuple.toString());
    }

    @Test
    void testEqualsItem1Differs() {
        MyTuple<String, Integer> tuple1 = new MyTuple<>("A", 24);
        MyTuple<String, Integer> tuple2 = new MyTuple<>("B", 24);
        assertFalse(tuple1.equals(tuple2));
    }

    @Test
    void testEqualsItem2Differs() {
        MyTuple<String, Integer> tuple1 = new MyTuple<>("A", 24);
        MyTuple<String, Integer> tuple2 = new MyTuple<>("A", 25);
        assertFalse(tuple1.equals(tuple2));
    }

    @Test
    void testEquals() {
        MyTuple<String, String> tuple = new MyTuple<>("A", "24");
        assertTrue(tuple.equals(tuple));
    }

    @Test
    void testEqualsNullParameter() {
        MyTuple<Integer, String> tuple = new MyTuple<>(24, "A");
        assertFalse(tuple.equals(null));
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    void testEqualsDifferentClass() {
        MyTuple<String, Integer> tuple = new MyTuple<>("A", 24);
        assertFalse(tuple.equals("(A, 24)"));
    }

    @Test
    void testCompareToItem1Different() {
        MyTuple<String, Integer> tuple1 = new MyTuple<>("A", 24);
        MyTuple<String, Integer> tuple2 = new MyTuple<>("B", 24);
        assertTrue(tuple1.compareTo(tuple2) < 0);
        assertTrue(tuple2.compareTo(tuple1) > 0);
    }

    @Test
    void testCompareToItem2Different() {
        MyTuple<String, Integer> tuple1 = new MyTuple<>("A", 24);
        MyTuple<String, Integer> tuple2 = new MyTuple<>("A", 25);
        assertTrue(tuple1.compareTo(tuple2) < 0);
        assertTrue(tuple2.compareTo(tuple1) > 0);
    }

    @Test
    void testCompareToEqualTuples() {
        MyTuple<String, Integer> tuple1 = new MyTuple<>("A", 24);
        MyTuple<String, Integer> tuple2 = new MyTuple<>("A", 24);
        assertTrue(tuple1.compareTo(tuple2) == 0);
    }

    @Test
    void testClone() {
        MyTuple<String, Integer> tuple      = new MyTuple<>("A", 24);
        MyTuple<String, Integer> tupleClone = tuple.clone();
        assertEquals(tuple, tupleClone);
        assertNotSame(tuple, tupleClone);
    }
}
