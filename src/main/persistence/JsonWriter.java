// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.TeamEvent;
import model.TeamProject;
import model.Task;
import model.Member;

import org.json.JSONObject;

import java.io.*;

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
    // EFFECTS: writes JSON representation of team event to file
    public void writeTeamEvent(TeamEvent te) {
        saveToFile(te.toJson());
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of team project to file
    public void writeTeamProject(TeamProject tp) {
        saveToFile(tp.toJson());
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of tasks to file
    public void writeTask(Task t) {
        saveToFile(t.toJson());
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of member to file
    public void writeMember(Member m) {
        saveToFile(m.toJson());
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
