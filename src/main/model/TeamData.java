package model;

import java.util.List;

// Represents team data containing team events and team projects.
public class TeamData {
    private List<TeamEvent> teamEvents;
    private List<TeamProject> teamProjects;
    private List<Member> members;

    // EFFECTS: Constructs a team data containing team events and team projects
    public TeamData(List<TeamEvent> teamEvents, List<TeamProject> teamProjects, List<Member> members) {
        this.teamEvents = teamEvents;
        this.teamProjects = teamProjects;
        this.members = members;
    }

    public List<TeamEvent> getTeamEvents() {
        return teamEvents;
    }

    public List<TeamProject> getTeamProjects() {
        return teamProjects;
    }

    public List<Member> getMembers() {
        return members;
    }
}
