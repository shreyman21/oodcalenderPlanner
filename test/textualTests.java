import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import model.User;
import model.plannerSystem;
import view.textualView;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the textual view of the planner system.
 */
public class textualTests{
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private plannerSystem ps;
  private textualView tv;

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outContent));
    ps = new plannerSystem();
    tv = new textualView();
    User user1 = new User("1", "Prof. Lucia");
    ps.addUser(user1);
    ps.uploadSchedule("prof.xml", user1);

    User user2 = new User("2", "Student Anon");
    ps.addUser(user2);
    ps.uploadSchedule("prof.xml", user2);
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void testDisplayUsersSchedules() {
    List<User> users = new ArrayList<>(ps.getUsers());
    tv.displayUsersSchedules(users);
    String output = outContent.toString();
    System.setOut(originalOut);
    System.out.println(output);
    assertTrue(output.contains("Prof. Lucia"));
    assertTrue(output.contains("Student Anon"));
  }
}
