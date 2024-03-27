package view;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;


import javax.swing.*;

import model.PlannerSystem;
import model.ReadOnlyModel;
import model.User;

public class EventFrame extends JFrame {
  private JTextField eventNameField;
  private JTextField locationField;
  private JCheckBox isOnlineCheckBox;
  private JComboBox<String> startDayComboBox;
  private JComboBox<String> endDayComboBox;
  private JTextField startTimeField;
  private JTextField endTimeField;
  private JList<String> userList;
  private JButton createButton;
  private JButton modifyButton;
  private JButton removeButton;
  private ReadOnlyModel readOnlyModel;
  private JComboBox<String> userComboBox;

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

    createButton = new JButton("Create event");
    modifyButton = new JButton("Modify event");
    removeButton = new JButton("Remove event");
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
        // Assume userComboBox has been initialized and populated with users
        String selectedUser = (String) userComboBox.getSelectedItem();

        if (eventName.isEmpty() || location.isEmpty() || startDay == null || startTime.isEmpty()
                || endDay == null || endTime.isEmpty() || selectedUser == null) {
          System.out.println("Error: Missing information for creating event.");
        } else {
          System.out.println("Create event: " + eventName + ", " + location + ", " + startDay
                  + ", " + startTime + ", " + endDay + ", " + endTime + ", Online: " + isOnline);
        }
      }
    });

    modifyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Implement modification logic
      }
    });

    removeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Implement removal logic
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

  private void initializeFileMenu() {
    JMenu fileMenu = new JMenu("File");

    JMenuItem loadXmlMenuItem = new JMenuItem("Load XML");
    loadXmlMenuItem.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int option = fileChooser.showOpenDialog(this);
      if (option == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        System.out.println("XML file chosen: " + selectedFile.getAbsolutePath());
        // Here you would actually load the XML file, but for this assignment just print the path
      }
    });
    fileMenu.add(loadXmlMenuItem);

    JMenuItem saveCalendarsMenuItem = new JMenuItem("Save Calendars");
    // Add action listener to saveCalendarsMenuItem if needed

    fileMenu.add(saveCalendarsMenuItem);
    JMenuBar menuBar = new JMenuBar();
    menuBar.add(fileMenu);
    setJMenuBar(menuBar);
  }

  public void setStartTime(LocalDateTime startTime) {
    startDayComboBox.setSelectedItem(startTime.getDayOfWeek().toString());
    startTimeField.setText(startTime.toLocalTime().toString());
  }


  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        ReadOnlyModel model = new PlannerSystem();
        new EventFrame(model);
      }
    });
  }
}