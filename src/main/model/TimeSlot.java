package model;

import java.util.List;
import java.util.ArrayList;

// Represents a time slot.
public class TimeSlot {

    private String date;
    private ArrayList<Member> bookedMembers;
    private int startTime;
    private int endTime;
    private ArrayList<TeamEvent> events;

    // EFFECTS: constructs a timeslot with date set to date, 
    //          start time set to startTime, end time set to endTime,
    //          bookedMembers set to an empty list, and is available.
    public TimeSlot(String date, int startTime, int endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookedMembers = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds team event to time slot
    public void addEvent(TeamEvent event) {
        this.events.add(event);
    }

    // MODIFIES: this
    // EFFECTS: returns true if this timeslot is available 
    //          (there is no team event during this timeslot)
    public boolean isAvailable() {
        for (TeamEvent teamEvent: this.events) {
            if (this.startTime >= teamEvent.getStartTime() && this.startTime < teamEvent.getEndTime()) {
                return false;
            } else if (this.endTime > teamEvent.getStartTime() && this.endTime <= teamEvent.getEndTime()) {
                return false;
            }
        }
        return true;
    }

    public String getDate() {
        return this.date;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public List<Member> getBookedMembers() {
        return this.bookedMembers;
    }

    public List<TeamEvent> getEvents() {
        return this.events;
    }

    // REQUIRES: this time slot is available
    // MODIFIES: this
    // EFFECTS: books this time slot
    public void bookSlot(Member member) {
        this.bookedMembers.add(member);
    }

    // MODIFIES: this
    // EFFECTS: returns the number of booked members
    public int getNumberOfBookedMembers() {
        return this.bookedMembers.size();
    }
}
