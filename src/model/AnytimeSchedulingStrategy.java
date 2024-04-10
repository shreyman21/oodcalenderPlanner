package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class AnytimeSchedulingStrategy implements SchedulingStrategy {

  // "Any time": This scheduling strategy will find the first possible time
  // (starting Sunday at 00:00) that allows
  // all invitees and the host to be present and return an event with that block of time.
  @Override
  public void scheduleEvent(Event event, User host, PlannerSystem plannerSystem) {
    Schedule hostSchedule = host.getSchedule();
    LocalDateTime startSearch = LocalDateTime.now().with(DayOfWeek.SUNDAY)
            .withHour(0).withMinute(0);
    LocalDateTime endSearch = startSearch.plusDays(6)
            .withHour(23).withMinute(59);

    WorkHoursSchedulingStrategy.search(event, plannerSystem, hostSchedule, startSearch, endSearch);
  }
}
