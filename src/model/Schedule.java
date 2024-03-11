package model;

import java.util.ArrayList;
import java.util.List;

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

  public boolean uploadSchedule(String xmlFilePath) {
    // This method could be implemented to read the XML file and populate the schedule with events.
    return true;
  }

  // Methods to modify events, get events by date/time, etc.
  // For example, findEventsOnDate(LocalDate date), modifyEvent(Event oldEvent, Event newEvent)
}