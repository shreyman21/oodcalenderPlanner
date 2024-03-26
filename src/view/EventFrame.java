package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

  public EventFrame() {
    eventNameField = new JTextField();
    locationField = new JTextField();
    isOnlineCheckBox = new JCheckBox("Is online");

    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    startDayComboBox = new JComboBox<>(days);
    endDayComboBox = new JComboBox<>(days);

    startTimeField = new JTextField();
    endTimeField = new JTextField();

    String[] users = {"Prof. Lucia", "Chat", "Student Anon"}; // Replace with dynamic user data
    userList = new JList<>(users);

    createButton = new JButton("Create event");
    modifyButton = new JButton("Modify event");
    removeButton = new JButton("Remove event");

    createButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Implement creation logic
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

  private JPanel createLabelAndComponent(String labelText, JComponent component) {
    JLabel label = new JLabel(labelText);
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
    panel.add(label);
    panel.add(component);
    return panel;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new EventFrame();
      }
    });
  }
}