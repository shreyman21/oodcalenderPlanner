package view;

import java.util.ArrayList;
import java.util.List;
import model.Event;

public class MockPlannerView implements IPlannerView {
  private PlannerViewListener listener;
  private List<Event> displayedEvents = new ArrayList<>();
  private boolean visible;


  @Override
  public void updateSchedule(List<Event> events) {
  displayedEvents = events;
  }

  @Override
  public void setListener(PlannerViewListener listener) {
    this.listener = listener;
  }

  @Override
  public void showError(String message) {

  }

  @Override
  public void setVisible(boolean visible) {

  }
}
