package cs3500.calendar.model.hw05;

/**
 * Represents the time of an event. Contains the day of the week and the time of the event.
 */
public interface EventTimeModel {

  /**
   * <b>Returns the integer array representation of the event time.</b>
   *
   * <p>Returns an integer array representation of the event time, allowing for easy
   * comparisons between two EventTime objects. The first element in the array is the
   * integer representation for the DayOfWeek (most significant). The second element is for
   * hours (less significant), and the 3rd and final element represents the minutes
   * (least significant).
   * <br>
   * <br>
   * Comparisons work much like 3 digit integer comparisons, where each digit is compared
   * from most significant to least. Much like how 300 is obviously greater than 200, if
   * the day of the week of one time is greater than the other, that EventTime is obviously
   * ahead of the other. If the days of week are equal, the next least significant digit is
   * checked, and so on and so forth, until a result is reached.</p>
   *
   * @return the integer array representation of the event time <br>[DayOfWeek, hours, minutes]
   */
  int[] getIntegerRepresentation();

  /**
   * <b>Checks if this EventTime occurs after another EventTime.</b>
   *
   * @param that the other EventTime to compare to
   * @return true if this EventTime is greater than the other EventTime, false otherwise
   */
  boolean isAfter(EventTimeModel that);

  /**
   * <b>Checks if this EventTime occurs before another EventTime.</b>
   *
   * @param that the other EventTime to compare to
   * @return true if this EventTime is less than the other EventTime, false otherwise
   */
  boolean isBefore(EventTimeModel that);

  /**
   * <b>Checks if this EventTime is equal to another EventTime.</b>
   *
   * @param that the other EventTime to compare to
   * @return true if this EventTime is equal to the other EventTime, false otherwise
   */
  boolean isSame(EventTimeModel that);

  /**
   * <b>Checks if this EventTime occurs after or at the same time as another EventTime.</b>
   *
   * @param that the other EventTime to compare to
   * @return true if this EventTime is greater than or equal to the other EventTime,
   *     false otherwise
   */
  boolean isAfterOrSame(EventTimeModel that);

  /**
   * <b>Checks if this EventTime occurs before or at the same time as another EventTime.</b>
   *
   * @param that the other EventTime to compare to
   * @return true if this EventTime is less than or equal to the other EventTime,
   */
  boolean isBeforeOrSame(EventTimeModel that);

  /**
   * <b>Adds the number of minutes onto this EventTimeModel and returns the time sum.</b>
   *
   * <p>Returns a new EventTimeModel that is this EventTimeModel plus the given number of
   *     minutes.</p>
   * @param minutes duration to add
   * @return EventTimeModel of this time plus given duration
   */
  EventTimeModel plus(int minutes);

  /**
   * <b>Returns the day of week of the EventTime object.</b>
   *
   * @return DayOfWeek when this EventTime occurs
   */
  DayOfWeek getDay();
}