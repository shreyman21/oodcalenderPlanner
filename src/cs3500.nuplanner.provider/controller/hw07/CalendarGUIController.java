package cs3500.calendar.controller.hw07;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import cs3500.calendar.controller.CalendarController;
import cs3500.calendar.model.hw05.CalendarModel;
import cs3500.calendar.model.hw05.DayOfWeek;
import cs3500.calendar.model.hw05.Event;
import cs3500.calendar.model.hw05.EventModel;
import cs3500.calendar.model.hw05.EventTime;
import cs3500.calendar.model.hw05.EventTimeModel;
import cs3500.calendar.model.hw05.Schedule;
import cs3500.calendar.view.hw06.CalendarGUIView;

/**
 * Controller of the GUI view for the calendar, specifically for
 * scheduling events at anytime.
 */
public class CalendarGUIController implements CalendarController {
  private CalendarGUIView view;
  protected CalendarModel<Schedule> model;

  /**
   * <b>Constructor for CalendarGUIController.</b>
   * @param view representation of the view
   * @throws NullPointerException if view is null
   */
  public CalendarGUIController(CalendarGUIView view) {
    this.view = Objects.requireNonNull(view);
  }

  @Override
  public void launch(CalendarModel<Schedule> model) {
    this.model = Objects.requireNonNull(model);
  }

  @Override
  public void showView() throws IOException {
    this.view.addFeatures(this);
    this.view.render();
  }

  @Override
  public void loadXML(String path) throws IllegalArgumentException {
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(new File(path));
      xmlDoc.getDocumentElement().normalize();
      NodeList eventList = xmlDoc.getElementsByTagName("event");
      for (int event = 0; event < eventList.getLength(); event++) {
        Node eventNode = eventList.item(event);
        if (eventNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eventElement = (Element) eventNode;
          this.makeEvent(eventElement);
        }
      }
    } catch (ParserConfigurationException pcEX) {
      throw new IllegalStateException("Error in creating builder");
    } catch (SAXException saxEX) {
      throw new IllegalStateException("Error in parsing the file.");
    } catch (IOException ioEX) {
      throw new IllegalStateException("Error in opening the file.");
    }
  }

  private void makeEvent(Element eventElement) {
    String name = eventElement.getElementsByTagName("name").item(0).getTextContent();
    String startDay = eventElement.getElementsByTagName("start-day").item(0)
            .getTextContent();
    String start = eventElement.getElementsByTagName("start").item(0).getTextContent();
    String endDay = eventElement.getElementsByTagName("end-day").item(0)
            .getTextContent();
    String end = eventElement.getElementsByTagName("end").item(0).getTextContent();
    boolean online = Boolean.parseBoolean(eventElement.getElementsByTagName("online")
            .item(0).getTextContent());
    String place = eventElement.getElementsByTagName("place").item(0).getTextContent();
    NodeList usersList = eventElement.getElementsByTagName("uid");
    String[] users = new String[usersList.getLength()];
    for (int invitee = 0; invitee < usersList.getLength(); invitee++) {
      String uid = usersList.item(invitee).getTextContent();
      users[invitee] = uid.substring(1, uid.length() - 1);
    }
    name = name.substring(1, name.length() - 1);
    EventModel ev = new Event.EventBuilder().title(name)
            .startingDay(DayOfWeek.valueOf(startDay.toUpperCase())).startingTime(start)
            .endingDay(DayOfWeek.valueOf(endDay.toUpperCase())).endingTime(end)
            .isOnline(online).location(place.substring(1, place.length() - 1))
            .invitedUIDs(users).build();
    this.model.addEvent(ev);
  }

  private EventModel buildEvent(HashMap<String, String> ed) {
    Event.EventBuilder eb = new Event.EventBuilder().title(ed.get("title"));
    if (ed.get("isOnline").equals("true")) {
      eb.isOnline(true);
    } else {
      eb.isOnline(false);
    }
    String[] invitedUIDs = ed.get("invitedUIDs").substring(1,
            ed.get("invitedUIDs").length() - 1).split(", ");
    if (invitedUIDs.length == 0) {
      throw new IllegalArgumentException("Must have at least one invitedUID, host");
    }
    eb.location(ed.getOrDefault("location", null))
            .startingDay(DayOfWeek.fromString(ed.get("startingDay")))
            .endingDay(DayOfWeek.fromString(ed.get("endingDay")))
            .startingTime(ed.get("startingTime"))
            .endingTime(ed.get("endingTime"))
            .invitedUIDs(invitedUIDs);
    return eb.build();
  }

  @Override
  public void createEvent(HashMap<String, String> ed) {
    /*
    private final String title;
    private final String location;
    private final boolean isOnline;
    private final EventTime startingEventTime;
    private final EventTime endingEventTime;
    private final String hostUID;
    private final String[] invitedUIDs;
     */

    this.model.addEvent(this.buildEvent(ed));
  }

  @Override
  public void modifyEvent(EventModel oldEvent, HashMap<String, String> ed) {
    EventModel newEvent = this.buildEvent(ed);
    this.model.modifyEvent(oldEvent.observeInvitedUIDs()[0], oldEvent, newEvent);
  }

  @Override
  public void removeEvent(EventModel event) {
    this.model.removeEvent(event.observeInvitedUIDs()[0], event);
  }

  @Override
  public void scheduleEvent(HashMap<String, String> ed) throws IllegalArgumentException {
    String[] invitedUIDs = ed.get("invitedUIDs").substring(1,
                    ed.get("invitedUIDs").length() - 1).split(", ");
    if (invitedUIDs.length == 0) {
      throw new IllegalArgumentException("Must have at least one invitedUID, host");
    }
    Event.EventBuilder eb = new Event.EventBuilder()
            .title(ed.get("title"))
            .location(ed.getOrDefault("location", null))
            .invitedUIDs(invitedUIDs);
    if (ed.get("isOnline").equals("true")) {
      eb.isOnline(true);
    } else {
      eb.isOnline(false);
    }

    for (int day = 0; day < 7; day++) {
      eb.startingDay(DayOfWeek.fromInt(day));
      for (int hour = 0; hour < 24; hour++) {
        for (int min = 0; min < 60; min++) {
          eb.startingTime(String.format("%02d", hour) + String.format("%02d", min));
          EventTimeModel endingEventTime = new EventTime(day, hour, min)
                  .plus(Integer.parseInt(ed.get("duration")));
          eb.endingDay(DayOfWeek.fromInt(endingEventTime.getIntegerRepresentation()[0]));
          eb.endingTime(String.format("%02d",
                  endingEventTime.getIntegerRepresentation()[1])
                  + String.format("%02d",
                  endingEventTime.getIntegerRepresentation()[2]));
          EventModel event = eb.build();
          if (this.model.getSchedule(invitedUIDs[0]).isAvailable(event)) {
            this.model.addEvent(event);
            return;
          }
        }
      }
    }
    throw new IllegalArgumentException("No available time slot for event.");
  }

  @Override
  public void saveAllXML(String dirPath) throws IllegalArgumentException, RuntimeException {
    for (String uid : this.model.getUsers()) {
      this.saveXML(dirPath, uid);
    }
  }

  /**
   * Saves the given uid's schedule to an XML file located in the given directory.
   * @param dirPath path of the directory to save XML file to
   * @param uid uid of the schedule to save
   */
  private void saveXML(String dirPath, String uid) throws RuntimeException {
    try {
      String path = dirPath + "\\" + uid + ".xml";
      Writer file = new FileWriter(path);
      file.write("<?xml version=\"1.0\"?>\n");
      file.write("<calendar id=\"" + uid + "\">\n");
      file.write(this.model.getSchedule(uid).xmlFormat());
      file.write("</calendar>");
      file.close();
    } catch (IOException ioEx) {
      throw new RuntimeException(ioEx.getMessage());
    }
  }
}
