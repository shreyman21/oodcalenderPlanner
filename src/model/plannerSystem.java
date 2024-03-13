package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class plannerSystem implements plannerSystemModel{

  private Map<String, User> users = new HashMap<>();

  @Override
  public boolean uploadSchedule(String xmlFilePath, User user) {
    try {
      File xmlFile = new File(xmlFilePath);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlFile);
      doc.getDocumentElement().normalize();

      NodeList nList = doc.getElementsByTagName("event");

      for (int i = 0; i < nList.getLength(); i++) {
        Node nNode = nList.item(i);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;

          // Extract event details from XML
          String name = eElement.getElementsByTagName("name").item(0).getTextContent().replace("\"", "");
          String startDay = eElement.getElementsByTagName("start-day").item(0).getTextContent();
          String startTimeString = eElement.getElementsByTagName("start").item(0).getTextContent();
          String endDay = eElement.getElementsByTagName("end-day").item(0).getTextContent();
          String endTimeString = eElement.getElementsByTagName("end").item(0).getTextContent();
          boolean isOnline = Boolean.parseBoolean(eElement.getElementsByTagName("online").item(0).getTextContent());
          String place = eElement.getElementsByTagName("place").item(0).getTextContent().replace("\"", "");

          // Parse the time string into LocalDateTime objects
          DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
          LocalTime startTime = LocalTime.parse(startTimeString, timeFormatter);
          LocalTime endTime = LocalTime.parse(endTimeString, timeFormatter);
          LocalDate startDate = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.valueOf(startDay.toUpperCase())));
          LocalDate endDate = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.valueOf(endDay.toUpperCase())));
          LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
          LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

          // Create an Event object with the parsed data
          Event event = new Event(name, startDateTime, endDateTime, place, isOnline, new ArrayList<>(), new ArrayList<>());
          user.getSchedule().addEvent(event);
        }
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private String[] parseTime(String time) {
    String[] times = time.split("->");
    times[0] = times[0].substring(times[0].indexOf(":") + 1).trim();
    times[1] = times[1].substring(times[1].indexOf(":") + 1).trim();
    return times;
  }

  @Override
  public boolean saveSchedule(String xmlFilePath, User user) {
    return Utils.saveSchedule(xmlFilePath, user);
  }

  @Override
  public void selectUser(User user) {
    if (user == null || !users.containsKey(user.getId())) {
      throw new IllegalArgumentException("User not found in the system.");
    }
  }

  public void addUser(User user) {
    if (user == null || users.containsKey(user.getId())) {
      throw new IllegalArgumentException("Invalid user or user already exists.");
    }
    users.put(user.getId(), user);
  }

  public User getUser(String userId) {
    return users.get(userId);
  }

  public void selectAndModifyUserSchedule(String userId) {
    User user = getUser(userId);
    if (user != null) {
      selectUser(user);
      // Now you can operate on selectedUser's schedule
      // For example: selectedUser.getSchedule().addEvent(new Event(...));
    } else {
      System.out.println("User not found");
    }
  }

  @Override
  public boolean createEvent(User user, Event event) {
    if (user == null || event == null) {
      System.out.println("User or event is null.");
      return false;
    }

    // Check if the user exists in the system
    if (!users.containsKey(user.getId())) {
      System.out.println("User does not exist in the system.");
      return false;
    }

    // Assuming the User's Schedule has an addEvent method
    // And assuming that it returns a boolean indicating success/failure
    try {
      user.getSchedule().addEvent(event);
      return true;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public boolean modifyEvent(User user, Event originalEvent, Event updatedEvent) {

    if (user == null || originalEvent == null || updatedEvent == null) {
      System.out.println("User or event is null.");
      return false;
    }

    if (!users.containsKey(user.getId())) {
      System.out.println("User does not exist in the system.");
      return false;
    }
    try {
      user.getSchedule().removeEvent(originalEvent);
      user.getSchedule().addEvent(updatedEvent);
      return true;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean removeEvent(User user, Event event) {
    if (user == null || event == null) {
      System.out.println("User or event is null.");
      return false;
    }

    if (!users.containsKey(user.getId())) {
      System.out.println("User does not exist in the system.");
      return false;
    }
    try {
      user.getSchedule().removeEvent(event);
      return true;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean autoSchedule(User user, Event event) {
    // First availabe open time slot, cant conflict with other events
    if (user == null || event == null) {
      System.out.println("User or event is null.");
      return false;
    }

    if (!users.containsKey(user.getId())) {
      System.out.println("User does not exist in the system.");
      return false;
    }
    try {
      user.getSchedule().addEvent(event);
      return true;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Event> seeEvents(User user, LocalDateTime time) {
    if (user == null || time == null) {
      System.out.println("User or time is null.");
      return new ArrayList<>();
    }

    if (!users.containsKey(user.getId())) {
      System.out.println("User does not exist in the system.");
      return new ArrayList<>();
    }
    List<Event> events = user.getSchedule().getEvents();
    List<Event> eventsAtTime = new ArrayList<>();
    for (Event event : events) {
      if (event.getStartTime().isBefore(time) && event.getEndTime().isAfter(time)) {
        eventsAtTime.add(event);
      }
    }
    return eventsAtTime;
  }

  public List<User> getUsers() {
    return new ArrayList<>(users.values());
  }
}
