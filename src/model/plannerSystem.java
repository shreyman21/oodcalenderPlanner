package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class plannerSystem implements plannerSystemModel{

  private Map<String, User> users = new HashMap<>();
  private User selectedUser;

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
          String name = eElement.getElementsByTagName("name").item(0).getTextContent();
          String location = eElement.getElementsByTagName("location").item(0).getTextContent();
          boolean isOnline = Boolean.parseBoolean(eElement.getElementsByTagName("online").item(0).getTextContent());
          String start = eElement.getElementsByTagName("start").item(0).getTextContent();
          String end = eElement.getElementsByTagName("end").item(0).getTextContent();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
          LocalDateTime startTime = LocalDateTime.parse(start, formatter);
          LocalDateTime endTime = LocalDateTime.parse(end, formatter);
          Event event = new Event(name, startTime, endTime, location, isOnline, new ArrayList<>());
          user.getSchedule().addEvent(event);
        }
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
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
    this.selectedUser = user;
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
    return false;
  }

  @Override
  public boolean removeEvent(User user, Event event) {
    return false;
  }

  @Override
  public boolean autoSchedule(User user, Event event) {
    return false;
  }

  @Override
  public List<Event> seeEvents(User user, LocalDateTime time) {
    return null;
  }
}
