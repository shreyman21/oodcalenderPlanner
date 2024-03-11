package model;

public interface plannerSystemModel {
  /*
   Upload an XML file representing a single user’s schedule.
   Save each user’s schedule to an XML file.
   Select one of the users to display their schedule.
   Create, modify, or remove an event on a user’s schedule, which may affect other user’s schedule.
   Have the program automatically schedule an event on some users’
   schedules at some time if possible.
   See events occurring at a given time for the given user.
   */
  public void uploadSchedule(String xmlFile);
  public void saveSchedule(String xmlFile);
  public void selectUser(String user);
  public void createEvent(String user, String event);
  public void modifyEvent(String user, String event);
  public void removeEvent(String user, String event);
  public void autoSchedule(String user, String event);
  public void seeEvents(String user, String time);

}
