package model;

public class User {
  private String id;
  private String name;
  private Schedule schedule;

  public User(String id, String name) {
    this.id = id;
    this.name = name;
    this.schedule = new Schedule();
  }

  // Getter and setter methods
  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Schedule getSchedule() {
    return schedule;
  }
}
