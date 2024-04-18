package cs3500.nuplanner.provider.src.cs3500.calendar.controller;

import java.io.IOException;

import cs3500.nuplanner.provider.src.cs3500.calendar.controller.hw07.ControllerFeatures;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.CalendarModel;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.Schedule;


/**
 * Controller Interface, holds all the methods needed for any type of
 * controller to function.
 */
public interface CalendarController extends ControllerFeatures {

  /**
   * Starts the calendar.
   *
   * @param model the model to be used
   * @throws NullPointerException if model is null
   */
  void launch(CalendarModel<Schedule> model);


  void showView() throws IOException;
}
