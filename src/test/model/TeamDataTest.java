package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TeamDataTest {
    private TeamData testTeamData;
    private TeamProject testTeamProject1;
    private TeamProject testTeamProject2;
    private TeamProject testTeamProject3;
    private TeamEvent testTeamEvent1;
    private TeamEvent testTeamEvent2;
    private List<TeamProject> testTeamProjects;
    private List<TeamEvent> testTeamEvents;
    private Member testMember1;
    private Member testMember2;
    private List<Member> testMembers;


    @BeforeEach
    void runBefore() {
        testTeamProject1 = new TeamProject("project1");
        testTeamProject2 = new TeamProject("project2");
        testTeamProject3 = new TeamProject("project3");
        testTeamEvent1 = new TeamEvent("event1");
        testTeamEvent2 = new TeamEvent("event2");
        testMember1 = new Member("June", 20030609);
        testMember2 = new Member("Stephen", 20010927);

        testTeamEvents = new ArrayList<>();
        testTeamProjects = new ArrayList<>();
        testMembers = new ArrayList<>();

        testTeamEvents.add(testTeamEvent1);
        testTeamEvents.add(testTeamEvent2);

        testTeamProjects.add(testTeamProject1);
        testTeamProjects.add(testTeamProject2);
        testTeamProjects.add(testTeamProject3);

        testMembers.add(testMember1);
        testMembers.add(testMember2);

        testTeamData = new TeamData(testTeamEvents, testTeamProjects, testMembers);
    }

    @Test
    void testConstructor() {
        List<TeamProject> teamProjects = testTeamData.getTeamProjects();
        List<TeamEvent> teamEvents = testTeamData.getTeamEvents();

        assertEquals(3, teamProjects.size());
        assertEquals(testTeamProject1, teamProjects.get(0));
        assertEquals(testTeamProject2, teamProjects.get(1));
        assertEquals(testTeamProject3, teamProjects.get(2));

        assertEquals(2, teamEvents.size());
        assertEquals(testTeamEvent1, teamEvents.get(0));
        assertEquals(testTeamEvent2, teamEvents.get(1));
    }
}
