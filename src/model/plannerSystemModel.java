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

  /**
   * Upload an XML file representing a single user’s schedule.
   * @param xmlFilePath the path to the XML file
   * @param user the user to upload the schedule for
   * @return true if the schedule was uploaded successfully, false otherwise
   */
  boolean uploadSchedule(String xmlFilePath, User user);

  /**
   * Save each user’s schedule to an XML file.
   * @param xmlFilePath the path to the XML file
   * @param user the user to save the schedule for
   * @return true if the schedule was saved successfully, false otherwise
   */
  boolean saveSchedule(String xmlFilePath, User user);

  /**
   * Select one of the users to display their schedule.
   * @param user the user to select
   */
  void selectUser(User user);

  /**
   * Create an event for a user.
   * @param user the user to create the event for
   * @param event the event to create
   * @return true if the event was created successfully, false otherwise
   */
  boolean createEvent(User user, Event event);

  /**
   * Modify an event for a user.
   * @param user the user to modify the event for
   * @param originalEvent the original event to modify
   * @param updatedEvent the updated event
   * @return true if the event was modified successfully, false otherwise
   */
  boolean modifyEvent(User user, Event originalEvent, Event updatedEvent);

  /**
   * Remove an event for a user.
   * @param user the user to remove the event for
   * @param event the event to remove
   * @return true if the event was removed successfully, false otherwise
   */
  boolean removeEvent(User user, Event event);

  /**
   * Have the program automatically schedule an event on some users’
   * schedules at some time if possible.
   * @param user the user to schedule the event for
   * @param event the event to schedule
   * @return true if the event was scheduled successfully, false otherwise
   */
  boolean autoSchedule(User user, Event event);

  /**
   * See events occurring at a given time for the given user.
   * @param user the user to see the events for
   * @param time the time to see the events for
   * @return a list of events occurring at the given time for the given user
   */
  List<Event> seeEvents(User user, LocalDateTime time);

}
