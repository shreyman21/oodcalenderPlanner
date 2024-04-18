package model;

import java.time.LocalDateTime;
import java.util.Iterator;

import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.DayOfWeek;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.EventModel;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.EventTimeModel;

/**
 * Represents an event model that adapts the event model to the event model.
 * This class is used to convert the event model to the event model.
 * This class implements EventModel.
 */
public class EventModelAdapter implements EventModel {

  private Event event;

  public EventModelAdapter(Event event) {
    this.event = event;
  }
  @Override
  public boolean isOverlapping(EventModel that) {
    if (that instanceof EventModelAdapter) {
      Event otherEvent = ((EventModelAdapter) that).event;
      return this.event.conflictsWith(otherEvent);
    }
    return false;
  }

  @Override
  public boolean isOverlapping(DayOfWeek day, String time) {
    return false;
  }

  @Override
  public boolean isAfter(EventModel that) {
    if (that instanceof EventModelAdapter) {
      Event otherEvent = ((EventModelAdapter) that).event;
      return this.event.getEndTime().isAfter(otherEvent.getEndTime());
    }
    return false;
  }

  @Override
  public boolean isBefore(EventModel that) {
    if (that instanceof EventModelAdapter) {
      Event otherEvent = ((EventModelAdapter) that).event;
      return this.event.getStartTime().isBefore(otherEvent.getStartTime());
    }
    return false;
  }

  @Override
  public EventTimeModel getStartingEventTime() {
    return null;
  }

  @Override
  public EventTimeModel getEndingEventTime() {
    return null;
  }

  @Override
  public DayOfWeek getStartingDay() {
    // get start time and end time convert to day of week
    LocalDateTime start = this.event.getStartTime();
    return DayOfWeek.values()[start.getDayOfWeek().getValue() - 1];
  }

  @Override
  public DayOfWeek getEndingDay() {
    LocalDateTime end = this.event.getEndTime();
    return DayOfWeek.values()[end.getDayOfWeek().getValue() - 1];
  }

  @Override
  public Iterator<String> allInvitees() {
    return this.event.getInvitees().iterator();
  }

  @Override
  public String xmlString() {
    return null;
  }

  @Override
  public String observeTitle() {
    return null;
  }

  @Override
  public String observeLocation() {
    return this.event.getLocation();
  }

  @Override
  public boolean observeIsOnline() {
    return this.event.isOnline();
  }

  @Override
  public String observeStartTime() {
    return null;
  }

  @Override
  public String observeEndTime() {
    return null;
  }

  @Override
  public String observeStartDay() {
    return null;
  }

  @Override
  public String observeEndDay() {
    return null;
  }

  @Override
  public int startDayInt() {
    return 0;
  }

  @Override
  public int endDayInt() {
    return 0;
  }

  @Override
  public String[] observeInvitedUIDs() {
    return new String[0];
  }

  public Event getEvent() {
    return this.event;
  }
}
