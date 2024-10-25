// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.TeamEvent;
import model.Member;

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
        this.source = source;
    }

    // EFFECTS: reads team event from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TeamEvent readTeamEvent() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTeamEvent(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses team event from JSON object and returns it
    private TeamEvent parseTeamEvent(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        TeamEvent te = new TeamEvent(name);
        addMembersToTeamEvent(te, jsonObject);
        return te;
    }

    // MODIFIES: te
    // EFFECTS: parses members from JSON object and adds them to team event
    private void addMembersToTeamEvent(TeamEvent te, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("members");
        for (Object json : jsonArray) {
            JSONObject nextMember = (JSONObject) json;
            addMemberToTeamEvent(te, nextMember);
        }
    }

    // MODIFIES: te
    // EFFECTS: parses member from JSON object and adds it to team event
    private void addMemberToTeamEvent(TeamEvent te, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String bday = jsonObject.getString("birthday");
        Member member = new Member(name, Integer.valueOf(bday));
        te.addMember(member);
    }
}
