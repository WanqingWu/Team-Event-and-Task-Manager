package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.util.ArrayList;

// Represents a team event.
public class TeamEvent implements Writable {
    
    private String name;
    private int startTime;
    private int endTime;
    private String date;
    private ArrayList<Member> memberList;

    // EFFECTS: constructs a teamevent with a event name, startTime and endTime set to 0, 
    //          date set to "" and an empty member list.
    public TeamEvent(String name) {
        this.name = name;
        this.startTime = 0;
        this.endTime = 0;
        this.date = "";
        this.memberList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: changes the event name to newName
    public void changeNameTo(String newName) {
        this.name = newName;
    }

    // REQUIRES: 9 <= startTime <= 17
    // MODIFIES: this
    // EFFECTS: sets the end time of the event
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    // REQUIRES: endTime > startTime && 10 <= endTime <= 18
    // MODIFIES: this
    // EFFECTS: sets the end time of the event
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    // MODIFIES: this
    // EFFECTS: sets the date of the event
    public void setDate(String date) {
        this.date = date;
    }

    // MODIFIES: this
    // EFFECTS: adds member to this event
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

    // EFFECTS: returns number of members in this team event
    public int numMembers() {
        return memberList.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("members", membersToJson());
        return json;
    }

    // EFFECTS: returns members in this team event as a JSON array
    private JSONArray membersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Member m : memberList) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }
}
