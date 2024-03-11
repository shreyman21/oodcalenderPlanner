package model;

import java.time.LocalDateTime;
import java.util.List;

public interface plannerSystemModel {
  /*
   Upload an XML file representing a single user’s schedule.
   Save each user’s schedule to an XML file.
   Select one of the users to display their schedule.
   Create, modify, or remove an event on a user’s schedule, which may affect other user’s schedule.
   Have the program automatically schedule an event on some users’
   schedules at some time if possible.
   See events occurring at a given time for the given user.
   */

  // Upload a user's schedule from an XML file. The method might return a boolean to indicate success/failure.
  boolean uploadSchedule(String xmlFilePath, User user);

  // Save a user's schedule to an XML file. This could also return a boolean to indicate success or failure.
  boolean saveSchedule(String xmlFilePath, User user);

  // Select a user in the planner system. This could change to using the User object directly if that fits your design better.
  void selectUser(User user);

  // Create an event in the user's schedule. Parameters are adjusted to use the Event class and possibly return a success indicator.
  boolean createEvent(User user, Event event);

  // Modify an existing event for a user. This might involve providing the original event and the updated event.
  boolean modifyEvent(User user, Event originalEvent, Event updatedEvent);

  // Remove an event from the user's schedule. This uses the Event class directly for clarity.
  boolean removeEvent(User user, Event event);

  // Attempt to automatically schedule an event for a user, considering availability. The method may need more parameters or adjustments based on your auto-scheduling logic.
  boolean autoSchedule(User user, Event event);

  // See events occurring at a given time for a user. This method now returns a list of events.
  List<Event> seeEvents(User user, LocalDateTime time);

}
