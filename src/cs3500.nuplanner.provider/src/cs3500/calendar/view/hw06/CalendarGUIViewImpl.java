package cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06;

import java.io.IOException;

import cs3500.nuplanner.provider.src.cs3500.calendar.controller.hw07.ControllerFeatures;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.CalendarModel;


/**
 * The class that represents the GUI view of the calendar. This class is responsible for
 * rendering the calendar.
 */
public class CalendarGUIViewImpl implements CalendarGUIView {

  private MainFrameImpl mainFrame;

  /**
   * <b>Constructs a CalendarGUIViewImpl object.</b>
   * @param model the model to be used in the view
   */
  public CalendarGUIViewImpl(CalendarModel model) {
    this.mainFrame = new MainFrameImpl(model);
  }

  private void setVisible(boolean b) {
    this.mainFrame.makeVisible();
  }

  @Override
  public void addFeatures(ControllerFeatures features) {
    this.mainFrame.addFeatures(features);
  }

  @Override
  public void render() throws IOException {
    this.setVisible(true);
  }
}
