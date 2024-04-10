package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class LenientSchedulingStrategy implements SchedulingStrategy {

  /*
  In this entire project, we always make sure everyone invited to an event can attend the event.
  In other words, there can be no conflicts for any invitee. However,
  as programmers we can make our system more lenient when it comes to automatically
  scheduling these events.

  To that end, create a new strategy that creates an event in the first time block
  where the host and at least one other invitee can add that event to their schedule.
  If there is more than one non-host invitee that can add the event to their schedule,
  then they must also be invited. The strategy must
  find a block between Monday and Friday (inclusive) from 0900 to 1700 (inclusive).
   */
  @Override
  public void scheduleEvent(Event event, User user, PlannerSystem plannerSystem) {
    Schedule userSchedule = user.getSchedule();
    LocalDateTime startSearch = LocalDateTime.now().with(DayOfWeek.MONDAY)
            .withHour(9).withMinute(0);
    LocalDateTime endSearch = startSearch.plusDays(4)
            .withHour(17).withMinute(0);

    SearchingStrategy.search(event, plannerSystem, userSchedule, startSearch, endSearch);
  }
}
