package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;


import model.AnytimeSchedulingStrategy;
import model.Event;
import model.PlannerSystem;
import model.User;

public class SchedulingFrame extends JFrame {
    private JTextField eventNameField;
    private JTextField locationField;
    private JCheckBox onlineCheckBox;
    private JTextField durationField;
    private JComboBox<String> userComboBox;
    private JButton scheduleButton;

    private PlannerSystem model;

    private AnytimeSchedulingStrategy strat;

    public SchedulingFrame() {
        createUI();
        this.model = new PlannerSystem();
        this.strat = new AnytimeSchedulingStrategy();
    }

    private void createUI() {
        setTitle("Schedule Event");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        eventNameField = new JTextField();
        locationField = new JTextField();
        onlineCheckBox = new JCheckBox("Not online");
        durationField = new JTextField();
        userComboBox = new JComboBox<>(new String[]{"Prof. Lucia", "Jane", "Student Anon"});
        scheduleButton = new JButton("Schedule event");

        mainPanel.add(new JLabel("Event name:"));
        mainPanel.add(eventNameField);
        mainPanel.add(new JLabel("Location:"));
        mainPanel.add(locationField);
        mainPanel.add(onlineCheckBox);
        mainPanel.add(new JLabel("Duration in minutes:"));
        mainPanel.add(durationField);
        mainPanel.add(new JLabel("Available users"));
        mainPanel.add(userComboBox);

        scheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String eventName = eventNameField.getText();
                String location = locationField.getText();
                boolean online = onlineCheckBox.isSelected();
                int duration = Integer.parseInt(durationField.getText());
                String selectedUserName = (String) userComboBox.getSelectedItem();
                User user = model.getUserByName(selectedUserName);

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime nextOrSameThursday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
                LocalDateTime startSearch = nextOrSameThursday.withHour(0).withMinute(0);
                LocalDateTime endSearch = startSearch.plusDays(6);
                Event event = new Event(eventName, startSearch, endSearch, location, online,new ArrayList<>());

                // Anytime Scheduling strategy
                strat.scheduleEvent(event, user, model);
                JOptionPane.showMessageDialog(SchedulingFrame.this,
                        "Event scheduled for " + event.getStart());
            }
        });

        add(mainPanel, BorderLayout.CENTER);
        add(scheduleButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SchedulingFrame sf = new SchedulingFrame();
                sf.setVisible(true);
            }
        });
    }
}
