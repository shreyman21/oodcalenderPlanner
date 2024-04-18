package cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Represents an invariant event in a user's schedule.
 */
public final class Event implements EventModel {

  // Fields of an Event
  private final String title;
  private final String location;
  private final boolean isOnline;
  private final EventTimeModel startingEventTime;
  private final EventTimeModel endingEventTime;
  private final String hostUID;
  private final String[] invitedUIDs;


  /**
   * <b>Builder class for Event.</b>
   *
   * <p>Builds the Event, piece by piece.</p>
   */
  public static class EventBuilder {
    private String title;
    private String location;
    private boolean isOnline;
    private DayOfWeek startingDay;
    private String startingTime;
    private DayOfWeek endingDay;
    private String endingTime;
    private String[] invitedUIDs;

    /**
     * Builder class for Events.
     */
    public EventBuilder() {
      this.invitedUIDs = new String[0];
    }

    /**
     * <b>Builds a test Event.</b>
     *
     * <p>Builds a test Event with the following fields:</p>
     * <ul>
     *   <li>title: "Test"</li>
     *   <li>location: "Test"</li>
     *   <li>isOnline: false</li>
     *   <li>invitedUIDs: {"Dhruv", "Ethan", "Neirit"}</li>
     * </ul>
     * @return the EventBuilder
     */
    public EventBuilder testTime() {
      this.title = "Test";
      this.location = "Test";
      this.isOnline = false;
      this.invitedUIDs = new String[]{"Dhruv", "Ethan", "Neirit"};
      return this;
    }

    /**
     * Sets the title of the event.
     * @param title the title of the event
     * @return the EventBuilder
     */
    public EventBuilder title(String title) {
      this.title = title;
      return this;
    }

    /**
     * Sets the location of the event.
     * @param location the location of the event
     * @return the EventBuilder
     */
    public EventBuilder location(String location) {
      this.location = location;
      return this;
    }

    /**
     * Sets whether the event is online.
     * @param isOnline whether the event is online
     * @return the EventBuilder
     */
    public EventBuilder isOnline(boolean isOnline) {
      this.isOnline = isOnline;
      return this;
    }

    /**
     * Sets the starting day of the event.
     * @param startingDay the starting day of the event
     * @return the EventBuilder
     */
    public EventBuilder startingDay(DayOfWeek startingDay) {
      this.startingDay = startingDay;
      return this;
    }

    /**
     * Sets the starting time of the event.
     * @param startingTime the starting time of the event
     * @return the EventBuilder
     */
    public EventBuilder startingTime(String startingTime) {
      this.startingTime = startingTime;
      return this;
    }

    /**
     * Sets the ending day of the event.
     * @param endingDay the ending day of the event
     * @return the EventBuilder
     */
    public EventBuilder endingDay(DayOfWeek endingDay) {
      this.endingDay = endingDay;
      return this;
    }

    /**
     * Sets the ending time of the event.
     * @param endingTime the ending time of the event
     * @return the EventBuilder
     */
    public EventBuilder endingTime(String endingTime) {
      this.endingTime = endingTime;
      return this;
    }

    /**
     * Sets the UIDs of the users invited to the event.
     * @param invitedUIDs the UIDs of the users invited to the event
     * @return the EventBuilder
     */
    public EventBuilder invitedUIDs(String[] invitedUIDs) {
      this.invitedUIDs = invitedUIDs;
      return this;
    }

    /**
     * Builds the Event.
     * @return the Event
     */
    public Event build() {
      return new Event(this);
    }

  }

  /**
   * Constructs an Event.
   * @param eb the EventBuilder
   * @throws IllegalArgumentException if the event time overlaps with another event's time
   * @throws NullPointerException if any fields are null.
   */
  public Event(EventBuilder eb) {
    this.title = Objects.requireNonNull(eb.title, "Title cannot be null");
    this.isOnline = eb.isOnline;
    if (this.isOnline) {
      this.location = eb.location; // location can be null if the event is online
    } else {
      this.location = Objects.requireNonNull(eb.location, "Location cannot be null");
    }

    this.ensureCorrectTimeFormat(eb.startingTime, "Starting time");
    this.ensureCorrectTimeFormat(eb.endingTime, "Ending time");
    Objects.requireNonNull(eb.startingDay, "Starting day cannot be null");
    Objects.requireNonNull(eb.endingDay, "Ending day cannot be null");

    int startingDay = 0;
    int endingDay = 0;

    if (eb.startingDay.getValue() > eb.endingDay.getValue()) {
      startingDay = eb.startingDay.getValue();
      endingDay = eb.endingDay.getValue() + 7;
    } else {
      startingDay = eb.startingDay.getValue();
      endingDay = eb.endingDay.getValue();
    }

    this.startingEventTime = new EventTime(startingDay, eb.startingTime);
    this.endingEventTime = new EventTime(endingDay, eb.endingTime);

    if (eb.invitedUIDs.length == 0) {
      throw new IllegalArgumentException("There must be at least one invitee, the host.");
    } else {
      this.invitedUIDs = eb.invitedUIDs;
      this.hostUID = this.invitedUIDs[0];
    }
  }

  private void ensureCorrectTimeFormat(String time, String marker) {
    if (Objects.requireNonNull(time, marker + " cannot be null")
            .length() != 4) {
      throw new IllegalArgumentException(marker + " must be in the format HHmm");
    }
  }

  /**
   * <b>Checks if the event is overlapping with another event.</b>
   *
   * <p>Checks if the event is overlapping with another event.</p>
   * @param that the event to check for overlap
   */
  @Override
  public boolean isOverlapping(EventModel that) {
    if (this.startingEventTime.isSame(that.getStartingEventTime())) {
      return true;
    }
    if (this.startingEventTime.isAfter(that.getStartingEventTime())) {
      return this.startingEventTime.isBefore(that.getEndingEventTime());
    } else {
      return that.getStartingEventTime().isBefore(this.endingEventTime);
    }
  }

  @Override
  public boolean isOverlapping(DayOfWeek day, String time) {
    EventTimeModel that = new EventTime(day.getValue(), time);
    if (this.startingEventTime.isSame(that)) {
      return true;
    }
    if (this.startingEventTime.isAfter(that)) {
      return this.startingEventTime.isBefore(that);
    } else {
      return that.isBefore(this.endingEventTime);
    }
  }

  /**
   * <b>Checks if this event starts after another event.</b>
   *
   * <p>Checks if this event starts after another event.</p>
   * @param that the other event to compare to
   * @return true if this event starts after the other event, false otherwise
   */
  public boolean isAfter(EventModel that) {
    return this.startingEventTime.isAfter(that.getStartingEventTime());
  }

  /**
   * <b>Checks if this event starts before another event.</b>
   *
   * <p>Checks if this event starts before another event.</p>
   * @param that the other event to compare to.
   * @return true if this event starts before the other event, false otherwise.
   */
  @Override
  public boolean isBefore(EventModel that) {
    return this.startingEventTime.isBefore(that.getStartingEventTime());
  }

  /**
   * <b>Returns what day and time this event starts on.</b>
   *
   * <p>Note that the EventTime representation is non standard and parameterized.</p>
   * @return the representation of time this event starts.
   */
  @Override
  public EventTimeModel getStartingEventTime() {
    return this.startingEventTime;
  }

  /**
   * <b>Returns an Iterator of the invitees.</b>
   *
   * <p>Returns an array of all the invitees of this event. Note that the first element is
   * always the host.</p>
   * @return a list of invitees.
   */
  public Iterator<String> allInvitees() {
    return Arrays.stream(this.invitedUIDs.clone()).iterator();
  }
  
  /**
   * <b>Returns a string representation of the event in xml format.</b>
   *
   * <p>Returns a string representation of the event, including the time, date, and invitees.</p>
   * @return a string representation of the event in xml format.
   */
  @Override
  public String xmlString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\t<event>\n");
    sb.append("\t\t<name>\"").append(this.title).append("\"</name>\n");
    sb.append("\t\t<time>\n");
    sb.append("\t\t\t<start-day>").append(this.getStartingDay().toString())
            .append("</start-day>\n");
    String startTime = this.startingEventTime.toString();
    sb.append("\t\t\t<start>").append(startTime
            .substring(startTime.length() - 4, startTime.length())).append("</start>\n");
    sb.append("\t\t\t<end-day>").append(this.endingEventTime.getDay().toString())
            .append("</end-day>\n");
    String endTime = this.endingEventTime.toString();
    sb.append("\t\t\t<end>").append(endTime
            .substring(endTime.length() - 4, endTime.length())).append("</end>\n");
    sb.append("\t\t</time>\n");
    sb.append("\t\t<location>\n");
    sb.append("\t\t\t<online>").append(this.isOnline).append("</online>\n");
    sb.append("\t\t\t<place>\"").append(this.location).append("\"</place>\n");
    sb.append("\t\t</location>\n");
    sb.append("\t\t<users>\n");
    for (String uid : this.invitedUIDs) {
      sb.append("\t\t\t<uid>\"").append(uid).append("\"</uid>\n");
    }
    sb.append("\t\t</users>\n");
    sb.append("\t</event>");
    return sb.toString();
  }

  @Override
  public String observeTitle() {
    return this.title;
  }

  @Override
  public String observeLocation() {
    return this.location;
  }

  @Override
  public boolean observeIsOnline() {
    return this.isOnline;
  }

  @Override
  public String observeStartTime() {
    return this.startingEventTime.toString().split(": ")[1];
  }

  @Override
  public String observeEndTime() {
    return this.endingEventTime.toString().split(": ")[1];
  }

  @Override
  public String observeStartDay() {
    return this.getStartingDay().toString();
  }

  @Override
  public String observeEndDay() {
    return this.getEndingDay().toString();
  }

  @Override
  public int startDayInt() {
    return this.startingEventTime.getDay().getValue();
  }

  @Override
  public int endDayInt() {
    return this.endingEventTime.getDay().getValue();
  }

  @Override
  public String[] observeInvitedUIDs() {
    return this.invitedUIDs.clone();
  }

  /**
   * <b>Returns what day and time this event ends on.</b>
   *
   * <p>Note that the EventTime representation is non standard and parameterized.</p>
   * @return the representation of time this event ends.
   */
  @Override
  public EventTimeModel getEndingEventTime() {
    return this.endingEventTime;
  }

  /**
   * <b>Returns what day this event starts on.</b>
   *
   * <p>Returns what day this event starts on, based on this Event's EventTime.</p>
   *
   * @return the day this event starts on.
   */
  @Override
  public DayOfWeek getStartingDay() {
    return this.startingEventTime.getDay();
  }

  @Override
  public DayOfWeek getEndingDay() {
    return this.endingEventTime.getDay();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\tname: ").append(this.title).append("\n");
    sb.append("\ttime: ").append(this.getDuration()).append("\n");
    sb.append("\tlocation: ").append(this.location).append("\n");
    sb.append("\tonline: ").append(this.isOnline).append("\n");
    sb.append("\tinvitees: ");

    for (int uid = 0; uid < this.invitedUIDs.length; uid++) {
      sb.append(this.invitedUIDs[uid]);
      if (uid != this.invitedUIDs.length - 1) {
        sb.append("\n\t");
      }
    }
    return sb.toString();
  }

  private String getDuration() {
    return this.startingEventTime.toString()
            + " -> "
            + this.endingEventTime.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Event)) {
      return false;
    }
    Event that = (Event) o;
    return Objects.equals(this.title, that.title)
            && Objects.equals(this.location, that.location)
            && this.isOnline == that.isOnline
            && this.startingEventTime.isSame(that.startingEventTime)
            && this.endingEventTime.isSame(that.endingEventTime)
            && Arrays.equals(this.invitedUIDs, that.invitedUIDs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.title, this.location, this.isOnline, this.startingEventTime,
            this.endingEventTime, this.hostUID, Arrays.hashCode(this.invitedUIDs));
  }
}