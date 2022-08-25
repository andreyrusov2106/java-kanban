package ru.yandex.practicum.tasks;

import ru.yandex.practicum.http.HttpTaskManager;
import ru.yandex.practicum.interfaces.HistoryManager;
import ru.yandex.practicum.interfaces.TaskManager;
import ru.yandex.practicum.server.KVServer;

import java.io.IOException;

public class Managers {
    public static TaskManager getDefault() {
        return new HttpTaskManager(8078);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static KVServer getDefaultKVServer() throws IOException {
        KVServer kvServer = new KVServer();
        kvServer.start();
        return kvServer;
    }
}
