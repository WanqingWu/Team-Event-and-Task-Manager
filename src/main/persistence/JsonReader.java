package persistence;

import model.TeamEvent;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;


public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        // stub
    }

    // EFFECTS: reads team event from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TeamEvent readTeamEvent() throws IOException {
        TeamEvent tm = new TeamEvent("");
        return tm;// stub
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return ""; // stub
    }

    // EFFECTS: parses team event from JSON object and returns it
    private TeamEvent parseTeamEvent(JSONObject jsonObject) {
        TeamEvent tm = new TeamEvent("");
        return tm; // stub
    }

    // MODIFIES: wr
    // EFFECTS: parses members from JSON object and adds them to team event
    private void addMembersToTeamEvent(TeamEvent te, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: wr
    // EFFECTS: parses member from JSON object and adds it to team event
    private void addMemberToTeamEvent(TeamEvent te, JSONObject jsonObject) {
        // stub
    }
}
