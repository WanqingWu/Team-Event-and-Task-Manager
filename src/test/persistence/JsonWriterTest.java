// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Member;
import model.TeamEvent;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            TeamEvent te = new TeamEvent("team event");
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
            TeamEvent te = new TeamEvent("team event");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTeamEvent.json");
            writer.open();
            writer.writeTeamEvent(te);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTeamEvent.json");
            te = reader.readTeamEvent();
            assertEquals("team event", te.getName());
            assertEquals(0, te.numMembers());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            TeamEvent te = new TeamEvent("team event");
            te.addMember(new Member("June", 20030609));
            te.addMember(new Member("Stephen", 20010927));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTeamEvent.json");
            writer.open();
            writer.writeTeamEvent(te);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTeamEvent.json");
            te = reader.readTeamEvent();
            assertEquals("team event", te.getName());
            List<Member> members = te.getMemberList();
            assertEquals(2, members.size());
            checkMember("June", 20030609, members.get(0));
            checkMember("Stephen", 20010927, members.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}