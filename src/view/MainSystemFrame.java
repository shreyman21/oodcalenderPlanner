package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainSystemFrame extends JFrame {
  private JMenuBar menuBar;
  private JMenu fileMenu;
  private JMenuItem addCalendarMenuItem;
  private JMenuItem saveCalendarsMenuItem;
  private JPanel schedulePanel;
  private JButton createEventButton;
  private JButton scheduleEventButton;
  private JComboBox<String> userComboBox;

  public MainSystemFrame() {
    initializeMenu();
    initializeSchedulePanel();
    initializeButtons();
    setupFrameLayout();
    configureFrame();
  }

  private void initializeMenu() {
    menuBar = new JMenuBar();
    fileMenu = new JMenu("File");
    addCalendarMenuItem = new JMenuItem("Add calendar");
    saveCalendarsMenuItem = new JMenuItem("Save calendars");
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
        // set sechedule grid to size of panel

      }
    };
    schedulePanel.setPreferredSize(new Dimension(800, 600));
  }

  private void initializeButtons() {
    createEventButton = new JButton("Create event");
    scheduleEventButton = new JButton("Schedule event");
    // Add action listeners to buttons
    createEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Create event logic
      }
    });
    scheduleEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Schedule event logic
      }
    });
  }

  private void setupFrameLayout() {
    // Main layout setup
    setLayout(new BorderLayout());
    add(schedulePanel, BorderLayout.CENTER);
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(createEventButton);
    buttonPanel.add(scheduleEventButton);
    add(buttonPanel, BorderLayout.SOUTH);
  }



  private void configureFrame() {
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  private void drawScheduleGrid(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);

    // Assuming 24 hour-long grid, 7 days a week
    int panelHeight = schedulePanel.getHeight();
    int panelWidth = schedulePanel.getWidth();
    int hourHeight = panelHeight / 24; // for 24 hours
    int dayWidth = panelWidth / 7; // for 7 days

    // Draw hour lines
    for (int i = 0; i <= 24; i++) {
      g2d.drawLine(0, i * hourHeight, panelWidth, i * hourHeight);
    }
    // Draw day lines
    for (int i = 0; i <= 7; i++) {
      g2d.drawLine(i * dayWidth, 0, i * dayWidth, panelHeight);
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new MainSystemFrame();
      }
    });
  }
}