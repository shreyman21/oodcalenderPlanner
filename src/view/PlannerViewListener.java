package view;

import model.Event;
import model.User;

public interface PlannerViewListener {
  public void onEventCreate(Event event, String userId);
  void onScheduleLoad(String filePath, User user);

}
