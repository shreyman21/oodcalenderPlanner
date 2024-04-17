package cs3500.calendar.view.hw06;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

import cs3500.calendar.model.hw05.DayOfWeek;
import cs3500.calendar.model.hw05.EventModel;
import cs3500.calendar.model.hw05.ReadonlyCalendarModel;
import cs3500.calendar.controller.hw07.ControllerFeatures;
import cs3500.calendar.model.hw05.ScheduleModel;

/**
 * Represents the main frame of the calendar GUI.
 * Includes the main panel and the menu bar. Also includes the bottom panel.
 */
public class MainFrameImpl extends JFrame implements MainFrame {

  private MainPanelImpl main;
  private ReadonlyCalendarModel<ScheduleModel> model;
  private JComboBox<String> userComboBox;
  private JButton createEventButton;
  private JButton scheduleEventButton;
  private JMenuItem loadMenuItem;
  private JMenuItem saveMenuItem;

  /**
   * Constructs a MainFrameImpl object.
   * Initializes the main panel, menu bar, and the bottom panel.
   * @param calendar the calendar model
   */
  MainFrameImpl(ReadonlyCalendarModel<ScheduleModel> calendar) {
    super();
    this.setPreferredSize(new Dimension(800, 800));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    MainPanelImpl mainPanel = new MainPanelImpl(calendar);
    this.main = mainPanel;
    this.model = calendar;

    this.setJMenuBar(this.menuBar());
    this.add(mainPanel, BorderLayout.CENTER);
    this.add(this.bottomPanel(), BorderLayout.SOUTH);
    this.pack();
  }

  @Override
  public void addFeatures(ControllerFeatures features) {
    String user = (String) userComboBox.getSelectedItem();
    MainFrameImpl thisFrame = this;
    userComboBox.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
        String curUser = (String) comboBox.getSelectedItem();
        updateSchedule();
      }
    });

    createEventButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        EventFrameImpl eventFrame = new EventFrameImpl(model);
        eventFrame.addFeatures(features, thisFrame);
        eventFrame.makeVisible();
      }
    });

    scheduleEventButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ScheduleFrameImpl scheduleFrame = new ScheduleFrameImpl(model);
        scheduleFrame.addFeatures(features, thisFrame);
        scheduleFrame.makeVisible();
      }
    });

    saveMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showSaveDialog(main);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          try {
            features.saveAllXML(selectedFile.getAbsolutePath());
          } catch (Exception ex) {
            displayErrorFrame(ex.getMessage());
          }
        }
      }
    });

    loadMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(main);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          try {
            features.loadXML(selectedFile.getAbsolutePath());
            int itemCount = userComboBox.getItemCount();
            for (String user : model.getUsers()) {
              boolean boxContainsUser = false;
              for (int i = 0; i < itemCount; i++) {
                if (userComboBox.getItemAt(i).equals(user)) {
                  boxContainsUser = true;
                }
              }
              if (!boxContainsUser) {
                userComboBox.addItem(user);
              }
            }
          } catch (Exception ex) {
            displayErrorFrame(ex.getMessage());
          }
        }
        updateSchedule();
      }
    });

    main.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        DayOfWeek day = DayOfWeek.fromInt(mouseX / (getWidth() / 7));
        String time = String.format("%02d%02d", mouseY * 24 / main.getHeight(),
                mouseY * 60 / main.getHeight() % 60);
        for (EventModel event : model.getSchedule((String) userComboBox.getSelectedItem())
                .getEvents()) {
          if (event.isOverlapping(day, time)) {
            EventFrameImpl eventFrame = new EventFrameImpl(event, model);
            eventFrame.addFeatures(features, thisFrame);
            eventFrame.makeVisible();
            break;
          }
        }
      }
    });
  }

  @Override
  public void updateSchedule() {
    String user = (String) userComboBox.getSelectedItem();
    main.updateSchedule(model, user);
  }

  @Override
  public void makeVisible() {
    setVisible(true);
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

  private JPanel bottomPanel() {
    JPanel bottomPanel = new JPanel(new GridBagLayout());

    JComboBox<String> userComboBox = new JComboBox<>(model.getUsers());
    JButton createEventButton = new JButton("Create Event");
    JButton scheduleEventButton = new JButton("Schedule Event");

    this.userComboBox = userComboBox;
    this.createEventButton = createEventButton;
    this.scheduleEventButton = scheduleEventButton;

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.weightx = 1.0;
    bottomPanel.add(userComboBox, constraints);
    bottomPanel.add(createEventButton, constraints);
    bottomPanel.add(scheduleEventButton, constraints);

    return bottomPanel;
  }

  private JMenuBar menuBar() {
    JMenuBar mBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem saveMenuItem = new JMenuItem("Save Calendar");
    JMenuItem loadMenuItem = new JMenuItem("Load Calendar");

    fileMenu.add(saveMenuItem);
    fileMenu.add(loadMenuItem);
    mBar.add(fileMenu);

    this.saveMenuItem = saveMenuItem;
    this.loadMenuItem = loadMenuItem;

    return mBar;
  }
}