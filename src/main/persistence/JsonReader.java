// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.TeamEvent;
import model.TeamProject;
import model.Task;
import model.TeamData;
import model.Member;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads team event from JSON data stored in file
public class JsonReader {
    private String source;
    private List<Member> members;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
        this.members = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: reads JSON data from file and returns team data
    @SuppressWarnings("methodlength")
    public TeamData readTeamData() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);

        List<TeamEvent> teamEvents = new ArrayList<>();
        if (jsonObject.has("teamEvents")) {
            JSONArray eventsArray = jsonObject.getJSONArray("teamEvents");
            for (Object e : eventsArray) {
                JSONObject eventJson = (JSONObject) e;
                teamEvents.add(parseTeamEvent(eventJson));
            }
        }

        List<TeamProject> teamProjects = new ArrayList<>();
        if (jsonObject.has("teamProjects")) {
            JSONArray projectsArray = jsonObject.getJSONArray("teamProjects");
            for (Object p : projectsArray) {
                JSONObject projectJson = (JSONObject) p;
                teamProjects.add(parseTeamProject(projectJson));
            }
        }

        if (jsonObject.has("members")) {
            JSONArray membersArray = jsonObject.getJSONArray("members");
            for (Object m : membersArray) {
                JSONObject memberJson = (JSONObject) m;
                members.add(parseMember(memberJson));
            }
        }

        return new TeamData(teamEvents, teamProjects, members);
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
        String name = jsonObject.getString("teamEventName");
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
            String memberName = jsonObject.getString("member");
            for (Member m : members) {
                if (m.getName().equals(memberName)) {
                    task.assignTaskTo(m);
                    break;
                }
            }
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
        // if (jsonObject.has("tasks")) {
        //     JSONArray tasksArray = jsonObject.getJSONArray("tasks");
        //     for (Object t : tasksArray) {
        //         JSONObject taskJson = (JSONObject) t;
        //         Task task = parseTask(taskJson);
        //         task.assignTaskTo(member);
        //     }
        // }
        return member;
    }

    // MODIFIES: te
    // EFFECTS: parses members from JSON object and adds them to team event
    private void addMembersToTeamEvent(TeamEvent te, JSONArray membersArray) {
        for (Object m : membersArray) {
            String memberName = ((JSONObject) m).getString("name");
            for (Member member : members) {
                if (member.getName().equals(memberName)) {
                    te.addMember(member);
                    break;
                }
            }
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
