// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Member;
import model.Task;
import model.TeamEvent;

import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TeamEvent te = reader.readTeamEvent();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTeamEvent() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMembers.json");
        try {
            TeamEvent te = reader.readTeamEvent();
            assertEquals("Team Event", te.getName());
            assertEquals(0, te.numMembers());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTeamEvent() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMembers.json");
        try {
            TeamEvent te = reader.readTeamEvent();
            assertEquals("Team Event", te.getName());
            List<Member> members = te.getMemberList();
            assertEquals(2, members.size());
            checkMember("June", 20030609, members.get(0));
            checkMember("Stephen", 20010927, members.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}