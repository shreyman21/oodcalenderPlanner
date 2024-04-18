package cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;


/**
 * Represents a calendar.
 * A calendar has a map of users and their respective schedules.
 * A calendar can add a user, add an event, remove an event, modify an event, check if a user is
 * available, and get a list of all users.
 */
public class Calendar implements CalendarModel<Schedule> {
  private Map<String, Schedule> userSchedules;

  public Calendar() {
    this.userSchedules = new HashMap<>();
  }

  public Calendar(HashMap<String, Schedule> userSchedules) {
    this.userSchedules = userSchedules;
  }

  /**
   * <b>Creates a new user and empty schedule to add users to.</b>
   *
   * <p>Note that the uid must be unique; two users cannot have the same uid.</p>
   *
   * @param uid the user's id
   * @throws IllegalArgumentException if the uid is not unique
   */
  @Override
  public void addUser(String uid) {
    if (this.userSchedules.containsKey(uid)) {
      throw new IllegalArgumentException("User already exists");
    } else {
      this.userSchedules.put(uid, new Schedule());
    }
  }

  /**
   * <b>Adds an event to a user's schedule.</b>
   *
   * <p>Adds an event to a user's calendar</p>
   *
   * @param event the event to add
   * @throws IllegalArgumentException if the event time overlaps with another event's
   *                                  time for any invited users
   */
  @Override
  public void addEvent(EventModel event) {
    boolean allUsersAvail = true;
    for (Iterator<String> it = event.allInvitees(); it.hasNext(); ) {
      String invitee = it.next();
      if (userSchedules.containsKey(invitee)) {
        boolean flag = userSchedules.get(invitee).has(event)
                || userSchedules.get(invitee).isAvailable(event);
        allUsersAvail = allUsersAvail && flag;
      } else {
        this.addUser(invitee);
      }
    }
    if (allUsersAvail) {
      for (Iterator<String> it = event.allInvitees(); it.hasNext(); ) {
        String invitee = it.next();
        this.userSchedules.get(invitee).addEvent(event);
      }
    } else {
      throw new IllegalArgumentException("An invitee is not available.");
    }
  }

  /**
   * <b>Removes an event from the user's schedule.</b>
   *
   * <p>Note that removing the Event removes it from all invitees' schedules as well.</p>
   *
   * @param uid   uid of the user removing the Event
   * @param event event to be removed
   * @throws IllegalArgumentException if the event is not found
   */
  @Override
  public void removeEvent(String uid, EventModel event) {
    for (Iterator<String> it = event.allInvitees(); it.hasNext(); ) {
      String user = it.next();
      this.userSchedules.get(user).removeEvent(event);
    }
  }

  @Override
  public ScheduleModel getSchedule(String uid) {
    // TODO: Test this method
    Schedule userSchedule = new Schedule();
    for (EventModel event : this.userSchedules.get(uid).getEvents()) {
      userSchedule.addEvent(event);
    }
    return userSchedule;
  }

  /**
   * <b>Replaces an Event with a new Event.</b>
   *
   * <p>Replaces an event in a user's schedule, as well as all affected users.</p>
   *
   * @param uid       uid of the user modifying the Event.
   * @param prevEvent previous Event to be replaced
   * @param newEvent  new Event to replace
   */
  @Override
  public void modifyEvent(String uid, EventModel prevEvent, EventModel newEvent) {
    this.removeEvent(uid, prevEvent);
    this.addEvent(newEvent);
  }

  /**
   * <b>Checks if the given uid is available for a given time.</b>
   *
   * <p>Checks if the given uid is available for a given time.</p>
   *
   * @param uid  uid of the user to check
   * @param day  day of the week
   * @param time time of day
   * @return true if user is available for given time, false if not
   */
  @Override
  public boolean isUserAvailable(String uid, DayOfWeek day, String time) {
    this.ensureUserExists(uid);
    return userSchedules.get(uid).isAvailable(new Event.EventBuilder()
            .testTime()
            .startingDay(day)
            .startingTime(time)
            .endingDay(day)
            .endingTime(time)
            .build());
  }

  private void ensureUserExists(String uid) throws IllegalArgumentException {
    if (!this.userSchedules.containsKey(uid)) {
      throw new IllegalArgumentException("User does not exist");
    }
  }

  /**
   * <b>Gets an array of all users (uid).</b>
   *
   * @return an array of all users
   */
  @Override
  public String[] getUsers() {
    return userSchedules.keySet().toArray(new String[0]);
  }

  @Override
  public String userScheduleToString(String uid) {
    StringBuilder result = new StringBuilder();
    result.append("User: ")
            .append(uid)
            .append("\n")
            .append(userSchedules.get(uid)
                    .toString());
    return result.toString();
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Schedule> entry : userSchedules.entrySet()) {
      String user = entry.getKey();
      Schedule schedule = entry.getValue();
      result.append("User: ")
              .append(user)
              .append("\n")
              .append(schedule
                      .toString());
    }
    return result.toString();
  }
}