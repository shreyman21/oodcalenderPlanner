package cs3500.calendar.model.hw05;

/**
 * Represents a Read Only version of the Calendar Model, only holds methods that read from
 * this class, no method will ever change a property of this class.
 * @param <T> Type of the Schedule used, must extend ScheduleModel.
 */
public interface ReadonlyCalendarModel<T extends ScheduleModel> {

  /**
   * <b>Checks if the given uid is available for a given time.</b>
   *
   * <p>Checks if the given uid is available for a given time.</p>
   * @param uid uid of the user.
   * @param day day of the week.
   * @param time time of day.
   * @return true if user is available for given time, false if not.
   */
  boolean isUserAvailable(String uid, DayOfWeek day, String time);

  /**
   * <b>Gets the schedule for a given user.</b>
   *
   * <p></p>
   * @param uid the user's id
   * @return the given user's schedule
   * @throws IllegalArgumentException if the uid does not exist
   */
  ScheduleModel getSchedule(String uid) throws IllegalArgumentException;

  /**
   * <b>Gets an array of all users (uid).</b>
   *
   * @return an array of all users.
   */
  String[] getUsers();
}
