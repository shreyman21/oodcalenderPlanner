package cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06;

import java.io.IOException;

import cs3500.nuplanner.provider.src.cs3500.calendar.controller.hw07.ControllerFeatures;
import cs3500.nuplanner.provider.src.cs3500.calendar.view.CalendarView;


/**
 * A GUI view for Calendar: display the central system and provide visual interface for users.
 */
public interface CalendarGUIView extends CalendarView {

  /**
   * <b>Make the view visible to start the calendar session.</b>
   */
  void render() throws IOException;

  /**
   * <b>Add the given features to the CalendarGUIView.</b>
   * @param features the features to add
   */
  void addFeatures(ControllerFeatures features);
}
