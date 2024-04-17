package cs3500.calendar.model.hw05;

import java.util.Iterator;

/**
 * Represents an event in a user's schedule.
 */
public interface EventModel {

  /**
   * <b>Checks if the event is overlapping with another event.</b>
   *
   * <p>Checks if the event is overlapping with another event.</p>
   * @param that the event to check for overlap
   */
  boolean isOverlapping(EventModel that);

  /**
   *<b>Checks if the event is overlapping with another event.</b>
   *
   * <p>Checks if the event is overlapping with another event.</p>
   * @param day day to check if it is overlapping
   * @param time time to check if it is overlapping
   */
  boolean isOverlapping(DayOfWeek day, String time);

  /**
   * <b>Checks if this event starts after another event.</b>
   *
   * <p>Checks if this event starts after another event.</p>
   * @param that the other event to compare to
   * @return true if this event starts after the other event, false otherwise
   */
  boolean isAfter(EventModel that);

  /**
   * <b>Checks if this event starts before another event.</b>
   *
   * <p>Checks if this event starts before another event.</p>
   * @param that the other event to compare to
   * @return true if this event starts before the other event, false otherwise
   */
  boolean isBefore(EventModel that);

  /**
   * <b>Returns what day and time this event starts on.</b>
   *
   * <p>Note that the EventTime representation is non standard and parameterized.</p>
   * @return the representation of time this event starts
   */
  EventTimeModel getStartingEventTime();

  /**
   * <b>Returns what day and time this event ends on.</b>
   *
   * <p>Note that the EventTime representation is non standard and parameterized.</p>
   * @return the representation of time this event ends
   */
  EventTimeModel getEndingEventTime();

  /**
   * <b>Returns what day this event starts on.</b>
   *
   * <p>Returns what day this event starts on, based on this Event's EventTime.</p>
   * @return the day this event starts on
   */
  DayOfWeek getStartingDay();

  /**
   * <b>Returns what day this event ends on.</b>
   *
   * <p>Returns what day this event ends on, based on this Event's EventTime.</p>
   * @return the day this event starts on
   */
  DayOfWeek getEndingDay();

  /**
   * <b>Returns an Iterator of the invitees.</b>
   *
   * <p>Returns an array of all the invitees of this event. Note that the first element is
   * always the host.</p>
   * @return a list of invitees
   */
  Iterator<String> allInvitees();

  /**
   * <b>Returns a string representation of the event in xml format.</b>
   *
   * <p>Returns a string representation of the event, including the time, date, and invitees.</p>
   * @return a string representation of the event in xml format
   */
  String xmlString();

  /**
   * <b>Returns a string representation of the events title.</b>
   * @return a string representation of the events title
   */
  String observeTitle();

  /**
   * <b>Returns a string representation of the events location.</b>
   * @return a string representation of the events location
   */
  String observeLocation();

  /**
   * <b>Returns a boolean representation of whether the event is online.</b>
   * @return a boolean representation of whether the event is online
   */
  boolean observeIsOnline();

  /**
   * <b>Returns a string representation of the events start time in text format.</b>
   * @return a string representation of the events start time in text format
   */
  String observeStartTime();

  /**
   * <b>Returns a string representation of the events end time in text format.</b>
   * @return a string representation of the events end time in text format
   */
  String observeEndTime();

  /**
   * <b>Returns a string representation of the events start day in text format.</b>
   * @return a string representation of the events start day in text format
   */
  String observeStartDay();

  /**
   * <b>Returns a string representation of the events end day in text format.</b>
   * @return a string representation of the events end day in text format
   */
  String observeEndDay();

  /**
   * <b>Returns a int representation of the events start day in int format.</b>
   * @return a int representation of the events start day in int format
   */
  int startDayInt();

  /**
   * <b>Returns a int representation of the events end day in int format.</b>
   * @return a int representation of the events end day in int format
   */
  int endDayInt();

  /**
   * <b>Returns a string representation of the event in text format.</b>
   *
   * <p>Returns a string representation of the event, including the time, date, and invitees.</p>
   * @return a string representation of the event in text format
   */
  String[] observeInvitedUIDs();
}