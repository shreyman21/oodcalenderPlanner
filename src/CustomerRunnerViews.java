import controller.ControllerFeaturesAdapter;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.CalendarModel;
import cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06.CalendarGUIViewImpl;
import cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06.MainFrameImpl;
import cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06.MainPanelImpl;
import cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06.ScheduleFrameImpl;
import model.AnytimeSchedulingStrategy;
import model.CalenderModelAdapter;
import model.ISchedulingStrategy;
import model.LenientSchedulingStrategy;
import model.PlannerSystem;
import model.WorkHoursSchedulingStrategy;
import view.IPlannerView;
import view.IPlannerViewListener;
import view.MainFrameAdapter;
import view.MainSystemFrame;

/**
 * This class runs the Providers view of the NUPlanner system.
 * Our providers have four different views to choose from.
 * The views are MainPanel, MainFrame, CalendarGUIView, and ScheduleFrame.
 * The default view is MainSystemFrame which is our view.
 */
public class CustomerRunnerViews {

  public static void main(String[] args) {
    IPlannerView view;
    PlannerSystem model = new PlannerSystem();  // System model
    ISchedulingStrategy strategy = determineStrategy(args);
    IPlannerViewListener listener = null;
    CalenderModelAdapter calendarModel = new CalenderModelAdapter(model);

    MainPanelImpl mainPanel = new MainPanelImpl(calendarModel);
    MainFrameImpl mainFrame = new MainFrameImpl(calendarModel);
    CalendarGUIViewImpl calGuiView = new CalendarGUIViewImpl((CalendarModel) calendarModel);
    ScheduleFrameImpl scheduleFrame = new ScheduleFrameImpl(calendarModel);

    // Choose which view to use else default to MainSystemFrame
    // mainPanel, mainFrame, calGuiView, scheduleFrame
    if (args.length > 0 && args[0].equals("--use-provided-view")) {
      view = new MainFrameAdapter(mainFrame, model, strategy, listener);
      ControllerFeaturesAdapter controller =
              new ControllerFeaturesAdapter(listener, model, strategy);
      view.setListener(listener);
      mainFrame.addFeatures(controller);
      view.setVisible(true);
    } else {
      view = new MainSystemFrame(model);
    }
  }

  private static ISchedulingStrategy determineStrategy(String[] args) {
    if (args.length > 0) {
      switch (args[0].toLowerCase()) {
        case "anytime":
          return new AnytimeSchedulingStrategy();
        case "workhours":
          return new WorkHoursSchedulingStrategy();
        case "lenient":
          return new LenientSchedulingStrategy();
        default:
          System.out.println("Unknown scheduling strategy specified. Defaulting to 'anytime'.");
          return new AnytimeSchedulingStrategy();
      }
    } else {
      System.out.println("No scheduling strategy specified. Defaulting to 'anytime'.");
      return new AnytimeSchedulingStrategy();
    }
  }


}
