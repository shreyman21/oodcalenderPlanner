package model;

import java.time.LocalDateTime;
import java.util.List;

public interface ReadOnlyModel {
  /**
   * Get a user from the system.
   *
   * @param userId the id of the user to get
   * @return the user with the given id
   */
  User getUser(String userId);

  /**
   * See events occurring at a given time for the given user.
   *
   * @param user the user to see the events for
   * @param time the time to see the events for
   * @return a list of events occurring at the given time for the given user
   * @throws IllegalArgumentException if the user is null or the time is null
   * @throws IllegalStateException    if there is an error seeing the events
   */
  List<Event> seeEvents(User user, LocalDateTime time);

  /**
   * Get all users in the system as a list.
   *
   * @return a list of all users in the system
   */
  List<User> getUsers();

  /**
   * Get all events in the system as a list.
   * @return a list of all events in the system
   */
  List<Event> getEvents();

  public User getUserByName(String name);
}
