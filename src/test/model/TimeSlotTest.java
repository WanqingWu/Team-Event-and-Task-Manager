package model;

import java.util.List;
import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeSlotTest {

    private TimeSlot testTimeSlot1;
    private TimeSlot testTimeSlot2;
    private TimeSlot testTimeSlot3;

    private TeamEvent teamEvent1;
    private TeamEvent teamEvent2;

    private List<TeamEvent> events0;
    private List<TeamEvent> events1;
    private List<TeamEvent> events2;

    private List<Member> bookedMembers0;
    private List<Member> bookedMembers1;
    private List<Member> bookedMembers2;
    private List<Member> bookedMembers3;

    private Member member1;
    private Member member2;
    private Member member3;

    @BeforeEach
    void runBefore() {
        testTimeSlot1 = new TimeSlot("Oct 28", 9, 11);
        testTimeSlot2 = new TimeSlot("Oct 28", 15, 16);
        testTimeSlot3 = new TimeSlot("Oct 28", 13, 16);

        teamEvent1 = new TeamEvent("party");
        teamEvent1.setStartTime(10);
        teamEvent1.setEndTime(13);
        teamEvent2 = new TeamEvent("meeting");
        teamEvent2.setStartTime(15);
        teamEvent2.setEndTime(16);

        events0 = new ArrayList<>();
        events1 = new ArrayList<>();
        events1.add(teamEvent1);
        events2 = new ArrayList<>();
        events2.add(teamEvent1);
        events2.add(teamEvent2);

        member1 = new Member("June", 20030609);
        member2 = new Member("Stephen", 20010927);
        member3 = new Member("Mina", 20040506);
        
        bookedMembers0 = new ArrayList<>();
        bookedMembers1 = new ArrayList<>();
        bookedMembers1.add(member1);
        bookedMembers2 = new ArrayList<>();
        bookedMembers2.add(member1);
        bookedMembers2.add(member2);
        bookedMembers3 = new ArrayList<>();
        bookedMembers3.add(member1);
        bookedMembers3.add(member2);
        bookedMembers3.add(member3);
    }

    @Test
    void testConstructor() {
        assertEquals("Oct 28", testTimeSlot1.getDate());
        assertEquals(9, testTimeSlot1.getStartTime());
        assertEquals(11, testTimeSlot1.getEndTime());
        assertEquals(bookedMembers0, testTimeSlot1.getBookedMembers());
        assertEquals(true, testTimeSlot1.isAvailable());
        assertEquals(events0, testTimeSlot1.getEvents());
    }

    @Test
    void testAddEvent() {
        assertEquals(events0, testTimeSlot1.getEvents());
        testTimeSlot1.addEvent(teamEvent1);
        assertEquals(events1, testTimeSlot1.getEvents());
        testTimeSlot1.addEvent(teamEvent2);
        assertEquals(events2, testTimeSlot1.getEvents());
    }

    @Test
    void testIsAvailable() {
        testTimeSlot1.addEvent(teamEvent1);
        assertEquals(false, testTimeSlot1.isAvailable());
        testTimeSlot2.addEvent(teamEvent1);
        assertEquals(true, testTimeSlot2.isAvailable());
        testTimeSlot3.addEvent(teamEvent1);
        assertEquals(true, testTimeSlot3.isAvailable());
        
        testTimeSlot2.addEvent(teamEvent2);
        testTimeSlot3.addEvent(teamEvent2);
        assertEquals(false, testTimeSlot2.isAvailable());
        assertEquals(false, testTimeSlot3.isAvailable());
    }

    @Test
    void testBookSlot() {
        testTimeSlot1.bookSlot(member1);
        assertEquals(bookedMembers1, testTimeSlot1.getBookedMembers());
        testTimeSlot1.bookSlot(member2);
        assertEquals(bookedMembers2, testTimeSlot1.getBookedMembers());
        testTimeSlot2.bookSlot(member1);
        testTimeSlot2.bookSlot(member2);
        testTimeSlot2.bookSlot(member3);
        assertEquals(bookedMembers3, testTimeSlot2.getBookedMembers());
    }

    @Test
    void testGetNumberOfBookedMembers() {
        assertEquals(0, testTimeSlot1.getNumberOfBookedMembers());
        testTimeSlot1.bookSlot(member1);
        assertEquals(1, testTimeSlot1.getNumberOfBookedMembers());
        testTimeSlot1.bookSlot(member2);
        assertEquals(2, testTimeSlot1.getNumberOfBookedMembers());
        testTimeSlot1.bookSlot(member3);
        assertEquals(3, testTimeSlot1.getNumberOfBookedMembers());
    }
        
}
