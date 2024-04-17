package cs3500.calendar.model.hw05;

/**
 * Represents the days of the week.
 */
public enum DayOfWeek {
  SUNDAY(0),
  MONDAY(1),
  TUESDAY(2),
  WEDNESDAY(3),
  THURSDAY(4),
  FRIDAY(5),
  SATURDAY(6);

  private final int value;
  private DayOfWeek(int value) {
    this.value = value;
  }

  /**
   * Returns the integer value of the day of the week.
   * @return the integer value of the day of the week
   */
  public int getValue() {
    return value;
  }

  /**
   * Returns the day of the week from the given integer value.
   * @param value the integer value of the day of the week
   * @return the day of the week from the given integer value
   */
  public static DayOfWeek fromString(String value) {
    switch (value) {
      case "Sunday":
        return SUNDAY;
      case "Monday":
        return MONDAY;
      case "Tuesday":
        return TUESDAY;
      case "Wednesday":
        return WEDNESDAY;
      case "Thursday":
        return THURSDAY;
      case "Friday":
        return FRIDAY;
      case "Saturday":
        return SATURDAY;
      default:
        throw new IllegalArgumentException("Invalid day of the week");
    }
  }

  /**
   * Static method that creates a DayOfWeek object from a given number.
   * @param num value of the day
   * @throws IllegalArgumentException if num is out of bounds
   */
  public static DayOfWeek fromInt(int num) throws IllegalArgumentException {
    switch (num) {
      case 0:
        return SUNDAY;
      case 1:
        return MONDAY;
      case 2:
        return TUESDAY;
      case 3:
        return WEDNESDAY;
      case 4:
        return THURSDAY;
      case 5:
        return FRIDAY;
      case 6:
        return SATURDAY;
      default:
        throw new IllegalArgumentException("Integer out of bounds, must be between 0 and 6" +
                "inclusive.");
    }
  }

  /**
   * Returns the string representation of the day of the week.
   * @return the string representation of the day of the week
   */
  @Override
  public String toString() {
    switch (this) {
      case SUNDAY:
        return "Sunday";
      case MONDAY:
        return "Monday";
      case TUESDAY:
        return "Tuesday";
      case WEDNESDAY:
        return "Wednesday";
      case THURSDAY:
        return "Thursday";
      case FRIDAY:
        return "Friday";
      case SATURDAY:
        return "Saturday";
      default:
        throw new IllegalArgumentException("Invalid day of the week");
    }
  }

}
