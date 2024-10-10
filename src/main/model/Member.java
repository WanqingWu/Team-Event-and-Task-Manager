package model;

// Represents a team member having a name, birthday and task.
public class Member {
    private String name;
    private int bday;
    private Task task;

    /*
     * EFFECTS: constructs a team member, name of the member is set to name; 
     *          birthday of the member is set to bday; 
     *          task of the member is set to null.
     */
    public Member(String name, int bday) {
        this.name = name;
        this.bday = bday;
        this.task = null;
    }

    public String getName() {
        return this.name;
    }

    public int getBday() {
        return this.bday;
    }

    public Task getTask() {
        return this.task;
    }

    /*
     * REQUIRES: this member doesn't have a current task
     * MODIFIES: this
     * EFFECTS: assign a task to this member
     */
    public void addTask(Task task) {
        this.task = task;
    }
}
