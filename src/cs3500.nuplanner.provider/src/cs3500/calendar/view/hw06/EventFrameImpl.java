package cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;

import cs3500.nuplanner.provider.src.cs3500.calendar.controller.hw07.ControllerFeatures;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.EventModel;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.ReadonlyCalendarModel;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.ScheduleModel;


/**
 * Represents the frame that displays an event. Allows the user to modify or remove the
 * event. Also displays the event's title, location, start and end times, and available
 * users. Implements the EventFrame interface.
 */
public class EventFrameImpl extends JFrame implements EventFrame {

  private final JPanel eventPanel = new JPanel();

  private final String title;
  private final String location;
  private final boolean online;
  private final String startDay;
  private final String startTime;
  private final String endDay;
  private final String endTime;
  private final String[] availableUsers;
  private EventModel event;

  private JTextField titleField;
  private JTextField locationField;
  private JComboBox<String> onlineComboBox;
  private JTextField startTimeField;
  private JComboBox<String> startDayComboBox;
  private JTextField endTimeField;
  private JComboBox<String> endDayComboBox;
  private JList<String> userList;

  private JButton modifyButton;
  private JButton removeButton;
  private JButton createButton;


  /**
   * Constructs an EventFrameImpl object with default values of nothing or false.
   */
  public EventFrameImpl(ReadonlyCalendarModel<ScheduleModel> model) {
    this.setTitle("Event");
    this.eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
    this.title = "";
    this.location = "";
    this.online = false;
    this.startDay = "";
    this.startTime = "";
    this.endDay = "";
    this.endTime = "";
    this.availableUsers = model.getUsers();
    this.addSections();
  }

  /**
   * Constructs an EventFrameImpl object with the given event model.
   * Sets all fields to the values of the event model.
   * @param e the event model
   */
  public EventFrameImpl(EventModel e, ReadonlyCalendarModel model) {
    this.setTitle("Event");
    this.eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
    //use observe methods on e to update the view with the current event information
    this.title = e.observeTitle();
    this.location = e.observeLocation();
    this.online = e.observeIsOnline();
    this.startDay = e.getStartingDay().toString();
    this.startTime = e.observeStartTime();
    this.endDay = e.getEndingDay().toString();
    this.endTime = e.observeEndTime();
    this.availableUsers = model.getUsers();
    this.event = e;
    this.addSections();
  }

  @Override
  public void addFeatures(ControllerFeatures features, MainFrame main) {
    EventFrameImpl thisFrame = this;
    this.modifyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        HashMap<String, String> eventInfo = new HashMap<>();
        eventInfo.put("title", titleField.getText());
        eventInfo.put("location", locationField.getText());
        eventInfo.put("isOnline", (onlineComboBox.getSelectedItem()).toString());
        eventInfo.put("startingDay", (startDayComboBox.getSelectedItem()).toString());
        eventInfo.put("startingTime", startTimeField.getText());
        eventInfo.put("endingDay", (endDayComboBox.getSelectedItem()).toString());
        eventInfo.put("endingTime", endTimeField.getText());
        eventInfo.put("invitedUIDs", userList.getSelectedValuesList().toString());
        try {
          if (event != null) {
            features.modifyEvent(event, eventInfo);
            main.updateSchedule();
          }
          thisFrame.dispose();
          main.updateSchedule();
        } catch (Exception ex) {
          displayErrorFrame(ex.getMessage());
        }
      }
    });

    this.removeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          if (event != null) {
            features.removeEvent(event);
            main.updateSchedule();
          }
          thisFrame.dispose();
          main.updateSchedule();
        } catch (Exception ex) {
          displayErrorFrame(ex.getMessage());
        }
      }
    });

    this.createButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        HashMap<String, String> eventInfo = new HashMap<>();
        eventInfo.put("title", titleField.getText());
        eventInfo.put("location", locationField.getText());
        eventInfo.put("isOnline", onlineComboBox.getSelectedItem().toString());
        eventInfo.put("startingDay", startDayComboBox.getSelectedItem().toString());
        eventInfo.put("startingTime", startTimeField.getText());
        eventInfo.put("endingDay", endDayComboBox.getSelectedItem().toString());
        eventInfo.put("endingTime", endTimeField.getText());
        eventInfo.put("invitedUIDs", userList.getSelectedValuesList().toString());
        try {
          features.createEvent(eventInfo);
          thisFrame.dispose();
          main.updateSchedule();
        } catch (Exception ex) {
          displayErrorFrame(ex.getMessage());
        }
      }
    });
  }

  @Override
  public void makeVisible() {
    this.pack();
    this.setLocationRelativeTo(null); // Center the frame on the screen
    this.setVisible(true);
  }

  // Add all sections to the event panel
  private void addSections() {
    this.eventPanel.add(this.titleSection());
    this.eventPanel.add(this.locationSection());
    this.eventPanel.add(this.start());
    this.eventPanel.add(this.end());
    this.eventPanel.add(this.availableUsers());
    this.eventPanel.add(this.bottomPanel());
    this.add(new JScrollPane(this.eventPanel));
  }

  private void displayErrorFrame(String errorMessage) {
    JFrame errorFrame = new JFrame();
    JPanel errorPanel = new JPanel();
    errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));
    errorPanel.setPreferredSize(new Dimension(300, 150));
    JLabel errorLabel = new JLabel("Error: " + errorMessage);
    errorPanel.add(errorLabel, BorderLayout.NORTH);
    errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    errorLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
    JButton okButton = new JButton("OK");
    okButton.setPreferredSize(new Dimension(100, 50));
    okButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorFrame.dispose();
      }
    });
    errorPanel.add(okButton, BorderLayout.SOUTH);
    okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    okButton.setAlignmentY(Component.CENTER_ALIGNMENT);
    errorFrame.add(errorPanel);
    errorFrame.pack();
    errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    errorFrame.setVisible(true);  // Make the frame visible
  }

  // Create the title section of the event panel
  private JPanel titleSection() {
    JPanel title = new JPanel();
    title.setLayout(new BoxLayout(title, BoxLayout.Y_AXIS));

    JLabel titleLabel = new JLabel("Event name:");
    title.add(titleLabel);

    JTextField titleField = new JTextField(this.title);
    this.titleField = titleField;
    title.add(titleField);

    return title;
  }

  // Create the location section of the event panel
  private JPanel locationSection() {
    JPanel locationPanel = new JPanel();
    locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.Y_AXIS));

    JLabel locationLabel = new JLabel("Location:");
    locationPanel.add(locationLabel);

    JPanel comboBoxPanel = new JPanel();
    comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.X_AXIS));

    String[] onlineOptions = {"Online", "Not Online"};
    JComboBox<String> onlineComboBox = new JComboBox<>(onlineOptions);
    onlineComboBox.setSelectedItem(online ? "Online" : "Not Online");
    this.onlineComboBox = onlineComboBox;
    comboBoxPanel.add(onlineComboBox);

    JTextField locationTextField = new JTextField(this.location);
    this.locationField = locationTextField;
    comboBoxPanel.add(locationTextField);

    locationPanel.add(comboBoxPanel);

    return locationPanel;
  }

  // Create the start section of the event panel
  private JPanel start() {
    JPanel start = new JPanel();
    start.setLayout(new BoxLayout(start, BoxLayout.Y_AXIS));

    JLabel startDayLabel = new JLabel("Starting Day:");
    start.add(startDayLabel);

    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    JComboBox<String> dayComboBox = new JComboBox<>(days);
    this.startDayComboBox = dayComboBox;
    dayComboBox.setSelectedItem(this.startDay);
    start.add(dayComboBox);

    JLabel startTimeLabel = new JLabel("Starting Time:");
    start.add(startTimeLabel);

    JTextField startTimeTextField = new JTextField(this.startTime);
    this.startTimeField = startTimeTextField;
    start.add(startTimeTextField);

    return start;
  }

  // Create the end section of the event panel
  private JPanel end() {
    JPanel end = new JPanel();
    end.setLayout(new BoxLayout(end, BoxLayout.Y_AXIS));

    JLabel endDayLabel = new JLabel("Ending Day:");
    end.add(endDayLabel);

    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    JComboBox<String> dayComboBox = new JComboBox<>(days);
    dayComboBox.setSelectedItem(this.endDay);
    this.endDayComboBox = dayComboBox;
    end.add(dayComboBox);

    JLabel endTimeLabel = new JLabel("Ending Time:");
    end.add(endTimeLabel);

    JTextField endTimeTextField = new JTextField(this.endTime);
    this.endTimeField = endTimeTextField;
    end.add(endTimeTextField);

    return end;
  }

  // Create the available users section of the event panel
  private JPanel availableUsers() {
    JPanel userListPanel = new JPanel();
    userListPanel.setLayout(new BorderLayout());

    JLabel userListLabel = new JLabel("Available Users:");
    userListPanel.add(userListLabel, BorderLayout.NORTH);

    String[] users = this.availableUsers;
    JList<String> userList = new JList<>(users);
    JScrollPane scrollPane = new JScrollPane(userList);

    this.userList = userList;
    userListPanel.add(scrollPane, BorderLayout.CENTER);

    return userListPanel;
  }

  // Create the bottom panel of the event panel
  private JPanel bottomPanel() {
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

    JButton modifyButton = new JButton("Modify event");
    JButton removeButton = new JButton("Remove event");
    JButton createButton = new JButton("Create event");

    this.modifyButton = modifyButton;
    this.removeButton = removeButton;
    this.createButton = createButton;

    bottomPanel.add(Box.createHorizontalGlue()); // Add glue to center buttons
    bottomPanel.add(modifyButton);
    bottomPanel.add(removeButton);
    bottomPanel.add(createButton);
    bottomPanel.add(Box.createHorizontalGlue()); // Add glue to center buttons

    return bottomPanel;
  }
}