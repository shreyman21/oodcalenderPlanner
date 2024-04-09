package model;

public interface SchedulingStrategy {
  void scheduleEvent(Event event, Schedule schedule);
}

