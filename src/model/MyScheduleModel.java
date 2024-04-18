package model;

import java.util.List;
import java.util.stream.Collectors;

import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.EventModel;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.ScheduleModel;

/**
 * Represents a schedule model that adapts the event model to the schedule model.
 * This class is used to convert the event model to the schedule model.
 * This class implements ScheduleModel.
 */
public class MyScheduleModel implements ScheduleModel<EventModelAdapter> {
  private List<EventModelAdapter> events;


  public MyScheduleModel(List<Event> events) {
    this.events = events.stream()
            .map(EventModelAdapter::new)
            .collect(Collectors.toList());
  }

  @Override
  public void addEvent(EventModel event) {
    if (event instanceof EventModelAdapter) {
      EventModelAdapter adapter = (EventModelAdapter) event;
      if (events.stream().noneMatch(e -> e.isOverlapping(adapter))) {
        events.add(adapter);
      } else {
        throw new IllegalArgumentException("Event time overlaps with another event's time");
      }
    } else {
      throw new IllegalArgumentException("Event type not supported");
    }
  }

  @Override
  public void removeEvent(EventModel event) {
    if (event instanceof EventModelAdapter) {
      EventModelAdapter adapter = (EventModelAdapter) event;
      if (!events.remove(adapter)) {
        throw new IllegalArgumentException("Event does not exist in the schedule");
      }
    } else {
      throw new IllegalArgumentException("Event type not supported");
    }
  }

  @Override
  public boolean has(EventModel event) {
    if (event instanceof EventModelAdapter) {
      EventModelAdapter adapter = (EventModelAdapter) event;
      return events.stream().anyMatch(e -> e.equals(adapter));
    } else {
      throw new IllegalArgumentException("Event type not supported");
    }
  }

  @Override
  public boolean isAvailable(EventModel event) {
    if (event instanceof EventModelAdapter) {
      EventModelAdapter adapter = (EventModelAdapter) event;
      return events.stream().noneMatch(e -> e.isOverlapping(adapter));
    } else {
      throw new IllegalArgumentException("Event type not supported");
    }
  }

  @Override
  public EventModelAdapter[] getEvents() {
    return events.toArray(new EventModelAdapter[0]);
  }

  @Override
  public String xmlFormat() {
    return null;
  }
}
