// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Member;
import model.Task;
import model.TeamData;
import model.TeamEvent;
import model.TeamProject;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JSONReader reader = new JSONReader("./data/noSuchFile.json");
        try {
            reader.readTeamData();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTeamData() {
        JSONReader reader = new JSONReader("./data/testReaderEmptyTeamData.json");
        try {
            TeamData teamData = reader.readTeamData();
            assertTrue(teamData.getTeamEvents().isEmpty());
            assertTrue(teamData.getTeamProjects().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTeamData() {
        JSONReader reader = new JSONReader("./data/testReaderGeneralTeamData.json");
        try {
            TeamData teamData = reader.readTeamData();

            List<TeamEvent> teamEvents = teamData.getTeamEvents();
            assertEquals(1, teamEvents.size());
            TeamEvent te = teamEvents.get(0);
            assertEquals("Team Event", te.getName());
            List<Member> members = te.getMemberList();
            assertEquals(2, members.size());
            assertEquals("June", members.get(0).getName());
            assertEquals("Stephen", members.get(1).getName());

            List<TeamProject> teamProjects = teamData.getTeamProjects();
            assertEquals(1, teamProjects.size());
            TeamProject tp = teamProjects.get(0);
            assertEquals("Team Project", tp.getName());
            List<Task> tasks = tp.getTasks();
            assertEquals(2, tasks.size());
            assertEquals("Task1", tasks.get(0).getName());
            assertEquals("Task2", tasks.get(1).getName());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}