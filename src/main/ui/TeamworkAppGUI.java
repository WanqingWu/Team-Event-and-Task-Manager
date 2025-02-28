package ui;

import model.*;
import persistence.*;

import javax.swing.*;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents the teamwork application (GUI)
public class TeamworkAppGUI {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    private static final String JSON_STORE = "./data/teamData.json";

    private JFrame frame;
    private List<TeamEvent> teamEvents;
    private List<TeamProject> teamProjects;
    private List<Member> members;
    private List<Task> tasks;
    private JsonWriter jsonWriter;
    private JSONReader jsonReader;
    

    // EFFECTS: tuns the teamwork application
    public TeamworkAppGUI() throws FileNotFoundException {
        teamEvents = new ArrayList<>();
        teamProjects = new ArrayList<>();
        members = new ArrayList<>();
        tasks = new ArrayList<>();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JSONReader(JSON_STORE);

        initialize();
    }

    // EFFECTS: initialize the main window
    private void initialize() {
        frame = new JFrame("Teamwork Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(new BorderLayout());
        frame.addWindowListener(new WindowCloseListener());

        loadMainPanel();

        frame.setVisible(true);
    }

    // EFFECTS: loads the main menu panel
    public void loadMainPanel() {
        frame.getContentPane().removeAll();
        frame.add(new MainPanel(this));
        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: loads the member panel
    public void loadMemberPanel() {
        frame.getContentPane().removeAll();
        frame.add(new MemberPanel(this, members, tasks, teamProjects));
        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: loads the event panel
    public void loadEventPanel() {
        frame.getContentPane().removeAll();
        frame.add(new EventPanel(this, teamEvents, members));
        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: loads the project panel
    public void loadProjectPanel() {
        frame.getContentPane().removeAll();
        frame.add(new ProjectPanel(this, teamProjects, members, tasks));
        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: saves the team data to a JSON file
    public void save() {
        try {
            jsonWriter.open();
            jsonWriter.writeTeamData(teamEvents, teamProjects, members);
            jsonWriter.close();
            JOptionPane.showMessageDialog(frame, "Data saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Failed to save data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: loads the team data from a JSON file
    public void load() {
        try {
            TeamData teamData = jsonReader.readTeamData();
            teamEvents = teamData.getTeamEvents();
            teamProjects = teamData.getTeamProjects();
            members = teamData.getMembers();
            JOptionPane.showMessageDialog(frame, "Data loaded!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Failed to load data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
