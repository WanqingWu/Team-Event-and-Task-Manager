package model;
import java.util.List;
import java.util.ArrayList;

// Represents a team event.
public class TeamEvent {
    
    public String name;
    public int time;
    public List<Member> memberList;

    // EFFECTS: construct a teamevent with a event name, time, and an empty member list.
    public TeamEvent(String name) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: change the event name to newName
    public void changeNameTo(String newName) {
        // stub
    }

    public void setTime(int time) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: add member to this event
    public void addMember() {
        // stub
    }

    public String getName() {
        return "";
    } 
    
    public int getTime() {
        return 0;
    }

    public List<Member> getMemberList() {
        return null;
    }
}
