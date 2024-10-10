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
        return this.status;
    }

    public Member getMember() {
        return this.member;
    }

    // REQUIRES: this task hasn't been assigned
    // MODIFIES: this
    // EFFECTS: assign the task to a member
    public void assignTaskTo(Member member) {
        this.member = member;
    }

    // REQUIRES: this task hasn't started
    // MODIFIES: this
    // EFFECTS: work on the task; set task status to "in progress"
    public void workOnTask() {
        this.status = "in progress";
    }

    // REQUIRES: this task has been in progress
    // MODIFIES: this
    // EFFECTS: mark the task as done; set task status to "completed"
    public void completeTask() {
        this.status = "completed";
    }
}
