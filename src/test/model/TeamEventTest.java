package model;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TeamEventTest {

    private TeamEvent testTeamEvent;
    private Member member1;
    private Member member2;
    private Member member3;
    private List<Member> memberList1;
    private List<Member> memberList2;
    private List<Member> memberList3;

    @BeforeEach
    void runBefore() {
        testTeamEvent = new TeamEvent("weekly meeting");
        member1 = new Member("June", 20030609);
        member2 = new Member("Stephen", 20010927);
        member3 = new Member("Anna", 20001028);
        memberList1 = new ArrayList<>();
        memberList1.add(member1);
        memberList2 = new ArrayList<>();
        memberList1.add(member1);
        memberList1.add(member2);
        memberList3 = new ArrayList<>();
        memberList1.add(member1);
        memberList1.add(member2);
        memberList1.add(member3);
    }

    @Test
    void testConstructor() {
        assertEquals("weekly meeting", testTeamEvent.getName());
        assertEquals(0, testTeamEvent.getStartTime());
        assertEquals(0, testTeamEvent.getEndTime());
        assertEquals(null, testTeamEvent.getMemberList());
    }

    @Test
    void testChangeNameTo() {
        testTeamEvent.changeNameTo("monthly meeting");
        assertEquals("monthly meeting", testTeamEvent.getName());
        testTeamEvent.changeNameTo("birthday party");
        assertEquals("birthday party", testTeamEvent.getName());
    }

    @Test
    void testSetStartTime() {
        testTeamEvent.setStartTime(9);
        assertEquals(9, testTeamEvent.getStartTime());
        testTeamEvent.setStartTime(13);
        assertEquals(13, testTeamEvent.getStartTime());
        testTeamEvent.setStartTime(17);
        assertEquals(17, testTeamEvent.getStartTime());
    }

    @Test
    void testSetEndTime() {
        testTeamEvent.setEndTime(10);
        assertEquals(10, testTeamEvent.getEndTime());
        testTeamEvent.setEndTime(11);
        assertEquals(11, testTeamEvent.getEndTime());
        testTeamEvent.setEndTime(18);
        assertEquals(18, testTeamEvent.getEndTime());
    }

    @Test
    void testAddMember() {
        testTeamEvent.addMember(member1);
        assertEquals(memberList1, testTeamEvent.getMemberList());
        testTeamEvent.addMember(member2);
        assertEquals(memberList2, testTeamEvent.getMemberList());
        testTeamEvent.addMember(member3);
        assertEquals(memberList3, testTeamEvent.getMemberList());
    }

    @Test
    void testGetName() {
        assertEquals("weekly meeting", testTeamEvent.getName());
        testTeamEvent.changeNameTo("monthly meeting");
        assertEquals("monthly meeting", testTeamEvent.getName());
    }

    @Test
    void testGetStartTime() {
        testTeamEvent.setStartTime(9);
        assertEquals(9, testTeamEvent.getStartTime());
    }

    @Test
    void testGetEndTime() {
        testTeamEvent.setEndTime(10);
        assertEquals(10, testTeamEvent.getEndTime());
    }

    @Test
    void testGetMemberList() {
        testTeamEvent.addMember(member1);
        assertEquals(memberList1, testTeamEvent.getMemberList());
        testTeamEvent.addMember(member2);
    }
}
