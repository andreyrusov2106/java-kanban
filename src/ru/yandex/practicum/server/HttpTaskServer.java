package ru.yandex.practicum.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.adapters.LocalDateTimeAdapter;
import ru.yandex.practicum.enums.Status;
import ru.yandex.practicum.interfaces.TaskManager;
import ru.yandex.practicum.tasks.Epic;
import ru.yandex.practicum.tasks.Managers;
import ru.yandex.practicum.tasks.SubTask;
import ru.yandex.practicum.tasks.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private final HttpServer server;
    private final TaskManager taskManager;
    private final Gson gson;

    public HttpTaskServer() throws IOException {
        this(Managers.getDefault());
    }

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        Task task1 = new Task(1, "Task1", "Task1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(15));
        taskManager.createTask(task1);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gson = gsonBuilder.create();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks/task", this::handleTask);
        server.createContext("/tasks/epic", this::handleEpic);
        server.createContext("/tasks/subtask", this::handleSubTask);
        server.createContext("/tasks/history", this::handleHistory);
    }

    private void handleHistory(HttpExchange httpExchange) {
        try {
            System.out.println("\n/tasks/history" + httpExchange.getRequestURI());
            String requestMethod = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String body = readText(httpExchange);
            switch (requestMethod) {
                case "GET": {
                    if (Pattern.matches("^/tasks/history/$", path)) {
                        final String response = gson.toJson(taskManager.getHistory());
                        sendText(httpExchange, response);
                        return;
                    }
                    break;
                }

                default: {
                    System.out.println("/tasks/history/ ждёт GET запрос, а получил " + httpExchange.getRequestMethod());
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }

        } catch (Exception e) {
            System.out.println("Ошибка при обработке");
        } finally {
            httpExchange.close();
        }
    }


    private void handleSubTask(HttpExchange httpExchange) {
        try {
            System.out.println("\n/tasks/subtask" + httpExchange.getRequestURI());
            String requestMethod = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String body = readText(httpExchange);
            switch (requestMethod) {
                case "GET": {
                    if (Pattern.matches("^/tasks/subtask/$", path)) {
                        final String response = gson.toJson(taskManager.getAllSubTasks());
                        sendText(httpExchange, response);
                        return;
                    }
                    if (Pattern.matches("^/tasks/subtask/\\d+$", path)) {
                        String idString = path.replaceFirst("/tasks/subtask/", "");
                        int id = parsePathId(idString);
                        if (id != -1) {
                            final String response = gson.toJson(taskManager.getSubTaskById(id));
                            sendText(httpExchange, response);
                            return;
                        }
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/subtask/\\d+$", path)) {
                        String idString = path.replaceFirst("/tasks/subtask/", "");
                        int id = parsePathId(idString);
                        if (id != -1) {
                            taskManager.removeSubTaskById(id);
                            System.out.println("Удалена подзадача с ID=" + id);
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                    }
                    break;
                }
                case "POST": {
                    if (Pattern.matches("^/tasks/subtask/$", path)) {
                        SubTask s = gson.fromJson(body, SubTask.class);
                        taskManager.createSubTask(s);
                        System.out.println("Подзадача добавлена");
                        httpExchange.sendResponseHeaders(200, 0);
                    }
                    if (Pattern.matches("^/tasks/subtask/\\d+$", path)) {
                        String idString = path.replaceFirst("/tasks/subtask/", "");
                        int id = parsePathId(idString);
                        if (id != -1) {
                            SubTask s = gson.fromJson(body, SubTask.class);
                            taskManager.updateSubTask(s);
                            System.out.println("Подзадача обновлена с ID" + id);
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                    }
                    break;
                }
                default: {
                    System.out.println("/tasks/subtask/ ждёт GET,POST или DELETE-запрос, а получил " + httpExchange.getRequestMethod());
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }

        } catch (Exception e) {
            System.out.println("Ошибка при обработке");
        } finally {
            httpExchange.close();
        }
    }

    private void handleEpic(HttpExchange httpExchange) {
        try {
            System.out.println("\n/tasks/epic" + httpExchange.getRequestURI());
            String requestMethod = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String body = readText(httpExchange);
            switch (requestMethod) {
                case "GET": {
                    if (Pattern.matches("^/tasks/epic/$", path)) {
                        final String response = gson.toJson(taskManager.getAllEpics());
                        sendText(httpExchange, response);
                        return;
                    }
                    if (Pattern.matches("^/tasks/epic/\\d+$", path)) {
                        String idString = path.replaceFirst("/tasks/epic/", "");
                        int id = parsePathId(idString);
                        if (id != -1) {
                            final String response = gson.toJson(taskManager.getEpicById(id));
                            sendText(httpExchange, response);
                            return;
                        }
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/epic/\\d+$", path)) {
                        String idString = path.replaceFirst("/tasks/epic/", "");
                        int id = parsePathId(idString);
                        if (id != -1) {
                            taskManager.removeEpicById(id);
                            System.out.println("Удален эпик с ID=" + id);
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                    }
                    break;
                }
                case "POST": {
                    if (Pattern.matches("^/tasks/epic/$", path)) {
                        Epic e = gson.fromJson(body, Epic.class);
                        taskManager.createEpic(e);
                        System.out.println("Эпик добавлен");
                        httpExchange.sendResponseHeaders(200, 0);
                    }
                    if (Pattern.matches("^/tasks/epic/\\d+$", path)) {
                        String idString = path.replaceFirst("/tasks/epic/", "");
                        int id = parsePathId(idString);
                        if (id != -1) {
                            Epic e = gson.fromJson(body, Epic.class);
                            taskManager.updateEpic(e);
                            System.out.println("Эпик обновлен с ID" + id);
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                    }
                    break;
                }
                default: {
                    System.out.println("/tasks/epic ждёт GET,POST или DELETE-запрос, а получил " + httpExchange.getRequestMethod());
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }

        } catch (Exception e) {
            System.out.println("Ошибка при обработке");
        } finally {
            httpExchange.close();
        }
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();

    }

    private void handleTask(HttpExchange httpExchange) {
        try {
            System.out.println("\n/tasks/task" + httpExchange.getRequestURI());
            String requestMethod = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String body = readText(httpExchange);
            switch (requestMethod) {
                case "GET": {
                    if (Pattern.matches("^/tasks/task/$", path)) {
                        final String response = gson.toJson(taskManager.getAllTasks());
                        sendText(httpExchange, response);
                        return;
                    }
                    if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                        String idString = path.replaceFirst("/tasks/task/", "");
                        int id = parsePathId(idString);
                        if (id != -1) {
                            final String response = gson.toJson(taskManager.getTaskById(id));
                            sendText(httpExchange, response);
                            return;
                        }
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                        String idString = path.replaceFirst("/tasks/task/", "");
                        int id = parsePathId(idString);
                        if (id != -1) {
                            taskManager.removeTaskById(id);
                            System.out.println("Удалена задача с ID=" + id);
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                    }
                    break;
                }
                case "POST": {
                    if (Pattern.matches("^/tasks/task/$", path)) {
                        Task t = gson.fromJson(body, Task.class);
                        taskManager.createTask(t);
                        System.out.println("Задача добавлена");
                        httpExchange.sendResponseHeaders(200, 0);
                    }
                    if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                        String idString = path.replaceFirst("/tasks/task/", "");
                        int id = parsePathId(idString);
                        if (id != -1) {
                            Task t = gson.fromJson(body, Task.class);
                            taskManager.updateTask(t);
                            System.out.println("Задача обновлена с ID" + id);
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                    }
                    break;
                }
                default: {
                    System.out.println("/tasks/task ждёт GET,POST или DELETE-запрос, а получил " + httpExchange.getRequestMethod());
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }

        } catch (Exception e) {
            System.out.println("Ошибка при обработке");
        } finally {
            httpExchange.close();
        }
    }

    private int parsePathId(String idString) {
        try {
            return Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту " + PORT);
    }


    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }
}
