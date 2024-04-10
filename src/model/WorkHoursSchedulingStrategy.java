package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class WorkHoursSchedulingStrategy implements SchedulingStrategy {
//"Work hours": This scheduling strategy will find the first possible time
// from Monday to Friday (inclusive) between the hours of 0900 and 1700 (inclusive)
// where all invitees and the host can attend the even and return an event with that
// block of time. Note this means it is impossible to
// schedule an event that goes to next week. Otherwise it would not be a work hours event.
  @Override
  public void scheduleEvent(Event event, User user, PlannerSystem plannerSystem) {
    Schedule userSchedule = user.getSchedule();
    LocalDateTime startSearch = LocalDateTime.now().with(DayOfWeek.MONDAY)
            .withHour(9).withMinute(0);
    LocalDateTime endSearch = startSearch.plusDays(4)
            .withHour(17).withMinute(0);

    search(event, plannerSystem, userSchedule, startSearch, endSearch);
  }

  static void search(Event event, PlannerSystem plannerSystem, Schedule userSchedule, LocalDateTime startSearch, LocalDateTime endSearch) {
    while (startSearch.isBefore(endSearch)) {
      LocalDateTime endSearchTime = startSearch.plusMinutes(event.getDuration());
      if (userSchedule.isAvailable(startSearch, endSearchTime)) {
        boolean allInviteesAvailable = true;
        for (String inviteeId : event.getInvitees()) {
          User invitee = plannerSystem.getUser(inviteeId);
          if (invitee == null || !invitee.getSchedule().isAvailable(startSearch, endSearchTime)) {
            allInviteesAvailable = false;
            break;
          }
        }
        if (allInviteesAvailable) {
          event.setTime(startSearch);
          userSchedule.addEvent(event);
          for (String inviteeId : event.getInvitees()) {
            User invitee = plannerSystem.getUser(inviteeId);
            invitee.getSchedule().addEvent(event);
          }
          return;
        }
      }
      startSearch = startSearch.plusMinutes(30);
      if (!startSearch.isBefore(endSearch)) {
        break;
      }
    }
  }
}