package controller;

import javax.swing.*;

import model.Event;
import model.PlannerSystem;
import model.User;
import view.MainSystemFrame;
import view.PlannerViewListener;

/**
 * This class represents the PlannerController.
 * This class is used to control the planner system.
 * It listens for events from the view and updates the model accordingly.
 */
public class PlannerController {
  private final MainSystemFrame view;

  public PlannerController(PlannerSystem model, MainSystemFrame view) {
    this.view = view;
    this.view.setListener(new PlannerViewListener() {
      @Override
      public void onEventCreate(Event event, String userId) {
        model.addEventToUserSchedule(userId, event);
        view.updateSchedule(model.getEvents());
      }

      @Override
      public void onScheduleLoad(String filePath, User user) {
        try {
          model.uploadSchedule(filePath, user);
          view.updateSchedule(model.getEvents());
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, "Error loading schedule: " + e.getMessage());
        }
      }
    });
  }

  public void start() {
    view.setVisible(true);
  }
}
