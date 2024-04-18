package cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06;

import java.awt.Graphics;

import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.ReadonlyCalendarModel;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.ScheduleModel;


/**
 * Represents the main panel of the calendar GUI.
 * Includes the main panel and the menu bar. Also includes the bottom panel.
 */
public interface MainPanel {

  /**
   * <b>Updates the schedule.</b>
   *
   * <p>Used after adding, removing, or modifying events.</p>
   * @param model the calendar model
   * @param user the user to update the schedule for
   */
  void updateSchedule(ReadonlyCalendarModel<ScheduleModel> model, String user);

  /**
   * <b>Paints the grid for the calendar, as well as the events in the currently selected
   * user's schedule.</b>
   * @param g the graphics object to paint with
   */
  void paintComponent(Graphics g);
}
