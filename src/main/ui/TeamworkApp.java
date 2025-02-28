package ui;

import java.util.Scanner;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import model.Member;
import model.Task;
import model.TeamEvent;
import model.TeamProject;
import model.TeamData;
import persistence.JSONReader;
import persistence.JsonWriter;

// Represents the teamwork application
public class TeamworkApp {
    private static final String JSON_STORE = "./data/teamData.json";
    private Scanner input;
    private List<TeamEvent> teamEvents;
    private List<TeamProject> teamProjects;
    private List<Member> members;
    private List<Task> tasks;
    private JsonWriter jsonWriter;
    private JSONReader jsonReader;

    // EFFECTS: tuns the teamwork application
    public TeamworkApp() throws FileNotFoundException {
        teamEvents = new ArrayList<>();
        teamProjects = new ArrayList<>();
        members = new ArrayList<>();
        tasks = new ArrayList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JSONReader(JSON_STORE);
        runTeamwork();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTeamwork() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    @SuppressWarnings("methodlength")
    private void processCommand(String command) {
        if (command.equals("m")) {
            manageMembers();
        } else if (command.equals("e")) {
            manageTeamEvents();
        } else if (command.equals("p")) {
            manageTeamProjects();
        } else if (command.equals("v")) {
            viewTeamData();
        } else if (command.equals("save")) {
            save();
        } else if (command.equals("load")) {
            load();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tm -> manage member");
        System.out.println("\te -> manage team events");
        System.out.println("\tp -> manage team projects");
        System.out.println("\tv -> view team data");
        System.out.println("\tsave -> save team data to file");
        System.out.println("\tload -> load team data to file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: manages member related actions
    private void manageMembers() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> create a new member");
        System.out.println("\ta -> assign a task to a member");
        System.out.println("\tv -> view all members");
        System.out.println("\tb -> back to main menu");

        String command = input.next().toLowerCase();

        if (command.equals("c")) {
            createMember();
        } else if (command.equals("a")) {
            assignTaskToMember();
        } else if (command.equals("v")) {
            viewMembers();
        } else if (command.equals("b")) {
            return;
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a member and adds them to the member list
    private void createMember() {
        System.out.print("Enter member's name:");
        String name = input.next();
        System.out.print("Enter member's birthday(xxxx):");
        int bday = input.nextInt();
        Member member = new Member(name, bday);
        members.add(member);
        System.out.println("Created team member: " + name);
    }

    // MODIFIES: this
    // EFFECTS: assigns a selected task to a selected member
    private void assignTaskToMember() {
        Task selectedTask = selectTask();
        Member selectedMember = selectMember();

        if (selectedMember.getTask() == null) {
            selectedTask.assignTaskTo(selectedMember);
            System.out.println("Assigned " + selectedTask.getName() + " to " + selectedMember.getName());
        } else {
            System.out.print("This member already has a task to do.");
        }
    }

    // MODIFIES: this
    // EFFECTS: views all members in the team
    private void viewMembers() {
        List<String> memberNameList = new ArrayList<>();

        for (Member member: members) {
            memberNameList.add(member.getName());
        }
        System.out.println("All members in the team: " + memberNameList);
    }

    // MODIFIES: this
    // EFFECTS: manages team event related actions
    private void manageTeamEvents() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> create a new team event");
        System.out.println("\tv -> view all team events");
        System.out.println("\ta -> add a member to an event");
        System.out.println("\tvm -> view event members");
        System.out.println("\tb -> back to main menu");

        String command = input.next().toLowerCase();

        if (command.equals("c")) {
            creatTeamEvent();
        } else if (command.equals("v")) {
            viewTeamEvents();
        } else if (command.equals("a")) {
            addMemberToEvent();
        } else if (command.equals("vm")) {
            viewEventMembers();
        } else if (command.equals("b")) {
            return;
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a team event and adds them to team event list
    private void creatTeamEvent() {
        System.out.print("Enter team event name:");
        String name = input.next();
        TeamEvent teamEvent = new TeamEvent(name);
        teamEvents.add(teamEvent);
        System.out.println("Created team event: " + name);
    }

    // MODIFIES: this
    // EFFECTS: views all events
    private void viewTeamEvents() {
        if (teamEvents.isEmpty()) {
            System.out.println("No events available.");
            return;
        }

        System.out.println("\nTeam events:");

        for (int i = 0; i < teamEvents.size(); i++) {
            TeamEvent teamEvent = teamEvents.get(i);
            System.out.println((i + 1) + ". Event name: " + teamEvent.getName());
            System.out.println("   Date: " + teamEvent.getDate());
            System.out.println("   Start time: " + teamEvent.getStartTime());
            System.out.println("   End time: " + teamEvent.getEndTime());
            System.out.println("   Members: ");
            if (teamEvent.getMemberList().isEmpty()) {
                System.out.println("No members yet.");
            } else {
                for (Member m : teamEvent.getMemberList()) {
                    System.out.println("      -" + m.getName());
                }
            }
            System.out.println();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a selected member to a selected team event
    private void addMemberToEvent() {
        Member selectedMember = selectMember();
        TeamEvent selectedTeamEvent = selectTeamEvent();
        selectedTeamEvent.addMember(selectedMember);
        System.out.println("Added " + selectedMember.getName() + " to " + selectedTeamEvent.getName());
    }

    // MODIFIES: this
    // EFFECTS: views all members under selected team event
    private void viewEventMembers() {
        List<String> memberNameList = new ArrayList<>();
        TeamEvent selectedTeamEvent = selectTeamEvent();

        for (Member member: selectedTeamEvent.getMemberList()) {
            memberNameList.add(member.getName());
        }
        System.out.println("Members in " + selectedTeamEvent.getName() + ": " + memberNameList);
    }

    // MODIFIES: this
    // EFFECTS: manages team project related actions
    @SuppressWarnings("methodlength")
    private void manageTeamProjects() {
        System.out.println("\nSelect from:");
        System.out.println("\tcp -> create a new team project");
        System.out.println("\tv -> view all team projects");
        System.out.println("\tct -> create a new task");
        System.out.println("\ta -> add a task to a team project");
        System.out.println("\td -> do a task");
        System.out.println("\tvt -> view project tasks");
        System.out.println("\tb -> back to main menu");

        String command = input.next().toLowerCase();

        if (command.equals("cp")) {
            createTeamProject();
        } else if (command.equals("ct")) {
            creatTask();
        } else if (command.equals("a")) {
            addTaskToProject();
        } else if (command.equals("v")) {
            viewTeamProjects();
        } else if (command.equals("d")) {
            doTask();
        } else if (command.equals("vt")) {
            viewProjectTasks();
        } else if (command.equals("b")) {
            return;
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a team project and adds them to team project list
    private void createTeamProject() {
        System.out.print("Enter team project name:");
        String name = input.next();
        TeamProject teamProject = new TeamProject(name);
        teamProjects.add(teamProject);
        System.out.println("Created team project: " + name);
    }

    // MODIFIES: this
    // EFFECTS: creates a task and adds them to task list
    private void creatTask() {
        System.out.print("Enter task name:");
        String name = input.next();
        Task task = new Task(name);
        tasks.add(task);
    }

    // MODIFIES: this
    // EFFECTS: views all projects
    @SuppressWarnings("methodlength")
    private void viewTeamProjects() {
        if (teamProjects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }
 
        System.out.println("\nTeam projects:");
 
        for (int i = 0; i < teamProjects.size(); i++) {
            TeamProject teamProject = teamProjects.get(i);
            System.out.println((i + 1) + ". Project name: " + teamProject.getName());
            System.out.println("   Tasks in project: " + teamProject.getName());
            List<Task> tasks = teamProject.getTasks();
            if (tasks.isEmpty()) {
                System.out.println("   No tasks available in this project.");
            } else {
                for (Task task : tasks) {
                    String memberName;
                    if (task.getMember() != null) {
                        memberName = task.getMember().getName();
                    } else {
                        memberName = "unassigned";
                    }
                    System.out.println("   - " + task.getName());
                    System.out.println("     Status: " + task.getStatus());
                    System.out.println("     Assigned to: " + memberName);
                    System.out.println();
                }
            }
            System.out.println();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a task to a team project
    private void addTaskToProject() {
        Task selectedTask = selectTask();
        TeamProject selectedTeamProject = selectTeamProject();

        selectedTeamProject.addTask(selectedTask);
        System.out.println("Added " + selectedTask.getName() + " to " + selectedTeamProject.getName());
    }

    // MODIFIES: this
    // EFFECTS: views all tasks in one team project
    private void viewProjectTasks() {
        if (teamProjects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        TeamProject teamProject = selectTeamProject();
        System.out.println("    Tasks in project: " + teamProject.getName());

        List<Task> tasks = teamProject.getTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks available in this project.");
        } else {
            for (Task task : tasks) {
                String memberName;
                if (task.getMember() != null) {
                    memberName = task.getMember().getName();
                } else {
                    memberName = "unassigned";
                }
                System.out.println("    - " + task.getName());
                System.out.println("    Status: " + task.getStatus());
                System.out.println("    Assigned to: " + memberName);
                System.out.println();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: works on or completes a task
    private void doTask() {
        Task selectedTask = selectTask();
        if (selectedTask.getStatus() == "not started") {
            selectedTask.workOnTask();
        } else if (selectedTask.getStatus() == "in progress") {
            selectedTask.completeTask();
        }
    }

    // MODIFIES: this
    // EFFECTS: views all team data
    private void viewTeamData() {
        System.out.println("Team data:");
        viewTeamEvents();
        viewTeamProjects();
    }

    // EFFECTS: saves everything to file
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.writeTeamData(teamEvents, teamProjects, members);
            jsonWriter.close();
            System.out.println("Saved team data to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void load() {
        try {
            TeamData teamData = jsonReader.readTeamData();
            teamEvents = teamData.getTeamEvents();
            teamProjects = teamData.getTeamProjects();
            System.out.println("Loaded team data from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: prompts user to select a member and returns it
    private Member selectMember() {
        int selection = -1;  // force entry into loop
        List<String> memberNameList = new ArrayList<>();

        while (!(selection >= 0 && selection < members.size())) {
            System.out.println("Choose one member from:");
            for (Member member: members) {
                memberNameList.add(member.getName());
            }
            System.out.println(memberNameList);
            System.out.println("Please enter a valid index.");
            selection = input.nextInt();
        }

        return members.get(selection);
    }

    // EFFECTS: prompts user to select a team project and returns it
    private TeamProject selectTeamProject() {
        int selection = -1;  // force entry into loop
        List<String> teamProjectNameList = new ArrayList<>();

        while (!(selection >= 0 && selection < teamProjects.size())) {
            System.out.println("Choose one team project from:");
            for (TeamProject teamProject: teamProjects) {
                teamProjectNameList.add(teamProject.getName());
            }
            System.out.println(teamProjectNameList);
            System.out.println("Please enter a valid index.");
            selection = input.nextInt();
        }

        return teamProjects.get(selection);
    }

    // EFFECTS: prompts user to select a task and returns it
    private Task selectTask() {
        int selection = -1;  // force entry into loop
        List<String> taskNameList = new ArrayList<>();

        while (!(selection >= 0 && selection < tasks.size())) {
            System.out.println("Choose one task from:");
            for (Task task: tasks) {
                taskNameList.add(task.getName());
            }
            System.out.println(taskNameList);
            System.out.println("Please enter a valid index.");
            selection = input.nextInt();
        }

        return tasks.get(selection);
    }

    // EFFECTS: prompts user to select a team event and returns it
    private TeamEvent selectTeamEvent() {
        int selection = -1;  // force entry into loop
        List<String> teamEventNameList = new ArrayList<>();

        while (!(selection >= 0 && selection < teamEvents.size())) {
            System.out.println("Choose one event from:");
            for (TeamEvent teamEvent: teamEvents) {
                teamEventNameList.add(teamEvent.getName());
            }
            System.out.println(teamEventNameList);
            System.out.println("Please enter a valid index.");
            selection = input.nextInt();
        }

        return teamEvents.get(selection);
    }
}
