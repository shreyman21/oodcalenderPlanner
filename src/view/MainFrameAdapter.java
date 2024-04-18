package view;

import java.util.List;

import javax.swing.*;

import cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06.MainFrame;

import model.Event;
import model.ISchedulingStrategy;
import model.PlannerSystem;

public class MainFrameAdapter implements IPlannerView {

  private MainFrame mainFrame;
  private ControllerFeaturesAdapter controllerFeaturesAdapter;

  private IPlannerViewListener listener;

  private PlannerSystem plannerSystem;

  private ISchedulingStrategy strategy;

  public MainFrameAdapter(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
    this.controllerFeaturesAdapter =
            new ControllerFeaturesAdapter(listener, plannerSystem, strategy);
  }


  @Override
  public void updateSchedule(List<Event> events) {
    this.mainFrame.updateSchedule();
  }

  @Override
  public void setListener(IPlannerViewListener listener) {
    this.listener = listener;
    this.mainFrame.addFeatures(controllerFeaturesAdapter);
  }

  @Override
  public void showError(String message) {
   // open a dialog box with the error message with message
    JPanel panel = new JPanel();
    JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void setVisible(boolean visible) {
    this.mainFrame.makeVisible();
  }
}
