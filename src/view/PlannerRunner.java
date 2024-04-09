package view;

import controller.PlannerController;
import model.PlannerSystem;

/**
 * This class represents the GUI for the planner system.
 */
public class PlannerRunner {
  /**
   * The main method for the planner system.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
//    PlannerSystem plannerSystem = new PlannerSystem();
//    MainSystemFrame plannerView = new MainSystemFrame(plannerSystem);
//    plannerView.setVisible(true);

    PlannerSystem model = new PlannerSystem();
    MainSystemFrame view = new MainSystemFrame(model);
    PlannerController controller = new PlannerController(model, view);
    controller.start();
  }
}
