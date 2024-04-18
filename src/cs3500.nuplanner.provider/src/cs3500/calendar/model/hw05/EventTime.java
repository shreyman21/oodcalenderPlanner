package cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05;

import java.util.Objects;

/**
 * Represents the time of an event. Contains the day of the week and the time of the event.
 */
public final class EventTime implements EventTimeModel {
  private final int dayOfWeek;
  private final int hour;
  private final int minute;


  private static final int DAYS = 0;
  private static final int HOURS = 1;
  private static final int MINUTES = 2;

  /**
   * <b>Constructor for EventTime.</b>
   *
   * <p>Creates a new EventTime object with the given day of the week and time.<br>
   * <br>
   * Note that the day must be between 0 and 13 inclusive. Time must be a 4 character long
   * string, with 24 hour time format (HHmm).</p>
   *
   * @param dayOfWeek the day of the week (0-13)
   * @param time the time of the event (24 hour time format, HHmm)
   * @throws IllegalArgumentException if the day of the week is invalid or if the time is
   *     invalid
   */
  public EventTime(int dayOfWeek, String time) {
    if (dayOfWeek < 0 || dayOfWeek > 13) {
      throw new IllegalArgumentException("Invalid day of the week");
    }
    if (time.length() != 4) {
      throw new IllegalArgumentException("Invalid time format");
    }
    this.dayOfWeek = dayOfWeek;
    try {
      int hour = Integer.parseInt(time.substring(0, 2));
      if (hour < 0 || hour > 23) {
        throw new IllegalArgumentException("Invalid hour");
      } else {
        this.hour = hour;
      }
      int minute = Integer.parseInt(time.substring(2, 4));
      if (minute < 0 || minute > 59) {
        throw new IllegalArgumentException("Invalid minute");
      } else {
        this.minute = minute;
      }
    } catch (StringIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Invalid time format");
    }
  }

  /**
   * Secondary constructor for making an EventTime class using integer representation of
   * days, hours, and min.
   * @param dayOfWeek day of week integer form
   * @param hour integer form of hour
   * @param minute integer form of minute
   * @throws IllegalArgumentException if dayOfWeek, hour, or min are out of bounds
   */
  public EventTime(int dayOfWeek, int hour, int minute) {
    if (dayOfWeek < 0 || dayOfWeek > 13) {
      throw new IllegalArgumentException("Invalid day of the week");
    }
    if (hour < 0 || hour > 23) {
      throw new IllegalArgumentException("Invalid hour");
    }
    if (minute < 0 || minute > 59) {
      throw new IllegalArgumentException("Invalid minute");
    }
    this.dayOfWeek = dayOfWeek;
    this.hour = hour;
    this.minute = minute;
  }


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
  @Override
  public int[] getIntegerRepresentation() {
    return new int[]{this.dayOfWeek, this.hour, this.minute};
  }

  /**
   * <b>Checks if this EventTime occurs after another EventTime.</b>
   *
   * @param etm the other EventTime to compare to
   * @return true if this EventTime is greater than the other EventTime, false otherwise
   */
  public boolean isAfter(EventTimeModel etm) {
    int[] that = etm.getIntegerRepresentation();
    if (this.dayOfWeek > that[DAYS]) {
      return true;
    } else if (this.dayOfWeek == that[DAYS]) {
      if (this.hour > that[HOURS]) {
        return true;
      } else if (this.hour == that[HOURS]) {
        return this.minute > that[MINUTES];
      }
    }
    return false;
  }

  /**
   * <b>Checks if this EventTime occurs before another EventTime.</b>
   *
   * @param that the other EventTime to compare to
   * @return true if this EventTime is less than the other EventTime, false otherwise
   */
  public boolean isBefore(EventTimeModel that) {
    return that.isAfter(this);
  }

  /**
   * <b>Checks if this EventTime is equal to another EventTime.</b>
   *
   * @param etm the other EventTime to compare to
   * @return true if this EventTime is equal to the other EventTime, false otherwise
   */
  public boolean isSame(EventTimeModel etm) {
    int[] that = etm.getIntegerRepresentation();
    return this.dayOfWeek == that[DAYS]
            && this.hour == that[HOURS]
            && this.minute == that[MINUTES];
  }

  /**
   * <b>Checks if this EventTime occurs after or at the same time as another EventTime.</b>
   *
   * @param that the other EventTime to compare to
   * @return true if this EventTime is greater than or equal to the other EventTime,
   *     false otherwise
   */
  public boolean isAfterOrSame(EventTimeModel that) {
    return this.isAfter(that) || this.isSame(that);
  }

  /**
   * <b>Checks if this EventTime occurs before or at the same time as another EventTime.</b>
   *
   * @param that the other EventTime to compare to
   * @return true if this EventTime is less than or equal to the other EventTime,
   */
  public boolean isBeforeOrSame(EventTimeModel that) {
    return this.isBefore(that) || this.isSame(that);
  }

  @Override
  public EventTimeModel plus(int minutes) {
    int newMinutes = this.minute + minutes;
    int newHours = this.hour + newMinutes / 60;
    newMinutes = newMinutes % 60;
    int newDayOfWeek = this.dayOfWeek + newHours / 24;
    newHours = newHours % 24;
    return new EventTime(newDayOfWeek, newHours, newMinutes);
  }

  @Override
  public DayOfWeek getDay() {
    return DayOfWeek.fromInt(this.dayOfWeek % 7);
  }

  @Override
  public String toString() {
    return this.getDayOfWeek().toString() +
            ": " +
            this.timeToString();
  }

  DayOfWeek getDayOfWeek() {
    switch (this.dayOfWeek % 7) {
      case 0:
        return DayOfWeek.SUNDAY;
      case 1:
        return DayOfWeek.MONDAY;
      case 2:
        return DayOfWeek.TUESDAY;
      case 3:
        return DayOfWeek.WEDNESDAY;
      case 4:
        return DayOfWeek.THURSDAY;
      case 5:
        return DayOfWeek.FRIDAY;
      case 6:
        return DayOfWeek.SATURDAY;
      default:
        throw new IllegalArgumentException("Invalid day of the week");
    }
  }

  private String timeToString() {
    StringBuilder sb = new StringBuilder();
    if (this.hour < 10) {
      sb.append("0");
    }
    sb.append(this.hour);
    if (this.minute < 10) {
      sb.append("0");
    }
    sb.append(this.minute);
    return sb.toString();
  }

  @Override
  public boolean equals(Object that) {
    if (!(that instanceof EventTime)) {
      return false;
    }
    EventTime other = (EventTime) that;
    return this.hashCode() == other.hashCode();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.dayOfWeek, this.hour, this.minute);
  }
}
