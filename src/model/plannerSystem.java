package model;

import java.time.LocalDateTime;
import java.util.List;

public class plannerSystem implements plannerSystemModel{

  @Override
  public boolean uploadSchedule(String xmlFilePath, User user) {
   if (xmlFilePath == null || user == null) {
      throw new IllegalArgumentException("Invalid input");
    }
    return user.getSchedule().uploadSchedule(xmlFilePath);
  }

  @Override
  public boolean saveSchedule(String xmlFilePath, User user) {
    return false;
  }

  @Override
  public void selectUser(User user) {

  }

  @Override
  public boolean createEvent(User user, Event event) {
    return false;
  }

  @Override
  public boolean modifyEvent(User user, Event originalEvent, Event updatedEvent) {
    return false;
  }

  @Override
  public boolean removeEvent(User user, Event event) {
    return false;
  }

  @Override
  public boolean autoSchedule(User user, Event event) {
    return false;
  }

  @Override
  public List<Event> seeEvents(User user, LocalDateTime time) {
    return null;
  }
}
