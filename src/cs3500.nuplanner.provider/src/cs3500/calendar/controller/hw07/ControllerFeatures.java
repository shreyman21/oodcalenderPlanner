package cs3500.nuplanner.provider.src.cs3500.calendar.controller.hw07;

import java.util.HashMap;

import cs3500.nuplanner.provider.src.cs3500.calendar.model.hw05.EventModel;


/**
 * Interface containing all the methods that the user can do on the GUI,
 * such as creating an event, loading an xml, scheduling an event, etc.
 */
public interface ControllerFeatures {
  /**
   * <b>Adds a schedule to the calendar from an .xml file.</b>
   *
   * <p>Note that the path must exist.</p>
   *
   * @param path the path to the .xml file
   * @throws IllegalArgumentException if the path does not exist
   */
  void loadXML(String path) throws IllegalArgumentException;

  /**
   * <b>Saves all schedules to .xml files in specified directory.</b>
   *
   * @param dirPath the path of the directory to save schedules to.
   */
  void saveAllXML(String dirPath) throws IllegalArgumentException;

  /**
   * <b>Creates an event in the model.</b>
   * @param eventDetails HashMap encapsulating the data of the event to be created.
   * @throws IllegalArgumentException if any event arguments are invalid according to
   *     the model.
   */
  void createEvent(HashMap<String, String> eventDetails) throws IllegalArgumentException;

  /**
   * <b>Schedules the event according to set rules of that class.</b>
   *
   * <p>Schedules the event according to the class's needs. i.e. anytime, work hours only,
   *    etc</p>
   * @param eventDetails HashMap encapsulating the data of the event to be created.
   * @throws IllegalArgumentException if any event arguments are invalid according to
   *     the model.
   */
  void scheduleEvent(HashMap<String, String> eventDetails) throws IllegalArgumentException;

  /**
   * <b>Replaces an event with a new event.</b>
   *
   * <p>Takes in an old event and new event details, finds and removes the old event from
   *     the user's schedules and replaces it with a new event specified by the given event
   *     details.</p>
   * @param oldEvent old event to be replaced
   * @param ed new event details
   */
  void modifyEvent(EventModel oldEvent, HashMap<String, String> ed);

  /**
   * <b>Removes an event from the model.</b>
   *
   * <p>Removes the given event from the model, using the method in the CalendarModel.</p>
   * @param event event to be removed
   * @throws IllegalArgumentException if event doesn't exist.
   */
  void removeEvent(EventModel event);
}
