package model;

public interface SchedulingStrategy {
  void scheduleEvent(Event event, User user,  PlannerSystem plannerSystem);
}

