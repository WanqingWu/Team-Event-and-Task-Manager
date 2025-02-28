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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            List<TeamEvent> teamEvents = new ArrayList<>();
            List<TeamProject> teamProjects = new ArrayList<>();
            List<Member> members = new ArrayList<>();

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyData.json");
            writer.open();
            writer.writeTeamData(teamEvents, teamProjects, members);
            writer.close();

            JSONReader reader = new JSONReader("./data/testWriterEmptyData.json");
            TeamData teamData = reader.readTeamData();
            assertTrue(teamData.getTeamEvents().isEmpty());
            assertTrue(teamData.getTeamProjects().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Member member1 = new Member("June", 20030609);
            Member member2 = new Member("Stephen", 20010927);
            List<Member> members = new ArrayList<>();
            members.add(member1);
            members.add(member2);

            Task task1 = new Task("q1");
            task1.setStatus("completed");
            Task task2 = new Task("q2");
            task2.setStatus("in progress");
            Task task3 = new Task("q3");
            task3.setStatus("not started");

            task1.assignTaskTo(member1);
            task2.assignTaskTo(member2);

            TeamEvent teamEvent = new TeamEvent("Meeting");
            teamEvent.addMember(member1);
            teamEvent.addMember(member2);
            List<TeamEvent> teamEvents = new ArrayList<>();
            teamEvents.add(teamEvent);

            TeamProject teamProject = new TeamProject("hw1");
        
            teamProject.addTask(task1);
            teamProject.addTask(task2);
            teamProject.addTask(task3);
            List<TeamProject> teamProjects = new ArrayList<>();
            teamProjects.add(teamProject);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTeamData.json");
            writer.open();
            writer.writeTeamData(teamEvents, teamProjects, members);
            writer.close();

            JSONReader reader = new JSONReader("./data/testWriterGeneralTeamData.json");
            TeamData teamData = reader.readTeamData();

            List<TeamEvent> readEvents = teamData.getTeamEvents();
            assertEquals(1, readEvents.size());
            TeamEvent readEvent = readEvents.get(0);
            assertEquals("Meeting", readEvent.getName());
            assertEquals(2, readEvent.getMemberList().size());
            Member m1 = readEvent.getMemberList().get(0);
            assertEquals("June", m1.getName());
            assertEquals(20030609, m1.getBday());
            Member m2 = readEvent.getMemberList().get(1);
            assertEquals("Stephen", m2.getName());
            assertEquals(20010927, m2.getBday());

            List<TeamProject> readProjects = teamData.getTeamProjects();
            assertEquals(1, readProjects.size());
            TeamProject readProject = readProjects.get(0);
            assertEquals("hw1", readProject.getName());
            assertEquals(3, readProject.getTasks().size());
            Task t1 = readProject.getTasks().get(0);
            assertEquals("q1", t1.getName());
            assertEquals("completed", t1.getStatus());
            Task t2 = readProject.getTasks().get(1);
            assertEquals("q2", t2.getName());
            assertEquals("in progress", t2.getStatus());
            Task t3 = readProject.getTasks().get(2);
            assertEquals("q3", t3.getName());
            assertEquals("not started", t3.getStatus());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}