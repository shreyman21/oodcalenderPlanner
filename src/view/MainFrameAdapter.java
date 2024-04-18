package view;

import java.util.List;

import cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06.MainFrame;

import model.Event;

public class MainFrameAdapter implements IPlannerView {

  private MainFrame mainFrame; // This will actually be a MainFrameImpl

  public MainFrameAdapter(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
  }


  @Override
  public void updateSchedule(List<Event> events) {

  }

  @Override
  public void setListener(IPlannerViewListener listener) {

  }

  @Override
  public void showError(String message) {

  }

  @Override
  public void setVisible(boolean visible) {

  }
}
