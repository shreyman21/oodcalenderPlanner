package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class plannerSystemTest {
  private plannerSystem system;

  @Before
  public void setUp() throws Exception {
    system = new plannerSystem();
  }

  @Test
  public void testUploadSchedule() {

  }

  @Test
  public void testSaveSchedule() {
  }

  @Test
  public void testSelectUser() {
  }

  @Test
  public void testAddUser() {
    // tests to see if you can add a user to the system
    User user = new User("1", "John Doe");
    system.addUser(user);
    assertNotNull("User should be added to the system", system.getUser("1"));
  }

  @Test
  public void testGetUser() {
    // tests that the gotten user is the right one
    User newUser = new User("123", "Alice");
    system.addUser(newUser);

    User retrievedUser = system.getUser("123");
    assertNotNull("User should not be null", retrievedUser);
    assertEquals("User ID should match", "123", retrievedUser.getId());
    assertEquals("User name should match", "Alice", retrievedUser.getName());
  }

  @Test
  public void testSelectAndModifyUserSchedule() {
    User user = new User("5", "Eve");
    system.addUser(user);
    Event event = new Event("Conference", LocalDateTime.now(),
            LocalDateTime.now().plusHours(2), "Room 404", false, new ArrayList<>());

    system.selectAndModifyUserSchedule(user.getId());
    system.createEvent(user, event);

    assertEquals("Selected user's schedule should contain 1 event",
            1, user.getSchedule().getEvents().size());
  }

  @Test
  public void testCreateEvent() {
    User user = new User("3", "Carol");
    system.addUser(user);
    Event event = new Event("Workshop", LocalDateTime.now(),
            LocalDateTime.now().plusHours(1), "Room 202", true, new ArrayList<>());

    assertTrue("Event should be added successfully", system.createEvent(user, event));
    assertEquals( 1, user.getSchedule().getEvents().size());
  }

  @Test
  public void testModifyEvent() {
    User user = new User("4", "Dave");
    system.addUser(user);
    Event originalEvent = new Event("Seminar", LocalDateTime.now(),
            LocalDateTime.now().plusHours(1), "Room 303", true, new ArrayList<>());
    system.createEvent(user, originalEvent);

    Event updatedEvent = new Event("Updated Seminar", originalEvent.getStartTime(),
            originalEvent.getEndTime().plusHours(1), "Room 303", true,
            new ArrayList<>());
    assertTrue("Event should be modified successfully", system.modifyEvent(user,
            originalEvent, updatedEvent));

    Event modifiedEvent = user.getSchedule().getEvents().get(0);
    assertEquals("Event name should be updated", "Updated Seminar",
            modifiedEvent.getName());
    assertEquals("Event end time should be extended",
            originalEvent.getEndTime().plusHours(1), modifiedEvent.getEndTime());
  }

  @Test
  public void TestRemoveEvent() {
    User user = new User("126", "Dave");
    system.addUser(user);

    Event event = new Event("Seminar", LocalDateTime.now(), LocalDateTime.now().plusHours(2),
            "Room 3", false, new ArrayList<>());
    system.createEvent(user, event);

    assertTrue("Event should be removed successfully", system.removeEvent(user, event));
  }

  @Test
  public void testAutoSchedule() {
    
  }

  @Test
  public void testSeeEvents() {
    User user = new User("1", "Alice");
    system.addUser(user);
    LocalDateTime eventTime = LocalDateTime.of(2024, 3,
            15, 10, 0);
    Event event = new Event("Meeting", eventTime, eventTime.plusHours(2), "Room 101",
            false, new ArrayList<>());
    system.createEvent(user, event);

    List<Event> events = system.seeEvents(user, eventTime.plusMinutes(30));
    assertEquals(1, events.size());
    assertEquals("Meeting", events.get(0).getName());
  }

  @Test
  public void testGetUsers() {
    // Checks if right amount of users are actually gotten
    system.addUser(new User("1", "Alice"));
    system.addUser(new User("2", "Bob"));

    List<User> users = system.getUsers();
    assertEquals("Should return 2 users", 2, users.size());
  }

  @Test
  public void testAddDuplicateUser() {
    // Ensure duplicate users throw an exception
    User user = new User("123", "Alice");
    system.addUser(user);

    try {
      system.addUser(user); // Attempt to add the same user again
      fail("Expected an IllegalArgumentException to be thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid user or user already exists.", e.getMessage());
    }
  }

  @Test
  public void testINvalidSelectUser() {
    try {
      system.selectUser(new User("invalid", "Invalid"));
      fail("Expected IllegalArgumentException to be thrown for an invalid user");
    } catch (IllegalArgumentException e) {
      assertEquals("User not found in the system.", e.getMessage());
    }
  }
}


// TODO: Add tests for savescheulde selectuser uploadschedule autoschedule
//  (not sure if i tested properly)
//  and more edge cases