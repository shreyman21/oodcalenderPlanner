package view;

import java.util.List;

import cs3500.nuplanner.provider.src.cs3500.calendar.controller.hw07.ControllerFeatures;
import cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06.MainFrame;

import model.Event;

public class MainFrameAdapter implements MainFrame {

  private MainFrame mainFrame; // This will actually be a MainFrameImpl

  public MainFrameAdapter(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
  }


  @Override
  public void addFeatures(ControllerFeatures features) {

  }

  @Override
  public void updateSchedule() {

  }

  @Override
  public void makeVisible() {

  }
}
