package ru.yandex.practicum.tasks;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.enums.Status;
import ru.yandex.practicum.interfaces.TaskManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest {
    public InMemoryTaskManagerTest() {
        super(new InMemoryTaskManager());
    }
}