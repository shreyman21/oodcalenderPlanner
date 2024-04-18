package view;

import java.util.List;

import javax.swing.*;

import controller.ControllerFeaturesAdapter;
import cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06.MainFrame;

import model.Event;
import model.ISchedulingStrategy;
import model.PlannerSystem;
import model.User;

public class MainFrameAdapter implements IPlannerView, IPlannerViewListener{

  private MainFrame mainFrame;
  private ControllerFeaturesAdapter controllerFeaturesAdapter;

  private IPlannerViewListener listener;

  private PlannerSystem plannerSystem;

  private ISchedulingStrategy strategy;

  public MainFrameAdapter(MainFrame mainFrame, PlannerSystem plannerSystem
          , ISchedulingStrategy strategy, IPlannerViewListener listener) {
    this.mainFrame = mainFrame;
    this.plannerSystem = plannerSystem;
    this.strategy = strategy;
    this.listener = listener;
    this.controllerFeaturesAdapter =
            new ControllerFeaturesAdapter(this, plannerSystem, strategy);
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
    JOptionPane.showMessageDialog(null,
            message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void setVisible(boolean visible) {
    this.mainFrame.makeVisible();
  }

  @Override
  public void onEventCreate(Event event, String userId) {

  }

  @Override
  public void onScheduleLoad(String filePath, User user) {

  }
}
