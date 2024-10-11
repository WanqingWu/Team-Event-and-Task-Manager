package model;

import java.util.List;
import java.util.ArrayList;

// Represents a team project containing several tasks.
public class TeamProject {

    private String name;
    private ArrayList<Task> taskList;
    private ArrayList<Task> unstartedTaskList;
    private ArrayList<Task> inProgressTaskList;
    private ArrayList<Task> completedTaskList;

    // EFFECTS: constructs a team project with project name and an empty list of to-do tasks.
    public TeamProject(String name) {
        this.name = name;
        this.taskList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: changes the project name to newName
    public void changeNameTo(String newName) {
        this.name = newName;
    }

    public String getName() {
        return this.name;
    }

    public List<Task> getTasks() {
        return this.taskList;
    } 

    // MODIFIES: this
    // EFFECTS: adds a task to this project
    public void addTask(Task task) {
        this.taskList.add(task);
    }

    // MODIFIES: this
    // EFFECTS: shows a list of unstarted tasks
    public List<Task> showUnstartedTasks() { 
        unstartedTaskList = new ArrayList<>();

        for (Task task: this.taskList) {
            if (task.getStatus() == "not started") {
                unstartedTaskList.add(task);
            }
        }
        return unstartedTaskList;
    }

    // MODIFIES: this
    // EFFECTS: shows a list of in-progress tasks
    public List<Task> showInProgressTasks() {
        inProgressTaskList = new ArrayList<>();

        for (Task task: this.taskList) {
            if (task.getStatus() == "in progress") {
                inProgressTaskList.add(task);
            }
        }
        return inProgressTaskList;
    }

    // MODIFIES: this
    // EFFECTS: shows a list of completed tasks
    public List<Task> showCompletedTasks() {
        completedTaskList = new ArrayList<>();
        for (Task task: this.taskList) {
            if (task.getStatus() == "completed") {
                completedTaskList.add(task);
            }
        }
        return completedTaskList;
    }

    // MODIFIES: this
    // EFFECTS: returns true if all the tasks in this project are completed
    public boolean isProjectCompleted() {
        for (Task task: this.taskList) {
            if (task.getStatus() != "completed") {
                return false;
            }
        }
        return true;
    }
}
