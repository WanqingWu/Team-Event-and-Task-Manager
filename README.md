# Team Event and Task Manager

This application helps teams manage their events and projects efficiently. It allows all team members to view upcoming events and track progress on assigned tasks within projects. The application is designed to streamline collaboration by keeping everyone updated on key dates, availability, and task completion.

## Features
- **Event Management:** 
    - Add upcoming team events like meetings, birthday parties, or other events.
    - Team members can view all scheduled events.
    - Members can select available time slots, allowing the app to suggest the best time for most participants.   
- **Team Member Profiles:**
    - Record each team member's name, birthday, task, and additional information.
- **Project Task Assignment:**
    - Team leaders can assign tasks to members within a project.
    - All members can view project tasks and see the current progress (e.g., Task A is completed, Task B is in progress).


## Why is this project of interest to me?
When working on group assignments, it's hard to find a good meeting time just by texting in a group chat, especially when someone’s schedule changes. It's also tough to keep track of the assignments' progress. I want to create a project that makes scheduling easier and helps keep track of group activities.


## User Stories
 - As a user, I want to be able to view the list of upcoming team events.
 - As a user, I want to be able to create a new member, add it to a list of members, and specify the name, birthday, and task.
 - As a user, I want to be able to select a member and add a new statistic for that member, for example, the task they should do.
 - As a user, I want to be able to select a team project and view its tasks and progress.
 - As a user, I want to be able to select a member and mark their task as completed.
 - As a user, I want to be able to view all members in a event.
 - As a user, I want to be able to save a team event and its members to file (if I so choose).
 - As a user, I want to be able to load a team event and its members to file (if I so choose).
 - As a user, I want to be able to save a team project and its tasks/members to file (if I so choose).
 - As a user, I want to be able to load a team project and its tasks/members to file (if I so choose).

## Instructions for End User
- You can generate the first required action related to the user story "adding multiple tasks to a project" by selecting a project from the "Projects" list and then selecting a task from the "Unassigned Tasks" list, and then clicking the "Work/Complete Task" button to change its status.
- You can generate the second required action related to the user story "adding multiple tasks to a project" by highlighting a task in the "Project Tasks" list using the "Highlight Task" button and choosing a color to emphasize its importance.
- You can locate my visual component by observing the background images for the MainPanel, ProjectPanel, MemberPanel, and EventPanel.
- You can save the state of my application by clicking the "Save" button in the main menu.
- You can reload the state of my application by clicking the "Load" button in the main menu.

## Phase 4: Task 2
Logged Events:
Thu Nov 28 23:50:33 PST 2024
Member added to event.
Thu Nov 28 23:50:38 PST 2024
Member added to event.
Thu Nov 28 23:51:04 PST 2024
Task added to project.
Thu Nov 28 23:51:10 PST 2024
Task assigned to member.
Thu Nov 28 23:51:10 PST 2024
Task added to member.
Thu Nov 28 23:51:10 PST 2024
Task assigned to member.

## Phase 4: Task 3
To improve my design, I can replace the String status in Task with an enum (e.g., TaskStatus). This would improve type safety, prevent errors from invalid strings, and enhance code clarity.
