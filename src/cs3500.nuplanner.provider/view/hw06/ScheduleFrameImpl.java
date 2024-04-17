package cs3500.calendar.view.hw06;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import cs3500.calendar.controller.hw07.ControllerFeatures;
import cs3500.calendar.model.hw05.ReadonlyCalendarModel;
import cs3500.calendar.model.hw05.ScheduleModel;

/**
 * Represents the frame for scheduling an event.
 */
public class ScheduleFrameImpl extends JFrame implements ScheduleFrame {
  private final JPanel eventPanel = new JPanel();

  private final String title;
  private final String location;
  private final boolean online;
  private final String[] availableUsers;

  private JTextField titleField;
  private JTextField locationField;
  private JTextField durationField;
  private JComboBox<String> onlineComboBox;
  private JList<String> userList;
  private JButton scheduleButton;


  /**
   * Constructs an EventFrameImpl object with default values of nothing or false.
   */
  public ScheduleFrameImpl(ReadonlyCalendarModel<ScheduleModel> model) {
    this.setTitle("Event");
    this.eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
    this.title = "";
    this.location = "";
    this.online = false;
    this.availableUsers = model.getUsers();
    this.addSections();
  }

  @Override
  public void addFeatures(ControllerFeatures features, MainFrame main) {
    ScheduleFrameImpl thisFrame = this;
    this.scheduleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        HashMap<String, String> eventInfo = new HashMap<>();
        eventInfo.put("title", titleField.getText());
        eventInfo.put("location", locationField.getText());
        eventInfo.put("isOnline", onlineComboBox.getSelectedItem().toString());
        eventInfo.put("duration", durationField.getText());
        eventInfo.put("invitedUIDs", userList.getSelectedValuesList().toString());
        try {
          features.scheduleEvent(eventInfo);
          thisFrame.dispose();
          main.updateSchedule();
        } catch (Exception ex) {
          JFrame errorFrame = new JFrame();
          JPanel errorPanel = new JPanel();
          errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));
          errorPanel.setPreferredSize(new Dimension(300, 150));
          JLabel errorLabel = new JLabel("Error: " + ex.getMessage());
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
    this.eventPanel.add(this.duration());
    this.eventPanel.add(this.availableUsers());
    this.eventPanel.add(this.bottomPanel());
    this.add(new JScrollPane(this.eventPanel));
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

  // Create the duration section of the event panel
  private JPanel duration() {
    JPanel duration = new JPanel();
    duration.setLayout(new BoxLayout(duration, BoxLayout.Y_AXIS));

    JLabel titleLabel = new JLabel("Duration in minutes:");
    duration.add(titleLabel);

    JTextField durationField = new JTextField(this.title);
    durationField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c)) {
            e.consume();  // ignore non-digits
        }
      }
    });
    this.durationField = durationField;
    duration.add(durationField);

    return duration;
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

    JButton scheduleButton = new JButton("Schedule event");

    this.scheduleButton = scheduleButton;

    bottomPanel.add(Box.createHorizontalGlue()); // Add glue to center buttons
    bottomPanel.add(scheduleButton);
    bottomPanel.add(Box.createHorizontalGlue()); // Add glue to center buttons

    return bottomPanel;
  }
}
