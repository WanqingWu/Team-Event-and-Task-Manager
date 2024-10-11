package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskTest {

    private Member member1;
    private Member member2;
    private Task testTask;

    @BeforeEach
    void runBefore() {
        testTask = new Task("edit video");
        member1 = new Member("June", 20030609);
        member2 = new Member("Stephen", 20010927);
    }

    @Test
    void testConstructor() {
        assertEquals("edit video", testTask.getName());
        assertEquals("not started", testTask.getStatus());
        assertEquals(null, testTask.getMember());
    }

    @Test
    void testGetName() {
        assertEquals("edit video", testTask.getName());
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
        testTask.assignTaskTo(member1);
        assertEquals(member1, testTask.getMember());
        testTask.assignTaskTo(member2);
        assertEquals(member2, testTask.getMember());
    }

    @Test
    void testChangeNameTo() {
        testTask.changeNameTo("write report");
        assertEquals("write report", testTask.getName());
        testTask.changeNameTo("analyze data");
        assertEquals("analyze data", testTask.getName());
    }

    @Test
    void testAssignTaskTo() {
        testTask.assignTaskTo(member1);
        assertEquals(member1, testTask.getMember());
        testTask.assignTaskTo(member2);
        assertEquals(member2, testTask.getMember());
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
