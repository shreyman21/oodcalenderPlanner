package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import model.PlannerSystem;
import model.ReadOnlyModel;
import model.User;

public class MainSystemFrame extends JFrame {
  private JMenuBar menuBar;
  private JMenu fileMenu;
  private JMenuItem addCalendarMenuItem;
  private JMenuItem saveCalendarsMenuItem;
  private JPanel schedulePanel;
  private JButton createEventButton;
  private JButton scheduleEventButton;
  private JComboBox<String> userComboBox;
  private ReadOnlyModel readOnlyModel = new PlannerSystem();

  private List<Event> currentEvents;

  public MainSystemFrame() {
    initializeMenu();
    initializeSchedulePanel();
    initializeButtons();
    initializeUserComboBox();
    setupFrameLayout();
    configureFrame();
  }

  private void initializeUserComboBox() {
    String[] users = readOnlyModel.getUsers().stream().map(user -> user.getName()).toArray(String[]::new);
    userComboBox = new JComboBox<>(users);
    userComboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // User selection logic
      }
    });
  }


  private void initializeMenu() {
    menuBar = new JMenuBar();
    fileMenu = new JMenu("File");

    addCalendarMenuItem = new JMenuItem("Load calendar");
    addCalendarMenuItem.addActionListener(e -> openFileChooserForLoad()); // Added action listener

    saveCalendarsMenuItem = new JMenuItem("Save calendars");
    saveCalendarsMenuItem.addActionListener(e -> openFileChooserForSave()); // Added action listener

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
    int panelHeight = schedulePanel.getHeight();
    int panelWidth = schedulePanel.getWidth();

    int hourHeight = panelHeight / 24;
    int dayWidth = panelWidth / 7;

    // Set the color for regular lines
    g2d.setColor(Color.LIGHT_GRAY);
    Stroke defaultStroke = g2d.getStroke(); // Save the default stroke

    // Draw hour lines
    for (int i = 0; i <= 24; i++) {
      if (i % 4 == 0) { // Bold line for every fourth hour
        g2d.setStroke(new BasicStroke(2)); // Use a thicker stroke
        g2d.setColor(Color.BLACK); // Set color for bold lines
      } else {
        g2d.setStroke(defaultStroke); // Revert to the default stroke
        g2d.setColor(Color.LIGHT_GRAY); // Set color for regular lines
      }
      g2d.drawLine(0, i * hourHeight, panelWidth, i * hourHeight); // Draw the line

      // Reset to default stroke and color after each line
      g2d.setStroke(defaultStroke);
      g2d.setColor(Color.LIGHT_GRAY);
    }

    // Draw vertical day lines with the default stroke
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



  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new MainSystemFrame();
      }
    });
  }
}