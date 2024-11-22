package ui;

import model.TeamEvent;
import model.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Represents the team event panel
public class EventPanel extends ImagePanel implements ActionListener {
    private List<TeamEvent> teamEvents;
    private List<Member> members;
    private TeamworkAppGUI app;

    private JList<String> eventList;
    private DefaultListModel<String> eventListModel;

    private JButton createEventButton;
    private JButton addMemberButton;
    private JButton viewMembersButton;
    private JButton backButton;

    // EFFECTS: constructs a event panel
    public EventPanel(TeamworkAppGUI app, List<TeamEvent> teamEvents, List<Member> members) {
        super("data/images/background2.jpg");
        this.app = app;
        this.teamEvents = teamEvents;
        this.members = members;

        eventListModel = new DefaultListModel<>();
        for (TeamEvent e : teamEvents) {
            eventListModel.addElement(formatEvent(e));
        }

        eventList = new JList<>(eventListModel);
        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventList.setLayoutOrientation(JList.VERTICAL);
        eventList.setVisibleRowCount(10);
        JScrollPane listScroller = new JScrollPane(eventList);

        createEventButton = new JButton("Create Event");
        createEventButton.setActionCommand("createEvent");
        createEventButton.addActionListener(this);

        addMemberButton = new JButton("Add Member to Event");
        addMemberButton.setActionCommand("addMemberToEvent");
        addMemberButton.addActionListener(this);

        viewMembersButton = new JButton("View Event Members");
        viewMembersButton.setActionCommand("viewMembers");
        viewMembersButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 5, 5));
        buttonPanel.add(createEventButton);
        buttonPanel.add(addMemberButton);
        buttonPanel.add(viewMembersButton);
        buttonPanel.add(backButton);

        add(listScroller, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    // EFFECTS: returns event with date, start time, end time information in string
    private String formatEvent(TeamEvent teamEvent) {
        StringBuilder eventDetails = new StringBuilder();
        eventDetails.append(teamEvent.getName()).append(" | Date: ").append(teamEvent.getDate())
                                                .append(" | Time: ").append(teamEvent.getStartTime()).append("-").append(teamEvent.getEndTime())
                                                .append("\nMembers: ");
        
        if (teamEvent.getMemberList().isEmpty()) {
            eventDetails.append("None");
        } else{
            for (Member m : teamEvent.getMemberList()) {
                eventDetails.append(m.getName()).append(" ");
            }
        }

        return eventDetails.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("createEvent".equals(command)) {
            createEvent();
        } else if ("addMemberToEvent".equals(command)) {
            addMemberToEvent();
        } else if ("viewMembers".equals(command)) {
            viewEventMembers();
        } else if ("back".equals(command)) {
            app.loadMainPanel();
        }
    }

    // MODIFIES: this
    // EFFECTS: refreshes the event list
    private void refreshEventList() {
        eventListModel.clear();
        for (TeamEvent e : teamEvents) {
            eventListModel.addElement(formatEvent(e));
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a team event with event name, date, start time, end time
    private void createEvent() {
        JTextField nameField = new JTextField();
        JTextField dateField = new JTextField("YYYY-MM-DD");
        JTextField startTimeField = new JTextField("9");
        JTextField endTimeField = new JTextField("18");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Event Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Start Time (9-17):"));
        panel.add(startTimeField);
        panel.add(new JLabel("End Time (10-18):"));
        panel.add(endTimeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Create Event", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String date = dateField.getText();
                int startTime = Integer.parseInt(startTimeField.getText());
                int endTime = Integer.parseInt(endTimeField.getText());

                if (name.isEmpty() || date.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Event name and date can not be empty.");
                    return;
                }

                TeamEvent teamEvent = new TeamEvent(name);
                teamEvent.setDate(date);
                teamEvent.setStartTime(startTime);
                teamEvent.setEndTime(endTime);

                teamEvents.add(teamEvent);
                refreshEventList();
                JOptionPane.showMessageDialog(this, "Event created!");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Invalid start/end time: " + e.getMessage());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a selected member to a selected team event
    private void addMemberToEvent() {
        int selectedIndex = eventList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an event.");
            return;
        }
        TeamEvent selectedEvent = teamEvents.get(selectedIndex);
        Member selectedMember = selectMember();

        if (selectedMember != null) {
            selectedEvent.addMember(selectedMember);
            refreshEventList();
            JOptionPane.showMessageDialog(this, "Added " + selectedMember.getName() + " to " + selectedEvent.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: views all members under selected team event
    private void viewEventMembers() {
        int selectedIndex = eventList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an event.");
            return;
        }

        TeamEvent selectedTeamEvent = teamEvents.get(selectedIndex);
        StringBuilder memberNames = new StringBuilder("Members in " + selectedTeamEvent.getName() + ": \n");
        for (Member m : selectedTeamEvent.getMemberList()) {
            memberNames.append("- ").append(m.getName()).append("\n");
        }
        JOptionPane.showMessageDialog(this, memberNames.toString());
    }

    // EFFECTS: prompts user to select a member and returns it
    private Member selectMember() {
        if (members.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No members available.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        List<String> memberNameList = new ArrayList<>();

        for (Member m : members) {
            memberNameList.add(m.getName());
        }

        Object[] memberArray = memberNameList.toArray();
        String selectedName = (String) JOptionPane.showInputDialog(this, "Choose one member:", "Select Member", JOptionPane.PLAIN_MESSAGE, null, memberArray, memberArray[0]);

        if (selectedName != null) {
            for (Member m : members) {
                if (m.getName().equals(selectedName)) {
                    return m;
                }
            }
        }

        JOptionPane.showMessageDialog(this, "No member selected.", "Info", JOptionPane.INFORMATION_MESSAGE);
        return null;
    }
}
