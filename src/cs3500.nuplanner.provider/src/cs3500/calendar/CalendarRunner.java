package cs3500.nuplanner.provider.src.cs3500.calendar;

import java.io.IOException;

import cs3500.nuplanner.provider.src.cs3500.calendar.controller.CalendarController;
import cs3500.nuplanner.provider.src.cs3500.calendar.controller.hw07.CalendarGUIController;
import cs3500.nuplanner.provider.src.cs3500.calendar.controller.hw07.CalendarGUIControllerWorkdays;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.Calendar;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.CalendarModel;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.Schedule;
import cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06.CalendarGUIView;
import cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06.CalendarGUIViewImpl;


/**
 * The main class to run the calendar. This class loads in a sample schedule and displays it
 * in the GUIView.
 */
public class CalendarRunner {

  /**
   * The main method to run the calendar. Makes a model and loads a schedule to it.
   * Displays this model in the GUIView
   * @param args the arguments to run the calendar
   */
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      throw new IllegalArgumentException("Must specify scheduling method");
    }
    CalendarController controller;
    CalendarModel<Schedule> model = new Calendar();
    CalendarGUIView view = new CalendarGUIViewImpl(model);
    switch (args[0].toLowerCase()) {
      case "anytime":
        controller = new CalendarGUIController(view);
        break;
      case "workhours":
        controller = new CalendarGUIControllerWorkdays(view);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + args[0].toLowerCase());
    }
    controller.launch(model);
    controller.showView();
  }
}