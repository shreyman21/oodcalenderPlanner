package cs3500.calendar.controller.hw07;

import java.util.HashMap;

import cs3500.calendar.model.hw05.DayOfWeek;
import cs3500.calendar.model.hw05.Event;
import cs3500.calendar.model.hw05.EventTime;
import cs3500.calendar.model.hw05.EventTimeModel;
import cs3500.calendar.view.hw06.CalendarGUIView;

/**
 * Controller of the GUI view for the calendar, specifically for
 * scheduling events during workhours only.
 */
public class CalendarGUIControllerWorkdays extends CalendarGUIController {
  public CalendarGUIControllerWorkdays(CalendarGUIView view) {
    super(view);
  }

  @Override
  public void scheduleEvent(HashMap<String, String> ed) {
    /*
    - title
    - isOnline
    - location
    - duration
    - invitedUIDs
     */
    Event.EventBuilder eb = new Event.EventBuilder()
            .title(ed.get("title"))
            .location(ed.getOrDefault("location", null))
            .invitedUIDs(ed.get("invitedUIDs").substring(1, ed.get("invitedUIDs").length() - 1)
                    .split(", "));
    if (ed.get("isOnline").equals("true")) {
      eb.isOnline(true);
    } else {
      eb.isOnline(false);
    }
    final int duration = Integer.parseInt(ed.get("duration"));
    if (duration <= 480) {
      for (int day = 1; day < 6; day++) {
        eb.startingDay(DayOfWeek.fromInt(day));
        for (int hour = 9; hour < 17; hour++) {
          for (int min = 0; min < 60; min++) {
            eb.startingTime(String.format("%02d", hour) + String.format("%02d", min));
            EventTimeModel startingEventTime = new EventTime(day, hour, min);
            EventTimeModel endingEventTime = startingEventTime.plus(duration);
            EventTimeModel lastPossibleWorkHour = new EventTime(day, 17, 1);
            if (endingEventTime.isBefore(lastPossibleWorkHour)) {
              boolean success = true;
              eb.endingDay(DayOfWeek.fromInt(endingEventTime.getIntegerRepresentation()[0]));
              eb.endingTime(String.format("%02d",
                      endingEventTime.getIntegerRepresentation()[1])
                      + String.format("%02d",
                      endingEventTime.getIntegerRepresentation()[2]));
              try {
                model.addEvent(eb.build());
              } catch (IllegalArgumentException iaEx) {
                success = false;
              }

              if (success) {
                return;
              }
            }
          }
        }
      }
    }
    throw new IllegalArgumentException("Event cannot be longer than a workday.");
  }
}
