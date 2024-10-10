package model;

// Represents a task in the team project.
public class Task {
    private Member member;
    private String status;

    // EFFECTS: constructs a task with status "in progress"
    //          and no member has been assigned to this task
    public Task() {
        this.status = "in progress";
        this.member = null;
    }

    // MODIFIES: this
    // EFFECTS: complete the task
    public void completeTask() {
        // stub
    }

    // REQUIRES: this task hasn't been assigned
    // MODIFIES: this
    // EFFECTS: assign the task to a member
    public void assignTaskTo(Member member) {
        // stub
    }
}
