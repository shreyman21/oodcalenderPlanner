import javax.swing.SwingUtilities;
import model.PlannerSystem;
import view.IPlannerView;
import view.MainFrameAdapter;
import controller.PlannerController;
import cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06.MainFrameImpl;
import view.MainSystemFrame;

public class CustomerRunner {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      IPlannerView view;
      PlannerSystem model = new PlannerSystem();
      PlannerController controller;



      if (args.length > 0 && args[0].equals("--use-provided-view")) {
        MainFrameImpl mainFrame = new MainFrameImpl()
        //ReadonlyCalendarModel<ScheduleModel> calendar
        view = new MainFrameAdapter(mainFrame);
      } else {
        // Initialize your default view
        view = new MainSystemFrame(model);
      }

      controller = new PlannerController(view, model); // Assuming your controller accepts a view and a model
      view.setListener(controller); // Setup the listener, this assumes your view and controller are set up to handle this
      controller.initialize();
      view.setVisible(true);
    });
  }
}
