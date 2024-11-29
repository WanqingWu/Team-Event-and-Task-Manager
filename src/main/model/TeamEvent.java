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

        EventLog.getInstance().logEvent(new Event("Team event name changed."));
    }

    // REQUIRES: 9 <= startTime <= 17
    // MODIFIES: this
    // EFFECTS: sets the end time of the event
    public void setStartTime(int startTime) throws IllegalArgumentException {
        if (startTime < 9 || startTime > 17) {
            throw new IllegalArgumentException("startTime must be between 9 and 17.");
        }
        this.startTime = startTime;
    }

    // REQUIRES: endTime > startTime && 10 <= endTime <= 18
    // MODIFIES: this
    // EFFECTS: sets the end time of the event
    public void setEndTime(int endTime) throws IllegalArgumentException {
        if (endTime <= startTime || endTime < 10 || endTime > 18) {
            throw new IllegalArgumentException("endTime must be greater than startTime and between 10 and 18.");
        }
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

        EventLog.getInstance().logEvent(new Event("Member added to event."));
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

    @Override
    // MODIFIES: this
    // EFFECTS: writes member to Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teamEventName", name);
        json.put("date", date);
        json.put("startTime", startTime);
        json.put("endTime", endTime);
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
