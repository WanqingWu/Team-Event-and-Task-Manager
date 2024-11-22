package ui;

import model.TeamProject;
import model.Member;
import model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Represents the team project panel
public class ProjectPanel extends JPanel implements ActionListener {
    private List<TeamProject> teamProjects;
    private List<Task> tasks;
    private TeamworkAppGUI app;

    private JList<String> projectList;
    private DefaultListModel<String> projectListModel;
    private JList<String> unassignedTaskList;
    private DefaultListModel<String> unassignedTaskListModel;
    private JList<String> projectTaskList;
    private DefaultListModel<String> projectTaskListModel;


    private JButton createProjectButton;
    private JButton createTaskButton;
    private JButton addTaskButton;
    private JButton doTaskButton;
    private JButton backButton;

    // EFFECTS: constructs a project panel
    public ProjectPanel(TeamworkAppGUI app, List<TeamProject> teamProjects, List<Member> members, List<Task> tasks) {
        super(new BorderLayout());
        this.app = app;
        this.teamProjects = teamProjects;
        this.tasks = tasks;

        System.out.println("Initializing ProjectPanel...");

        projectListModel = new DefaultListModel<>();
        for (TeamProject p : teamProjects) {
            projectListModel.addElement(formatProject(p));
        }

        unassignedTaskListModel = new DefaultListModel<>();
        if (tasks == null) {
            tasks = new ArrayList<>();
        }

        for (Task t : tasks) {
            unassignedTaskListModel.addElement(formatTask(t));
        }

        projectTaskListModel = new DefaultListModel<>();

        projectList = new JList<>(projectListModel);
        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectList.setLayoutOrientation(JList.VERTICAL);
        projectList.setVisibleRowCount(5);
        projectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshProjectTaskList();
            }
        });
        JScrollPane projectListScroller = new JScrollPane(projectList);

        unassignedTaskList = new JList<>(unassignedTaskListModel);
        unassignedTaskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        unassignedTaskList.setLayoutOrientation(JList.VERTICAL);
        unassignedTaskList.setVisibleRowCount(5);
        JScrollPane unassignedTaskListScroller = new JScrollPane(unassignedTaskList);

        projectTaskList = new JList<>(projectTaskListModel);
        projectTaskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectTaskList.setLayoutOrientation(JList.VERTICAL);
        projectTaskList.setVisibleRowCount(5);
        JScrollPane projectTaskListScroller = new JScrollPane(projectTaskList);

        createProjectButton = new JButton("Create Project");
        createProjectButton.setActionCommand("createProject");
        createProjectButton.addActionListener(this);

        createTaskButton = new JButton("Create Task");
        createTaskButton.setActionCommand("createTask");
        createTaskButton.addActionListener(this);

        addTaskButton = new JButton("Add Task to Project");
        addTaskButton.setActionCommand("addTaskToProject");
        addTaskButton.addActionListener(this);

        doTaskButton = new JButton("Work/Complete Task");
        doTaskButton.setActionCommand("doTask");
        doTaskButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 5, 5));
        buttonPanel.add(createProjectButton);
        buttonPanel.add(createTaskButton);
        buttonPanel.add(addTaskButton);
        buttonPanel.add(doTaskButton);
        buttonPanel.add(backButton);

        JPanel listPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        listPanel.add(projectListScroller);
        listPanel.add(unassignedTaskListScroller);
        listPanel.add(projectTaskListScroller);

        add(listPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);

        System.out.println("ProjectPanel initialized successfully.");
    }

    // EFFECTS: returns a project with name, task information in string
    private String formatProject(TeamProject teamProject) {
        StringBuilder projectDetails = new StringBuilder();
        projectDetails.append(teamProject.getName()).append("\nTasks: ");

        if (teamProject.getTasks().isEmpty()) {
            projectDetails.append("No tasks.");
        } else {
            for (Task t : teamProject.getTasks()) {
                projectDetails.append("\n - Tasks: ").append(t.getName()).append(" | Status: ").append(t.getStatus());

                if (t.getMember() != null) {
                    projectDetails.append(" | Assigned to: ").append(t.getMember());
                } else {
                    projectDetails.append(" | Assigned to: None");
                }
            }
        }

        return projectDetails.toString();
    }

    // EFFECTS: returns a task with name, status, member in string
    private String formatTask(Task task) {
        StringBuilder taskDetails = new StringBuilder();
        taskDetails.append(task.getName()).append(" | Status: ").append(task.getStatus());

        if (task.getMember() != null) {
            taskDetails.append(" | Assigned to: ").append(task.getMember().getName());
        } else {
            taskDetails.append(" | Assigned to: None");
        }

        return taskDetails.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println("Action performed: " + command);

        if ("createProject".equals(command)) {
            createProject();
        } else if ("createTask".equals(command)) {
            createTask();
        } else if ("addTaskToProject".equals(command)) {
            addTaskToProject();
        } else if ("doTask".equals(command)) {
            doTask();
        } else if ("back".equals(command)) {
            app.loadMainPanel();
        }
    }

    // MODIFIES: this
    // EFFECTS: refreshes the team project list
    private void refreshProjectList() {
        projectListModel.clear();
        for (TeamProject p : teamProjects) {
            projectListModel.addElement(formatProject(p));
        }
    }

    // MODIFIES: this
    // EFFECTS: refreshes the unassigned task list
    private void refreshUnassignedTaskList() {
        unassignedTaskListModel.clear();
        for (Task t : tasks) {
            boolean isAssigned = false;
            for (TeamProject p : teamProjects) {
                if (p.getTasks().contains(t)) {
                    isAssigned = true;
                    break;
                }
            }
            if (!isAssigned) {
                unassignedTaskListModel.addElement(formatTask(t));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: refreshes the selected project's task list
    private void refreshProjectTaskList() {
        int selectedIndex = projectList.getSelectedIndex();
        projectTaskListModel.clear();

        if (selectedIndex == -1) {
            return;
        }

        TeamProject selectedProject = teamProjects.get(selectedIndex);
        for (Task t : selectedProject.getTasks()) {
            projectTaskListModel.addElement(formatTask(t));
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new team project
    private void createProject() {
        JTextField nameField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Project Name:"));
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Create Project", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Project name can not be empty.");
                    return;
                }

                TeamProject teampProject = new TeamProject(name);

                teamProjects.add(teampProject);
                refreshProjectList();
                JOptionPane.showMessageDialog(this, "Project created!");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Invalid name");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new task
    private void createTask() {
        JTextField nameField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Task Name:"));
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Create Task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Task name can not be empty.");
                    return;
                }

                Task task = new Task(name);

                if (tasks == null) {
                    tasks = new ArrayList<>();
                }

                tasks.add(task);
                refreshUnassignedTaskList();
                JOptionPane.showMessageDialog(this, "Task created!");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Invalid name");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a task to a selected team project
    private void addTaskToProject() {
        int selectedProjectIndex = projectList.getSelectedIndex();
        int selectedTaskIndex = unassignedTaskList.getSelectedIndex();

        if (selectedProjectIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a project.");
            return;
        }

        if (selectedTaskIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task.");
            return;
        }

        TeamProject selectedProject = teamProjects.get(selectedProjectIndex);
        Task selectedTask = null;

        for (Task t : tasks) {
            if (formatTask(t).equals(unassignedTaskListModel.get(selectedTaskIndex))) {
                selectedTask = t;
                break;
            }
        }

        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "Error: Could not find selected task.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!selectedProject.getTasks().contains(selectedTask)) {
            selectedProject.addTask(selectedTask);
            refreshUnassignedTaskList();
            refreshProjectTaskList();
            refreshProjectList();
            JOptionPane.showMessageDialog(this, "Added " + selectedTask.getName() + " to " + selectedProject.getName());
        } else {
            JOptionPane.showMessageDialog(this, "This task is already added to this project.");
        }
    }

    // MODIFIES: this
    // EFFECTS: works on or completes a task
    private void doTask() {
        int selectedProjectIndex = projectList.getSelectedIndex();
        if (selectedProjectIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a project.");
            return;
        }
        TeamProject selectedProject = teamProjects.get(selectedProjectIndex);
        
        int selectedTaskIndex = projectTaskList.getSelectedIndex();
        if (selectedTaskIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task.");
            return;
        }
        Task selectedTask = selectedProject.getTasks().get(selectedTaskIndex);

        if ("not started".equals(selectedTask.getStatus())) {
            selectedTask.workOnTask();
            JOptionPane.showMessageDialog(this, "Task " + selectedTask.getName() + " is now in progress.");
        } else if ("in progress".equals(selectedTask.getStatus())) {
            selectedTask.completeTask();
            JOptionPane.showMessageDialog(this, "Task " + selectedTask.getName() + " is now completed.");
        } else if ("completed".equals(selectedTask.getStatus())) {
            JOptionPane.showMessageDialog(this, "Task " + selectedTask.getName() + " is already completed.");
        }

        refreshProjectList();

    }
}