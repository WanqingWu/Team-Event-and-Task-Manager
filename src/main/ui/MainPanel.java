package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends ImagePanel implements ActionListener {
    private TeamworkAppGUI app;

    // EFFECTS: constructs a main panel
    public MainPanel(TeamworkAppGUI app) {
        super("data/images/background4.jpg");
        this.app = app;
        setLayout(new GridLayout(3, 2, 10, 10));

        JButton manageMembersButton = new JButton("Manage Members");
        manageMembersButton.setActionCommand("manageMembers");
        manageMembersButton.addActionListener(this);

        JButton manageEventsButton = new JButton("Manage Team Events");
        manageEventsButton.setActionCommand("manageEvents");
        manageEventsButton.addActionListener(this);

        JButton manageProjectsButton = new JButton("Manage Team Projects");
        manageProjectsButton.setActionCommand("manageProjects");
        manageProjectsButton.addActionListener(this);

        JButton saveDataButton = new JButton("Save");
        saveDataButton.setActionCommand("save");
        saveDataButton.addActionListener(this);

        JButton loadDataButton = new JButton("Load");
        loadDataButton.setActionCommand("load");
        loadDataButton.addActionListener(this);

        add(manageMembersButton);
        add(manageEventsButton);
        add(manageProjectsButton);
        add(saveDataButton);
        add(loadDataButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("manageMembers".equals(command)) {
            app.loadMemberPanel();
        } else if ("manageEvents".equals(command)) {
            app.loadEventPanel();
        } else if ("manageProjects".equals(command)) {
            app.loadProjectPanel();
        } else if ("save".equals(command)) {
            app.save();
        } else if ("load".equals(command)) {
            app.load();
        } else {
            JOptionPane.showMessageDialog(null, "Failed to load data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
