package model;

import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.DayOfWeek;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.ReadonlyCalendarModel;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.ScheduleModel;


import java.time.LocalDateTime;
import java.util.List;

public class CalenderModelAdapter implements ReadonlyCalendarModel<ScheduleModel> {

  private PlannerSystem plannerSystem;


  public CalenderModelAdapter(PlannerSystem plannerSystem)  {
    this.plannerSystem = plannerSystem;
  }
  @Override
  public boolean isUserAvailable(String uid, DayOfWeek day, String time) {

    User user = plannerSystem.getUser(uid);
    List<Event> events = user.getEvents();
    for (Event event : events) {
      LocalDateTime ldt = event.getStart();
      DayOfWeek eventDay = DayOfWeek.valueOf(ldt.getDayOfWeek().toString());
      String eventTime = ldt.getHour() + ":" + ldt.getMinute();
      if (eventDay.equals(day) && eventTime.equals(time)) {
        return false;
      }
    }
    return true;

  }

  @Override
  public ScheduleModel getSchedule(String uid) throws IllegalArgumentException {
    User user = plannerSystem.getUser(uid);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }

    return convertToScheduleModel(user.getSchedule());
  }

  private ScheduleModel convertToScheduleModel(Schedule schedule) {
    List<Event> events = schedule.getEvents();
    return new MyScheduleModel(events);
  }

  @Override
  public String[] getUsers() {
    return plannerSystem.getUsers().stream()
            .map(User::getId)
            .toArray(String[]::new);
  }
}
