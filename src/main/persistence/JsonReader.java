// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.TeamEvent;
import model.TeamProject;
import model.Task;
import model.Member;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads team event from JSON data stored in file
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

    // EFFECTS: reads team project from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TeamProject readTeamProject() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTeamProject(jsonObject);
    }

    // EFFECTS: reads task from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Task readTask() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTask(jsonObject);
    }

    // EFFECTS: reads member from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Member readMember() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMember(jsonObject);
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
        if (jsonObject.has("members")) {
            addMembersToTeamEvent(te, jsonObject.getJSONArray("members"));
        }
        return te;
    }

    // EFFECTS: parses team project from JSON object and returns it
    private TeamProject parseTeamProject(JSONObject jsonObject) {
        String name = jsonObject.getString("teamProjectName");
        TeamProject tp = new TeamProject(name);
        if (jsonObject.has("tasks")) {
            addTasksToTeamProject(tp, jsonObject.getJSONArray("tasks"));
        }
        return tp;
    }

    // EFFECTS: parses task from JSON object and returns it
    private Task parseTask(JSONObject jsonObject) {
        String name = jsonObject.getString("taskName");
        Task task = new Task(name);
        if (jsonObject.has("member")) {
            JSONObject memberObject = jsonObject.getJSONObject("member");
            Member member = parseMember(memberObject);
            task.assignTaskTo(member);
        }
        if (jsonObject.has("status")) {
            task.setStatus(jsonObject.getString("status"));
        }
        return task;
    }

    // EFFECTS: parses member from JSON object and returns it
    private Member parseMember(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String bday = jsonObject.getString("birthday");
        Member member = new Member(name, Integer.valueOf(bday));
        if (jsonObject.has("tasks")) {
            JSONArray tasksArray = jsonObject.getJSONArray("tasks");
            for (Object t : tasksArray) {
                Task task = parseTask((JSONObject) t);
                member.addTask(task);
            }
        }
        return member;
    }

    // MODIFIES: te
    // EFFECTS: parses members from JSON object and adds them to team event
    private void addMembersToTeamEvent(TeamEvent te, JSONArray membersArray) {
        for (Object m : membersArray) {
            Member member = parseMember((JSONObject) m);
            te.addMember(member);
        }
    }

    // MODIFIES: tp
    // EFFECTS: parses tasks from JSON object and adds them to team project
    private void addTasksToTeamProject(TeamProject tp, JSONArray tasksArray) {
        for (Object t : tasksArray) {
            Task task = parseTask((JSONObject) t);
            tp.addTask(task);
        }
    }
}
