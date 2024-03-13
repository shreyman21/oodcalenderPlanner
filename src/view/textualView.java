package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Event;
import model.User;

/**
 * The textual view for the planner.
 * This view displays the schedule for each user in text format.
 * It implements the plannerView interface.
 */
public class textualView implements plannerView {

  public void displayUsersSchedules(List<User> users) {
    for (User user : users) {
      System.out.println("User: " + user.getName());
      user.getSchedule().getEvents().forEach(event -> {
        System.out.println("\t" + formatEvent(event));
      });
      System.out.println();
    }
  }

  private String formatEvent(Event event) {
    StringBuilder sb = new StringBuilder();
    sb.append("name: ").append(event.getName()).append("\n");
    sb.append("\ttime: ").append(formatDateTime(event.getStartTime())).append(" -> ").append(formatDateTime(event.getEndTime())).append("\n");
    sb.append("\tlocation: ").append(event.getLocation()).append("\n");
    sb.append("\tonline: ").append(event.isOnline() ? "true" : "false").append("\n");
    sb.append("\tinvitees: ");
    for (String invitee : event.getInvitees()) {
      sb.append(invitee).append(", ");
    }
    if (!event.getInvitees().isEmpty()) {
      sb.setLength(sb.length() - 2);
    }
    sb.append("\n");
    return sb.toString();
  }

  private String formatDateTime(LocalDateTime dateTime) {
    DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
    String dayOfWeek = dayOfWeekFormatter.format(dateTime);
    String time = timeFormatter.format(dateTime);
    return dayOfWeek + ": " + time;
  }
}
