package ui;

import model.Member;
import model.Task;
import model.TeamProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Represents the member panel
public class MemberPanel extends ImagePanel implements ActionListener {
    private List<Member> members;
    private List<Task> tasks;
    private List<TeamProject> teamProjects;
    private TeamworkAppGUI app;

    private JList<String> memberList;
    private DefaultListModel<String> memberListModel;

    private JButton createMemberButton;
    private JButton assignTaskButton;
    private JButton viewAllMembersButton;
    private JButton backButton;

    // EFFECTS: constructs a member panel
    @SuppressWarnings("methodlength")
    public MemberPanel(TeamworkAppGUI app, List<Member> members, List<Task> tasks, List<TeamProject> teamProjects) {
        super("data/images/background3.jpg");
        this.app = app;
        this.members = members;
        this.tasks = tasks;
        this.teamProjects = teamProjects;

        memberListModel = new DefaultListModel<>();
        for (Member m : members) {
            memberListModel.addElement(formatMember(m));
        }

        memberList = new JList<>(memberListModel);
        memberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        memberList.setLayoutOrientation(JList.VERTICAL);
        memberList.setVisibleRowCount(10);
        JScrollPane listScroller = new JScrollPane(memberList);

        createMemberButton = new JButton("Create Member");
        createMemberButton.setActionCommand("createMember");
        createMemberButton.addActionListener(this);

        assignTaskButton = new JButton("Assign Task to Member");
        assignTaskButton.setActionCommand("assignTaskToMember");
        assignTaskButton.addActionListener(this);

        viewAllMembersButton = new JButton("View All Members");
        viewAllMembersButton.setActionCommand("viewAllMembers");
        viewAllMembersButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 5, 5));
        buttonPanel.add(createMemberButton);
        buttonPanel.add(assignTaskButton);
        buttonPanel.add(viewAllMembersButton);
        buttonPanel.add(backButton);

        add(listScroller, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    // EFFECTS: returns member with name, birthday, task information in string
    private String formatMember(Member member) {
        String taskName;
        if (member.getTask() != null) {
            taskName = member.getTask().getName();
        } else {
            taskName = "No task assigned";
        }
        return member.getName() + " | Birthday: " + member.getBday() + " | Task: " + taskName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("createMember".equals(command)) {
            createMember();
        } else if ("assignTaskToMember".equals(command)) {
            assignTaskToMember();
        } else if ("viewAllMembers".equals(command)) {
            viewAllMembers();
        } else if ("back".equals(command)) {
            app.loadMainPanel();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new member with name and birthday
    @SuppressWarnings("methodlength")
    private void createMember() {
        JTextField nameField = new JTextField();
        JTextField birthdayField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Member Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Birthday (YYYYMMDD):"));
        panel.add(birthdayField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Create Member", 
                                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int birthday = Integer.parseInt(birthdayField.getText());

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Member name can not be empty.");
                    return;
                }

                Member member = new Member(name, birthday);
                members.add(member);
                memberListModel.addElement(formatMember(member));
                JOptionPane.showMessageDialog(this, "Member created!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid birthday.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: assigns a selected task to a selected member
    private void assignTaskToMember() {
        List<Task> unassignedTasks = getAllUnassignedTasks();

        if (unassignedTasks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No unassigned tasks available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedIndex = memberList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member.");
            return;
        }

        Member selectedMember = members.get(selectedIndex);
        Task selectedTask = selectTask(unassignedTasks);

        if (selectedTask != null) {
            if (selectedMember.getTask() == null) {
                selectedTask.assignTaskTo(selectedMember);
                JOptionPane.showMessageDialog(this, "Assigned " + selectedTask.getName() 
                                                + " to " + selectedMember.getName());
                refreshMemberList();
            } else {
                JOptionPane.showMessageDialog(this, "This member already has a task.");
            }
        }
    }

    // EFFECTS: returns all unassigned tasks from global tasks
    private List<Task> getAllUnassignedTasks() {
        List<Task> unassignedTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getMember() == null) {
                unassignedTasks.add(task);
            }
        }
        for (TeamProject project : teamProjects) {
            for (Task task : project.getTasks()) {
                if (task.getMember() == null) {
                    unassignedTasks.add(task);
                }
            }
        }
        return unassignedTasks;
    }

    // MODIFIES: this
    // EFFECTS: views all members
    private void viewAllMembers() {
        StringBuilder allMembers = new StringBuilder("All members:\n");
        if (members.isEmpty()) {
            allMembers.append("No members available.");
        } else {
            for (Member m : members) {
                allMembers.append("- ").append(formatMember(m)).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, allMembers.toString(), "All Members", JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: refreshed the member list
    private void refreshMemberList() {
        memberListModel.clear();
        for (Member m : members) {
            memberListModel.addElement(formatMember(m));
        }
    }

    // EFFECTS: prompts user to select a task and returns it
    private Task selectTask(List<Task> unassignedTasks) {
        List<String> taskNames = new ArrayList<>();
        for (Task task : unassignedTasks) {
            taskNames.add(task.getName());
        }

        Object[] taskArray = taskNames.toArray();
        String selectedTaskName = (String) JOptionPane.showInputDialog(this, "Choose a task:",
                                                                         "Select Task", JOptionPane.PLAIN_MESSAGE, 
                                                                         null, taskArray, taskArray[0]);

        if (selectedTaskName != null) {
            for (Task t : unassignedTasks) {
                if (t.getName().equals(selectedTaskName)) {
                    return t;
                }
            }
        }

        JOptionPane.showMessageDialog(this, "No task selected.", "Info", JOptionPane.INFORMATION_MESSAGE);
        return null;
    }
}
