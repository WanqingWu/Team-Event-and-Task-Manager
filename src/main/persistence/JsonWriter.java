// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.TeamEvent;
import model.TeamProject;
import model.Member;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.List;

// Represents a writer that writes JSON representation of team event to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of team events and projects to file
    public void writeTeamData(List<TeamEvent> teamEvents, List<TeamProject> teamProjects, List<Member> members) {
        JSONObject json = new JSONObject();

        JSONArray eventsArray = new JSONArray();
        for (TeamEvent te : teamEvents) {
            eventsArray.put(te.toJson());
        }
        json.put("teamEvents", eventsArray);

        JSONArray projectsArray = new JSONArray();
        for (TeamProject tp : teamProjects) {
            projectsArray.put(tp.toJson());
        }
        json.put("teamProjects", projectsArray);

        JSONArray membersArray = new JSONArray();
        for (Member m : members) {
            membersArray.put(m.toJson());
        }
        json.put("members", membersArray);

        saveToFile(json);
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(JSONObject json) {
        writer.print(json.toString(TAB));
    }
}
