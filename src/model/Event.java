package model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Event {
  private String name;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String location;
  private boolean isOnline;
  private List<String> participants; // List of user IDs

  private List<String> invitees;

  public Event(String name, LocalDateTime startTime, LocalDateTime endTime, String location, boolean isOnline, List<String> participants, List<String> invitees) {
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
    this.location = location;
    this.isOnline = isOnline;
    this.participants = participants;
    this.invitees = invitees;
  }
  public boolean checkForConflict(Event otherEvent) {
    return (this.startTime.isBefore(otherEvent.getEndTime()) && this.endTime.isAfter(otherEvent.getStartTime()));
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

  public List<String> getParticipants() {
    return participants;
  }

  public void setParticipants(List<String> participants) {
    this.participants = participants;
  }


  public List<String> getInvitees() {
    return invitees;
  }
}