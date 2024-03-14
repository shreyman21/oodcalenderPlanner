package model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The class for an event.
 * An event has a name, start time, end time, location, and participants, and invitees.
 * The start and end times are in LocalDateTime format.
 */
public class Event {
  private String name;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String location;
  private boolean isOnline;

  private List<String> invitees;

  /**
   * Constructor for the Event class.
   *
   * @param name      the name of the event
   * @param startTime the start time of the event
   * @param endTime   the end time of the event
   * @param location  the location of the event
   * @param isOnline  whether the event is online
   * @param invitees  the invitees of the event
   */

  public Event(String name, LocalDateTime startTime, LocalDateTime endTime,
               String location, boolean isOnline, List<String> invitees) {
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
    this.location = location;
    this.isOnline = isOnline;
    this.invitees = invitees;
  }

  public boolean checkForConflict(Event otherEvent) {
    return (this.startTime.isBefore(otherEvent.getEndTime())
            && this.endTime.isAfter(otherEvent.getStartTime()));
  }

  // Getter and setter methods
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public boolean isOnline() {
    return isOnline;
  }

  public void setOnline(boolean online) {
    isOnline = online;
  }

  public List<String> getInvitees() {
    return invitees;
  }

  public boolean conflictsWith(Event event) {
    return this.startTime.isBefore(event.getEndTime())
            && this.endTime.isAfter(event.getStartTime());
  }
}