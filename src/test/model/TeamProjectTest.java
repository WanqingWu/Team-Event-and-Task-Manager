package model;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TeamProjectTest {

    private TeamProject testTeamProject;
    private TeamProject testTeamProject2;

    private Task task1;
    private Task task2;
    private Task task3;

    private List<Task> taskList0;
    private List<Task> taskList1;
    private List<Task> taskList2;
    private List<Task> taskList3;
    private List<Task> taskListOnly2;

    @BeforeEach
    void runBefore() {
        testTeamProject = new TeamProject("Written Assignment");
        testTeamProject2 = new TeamProject("Written Assignment");

        task1 = new Task("q1");
        task2 = new Task("q2");
        task3 = new Task("q3");

        taskList0 = new ArrayList<>();
        taskList1 = new ArrayList<>();
        taskList1.add(task1);
        taskList2 = new ArrayList<>();
        taskList2.add(task1);
        taskList2.add(task2);
        taskList3 = new ArrayList<>();
        taskList3.add(task1);
        taskList3.add(task2);
        taskList3.add(task3);
        taskListOnly2 = new ArrayList<>();
        taskListOnly2.add(task2);

        testTeamProject2.addTask(task1);
        testTeamProject2.addTask(task2);
        testTeamProject2.addTask(task3);
    }

    @Test
    void testConstructor() {
        assertEquals("Written Assignment", testTeamProject.getName());
        assertEquals(taskList0, testTeamProject.getTasks());
    }

    @Test
    void testChangeNameTo() {
        testTeamProject.changeNameTo("written assignment 1");
        assertEquals("written assignment 1", testTeamProject.getName());
        testTeamProject.changeNameTo("written assignment 2");
        assertEquals("written assignment 2", testTeamProject.getName());
    }

    @Test
    void testAddTask() {
        testTeamProject.addTask(task1);
        assertEquals(taskList1, testTeamProject.getTasks());
        testTeamProject.addTask(task2);
        assertEquals(taskList2, testTeamProject.getTasks());
        testTeamProject.addTask(task3);
        assertEquals(taskList3, testTeamProject.getTasks());
    }

    @Test
    void testShowUnstartedTasks() {
        assertEquals(taskList3, testTeamProject2.showUnstartedTasks());
        task3.workOnTask();
        assertEquals(taskList2, testTeamProject2.showUnstartedTasks());
        task3.completeTask();
        task2.workOnTask();
        assertEquals(taskList1, testTeamProject2.showUnstartedTasks());
    }

    @Test
    void testShowInProgressTasks() {
        assertEquals(taskList0, testTeamProject2.showInProgressTasks());
        task1.workOnTask();
        assertEquals(taskList1, testTeamProject2.showInProgressTasks());
        task1.completeTask();
        assertEquals(taskList0, testTeamProject2.showInProgressTasks());
        task2.workOnTask();
        assertEquals(taskListOnly2, testTeamProject2.showInProgressTasks());
    }

    @Test
    void testShowCompletedTasks() {
        assertEquals(taskList0, testTeamProject2.showCompletedTasks());
        task1.workOnTask();
        assertEquals(taskList0, testTeamProject2.showCompletedTasks());
        task1.completeTask();
        task2.workOnTask();
        assertEquals(taskList1, testTeamProject2.showCompletedTasks());
        task2.completeTask();
        task3.workOnTask();
        assertEquals(taskList2, testTeamProject2.showCompletedTasks());
        task3.completeTask();
        assertEquals(taskList3, testTeamProject2.showCompletedTasks());
    }

    @Test
    void testIsProjectCompleted() {
        assertEquals(false, testTeamProject2.isProjectCompleted());
        task1.workOnTask();
        assertEquals(false, testTeamProject2.isProjectCompleted());
        task1.completeTask();
        assertEquals(false, testTeamProject2.isProjectCompleted());
        task2.workOnTask();
        assertEquals(false, testTeamProject2.isProjectCompleted());
        task2.completeTask();
        assertEquals(false, testTeamProject2.isProjectCompleted());
        task3.workOnTask();
        assertEquals(false, testTeamProject2.isProjectCompleted());
        task3.completeTask();
        assertEquals(true, testTeamProject2.isProjectCompleted());
    }
}
