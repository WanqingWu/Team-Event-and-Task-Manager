package ui;

import java.io.FileNotFoundException;

// Represents the main class
public class Main {
    public static void main(String[] args) {
        try {
            // new TeamworkApp();
            new TeamworkAppGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
