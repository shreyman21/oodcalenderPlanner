package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchedulingFrame extends JFrame {
    private JTextField eventNameField;
    private JTextField locationField;
    private JCheckBox onlineCheckBox;
    private JTextField durationField;
    private JComboBox<String> userComboBox;
    private JButton scheduleButton;

    public SchedulingFrame() {
        createUI();
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
        userComboBox = new JComboBox<>(new String[]{"Prof. Lucia", "Chat", "Student Anon"});
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

                if (eventName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Event name cannot be empty");
                    return;
                }
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
