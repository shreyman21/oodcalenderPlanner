package controller;

import javax.swing.*;

import model.Event;
import model.PlannerSystem;
import model.User;
import view.MainSystemFrame;
import view.PlannerView;
import view.PlannerViewListener;

public class PlannerController {
  private final PlannerSystem model;
  private final MainSystemFrame view;



  public PlannerController(PlannerSystem model, MainSystemFrame view) {
    this.model = model;
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
