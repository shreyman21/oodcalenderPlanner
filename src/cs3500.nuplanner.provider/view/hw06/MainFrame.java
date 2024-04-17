package cs3500.calendar.view.hw06;

import cs3500.calendar.controller.hw07.ControllerFeatures;

/**
 * Represents the main frame that displays the menu, main panel, and bottom panel.
 */
public interface MainFrame {

  /**
   * <b>Adds the given features to the MainFrame.</b>
   * <p>Allows for functionality of the user selection combo box, create event button, schedule
   * event button, load button, save button, and mouse click listener to see if the user has
   * clicked on an event.</p>
   * @param features the features to add
   */
  void addFeatures(ControllerFeatures features);

  /**
   * <b>Updates the schedule to reflect any changes made to the calendar.</b>
   * <p>This includes adding, removing, or modifying events.</p>
   */
  void updateSchedule();

  /**
   * <b>Makes the MainFrame visible.</b>
   */
  void makeVisible();
}
