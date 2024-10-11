package model;
import java.util.List;
import java.util.ArrayList;

// Represents a team event.
public class TeamEvent {
    
    public String name;
    public int startTime;
    public int endTime;
    public String date;
    public ArrayList<Member> memberList;

    // EFFECTS: construct a teamevent with a event name, startTime and endTime set to 0, 
    //          date set to "" and an empty member list.
    public TeamEvent(String name) {
        this.name = name;
        this.startTime = 0;
        this.endTime = 0;
        this.date = "";
        this.memberList = new ArrayList<>();
        
    }

    // MODIFIES: this
    // EFFECTS: change the event name to newName
    public void changeNameTo(String newName) {
        this.name = newName;
    }

    // REQUIRES: 9 <= startTime <= 17
    // MODIFIES: this
    // EFFECTS: set the end time of the event
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    // REQUIRES: endTime > startTime &&  <= startTime <= 18
    // MODIFIES: this
    // EFFECTS: set the end time of the event
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    // MODIFIES: this
    // EFFECTS: set the date of the event
    public void setDate(String date) {
        this.date = date;
    }

    // MODIFIES: this
    // EFFECTS: add member to this event
    public void addMember(Member member) {
        this.memberList.add(member);
    }

    public String getName() {
        return this.name;
    } 
    
    public int getStartTime() {
        return this.startTime;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public String getDate() {
        return this.date;
    }

    public List<Member> getMemberList() {
        return this.memberList;
    }
}
