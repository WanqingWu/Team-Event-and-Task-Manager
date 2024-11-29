package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents a team member having a name, birthday and task.
public class Member implements Writable {
    private String name;
    private int bday;
    private Task task;

    /*
     * EFFECTS: constructs a team member, name of the member is set to name; 
     *          birthday of the member is set to bday; 
     *          task of the member is set to null
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
     * EFFECTS: assigns a task to this member
     */
    public void addTask(Task task) {
        if (this.task != task) {
            this.task = task;
            if (task != null) {
                task.assignTaskTo(this);
            }
        }

        EventLog.getInstance().logEvent(new Event("Task added to member."));
    }

    @Override
    // MODIFIES: this
    // EFFECTS: writes member to Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("birthday", Integer.toString(bday));

        if (this.task != null) {
            json.put("task", this.task.toJson());
        }
        // json.put("task", task == null ? JSONObject.NULL : task.toJson());
        return json;
    }
}
