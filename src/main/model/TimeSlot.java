package model;
// Represents a time slot.

import java.util.ArrayList;

public class TimeSlot {

    public String date;
    public ArrayList<Member> bookedMembers;
    public int startTime;
    public int endTime;
    public boolean isAvailable;

    // EFFECTS: constructs a timeslot with date set to date, 
    //          start time set to startTime, end time set to endTime,
    //          bookedMembers set to an empty list, and is available.
    public TimeSlot(String date, int startTime, int endTime) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: returns true if this timeslot is available 
    //          (there is no team event during this timeslot)
    public boolean isAvailable() {
        return true; //stub
    }

    public String getDate() {
        return ""; // stub
    }

    public int getStartTime() {
        return 0; // stub
    }

    public int getEndTime() {
        return 0; // stub
    }

    public List<Member> getBookedMembers() {
        return null; // stub
    }

    // REQUIRES: this time slot is available
    // MODIFIES: this
    // EFFECTS: books this time slot
    public void bookSlot(Member member) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: returns the number of booked members
    public int getNumberOfBookedMembers() {
        return 0; //stub
    }
}
