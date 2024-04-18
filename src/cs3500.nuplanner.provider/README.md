# Save The Date

## Overview
This codebase is designed to solve the problem of managing and organizing events in a calendar. It
assumes that the user has a basic understanding of Java and object-oriented programming. The
codebase is designed to be extensible in terms of adding new types of events or different calendar
views. However, it does not currently support integration with external calendar services. To use
this codebase, you need a Java development environment.

## Invariant
Our invariant in this codebase is the Event class.

## Quick Start
Here's a short snippet of code showing how you might get started using this codebase:

```java
import cs3500.calendar.controller.hw07.CalendarGUIController;
import cs3500.calendar.controller.hw07.CalendarGUIControllerWorkdays;
import cs3500.calendar.model.hw05.Calendar;
import cs3500.calendar.model.hw05.CalendarModel;
import cs3500.calendar.model.hw05.Schedule;
import cs3500.calendar.view.hw06.CalendarGUIView;
import cs3500.calendar.view.hw06.CalendarGUIViewImpl;
import cs3500.calendar.controller.CalendarController;

public static void main(String[] args) throws IOException {
  if (args.length != 1) { throw new IllegalArgumentException("Must specify scheduling method");}
  CalendarController controller;
  CalendarModel<Schedule> model = new Calendar();
  // Load users, schedules, events, etc here...


  // ...
  CalendarGUIView view = new CalendarGUIViewImpl(model);
  switch (args[0].toLowerCase()) {
    case "anytime":
      controller = new CalendarGUIController(view);
      break;
    case "workhours":
      controller = new CalendarGUIControllerWorkdays(view);
      break;
    default:
      throw new IllegalStateException("Unexpected value: " + args[0].toLowerCase());
  }
  controller.launch(model);
  controller.showView();
}
```


## XML File Compatibility
This calendar system also has the ability to save a schedule to a .xml file or import a .xml file
and store it into the system. Here is an example of a properly formatted .xml to be imported.

```xml
<?xml version="1.0"?>
<calendar id="Ethan">
	<event>
		<name>"Event 1"</name>
		<time>
			<start-day>Monday</start-day>
			<start>0000</start>
			<end-day>Monday</end-day>
			<end>0100</end>
		</time>
		<location>
			<online>false</online>
			<place>"Location 1"</place>
		</location>
		<users>
			<uid>"Dhruv"</uid>
			<uid>"Ethan"</uid>
			<uid>"Neirit"</uid>
		</users>
	</event>
	<event>
		<name>"Event 2"</name>
		<time>
			<start-day>Tuesday</start-day>
			<start>0100</start>
			<end-day>Tuesday</end-day>
			<end>0200</end>
		</time>
		<location>
			<online>false</online>
			<place>"Location 2"</place>
		</location>
		<users>
			<uid>"Dhruv"</uid>
			<uid>"Ethan"</uid>
			<uid>"Neirit"</uid>
		</users>
	</event>
	<event>
		<name>"Event 4"</name>
		<time>
			<start-day>Thursday</start-day>
			<start>0300</start>
			<end-day>Thursday</end-day>
			<end>0400</end>
		</time>
		<location>
			<online>false</online>
			<place>"Location 4"</place>
		</location>
		<users>
			<uid>"Dhruv"</uid>
			<uid>"Ethan"</uid>
		</users>
	</event>
</calendar>
```

In order to save to an XML file, the Save XML button can be selected under the File menu in the GUI.
When selected, the GUI will prompt you to select a directory to save all user's schedules to. Upon
selection, all the schedules in the Calendar will be saved as XMLs in the selected directory.


## Key Components
<ul>
    <li><b>Calendar:</b> This is the main component of the system. It manages the users and their
schedules. It drives the control flow of the system.</li>
    <li><b>Schedule:</b> This component represents a user's schedule. It is driven by the 
CalendarModel.</li>
    <li><b>Event:</b> This component represents an event in a user's schedule. It is managed by the 
Schedule component.</li>
    <li><b>CalendarView:</b> This component represents the view of the calendar.</li>
</ul>

## Key Subcomponents

<ul>
    <li><b>EventTimeModel:</b> This component represents a time of an event, including the day of
the week and time.</li>
    <li><b>DayOfWeek:</b> This represents a day of the week. It is used for organizing events in a 
Schedule.</li>
</ul>

## Source Organization
```
+ docs
| + Dhruv.xml
| + Ethan.xml
| + Kimani.xml
| + Neirit.xml
| + prof.xml
| + RM.xml
| + testWarren.xml
| + tutorial.xml
+ src
| + cs3500.calendar
| | + controller
| | | + hw07
| | | | + CalendarGUIController
| | | | + CalendarGUIControllerWorkdays
| | | | + ControllerFeatures
| | | + CalendarController
| | + model
| | | + hw05
| | | | + Calendar.java
| | | | + CalendarModel.java
| | | | + DayOfWeek.java
| | | | + Event.java
| | | | + EventModel.java
| | | | + EventTime.java
| | | | + EventTimeModel.java
| | | | + ReadonlyCalendarModel.java
| | | | + Schedule.java
| | | | + ScheduleModel.java
| | + view
| | | + hw06
| | | | + CalendarGUIView
| | | | + CalendarGUIViewImpl
| | | | + EventFrame
| | | | + EventFrameImpl
| | | | + MainFrame
| | | | + MainFrameImpl
| | | | + MainPanel
| | | | + MainPanelImpl
| | | | + ScheduleFrame
| | | | + ScheduleFrameImpl
| | | + CalendarTextView.java
| | | + CalendarView.java
| | + CalendarRunner.java
+ test
| + cs3500
| | + calendar
| | | + controller
| | | | + hw07
| | | | | + CalendarGUIControllerTest
| | | | | + ControllerMock
| | | | | + GUIViewMock
| | | | | + IntegrationTests
| | | | | + ModelMock
| | | + model
| | | | + hw05
| | | | | + CalendarModelTest
| | | | | + EventTest
| | | | | + EventTimeTest
| | | | | + ScheduleTest
+ README.md
```

+ cs3500.calendar.controller: This package will contain the controller, to be implemented in a 
+ future assignment.
+ cs3500.calendar.model.hw05: This package contains the CalendarModel, Schedule, and Event classes.
+ cs3500.calendar.view: This package contains the CalendarView class.

## Changes Made to the Original Codebase in Part 2
+ Identified the invariant of the codebase in README.md
+ Changed the use of Event as a parameter to EventModel<EventTime>
+ Modified the CalendarTextView toString to loop through the users and print their schedules
  + Added a new method to the CalendarModel to print a specific user's schedule
+ Updated JavaDoc descriptions for classes to be more specific
+ Modified CalendarModel to extends ReadonlyCalendarModel, which contains all observer methods. 
+ ReadonlyCalendarModel methods include:
  + boolean isUserAvailable(String uid, DayOfWeek day, String time)
  + T getSchedule(String uid)
  + String[] getUsers()
+ New observer methods to EventModel to allow the view to observe the event details and display 
+ them.
+ Added tests for new observers in CalendarTest and ScheduleTest

## Changes Made to the Original Codebase in Part 3
+ Removed parameterization of EventModel class.
+ Changed all hard class implementations of Schedule and Event to ScheduleModel and EventModel 
interfaces.
+ CalendarController interface now extends ControllerFeatures interface.
+ Added new addition method to EventTimeModel to assist in scheduling events portion of the 
assignment.
+ Changed CalendarRunner main method to use a factory pattern based off of arguments in the main 
method.
+ Added new CalendarGUIController and CalendarGUIControllerWorkdays classes to implement the
CalendarController interface.
+ Organized and condensed the amount of class/files needed to display the main and event frames.
+ Added new interfaces for the main and event frames to allow for easier testing.
+ Added new interface and implementation for the ScheduleFrame to allow for easier testing.
+ Added functionality to display events that span multiple days.
+ Added tests for integration between controller and model.
+ Added tests by using mock classes to test communication between controller and view.
+ Added tests by using mock classes to test communication between controller and model.

## Quickstart with CalendarRunner
To display the Calendar using the new CalendarGUI, the code below should be ran in 
CalendarRunner.java.

```java
package cs3500.calendar;

import java.util.Arrays;

import cs3500.calendar.model.hw05.CalendarModel;
import cs3500.calendar.model.hw05.Calendar;
import cs3500.calendar.model.hw05.Schedule;
import cs3500.calendar.view.CalendarTextView;
import cs3500.calendar.view.hw06.CalendarGUIViewImpl;
import cs3500.calendar.view.hw06.EventFrameImpl;


public class CalendarRunner {
  public static void main(String[] args) {
    CalendarModel<Schedule> model = new Calendar();
    // Do whatever logic to the model here, i.e. load an xml or add a schedule or add a user, etc...
    model.loadSchedule("docs/Dhruv.xml"); // Example, loading a schedule from docs/
    
    CalendarGUIViewImpl view = new CalendarGUIViewImpl(model); // Creating new GUI view object
    view.setVisible(true); // Display GUI
  }
}
```

In the class CalendarRunner, run the main method. When setting the run configuration, set the 
arguments to contain either "anytime" or "workhours", to specify how it should autoschedule events 
when Schedule Event is selected. In the main method, after the CalendarModel is initialized, the 
user can alter the model with whatever available methods they want (i.e. load schedules, add events,
add users, etc). After this, the main method sends a Read Only version of the model to the View, 
preventing the view from altering any of the data in the model.