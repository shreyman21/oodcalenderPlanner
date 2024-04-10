package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import javax.swing.*;

import model.Event;
import model.PlannerSystem;
import model.ReadOnlyModel;
import model.User;


/**
 * The main system frame for the planner.
 * This frame displays the schedule for each user in a graphical view.
 * Allows the user to select a user, create events, and schedule events.
 */
public class MainSystemFrame extends JFrame implements IPlannerView {
  private static JPanel schedulePanel;
  private JButton createEventButton;
  private JButton scheduleEventButton;
  private JComboBox<String> userComboBox;
  private ReadOnlyModel readOnlyModel;

  private List<Event> currentEvents;
  private PlannerViewListener viewListener;

  /**
   * Constructs a MainSystemFrame with the given model.
   *
   * @param model the model to use
   */
  public MainSystemFrame(ReadOnlyModel model) {
    this.readOnlyModel = model;
    initializeMenu();
    initializeSchedulePanel();
    initializeButtons();
    initializeUserComboBox();
    setupFrameLayout();
    configureFrame();
  }

  /**
   * The main method to start the application.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new MainSystemFrame(new PlannerSystem());
      }
    });
  }

  /**
   * Refreshes the schedule display.
   */
  public static void refreshScheduleDisplay() {
    schedulePanel.repaint();
  }

  private void initializeUserComboBox() {
    userComboBox = new JComboBox<>();
    List<User> users = readOnlyModel.getUsers();
    for (User user : users) {
      userComboBox.addItem(user.getName());
    }

    userComboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String selectedUser = (String) userComboBox.getSelectedItem();
        if (selectedUser != null) {
          User user = readOnlyModel.getUserByName(selectedUser);
          if (user != null) {
            currentEvents = user.getEvents();
            if (currentEvents != null) {
              schedulePanel.repaint();
            } else {
              System.out.println("The event list for the user is null.");
            }
          } else {
            System.out.println("No user found with the name: " + selectedUser);
          }
        } else {
          System.out.println("No user is selected.");
        }
      }
    });
  }

  private void initializeMenu() {
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");

    JMenuItem addCalendarMenuItem = new JMenuItem("Load calendar");
    addCalendarMenuItem.addActionListener(e -> openFileChooserForLoad());

    JMenuItem saveCalendarsMenuItem = new JMenuItem("Save calendars");
    saveCalendarsMenuItem.addActionListener(e -> openFileChooserForSave());

    fileMenu.add(addCalendarMenuItem);
    fileMenu.add(saveCalendarsMenuItem);
    menuBar.add(fileMenu);
    setJMenuBar(menuBar);
  }

  private void initializeSchedulePanel() {
    schedulePanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawScheduleGrid(g);
        if (currentEvents != null && !currentEvents.isEmpty()) {
          Graphics2D g2d = (Graphics2D) g;
          for (Event event : currentEvents) {
            System.out.println("Drawing event: " + event.getName());
            LocalDateTime startTime = event.getStartTime();
            LocalDateTime endTime = event.getEndTime();
            int dayIndex = (startTime.getDayOfWeek().getValue() % 7);

            int startX = dayIndex * getWidth() / 7;
            int startY = startTime.getHour() * getHeight() / 24 +
                    (startTime.getMinute() * getHeight() / 24 / 60);
            int endY = endTime.getHour() * getHeight() / 24 +
                    (endTime.getMinute() * getHeight() / 24 / 60);
            endY = Math.min(endY, getHeight());

            int height = endY - startY;
            g2d.setColor(Color.RED);
            g2d.fillRect(startX, startY, getWidth() / 7, height);
          }
        }
      }

    };

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int dayWidth = schedulePanel.getWidth() / 7;
        int hourHeight = schedulePanel.getHeight() / 24;
        int dayIndex = e.getX() / dayWidth;
        int hourIndex = e.getY() / hourHeight;
        DayOfWeek day = DayOfWeek.of((dayIndex + 1) % 7 + 1);
        LocalTime time = LocalTime.of(hourIndex, 0);
        LocalDate date = LocalDate.now().with(TemporalAdjusters.nextOrSame(day));
        LocalDateTime startDateTime = LocalDateTime.of(date, time);
        EventFrame eventFrame = new EventFrame(readOnlyModel);
        eventFrame.setStartTime(startDateTime);
        eventFrame.setVisible(true);
      }
    }
    );


    schedulePanel.setPreferredSize(new Dimension(800, 600));

    schedulePanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        // Calculate which tile was clicked
        int dayWidth = schedulePanel.getWidth() / 7;
        int hourHeight = schedulePanel.getHeight() / 24;
        int dayIndex = e.getX() / dayWidth;
        int hourIndex = e.getY() / hourHeight;

        DayOfWeek day = DayOfWeek.of((dayIndex + 1) % 7 + 1);
        LocalTime time = LocalTime.of(hourIndex, 0);
        LocalDate date = LocalDate.now().with(TemporalAdjusters.nextOrSame(day));
        LocalDateTime startDateTime = LocalDateTime.of(date, time);
        EventFrame eventFrame = new EventFrame(readOnlyModel);
        eventFrame.setStartTime(startDateTime);
        eventFrame.setVisible(true);
      }
    });


    schedulePanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int clickedX = e.getX();
        int clickedY = e.getY();

        int dayWidth = getWidth() / 7;
        int hourHeight = getHeight() / 24;

        int clickedDayIndex = clickedX / dayWidth + 1;
        LocalTime timeClicked = LocalTime.of(clickedY / hourHeight, 0);

        for (Event event : currentEvents) {
          if (eventOccursDuringClick(event, clickedDayIndex, timeClicked)) {
            EventFrame eventFrame = new EventFrame(readOnlyModel);
            eventFrame.populateEventDetails(event);
            eventFrame.setVisible(true);
            break;
          }
        }
      }

      private boolean eventOccursDuringClick(Event event, int clickedDayIndex,
                                             LocalTime timeClicked) {
        DayOfWeek dayOfWeekEventStart = event.getStartTime().getDayOfWeek();
        DayOfWeek dayOfWeekEventEnd = event.getEndTime().getDayOfWeek();
        DayOfWeek dayClicked = DayOfWeek.of(clickedDayIndex);

        boolean isSameDay = (dayOfWeekEventStart.equals(dayClicked) || dayOfWeekEventEnd.
                equals(dayClicked));
        boolean isSameTime = (timeClicked.equals(event.getStartTime().toLocalTime())
                || timeClicked.isAfter(event.getStartTime().toLocalTime()))
                && (timeClicked.isBefore(event.getEndTime().toLocalTime()));

        return isSameDay && isSameTime;
      }
    });


  }

  private void initializeButtons() {
    createEventButton = new JButton("Create event");
    scheduleEventButton = new JButton("Schedule event");

    createEventButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        EventFrame eventFrame = new EventFrame(readOnlyModel);
        eventFrame.setVisible(true);
      }
    });
    scheduleEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // opens the SchedulingFrame
        SchedulingFrame schedulingFrame = new SchedulingFrame();
        schedulingFrame.setVisible(true);

      }
    });
  }

  private void setupFrameLayout() {
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    topPanel.add(new JLabel("Select User:"));
    topPanel.add(userComboBox);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(createEventButton);
    buttonPanel.add(scheduleEventButton);

    setLayout(new BorderLayout());
    add(topPanel, BorderLayout.NORTH);
    add(schedulePanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  private void configureFrame() {
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  private void drawScheduleGrid(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    int panelHeight = schedulePanel.getHeight();
    int panelWidth = schedulePanel.getWidth();

    int hourHeight = panelHeight / 24;
    int dayWidth = panelWidth / 7;


    g2d.setColor(Color.LIGHT_GRAY);
    Stroke defaultStroke = g2d.getStroke();

    // Draw hour lines
    for (int i = 0; i <= 24; i++) {
      if (i % 4 == 0) { // Bold line for every fourth hour
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);
      } else {
        g2d.setStroke(defaultStroke);
        g2d.setColor(Color.LIGHT_GRAY);
      }
      g2d.drawLine(0, i * hourHeight, panelWidth, i * hourHeight);
      g2d.setStroke(defaultStroke);
      g2d.setColor(Color.LIGHT_GRAY);
    }

    for (int i = 0; i <= 7; i++) {
      g2d.drawLine(i * dayWidth, 0, i * dayWidth, panelHeight);
    }
  }

  private void openFileChooserForLoad() {
    JFileChooser fileChooser = new JFileChooser();
    int option = fileChooser.showOpenDialog(this);
    if (option == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      System.out.println("Load XML file: " + selectedFile.getAbsolutePath());

      PlannerSystem plannerSystem = new PlannerSystem();
      User currentUser = new User("1", "Host");
      boolean isUploaded = plannerSystem.uploadSchedule(selectedFile.getAbsolutePath(),
              currentUser);

      for (Event event : currentUser.getSchedule().getEvents()) {
        System.out.println(event.getName());
      }
      if (isUploaded) {
        // Schedule uploaded successfully, now update the view
        currentEvents = currentUser.getSchedule().getEvents(); // Get the updated events
        schedulePanel.repaint();
      } else {

        System.out.println("Failed to upload schedule from XML.");
      }
    }
  }

  private void openFileChooserForSave() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int option = fileChooser.showSaveDialog(this);
    if (option == JFileChooser.APPROVE_OPTION) {
      File selectedDirectory = fileChooser.getSelectedFile();
      System.out.println("Save to directory: " + selectedDirectory.getAbsolutePath());
    }
  }

  @Override
  public void updateSchedule(List<Event> events) {
    currentEvents = events;
    schedulePanel.repaint();
  }

  @Override
  public void setListener(PlannerViewListener listener) {
    this.viewListener = listener;
  }

  @Override
  public void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }
}