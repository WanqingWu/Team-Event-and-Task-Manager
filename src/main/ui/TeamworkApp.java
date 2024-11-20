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
import persistence.JsonReader;
import persistence.JsonWriter;

// Represents the teamwork application
public class TeamworkApp {
    private static final String JSON_STORE = "./data/teamevent.json";
    private Scanner input;
    private TeamEvent teamEvent;
    private Member member;
    private Task task;
    private TeamProject teamProject;
    private List<Member> members;
    private List<Task> tasks;
    private List<TeamEvent> teamEvents;
    private List<TeamProject> teamProjects;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: tuns the teamwork application
    public TeamworkApp() throws FileNotFoundException {
        members = new ArrayList<>();
        tasks = new ArrayList<>();
        teamEvents = new ArrayList<>();
        teamProjects = new ArrayList<>();
        teamEvent = new TeamEvent("team event");
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
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
            doCreateMember();
        } else if (command.equals("t")) {
            doCreateTask();
        } else if (command.equals("e")) {
            doCreateTeamEvent();
        } else if (command.equals("p")) {
            doCreateTeamProject();
        } else if (command.equals("a")) {
            doAssignTaskToMember();
        } else if (command.equals("attp")) {
            doAddTaskToTeamProject();
        } else if (command.equals("amtt")) {
            doAddMemberToTeamEvent();
        } else if (command.equals("d")) {
            doDoTask();
        } else if (command.equals("vm")) {
            doViewMembers();
        } else if (command.equals("ve")) {
            doViewEvents();
        } else if (command.equals("vt")) {
            doViewTasks();
        } else if (command.equals("vp")) {
            doViewProjects();
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
        System.out.println("\tm -> create member");
        System.out.println("\tt -> create task");
        System.out.println("\te -> create team event");
        System.out.println("\tp -> create term project");
        System.out.println("\ta -> assign task to member");
        System.out.println("\tattp -> add task to team project");
        System.out.println("\tamtt -> add member to team event");
        System.out.println("\td -> do task");
        System.out.println("\tvm -> view members");
        System.out.println("\tvt -> view tasks");
        System.out.println("\tve -> view events");
        System.out.println("\tvp -> view projects");
        System.out.println("\tsave -> save to file");
        System.out.println("\tload -> load from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: creates a member and adds them to the member list
    private void doCreateMember() {
        System.out.print("Enter member's name:");
        String name = input.next();
        System.out.print("Enter member's birthday(xxxx):");
        int bday = input.nextInt();
        Member member = new Member(name, bday);
        members.add(member);
    }

    // MODIFIES: this
    // EFFECTS: creates a task and adds them to task list
    private void doCreateTask() {
        System.out.print("Enter task name:");
        String name = input.next();
        Task task = new Task(name);
        tasks.add(task);
    }

    // MODIFIES: this
    // EFFECTS: creates a team event and adds them to team event list
    private void doCreateTeamEvent() {
        System.out.print("Enter team event name:");
        String name = input.next();
        TeamEvent teamEvent = new TeamEvent(name);
        teamEvents.add(teamEvent);
    }

    // MODIFIES: this
    // EFFECTS: creates a team project and adds them to team project list
    private void doCreateTeamProject() {
        System.out.print("Enter team project name:");
        String name = input.next();
        TeamProject teamProject = new TeamProject(name);
        teamProjects.add(teamProject);
    }


    // MODIFIES: this
    // EFFECTS: assigns a selected task to a selected member
    private void doAssignTaskToMember() {
        Task selectedTask = selectTask();
        Member selectedMember = selectMember();

        if (selectedMember.getTask() == null) {
            selectedTask.assignTaskTo(selectedMember);
        } else {
            System.out.print("This member already has a task to do.");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a task to a team project
    private void doAddTaskToTeamProject() {
        Task selectedTask = selectTask();
        TeamProject selectedTeamProject = selectTeamProject();

        selectedTeamProject.addTask(selectedTask);
    }

    // MODIFIES: this
    // EFFECTS: adds a selected member to a selected team event
    private void doAddMemberToTeamEvent() {
        Member selectedMember = selectMember();
        TeamEvent selectedTeamEvent = selectTeamEvent();

        selectedTeamEvent.addMember(selectedMember);
    }

    // MODIFIES: this
    // EFFECTS: works on or completes a task
    private void doDoTask() {
        Task selectedTask = selectTask();
        if (selectedTask.getStatus() == "not started") {
            selectedTask.workOnTask();
        } else if (selectedTask.getStatus() == "in progress") {
            selectedTask.completeTask();
        }
    }

    // MODIFIES: this
    // EFFECTS: views all members under selected team event
    private void doViewMembers() {
        List<String> memberNameList = new ArrayList<>();
        TeamEvent selectedTeamEvent = selectTeamEvent();

        for (Member member: selectedTeamEvent.getMemberList()) {
            memberNameList.add(member.getName());
        }
        System.out.println(memberNameList);
    }

    // MODIFIES: this
    // EFFECTS: views all events
    private void doViewEvents() {
        List<String> teamEventNameList = new ArrayList<>();
        for (TeamEvent teamEvent: teamEvents) {
            teamEventNameList.add(teamEvent.getName());
        }
        System.out.println(teamEventNameList);
    }

    // MODIFIES: this
    // EFFECTS: views all tasks in one team project
    private void doViewTasks() {
        List<String> unstartedTaskNameList = new ArrayList<>();
        List<String> inProgressTaskNameList = new ArrayList<>();
        List<String> completedTaskNameList = new ArrayList<>();
        
        TeamProject selectedTeamProject = selectTeamProject();

        for (Task task: selectedTeamProject.showUnstartedTasks()) {
            unstartedTaskNameList.add(task.getName());
        }
        System.out.println("All unstarted tasks:");
        System.out.println(unstartedTaskNameList);
        
        for (Task task: selectedTeamProject.showInProgressTasks()) {
            inProgressTaskNameList.add(task.getName());
        }
        System.out.println("All tasks in progress:");
        System.out.println(inProgressTaskNameList);

        for (Task task: selectedTeamProject.showCompletedTasks()) {
            completedTaskNameList.add(task.getName());
        }
        System.out.println("All completed tasks:");
        System.out.println(completedTaskNameList);
    }

    // MODIFIES: this
    // EFFECTS: views all projects
    private void doViewProjects() {
        List<String> teamProjectNameList = new ArrayList<>();
        for (TeamProject teamProject: teamProjects) {
            teamProjectNameList.add(teamProject.getName());
        }
        System.out.println(teamProjectNameList);
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

    // EFFECTS: saves everything to file
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.writeTeamEvent(teamEvent);
            jsonWriter.writeMember(member);
            jsonWriter.writeTask(task);
            jsonWriter.writeTeamProject(teamProject);
            jsonWriter.close();
            // System.out.println("Saved " + teamEvent.getName() + " to " + JSON_STORE);
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void load() {
        try {
            teamEvent = jsonReader.readTeamEvent();
            member = jsonReader.readMember();
            task = jsonReader.readTask();
            teamProject = jsonReader.readTeamProject();
            // System.out.println("Loaded " + teamEvent.getName() + " from " + JSON_STORE);
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
