package view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import cs3500.nuplanner.provider.src.cs3500.calendar.controller.hw07.ControllerFeatures;
import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.EventModel;
import model.Event;
import model.EventModelAdapter;
import model.ISchedulingStrategy;
import model.PlannerSystem;
import model.User;

public class ControllerFeaturesAdapter implements ControllerFeatures {

  private IPlannerViewListener listener;
  private PlannerSystem plannerSystem;

  private ISchedulingStrategy strategy;

  // Pass a strategy potentially

  public ControllerFeaturesAdapter(IPlannerViewListener listener, PlannerSystem plannerSystem,
                                   ISchedulingStrategy strategy) {
    this.listener = listener;
    this.plannerSystem = plannerSystem;
    this.strategy = strategy; // can be null
  }

  @Override
  public void loadXML(String path) throws IllegalArgumentException {
    try {
      User user = plannerSystem.getUserByName("defaultUser");
      plannerSystem.uploadSchedule(path, user);
      listener.onScheduleLoad(path, user);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid file path");
    }

  }

  @Override
  public void saveAllXML(String dirPath) throws IllegalArgumentException {
    try {
      User user = plannerSystem.getUserByName("defaultUser");
      plannerSystem.saveSchedule(dirPath, user);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid directory path");
    }
  }

  @Override
  public void createEvent(HashMap<String, String> eventDetails) throws IllegalArgumentException {
    try {
      // Extract event details from the hashmap
      String name = eventDetails.get("name");
      LocalDateTime startDateTime = LocalDateTime.parse(eventDetails.get("startDateTime"));
      LocalDateTime endDateTime = LocalDateTime.parse(eventDetails.get("endDateTime"));
      String location = eventDetails.get("location");
      boolean isOnline = Boolean.parseBoolean(eventDetails.get("isOnline"));

      Event newEvent = new Event(name, startDateTime, endDateTime, location, isOnline,
              new ArrayList<>());
      User user = plannerSystem.getUserByName(eventDetails.get("userName")); // Assuming username is passed

      plannerSystem.createEvent(user, newEvent);
      listener.onEventCreate(newEvent, user.getId());
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid event details");
    }
  }

  @Override
  public void scheduleEvent(HashMap<String, String> eventDetails) throws IllegalArgumentException {
    plannerSystem.setSchedulingStrategy(strategy);
    try {
      // Extract event details from the hashmap
      String name = eventDetails.get("name");
      LocalDateTime startDateTime = LocalDateTime.parse(eventDetails.get("startDateTime"));
      LocalDateTime endDateTime = LocalDateTime.parse(eventDetails.get("endDateTime"));
      String location = eventDetails.get("location");
      boolean isOnline = Boolean.parseBoolean(eventDetails.get("isOnline"));

      Event newEvent = new Event(name, startDateTime, endDateTime, location, isOnline,
              new ArrayList<>());
      User user = plannerSystem.getUserByName(eventDetails.get("userName")); // Assuming username is passed

      plannerSystem.addEventToUserSchedule(user.getId(), newEvent);
      listener.onEventCreate(newEvent, user.getId());
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid event details");
    }
  }

  @Override
  public void modifyEvent(EventModel oldEvent, HashMap<String, String> ed) {
    try {
      // Extract updated event details from HashMap
      String name = ed.get("name");
      User user = plannerSystem.getUserByName(ed.get("userName"));
      LocalDateTime startDateTime = LocalDateTime.parse(ed.get("startDateTime"));
      LocalDateTime endDateTime = LocalDateTime.parse(ed.get("endDateTime"));
      String location = ed.get("location");
      boolean isOnline = Boolean.parseBoolean(ed.get("isOnline"));

      Event updatedEvent = new Event(name, startDateTime, endDateTime, location, isOnline, new ArrayList<>()); // Assuming no invitees info in HashMap

      Event originalEvent = ((EventModelAdapter) oldEvent).getEvent();

      // Adapt the updated Event to an EventModel
      EventModelAdapter updatedEventAdapter = new EventModelAdapter(updatedEvent);

      // Assuming modifyEvent in PlannerSystem requires EventModel objects
      plannerSystem.modifyEvent(user, originalEvent, updatedEvent); // You need to fetch the user as well

    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid event details: " + e.getMessage());
    }
  }

  @Override
  public void removeEvent(EventModel event) {
    try {
      User user = plannerSystem.getUserByName("defaultUser");
      Event eventToRemove = ((EventModelAdapter) event).getEvent();
      plannerSystem.removeEvent(user, eventToRemove);
    } catch (Exception e) {
      throw new IllegalArgumentException("Event does not exist");
    }
  }
}
