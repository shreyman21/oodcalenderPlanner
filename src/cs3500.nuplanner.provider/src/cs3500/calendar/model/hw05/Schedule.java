package cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a user's schedule.
 * A schedule can add an event, remove an event, check if the schedule is available for an event,
 * and get a String representation of all events.
 */
public class Schedule implements ScheduleModel<EventModel> {
  private ArrayList<EventModel> events;

  public Schedule() {
    this.events = new ArrayList<>();
  }

  /**
   * <b>Adds an event to the schedule.</b>
   *
   * <p>Note that the event time cannot overlap with another event's time.</p>
   *
   * @param event the event to add
   * @throws IllegalArgumentException if the event time overlaps with another event's time
   */
  @Override
  public void addEvent(EventModel event) {
    if (!this.events.contains(event)) {
      if (!this.isAvailable(event)) {
        throw new IllegalArgumentException("Event time overlaps with another event's time");
      }

      for (int posn = 0; posn < this.events.size(); posn++) {
        if (this.events.get(posn).isAfter(event)) {
          this.events.add(posn, event);
          return;
        }
      }
      this.events.add(event);
    }
  }

  /**
   * <b>Checks if this schedule has the given event.</b>
   *
   * <p>Note that the given event must contain the exact same fields as another event in this
   * schedule for it to consider it contained.</p>
   * @param event event to check.
   * @return true if this schedule contains event, false otherwise.
   */
  @Override
  public boolean has(EventModel event) {
    return this.events.contains(event);
  }

  /**
   * <b>Checks if the schedule is available for the given Event.</b>
   *
   * <p>This method checks if the timing of the given Event overlaps with any other events
   * in this schedule.</p>
   *
   * @param event the event to check
   * @return true if the schedule is available for the given Event, false otherwise
   */
  @Override
  public boolean isAvailable(EventModel event) {
    for (EventModel e : this.events) {
      if (e.isOverlapping(event)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Event[] getEvents() {
    // TODO: Test this method
    return this.events.toArray(new Event[0]);
  }

  /**
   * <b>Removes an event from the schedule.</b>
   *
   * <p>Note that the event must exist in the schedule.</p>
   *
   * @param event the event to remove.
   * @throws IllegalArgumentException if the event does not exist in the schedule.
   */
  @Override
  public void removeEvent(EventModel event) {
    if (!this.events.contains(event)) {
      throw new IllegalArgumentException("Event does not exist in the schedule");
    }
    for (EventModel e : this.events) {
      if (e.equals(event)) {
        this.events.remove(e);
        return;
      }
    }
  }

  /**
   * <b>Gets String representation of all events.</b>
   *
   * <p>This method returns a String representation of all the events in this schedule.</p>
   *
   * @return a String representation of all events
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    HashMap<DayOfWeek, ArrayList<EventModel>> eventsByDay = this.eventsByDay();
    for (DayOfWeek day : DayOfWeek.values()) {
      sb.append(day.toString()).append(":\n");
      for (EventModel e : eventsByDay.get(day)) {
        sb.append(e.toString());
        if (day != DayOfWeek.SATURDAY) {
          sb.append("\n");
        }
      }
    }
    return sb.toString();
  }



  /**
   * <b>Gets the XML format of the schedule.</b>
   *
   * <p>This method returns the XML format of the schedule.</p>
   *
   * @return the XML format of the schedule
   */
  @Override
  public String xmlFormat() {
    StringBuilder sb = new StringBuilder();
    for (EventModel e : this.events) {
      sb.append(e.xmlString()).append("\n");
    }
    return sb.toString();
  }

  private HashMap<DayOfWeek, ArrayList<EventModel>> eventsByDay() {
    HashMap<DayOfWeek, ArrayList<EventModel>> eventsByDay = new HashMap<>();
    for (DayOfWeek day : DayOfWeek.values()) {
      eventsByDay.put(day, new ArrayList<>());
    }
    for (EventModel e : this.events) {
      eventsByDay.get(e.getStartingDay()).add(e);
    }
    return eventsByDay;
  }

  /**
   * <b>Checks if two schedules are equal.</b>
   *
   * <p>This method returns true if the two schedules are equal, false otherwise.</p>
   *
   * @param that the other Object to compare to
   * @return true if the schedules are equal, false otherwise
   */
  @Override
  public boolean equals(Object that) {
    if (!(that instanceof Schedule)) {
      return false;
    }
    Schedule other = (Schedule) that;
    return this.hashCode() == other.hashCode();
  }

  /**
   * <b>Gets the hash code of the schedule.</b>
   *
   * <p>This method returns the hash code of the schedule.</p>
   *
   * @return the hash code of the schedule
   */
  @Override
  public int hashCode() {
    return this.events.hashCode();
  }
}