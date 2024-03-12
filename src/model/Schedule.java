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

}