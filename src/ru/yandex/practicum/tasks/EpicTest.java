package ru.yandex.practicum.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.enums.Status;
import ru.yandex.practicum.interfaces.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    Epic epic1;
    TaskManager taskManager;

    @BeforeEach
    void BeforeEach() {
        taskManager = Managers.getDefault();
        epic1 = new Epic("Epic1", "Epic description1");
        taskManager.createEpic(epic1);
    }

    @Test
    void CheckEmptyEpicStatusEqualsNEW() {
        assertEquals(epic1.getStatus(), Status.NEW);
    }

    @Test
    void CheckAllSubtasksInEpicWithStatusNEWEpicStatusEqualsNEW() {
        SubTask subTask1 = new SubTask("SubTask1", "Subtask description1", Status.NEW, 1);
        SubTask subTask2 = new SubTask("SubTask2", "Subtask description2", Status.NEW, 1);
        SubTask subTask3 = new SubTask("SubTask3", "Subtask description3", Status.NEW, 1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);
        assertEquals(epic1.getStatus(), Status.NEW);
    }

    @Test
    void CheckAllSubtasksInEpicWithStatusDONEEpicStatusEqualsDONE() {
        SubTask subTask1 = new SubTask("SubTask1", "Subtask description1", Status.DONE, 1);
        SubTask subTask2 = new SubTask("SubTask2", "Subtask description2", Status.DONE, 1);
        SubTask subTask3 = new SubTask("SubTask3", "Subtask description3", Status.DONE, 1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);
        assertEquals(epic1.getStatus(), Status.DONE);
    }

    @Test
    void CheckSubtasksInEpicWithStatusDONEAndNewEpicStatusEqualsINPROGRESS() {
        SubTask subTask1 = new SubTask("SubTask1", "Subtask description1", Status.NEW, 1);
        SubTask subTask2 = new SubTask("SubTask2", "Subtask description2", Status.DONE, 1);
        SubTask subTask3 = new SubTask("SubTask3", "Subtask description3", Status.DONE, 1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);
        assertEquals(epic1.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    void CheckAllSubtasksInEpicWithStatusINPROGRESSEpicStatusEqualsINPROGRESS() {
        SubTask subTask1 = new SubTask("SubTask1", "Subtask description1", Status.IN_PROGRESS, 1);
        SubTask subTask2 = new SubTask("SubTask2", "Subtask description2", Status.IN_PROGRESS, 1);
        SubTask subTask3 = new SubTask("SubTask3", "Subtask description3", Status.IN_PROGRESS, 1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);
        assertEquals(epic1.getStatus(), Status.IN_PROGRESS);
    }
}