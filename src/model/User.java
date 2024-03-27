package model;

import java.util.List;

/**
 * This class represents a user in the calendar system.
 * Each user has a unique ID, a name, and a schedule.
 * We can also have multiple users with multiple schedules.
 */
public class User {
  private String id;
  private String name;
  private Schedule schedule;

  /**
   * Constructor for the User class.
   *
   * @param id   the user's unique ID
   * @param name the user's name
   */
  public User(String id, String name) {
    this.id = id;
    this.name = name;
    this.schedule = new Schedule();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Schedule getSchedule() {
    return schedule;
  }

  //get events
  public List<Event> getEvents() {
    return schedule.getEvents();
  }





}
