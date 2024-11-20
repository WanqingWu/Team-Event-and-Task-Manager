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
        addMembersToTeamEvent(te, jsonObject);
        return te;
    }

    // EFFECTS: parses team project from JSON object and returns it
    private TeamProject parseTeamProject(JSONObject jsonObject) {
        String name = jsonObject.getString("team project name");
        TeamProject tp = new TeamProject(name);
        addTasksToTeamProject(tp, jsonObject);
        return tp;
    }

    // EFFECTS: parses task from JSON object and returns it
    private Task parseTask(JSONObject jsonObject) {
        String name = jsonObject.getString("task name");
        Task t = new Task(name);
        addMemberToTask(t, jsonObject);
        return t;
    }

    // EFFECTS: parses member from JSON object and returns it
    private Member parseMember(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String bday = jsonObject.getString("birthday");
        Member member = new Member(name, Integer.valueOf(bday));
        addTaskToMember(member, jsonObject);
        return member;
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

    // MODIFIES: tp
    // EFFECTS: parses tasks from JSON object and adds them to team project
    private void addTasksToTeamProject(TeamProject tp, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTaskToTeamProject(tp, nextTask);
        }
    }

    // MODIFIES: tp
    // EFFECTS: parses task from JSON object and adds it to team project
    private void addTaskToTeamProject(TeamProject tp, JSONObject jsonObject) {
        String name = jsonObject.getString("task name");
        String member = jsonObject.getString("member");
        String status = jsonObject.getString("status");
        Task task = new Task(name);
        Member m = new Member(member, 0);
        task.setStatus(status);
        task.assignTaskTo(m);
        tp.addTask(task);
    }

    // MODIFIES: t
    // EFFECTS: parses member from JSON object and adds it to task
    private void addMemberToTask(Task t, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String bday = jsonObject.getString("birthday");
        Member member = new Member(name, Integer.valueOf(bday));
        t.assignTaskTo(member);
    }

    // MODIFIES: m
    // EFFECTS: parses task from JSON object and adds it to member
    private void addTaskToMember(Member m, JSONObject jsonObject) {
        String name = jsonObject.getString("task name");
        String status = jsonObject.getString("status");
        Task task = new Task(name);
        task.setStatus(status);
        task.assignTaskTo(m);
        m.addTask(task);
    }
}
