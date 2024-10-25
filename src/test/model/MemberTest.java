package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberTest {

    private Member testMember;
    private Task task;

    @BeforeEach
    void runBefore() {
        testMember = new Member("June", 20030609);
        task = new Task("edit video");
    }

    @Test
    void testConstructor() {
        assertEquals("June", testMember.getName());
        assertEquals(20030609, testMember.getBday());
        assertEquals(null, testMember.getTask());
    }

    @Test
    void testGetName() {
        assertEquals("June", testMember.getName());
    }

    @Test
    void testGetBday() {
        assertEquals(20030609, testMember.getBday());
    }

    @Test
    void testGetTask() {
        assertEquals(null, testMember.getTask());
        testMember.addTask(task);
        assertEquals(task, testMember.getTask());
    }

    @Test
    void testAddTask() {
        testMember.addTask(task);
        assertEquals(task, testMember.getTask());
    }
}
