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
    private Event event;
    private Date date;

    @BeforeEach
    public void runBefore() {
        event = new Event("Sensor open at door");
        date = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", event.getDescription());
        assertTrue(!date.before(event.getDate()));
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + "\n" + "Sensor open at door", event.toString());
    }

    @Test
    public void testEqualsNull() {
        assertFalse(event.equals(null));
    }

    @Test
    public void testEqualsDifferentClass() {
        assertFalse(event.equals("Sensor open at door"));
    }

    @Test
    public void testEqualsSameValues() {
        Event other = new Event("Sensor open at door");
        if (event.getDate().equals(other.getDate())) {
            assertTrue(event.equals(other));
            assertEquals(event.hashCode(), other.hashCode());
        }
    }

    @Test
    public void testEqualsDifferentDescription() {
        Event other = new Event("Sensor closed at door");
        assertFalse(event.equals(other));
    }

    @Test
    public void testEqualsItself() {
        assertTrue(event.equals(event));
        assertEquals(event.hashCode(), event.hashCode());
    }
}