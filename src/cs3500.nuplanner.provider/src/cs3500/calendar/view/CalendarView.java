package cs3500.nuplanner.provider.src.cs3500.calendar.view;

import java.io.IOException;

/**
 * View for the calendar. Focuses on representing the state of the
 * calendar as a schedule with events.
 */
public interface CalendarView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   * @throws IOException if the rendering fails for some reason
   */
  void render() throws IOException;
}
