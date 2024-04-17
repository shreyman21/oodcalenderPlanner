package cs3500.calendar.view.hw06;

import cs3500.calendar.controller.hw07.ControllerFeatures;

/**
 * Represents the frame for scheduling events.
 */
public interface ScheduleFrame {
  /**
   * <b>Adds the features to the EventFrame.</b>
   * <p>Allows functionality of create, modify, and remove buttons within the EventFrame by using
   * command callback to the ControllerFeatures.</p>
   * @param features the ControllerFeatures object
   * @param main the MainFrame object
   */
  void addFeatures(ControllerFeatures features, MainFrame main);

  /**
   * <b>Packs the EventFrame, centers the frame, and makes it visible.</b>
   */
  void makeVisible();
}
