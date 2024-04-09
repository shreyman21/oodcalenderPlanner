package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class AnytimeSchedulingStrategy implements SchedulingStrategy {

  // "Any time": This scheduling strategy will find the first possible time
  // (starting Sunday at 00:00) that allows
  // all invitees and the host to be present and return an event with that block of time.
  @Override
  public void scheduleEvent(Event event, User host) {
    Schedule hostSchedule = host.getSchedule();
    LocalDateTime startSearch = LocalDateTime.now().with(DayOfWeek.SUNDAY)
            .withHour(0).withMinute(0);
    LocalDateTime endSearch = startSearch.plusDays(6)
            .withHour(23).withMinute(59);

    while (startSearch.isBefore(endSearch)) {
      LocalDateTime endSearchTime = startSearch.plusMinutes(event.getDuration());
      if (hostSchedule.isAvailable(startSearch, endSearchTime)) {
        boolean allInviteesAvailable = true;
        for (String inviteeId : event.getInvitees()) {
          User invitee = hostSchedule.getUser(inviteeId);
          if (invitee == null || !invitee.getSchedule().isAvailable(startSearch, endSearchTime)) {
            allInviteesAvailable = false;
            break;
          }
        }
        if (allInviteesAvailable) {
          event.setTime(startSearch); // Set the event time
          hostSchedule.addEvent(event); // Add event to host's schedule
          // Assuming we might need to add the event to each invitee's schedule as well
          for (String inviteeId : event.getInvitees()) {
            User invitee = hostSchedule.getUser(inviteeId);
            invitee.getSchedule().addEvent(event);
          }
          return; // Event successfully scheduled
        }
      }
      // Increment the search start by 30 minutes (or any other increment you see fit)
      startSearch = startSearch.plusMinutes(30);

      // Break the loop if the end of search time exceeds the week's end
      if (!startSearch.isBefore(endSearch)) {
        break;
      }
    }
    // Handle the case where no suitable time was found within the week.
    // This might involve setting an error on the event, logging a message, etc.

  }
}
