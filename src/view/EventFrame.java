package view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.*;

import model.Event;
import model.PlannerSystem;
import model.ReadOnlyModel;
import model.User;

/**
 * This class represents a frame for creating, modifying, and removing events.
 * This frame is used by the user to interact with the system.
 * The user can create, modify, and remove events using this frame.
 * This frame is used by the user to interact with the system.
 */
public class EventFrame extends JFrame {
  private JTextField eventNameField;
  private JTextField locationField;
  private JCheckBox isOnlineCheckBox;
  private JComboBox<String> startDayComboBox;
  private JComboBox<String> endDayComboBox;
  private JTextField startTimeField;
  private JTextField endTimeField;
  private JList<String> userList;
  private ReadOnlyModel readOnlyModel;
  private JComboBox<String> userComboBox;

  /**
   * Constructs an EventFrame with the given model.
   * This frame allows the user to create, modify, and remove events.
   *
   * @param model the model to use
   */
  public EventFrame(ReadOnlyModel model) {
    this.readOnlyModel = model;

    eventNameField = new JTextField();
    locationField = new JTextField();
    isOnlineCheckBox = new JCheckBox("Is online");

    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    startDayComboBox = new JComboBox<>(days);
    endDayComboBox = new JComboBox<>(days);

    startTimeField = new JTextField();
    endTimeField = new JTextField();

    String[] users = readOnlyModel.getUsers().stream().map(User::getName).toArray(String[]::new);
    userList = new JList<>(users);

    JButton createButton = new JButton("Create event");
    JButton modifyButton = new JButton("Modify event");
    JButton removeButton = new JButton("Remove event");
    initializeUserComboBox();


    createButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String eventName = eventNameField.getText();
        String location = locationField.getText();
        boolean isOnline = isOnlineCheckBox.isSelected();
        String startDay = (String) startDayComboBox.getSelectedItem();
        String startTime = startTimeField.getText();
        String endDay = (String) endDayComboBox.getSelectedItem();
        String endTime = endTimeField.getText();
        String selectedUser = (String) userComboBox.getSelectedItem();

        if (eventName.isEmpty() || location.isEmpty() || startDay == null || startTime.isEmpty()
                || endDay == null || endTime.isEmpty() || selectedUser == null) {
          System.out.println("Error: Missing information for creating event.");
        } else {
          // Parse the date and time from the input
          DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH);
          DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("Hmm");

          try {
            DayOfWeek startDayOfWeek = DayOfWeek.from(dateFormatter.parse(startDay));
            DayOfWeek endDayOfWeek = DayOfWeek.from(dateFormatter.parse(endDay));

            LocalTime startLocalTime = LocalTime.parse(startTime, timeFormatter);
            LocalTime endLocalTime = LocalTime.parse(endTime, timeFormatter);

            // Assume events happen within the next seven days from now
            LocalDate today = LocalDate.now();
            LocalDate startLocalDate = today.with(TemporalAdjusters.nextOrSame(startDayOfWeek));
            LocalDate endLocalDate = today.with(TemporalAdjusters.nextOrSame(endDayOfWeek));

            LocalDateTime startDateTime = LocalDateTime.of(startLocalDate, startLocalTime);
            LocalDateTime endDateTime = LocalDateTime.of(endLocalDate, endLocalTime);

            Event event = new Event(eventName, startDateTime,
                    endDateTime, location, isOnline, new ArrayList<>());
            readOnlyModel.addEventToUserSchedule(selectedUser, event);

            MainSystemFrame.refreshScheduleDisplay();

            System.out.println("Event created: " + event);

          } catch (DateTimeParseException ex) {
            System.out.println("Error parsing date or time: " + ex.getMessage());
          }
        }
      }
    });

    modifyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String selectedUser = (String) userComboBox.getSelectedItem();
        if (selectedUser == null) {
          System.out.println("Error: No user selected.");
        } else {
          User user = readOnlyModel.getUserByName(selectedUser);
          Event event = user.getSchedule().getEvents().get(userList.getSelectedIndex());
          Event modifiedEvent = new Event(eventNameField.getText(), LocalDateTime.parse(startTimeField.getText()), LocalDateTime.parse(endTimeField.getText()), locationField.getText(), isOnlineCheckBox.isSelected(), new ArrayList<>());
          //readOnlyModel.modifyEvent(user, event, modifiedEvent);
          MainSystemFrame.refreshScheduleDisplay();
          System.out.println("Event modified: " + modifiedEvent);
        }
      }
    });

    removeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String selectedUser = (String) userComboBox.getSelectedItem();
        if (selectedUser == null) {
          System.out.println("Error: No user selected.");
        } else {
          User user = readOnlyModel.getUserByName(selectedUser);
          Event event = user.getSchedule().getEvents().get(userList.getSelectedIndex());
          user.getSchedule().getEvents().remove(event);
          MainSystemFrame.refreshScheduleDisplay();
          System.out.println("Event removed: " + event);

        }
      }
    });

    // Layout setup using BoxLayout for simplicity
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

    add(createLabelAndComponent("Event name:", eventNameField));
    add(createLabelAndComponent("Location:", locationField));
    add(isOnlineCheckBox);
    add(createLabelAndComponent("Starting Day:", startDayComboBox));
    add(createLabelAndComponent("Starting time:", startTimeField));
    add(createLabelAndComponent("Ending Day:", endDayComboBox));
    add(createLabelAndComponent("Ending time:", endTimeField));
    add(new JScrollPane(userList));
    add(createButton);
    add(modifyButton);
    add(removeButton);

    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  /**
   * Main method to run the EventFrame.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        ReadOnlyModel model = new PlannerSystem();
        new EventFrame(model);
      }
    });
  }

  private void initializeUserComboBox() {
    DefaultComboBoxModel<String> userModel = new DefaultComboBoxModel<>();
    for (User user : readOnlyModel.getUsers()) { // Assuming getUsers() returns a collection of User objects
      userModel.addElement(user.getName()); // Assuming User has a getName() method
    }
    userComboBox = new JComboBox<>(userModel); // Populate the combo box with user names
  }

  private JPanel createLabelAndComponent(String labelText, JComponent component) {
    JLabel label = new JLabel(labelText);
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
    panel.add(label);
    panel.add(component);
    return panel;
  }

  /**
   * Set the start time of the event.
   *
   * @param startTime the start time of the event
   */
  public void setStartTime(LocalDateTime startTime) {
    startDayComboBox.setSelectedItem(startTime.getDayOfWeek().toString());
    startTimeField.setText(startTime.toLocalTime().toString());
  }

  /**
   * Populate the event details in the frame.
   * This method is called when the user selects an event from the list.
   *
   * @param event the event to populate the details for
   */
  public void populateEventDetails(Event event) {
    eventNameField.setText(event.getName());
    locationField.setText(event.getLocation());
    isOnlineCheckBox.setSelected(event.isOnline());
    startDayComboBox.setSelectedItem(event.getStartTime().getDayOfWeek().toString());
    startTimeField.setText(event.getStartTime().toLocalTime().toString());
    endDayComboBox.setSelectedItem(event.getEndTime().getDayOfWeek().toString());
    endTimeField.setText(event.getEndTime().toLocalTime().toString());
  }
}