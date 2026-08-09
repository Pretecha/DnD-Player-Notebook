package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    @BeforeEach
    public void runBefore() {
        e = new Event("Sensor open at door");
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e.getDescription());
        assertTrue(!d.before(e.getDate()));
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
    }

    @Test
    public void testEqualsNull() {
        assertFalse(e.equals(null));
    }

    @Test
    public void testEqualsDifferentClass() {
        assertFalse(e.equals("Sensor open at door"));
    }

    @Test
    public void testEqualsSameValues() {
        Event other = new Event("Sensor open at door");
        if (e.getDate().equals(other.getDate())) {
            assertTrue(e.equals(other));
            assertEquals(e.hashCode(), other.hashCode());
        }
    }

    @Test
    public void testEqualsDifferentDescription() {
        Event other = new Event("Sensor closed at door");
        assertFalse(e.equals(other));
    }

    @Test
    public void testEqualsItself() {
        assertTrue(e.equals(e));
        assertEquals(e.hashCode(), e.hashCode());
    }
}