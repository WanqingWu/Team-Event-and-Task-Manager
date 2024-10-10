package model;

// Represents a task in the team project.
public class Task {
    private Member member;
    private String status;

    // EFFECTS: constructs a task with status "not started"
    //          and no member has been assigned to this task
    public Task() {
        this.status = "not started";
        this.member = null;
    }

    public String getStatus() {
        return ""; // stub
    }

    public Member getMember() {
        return null; // stub
    }

    // REQUIRES: this task hasn't been assigned
    // MODIFIES: this
    // EFFECTS: assign the task to a member
    public void assignTaskTo(Member member) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: work on the task; set task status to "in progress"
    public void workOnTask() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: mark the task as done; set task status to "completed"
    public void completeTask() {
        // stub
    }
}
