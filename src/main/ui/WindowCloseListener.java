package ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import model.Event;
import model.EventLog;

// Represents a window close listener class
public class WindowCloseListener implements WindowListener {
    private final EventLog eventLog;

    // Constructs a windowCloseListener
    public WindowCloseListener() {
        this.eventLog = EventLog.getInstance();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    // EFFECTS: prints logged event when closing the window
    @Override
    public void windowClosing(WindowEvent e) {
        printLoggedEvent();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }

    // EFFECTS: prints all logged events in console
    private void printLoggedEvent() {
        System.out.println("Logged Events:");
        for (Event event : eventLog) {
            System.out.println(event.toString());
        }
    }
}