package cs3500.calendar.view;

import java.io.IOException;

import cs3500.calendar.model.hw05.CalendarModel;
import cs3500.calendar.model.hw05.Schedule;
import cs3500.calendar.model.hw05.EventModel;
import cs3500.calendar.model.hw05.ScheduleModel;

/**
 * View for the calendar. Focuses on representing the state of the calendar as a schedule
 * with events.
 */
public class CalendarTextView implements CalendarView {
  private final CalendarModel<Schedule> model;
  private final Appendable appendable;

  /**
   * Constructs a CalendarTextView.
   * @param model the model to be represented
   */
  public CalendarTextView(CalendarModel<Schedule> model) {
    this.model = model;
    this.appendable = System.out;
  }

  /**
   * Constructs a CalendarTextView.
   * @param model the model to be represented
   * @param appendable the appendable to write to
   */
  public CalendarTextView(CalendarModel<Schedule> model, Appendable appendable) {
    this.model = model;
    this.appendable = appendable;
  }

  /**
   * Renders the calendar as a string.
   * @return a string representation of the calendar.
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    String[] users = this.model.getUsers();
    for (String user : users) {
      result.append(model.userScheduleToString(user));
    }
    return this.model.toString();
  }

  private String eventString(EventModel event) {
    StringBuilder sb = new StringBuilder();
    sb.append("\tname: ").append(event.observeTitle()).append("\n");
    sb.append("\ttime: ").append(event.observeStartTime()).append(" -> ")
            .append(event.observeEndTime()).append("\n");
    sb.append("\tlocation: ").append(event.observeLocation()).append("\n");
    sb.append("\tonline: ").append(event.observeIsOnline()).append("\n");
    sb.append("\tinvitees: ");
    String[] invitedUIDs = event.observeInvitedUIDs();
    for (int uid = 0; uid < invitedUIDs.length; uid++) {
      sb.append(invitedUIDs[uid]);
      if (uid != invitedUIDs.length - 1) {
        sb.append("\n\t");
      }
    }
    return sb.toString();
  }

  private String scheduleString(ScheduleModel schedule) {
    //TODO implement this method
    return "";
  }

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   *
   * @throws IOException if the rendering fails for some reason
   */
  @Override
  public void render() throws IOException {
    try {
      this.appendable.append(this.toString());
    } catch (IOException e) {
      throw new IOException("Rendering failed: " + e);
    }
  }
}
