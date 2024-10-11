package model;

import java.util.List;
import java.util.ArrayList;

// Represents a team project containing several tasks.
public class TeamProject {

    public String name;
    public ArrayList<Task> taskList;

    // EFFECTS: constructs a team project with project name and an empty list of to-do tasks.
    public TeamProject(String name) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: change the project name to newName
    public void changeNameTo(String newName) {
        // stub
    }

    public String getName() {
        return "";
    }

    public List<Task> getTasks() {
        return null;
    } 

    // MODIFIES: this
    // EFFECTS: add a task to this project
    public void addTask() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: show a list of unstarted tasks
    public List<Task> showUnstartedTasks() {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: show a list of in-progress tasks
    public List<Task> showInProgressTasks() {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: show a list of completed tasks
    public List<Task> showCompletedTasks() {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: returns true if all the tasks in this project are completed
    public boolean isProjectCompleted() {
        return true; // stub
    }
}
