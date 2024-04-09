package view;

import java.util.List;

import model.Event;

public class PlannerView {

  private PlannerViewListener listener;

  public void setListener(PlannerViewListener listener) {
    this.listener = listener;
  }

  public void updateSchedule(List<Event> events) {
    // Update the schedule display
  }

  public void setVisible(boolean b) {
    // Display the view
  }
}
