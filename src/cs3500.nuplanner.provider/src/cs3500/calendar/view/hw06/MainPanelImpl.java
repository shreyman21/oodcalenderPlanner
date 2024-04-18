package cs3500.nuplanner.provider.src.cs3500.calendar.view.hw06;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.AlphaComposite;
import java.awt.Polygon;
import java.awt.Dimension;

import javax.swing.JPanel;

import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.EventModel;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.ReadonlyCalendarModel;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.ScheduleModel;


/**
 * Represents the main panel of the calendar GUI.
 * Includes the main panel and the menu bar. Also includes the bottom panel.
 */
public class MainPanelImpl extends JPanel implements MainPanel {

  private EventModel[] curSchedule;

  /**
   * Constructs a MainPanel object.
   * @param calendar the calendar model
   */
  public MainPanelImpl(ReadonlyCalendarModel<ScheduleModel> calendar) {
    super();
    if (calendar.getUsers().length == 0) {
      this.curSchedule = new EventModel[0];
    } else {
      this.curSchedule = calendar.getSchedule(calendar.getUsers()[0]).getEvents();
    }
  }

  @Override
  public void updateSchedule(ReadonlyCalendarModel<ScheduleModel> model, String user) {
    this.curSchedule = model.getSchedule(user).getEvents();
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    // Draw thick lines every 4 lines
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(Color.BLACK);
    for (int i = 0; i < 24; i++) {
      if ((i + 1) % 4 == 0 && i < 23) {
        g2d.setStroke(new BasicStroke(2));
      } else {
        g2d.setStroke(new BasicStroke(1));
      }
      int y = (i + 1) * this.getHeight() / 24;
      g2d.drawLine(0, y, getWidth(), y);
    }
    for (int i = 0; i < 7; i++) {
      g2d.setStroke(new BasicStroke(1));
      int x = (i + 1) * getWidth() / 7;
      g2d.drawLine(x, 0, x, this.getHeight());
    }
    g2d.dispose();
    this.setPreferredSize(new Dimension(800, 800));
    drawEvents(g);
  }

  private void drawEvents(Graphics g) {
    int daySize = getWidth() / 7; // Assuming 7 days in a week
    int frameHeight = this.getHeight(); // Height of the frame
    for (EventModel event : curSchedule) {
      String startTime = event.observeStartTime();
      String endTime = event.observeEndTime();
      int startHour = Integer.parseInt(startTime.substring(0, 2));
      int startMinute = Integer.parseInt(startTime.substring(2, 4));
      int endHour = Integer.parseInt(endTime.substring(0, 2));
      int endMinute = Integer.parseInt(endTime.substring(2, 4));
      int startDay = event.startDayInt();
      int endDay = event.endDayInt();
      int x = startDay * daySize;
      int y = (int) ((startHour + startMinute / 60.0) * frameHeight / 24);
      int height = ((endHour - startHour) * 60 + (endMinute - startMinute)) *
              frameHeight / 24 / 60;
      Graphics2D g2d = (Graphics2D) g.create();
      g2d.setColor(Color.RED);
      g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
      if (startDay == endDay) {
        Polygon eventPolygon = new Polygon();
        eventPolygon.addPoint(x, y);
        eventPolygon.addPoint(x + daySize, y);
        eventPolygon.addPoint(x + daySize, y + height);
        eventPolygon.addPoint(x, y + height);
        g2d.fillPolygon(eventPolygon);
      } else {
        Polygon startDayPolygon = new Polygon();
        startDayPolygon.addPoint(x, y);
        startDayPolygon.addPoint(x + daySize, y);
        startDayPolygon.addPoint(x + daySize, height);
        startDayPolygon.addPoint(x, height);
        g2d.fillPolygon(startDayPolygon);
        for (int day = startDay + 1; day < endDay; day++) {
          Polygon midDays = new Polygon();
          midDays.addPoint(day * daySize, 0);
          midDays.addPoint((day + 1) * daySize, 0);
          midDays.addPoint((day + 1) * daySize, height);
          midDays.addPoint(day * daySize, height);
          g2d.fillPolygon(midDays);
        }
        Polygon endDayPolygon = new Polygon();
        endDayPolygon.addPoint(endDay * daySize, 0);
        endDayPolygon.addPoint((endDay + 1) * daySize, 0);
        endDayPolygon.addPoint((endDay + 1) * daySize, y + height);
        endDayPolygon.addPoint(endDay * daySize, y + height);
        g2d.fillPolygon(endDayPolygon);
      }
      g2d.dispose();
    }
  }
}
