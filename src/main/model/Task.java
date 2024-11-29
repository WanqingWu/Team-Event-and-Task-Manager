package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents a task in the team project.
public class Task implements Writable {
    private String name;
    private Member member;
    private String status;

    // EFFECTS: constructs a task with status "not started"
    //          and no member has been assigned to this task
    public Task(String name) {
        this.name = name;
        this.status = "not started";
        this.member = null;
    }

    public String getName() {
        return this.name;
    }

    // MODIFIES: this
    // EFFECTS: changes the task name to newName
    public void changeNameTo(String newName) {
        this.name = newName;

        EventLog.getInstance().logEvent(new Event("Task name changed."));
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Member getMember() {
        return this.member;
    }

    // REQUIRES: this task hasn't been assigned
    // MODIFIES: this
    // EFFECTS: assigns the task to a member
    public void assignTaskTo(Member member) {
        if (this.member != member) {
            this.member = member;
            if (member != null) {
                member.addTask(this);
            }
        }

        EventLog.getInstance().logEvent(new Event("Task assigned to member."));
    }

    // REQUIRES: this task hasn't started
    // MODIFIES: this
    // EFFECTS: works on the task; set task status to "in progress"
    public void workOnTask() {
        if (this.status.equals("not started")) {
            this.status = "in progress";
        }

        EventLog.getInstance().logEvent(new Event("Task in progress."));
    }

    // REQUIRES: this task has been in progress
    // MODIFIES: this
    // EFFECTS: marks the task as done; set task status to "completed"
    public void completeTask() {
        if (this.status.equals("in progress")) {
            this.status = "completed";
        }

        EventLog.getInstance().logEvent(new Event("Task completed."));
    }

    @Override
    // MODIFIES: this
    // EFFECTS: writes member to Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("taskName", name);
        if (member != null) {
            json.put("member", member.getName());
        }
        json.put("status", status);
        return json;
    }
}
