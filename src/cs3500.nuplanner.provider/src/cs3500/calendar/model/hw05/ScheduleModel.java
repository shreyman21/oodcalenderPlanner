package cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05;

/*
 * Changes to be made after final submission (3/14/2024):
 * - change all parameters of type Event to T
 */

/**
 * Represents a user's schedule.
 *
 * @param <T> the type of event in the schedule
 */
public interface ScheduleModel<T extends EventModel> {

  /**
   * <b>Adds an event to the schedule.</b>
   *
   * <p>Note that the event time cannot overlap with another event's time.</p>
   *
   * @param event the event to add
   * @throws IllegalArgumentException if the event time overlaps with another event's time
   */
  void addEvent(EventModel event);

  /**
   * <b>Removes an event from the schedule.</b>
   *
   * <p>Note that the event must exist in the schedule.</p>
   *
   * @param event the event to remove.
   * @throws IllegalArgumentException if the event does not exist in the schedule.
   */
  void removeEvent(EventModel event);

  /**
   * <b>Checks if this schedule has the given event.</b>
   *
   * <p>Note that the given event must contain the exact same fields as another event in this
   * schedule for it to consider it contained.</p>
   * @param event event to check.
   * @return true if this schedule contains event, false otherwise.
   */
  boolean has(EventModel event);

  /**
   * <b>Gets String representation of all events.</b>
   *
   * <p>This method returns a String representation of all the events in this schedule.</p>
   *
   * @return a String representation of all events
   */
  String toString();

  /**
   * <b>Checks if two schedules are equal.</b>
   *
   * <p>This method returns true if the two schedules are equal, false otherwise.</p>
   *
   * @param that the other Object to compare to
   * @return true if the schedules are equal, false otherwise
   */
  boolean equals(Object that);

  /**
   * <b>Gets the hash code of the schedule.</b>
   *
   * <p>This method returns the hash code of the schedule.</p>
   *
   * @return the hash code of the schedule
   */
  int hashCode();

  /**
   * <b>Checks if the schedule is available for an event.</b>
   *
   * <p>This method returns true if the schedule is available for the event, false otherwise.</p>
   *
   * @param event the event to check
   * @return true if the schedule is available for the event, false otherwise
   */
  boolean isAvailable(EventModel event);

  /**
   * <b>Returns all the events in this schedule.</b>
   *
   * <p></p>
   * @return an array of all the events in this schedule
   */
  T[] getEvents();

  /**
   * <b>Gets the XML format of the schedule.</b>
   *
   * <p>This method returns the XML format of the schedule.</p>
   *
   * @return the XML format of the schedule
   */
  String xmlFormat();
}
