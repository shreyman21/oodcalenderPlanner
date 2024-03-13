package model;

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

  public Schedule() {
    this.events = new ArrayList<>();
  }

  public void addEvent(Event event) {
    events.add(event);
  }

  public boolean removeEvent(Event event) {
    return events.remove(event);
  }

  public List<Event> getEvents() {
    return new ArrayList<>(events);
  }

}