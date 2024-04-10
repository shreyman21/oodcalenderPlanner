package view;

import java.util.List;
import model.Event;
/**
 * The view for the planner.
 */
public class PlannerView {

  private PlannerViewListener listener;

  public void setListener(PlannerViewListener listener) {
    this.listener = listener;
  }

  public void updateSchedule(List<Event> events) {
    System.out.println("Updated Schedule:");
    for (Event event : events) {
      System.out.println(event);
    }
  }

  public void setVisible(boolean b) {
//    if (b) {
//      Scanner scanner = new Scanner(System.in);
//      while (true) {
//        System.out.println("Enter command (create/load/exit):");
//        String command = scanner.nextLine();
//        if ("exit".equalsIgnoreCase(command)) break;
//        if ("create".equalsIgnoreCase(command) && listener != null) {
//          // give random event
//          LocalDateTime now = LocalDateTime.now();
//          LocalDateTime eventTime = now.plusDays(1);
//
//        Event event = new Event("Event", now, eventTime, "Home", true, "Hello");
//        listener.onEventCreate(event, "user1");
//        } else if ("load".equalsIgnoreCase(command) && listener != null) {
//          listener.onScheduleLoad("schedule.txt", null);
//        }
//      }
//    }
  }
}
