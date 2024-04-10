package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a schedule in the calendar system.
 * A schedule has a list of events.
 * We can add and remove events from the schedule.
 * We can also get the list of events in the schedule.
 */
public class Schedule {
  private List<Event> events;

  /**
   * Constructs a new Schedule with an empty list of events.
   */
  public Schedule() {
    this.events = new ArrayList<>();
  }

  /**
   * Adds a new event to the Schedule.
   *
   * @param event the event that is being added
   */
  public void addEvent(Event event) {
    events.add(event);
  }

  /**
   * Removes a specific event from the schedule.
   *
   * @param event the event to be removed
   * @return true if the event was successfully removed.
   */
  public boolean removeEvent(Event event) {
    return events.remove(event);
  }

  /**
   * Retrieves the current list of events in the Schedule.
   * @return A new list of the events currently in the Schedule.
   */
  public List<Event> getEvents() {
    return new ArrayList<>(events);
  }

  /**
   * Check if the event conflicts with any existing events in the schedule.
   * @param event the event to check for conflicts
   * @return true if the event conflicts with any existing events, false otherwise
   */
  public boolean hasEventConflict(Event event) {
    for (Event e : events) {
      if (e.conflictsWith(event)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if the schedule is available for a given time range.
   *
   * @param startSearch the start time to search for
   * @param endSearchTime the end time to search for
   * @return
   */
  public boolean isAvailable(LocalDateTime startSearch, LocalDateTime endSearchTime) {
    for (Event e : events) {
      if (e.getStartTime().isBefore(endSearchTime) && e.getEndTime().isAfter(startSearch)) {
        return false;
      }
    }
    return true;
  }
}