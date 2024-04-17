package cs3500.calendar.model.hw05;

//TODO:
// Swap Event params for EventModel<T> in methods

/**
 * The operations and observations necessary for a Calendar model.
 *
 * @param <T> the type representing the schedule in the calendar.
 */
public interface CalendarModel<T extends ScheduleModel> extends ReadonlyCalendarModel<T> {

  /**
   * <b>Creates a new user and empty schedule to add users to.</b>
   *
   * <p>Note that the uid must be unique; two users cannot have the same uid.</p>
   *
   * @param uid the user's id
   * @throws IllegalArgumentException if the uid is not unique
   */
  void addUser(String uid) throws IllegalArgumentException;

  /**
   * <b>Adds an event to a user's schedule.</b>
   *
   * <p>Note that the uid must exist in the calendar system.</p>
   * @param event the event to add
   * @throws IllegalArgumentException if the uid does not exist in the calendar system
   */
  void addEvent(EventModel event) throws IllegalArgumentException;

  /**
   * <b>Removes an event from the user's schedule.</b>
   *
   * <p>Note that removing the Event removes it from all invitees' schedules as well.</p>
   * @param uid uid of the user removing the Event
   * @param event event to be removed
   * @throws IllegalArgumentException if the event is not found
   */
  void removeEvent(String uid, EventModel event);

  /**
   * <b>Replaces an Event with a new Event.</b>
   *
   * <p>Replaces an event in a user's schedule, as well as all affected users.</p>
   * @param uid uid of the user modifying the Event.
   * @param prevEvent previous Event to be replaced
   * @param newEvent new Event to replace
   */
  void modifyEvent(String uid, EventModel prevEvent, EventModel newEvent);

  /**
   * <b>Gets the string representation of a schedule of a specific user.</b>
   *
   * @param uid the user's id
   * @return the user's schedule
   */
  String userScheduleToString(String uid);
}
