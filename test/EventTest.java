

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import model.Event;

import static org.junit.Assert.assertThrows;

public class EventTest {

  private Event event;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private List<String> invitees;

  @Before
  public void setUp() {
    startTime = LocalDateTime.of(2024, 3, 10, 9, 0);
    endTime = LocalDateTime.of(2024, 3, 10, 11, 0);
    invitees = Arrays.asList("user1", "user2");
    event = new Event("Math Class", startTime, endTime, "Dodge Hall",
            true, invitees);
  }

  @Test
  public void testEventProperties() {
    // Checking the event properties are actually set right
    Assert.assertEquals("Math Class", event.getName());
    Assert.assertEquals(startTime, event.getStartTime());
    Assert.assertEquals(endTime, event.getEndTime());
    Assert.assertEquals("Dodge Hall", event.getLocation());
    Assert.assertTrue(event.isOnline());
    Assert.assertEquals(invitees, event.getInvitees());
  }

  @Test
  public void testSetters() {
    // Setting new variables to the parameters
    LocalDateTime newStartTime = LocalDateTime.of(2024, 3,
            10, 10, 0);
    LocalDateTime newEndTime = LocalDateTime.of(2024, 3,
            10, 12, 0);
    event.setStartTime(newStartTime);
    event.setEndTime(newEndTime);
    event.setName("English Class");
    event.setLocation("Richards Hall");
    event.setOnline(false);

    // Checking if the getters work on the new setters
    Assert.assertEquals("English Class", event.getName());
    Assert.assertEquals(newStartTime, event.getStartTime());
    Assert.assertEquals(newEndTime, event.getEndTime());
    Assert.assertEquals("Richards Hall", event.getLocation());
    Assert.assertFalse(event.isOnline());
  }

  @Test
  public void testCheckForNoConflict() {
    // Checking there is no conflict for custom science class
    Event scienceClass = new Event("Science Class", endTime, endTime.plusHours(2),
            "Churchill Hall", false, Arrays.asList("user3"));
    Assert.assertFalse(event.checkForConflict(scienceClass));
  }

  @Test
  public void testCheckForConflict() {
    // Checking there is a conflict for the cutsom science class
    LocalDateTime sciStart = LocalDateTime.of(2024, 3,
            10, 10, 30);
    LocalDateTime sciEnd = LocalDateTime.of(2024, 3,
            10, 12, 0);
    Event scienceClass = new Event("Other Event", sciStart, sciEnd,
            "Churchill Hall", false, Arrays.asList("user3"));
    Assert.assertTrue(event.checkForConflict(scienceClass));
  }

  @Test
  public void testEventCreationWithNull() {
    // Checks null parameters do not work
    // Null start time
    assertThrows(NullPointerException.class, () -> {
      new Event("Null Time Event", null, endTime,
              "Location", false, Arrays.asList("user1"));
    });

    // Null end time
    assertThrows(NullPointerException.class, () -> {
      new Event("Null Time Event", startTime, null,
              "Location", false, Arrays.asList("user1"));
    });

    // Null name
    assertThrows(NullPointerException.class, () -> {
      new Event(null, startTime, endTime,
              "Location", false, Arrays.asList("user1"));
    });

    // Null location
    assertThrows(NullPointerException.class, () -> {
      new Event("Null Time Event", startTime, endTime,
              null, false, Arrays.asList("user1"));
    });
  }

  @Test
  public void testEventWithSameStartAndEndTime() {
    // Testing same start and end time for shortest duration
    LocalDateTime sameTime = LocalDateTime.now();
    Event event = new Event("Zero Duration", sameTime, sameTime,
            "Anywhere", true, Arrays.asList("user1"));

    Assert.assertTrue(event.getStartTime().isEqual(event.getEndTime()));
  }

  @Test
  public void testLongestPossibleEventDuration() {
    // Testing the max duration
    LocalDateTime startTime = LocalDateTime.of(2024, 3,
            10, 16, 0);
    LocalDateTime endTime = startTime.plusDays(6).plusHours(23).plusMinutes(59);
    Event longestEvent = new Event("Long Event", startTime, endTime,
            "Anywhere", false, Arrays.asList("user1"));

    Assert.assertEquals("Start time should be as expected",
            startTime, longestEvent.getStartTime());
    Assert.assertEquals("End time should match the longest duration possible",
            endTime, longestEvent.getEndTime());
  }

  @Test
  public void testEventExceedsMaximumDuration() {
    // Checks that maximum duration is not allowed
    LocalDateTime startTime = LocalDateTime.of(2024, 3,
            10, 16, 0);
    LocalDateTime endTime = startTime.plusDays(7);

    assertThrows(IllegalArgumentException.class, () -> {
      new Event("Too Long Event", startTime, endTime,
              "Anywhere", false, Arrays.asList("user1"));
    });
  }
}