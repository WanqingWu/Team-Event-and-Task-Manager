package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskTest {

    private Member member;
    private Task testTask;

    @BeforeEach
    void runBefore() {
        testTask = new Task();
        member = new Member("June", 20030609);
    }

    @Test
    void testConstructor() {
        assertEquals("not started", testTask.getStatus());
        assertEquals(null, testTask.getMember());
    }

    @Test
    void testGetStatus() {
        assertEquals("not started", testTask.getStatus());
        testTask.workOnTask();
        assertEquals("in progress", testTask.getStatus());
        testTask.completeTask();
        assertEquals("completed", testTask.getStatus());
    }

    @Test
    void testGetMember() {
        assertEquals(null, testTask.getMember());
        testTask.assignTaskTo(member);
        assertEquals(member, testTask.getMember());
    }

    @Test
    void testAssignTaskTo() {
        testTask.assignTaskTo(member);
        assertEquals(member, testTask.getMember());
    }

    @Test
    void testWorkOnTask() {
        assertEquals("not started", testTask.getStatus());
        testTask.workOnTask();
        assertEquals("in progress", testTask.getStatus());
    }

    @Test
    void testCompleteTask() {
        assertEquals("not started", testTask.getStatus());
        testTask.workOnTask();
        assertEquals("in progress", testTask.getStatus());
        testTask.completeTask();
        assertEquals("completed", testTask.getStatus());
    }
}
