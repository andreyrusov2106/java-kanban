package ru.yandex.practicum.tasks;

import ru.yandex.practicum.enums.Status;
import ru.yandex.practicum.exceptions.DateTimeValidateException;
import ru.yandex.practicum.interfaces.HistoryManager;
import ru.yandex.practicum.interfaces.TaskManager;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int currentId;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final TreeSet<Task> sortedTasks = new TreeSet(Comparator.comparing(Task::getStartTime));
    private final List<Task> tasksWithoutDate = new ArrayList<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    //methods for Tasks
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
    }

    @Override
    public void createTask(Task task) {

        if (task.getStartTime() != null) {
            if (validateTaskTime(task)) {
                sortedTasks.add(task);
            } else {
                throw new DateTimeValidateException("Время задачи пересекается с уже добавленными задачами");
            }
        } else {
            tasksWithoutDate.add(task);
        }
        currentId++;
        task.setId(currentId);
        tasks.put(currentId, task);
    }

    @Override
    public void updateTask(Task task) {
        if (task != null) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void removeTaskById(int id) {

        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) historyManager.add(task);
        return task;
    }

    @Override
    //methods for Epics
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void clearAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) historyManager.add(epic);
        return epic;
    }

    @Override
    public void createEpic(Epic task) {
        currentId++;
        task.setId(currentId);
        epics.put(currentId, task);
        checkEpicStatus(currentId);
        checkEpicTime(task.getId());
    }

    @Override
    public void updateEpic(Epic task) {
        if (task != null) {
            epics.put(task.getId(), task);
        }
    }

    @Override
    public void removeEpicById(int id) {
        clearAllSubTasksByEpicId(id);
        epics.remove(id);
        historyManager.remove(id);
    }

    //methods for Subtasks
    @Override
    public List<SubTask> getAllSubTasksByEpicId(int epicId) {
        List<SubTask> subTasks = new ArrayList<>();
        for (Integer subTaskId : epics.get(epicId).getSubTaskIds()) {
            if (this.subTasks.get(subTaskId) != null) subTasks.add(this.subTasks.get(subTaskId));
        }
        return subTasks;
    }
    @Override
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void clearAllSubTasksByEpicId(int epicId) {
        if (epics.containsKey(epicId)) {
            for (Integer subTaskId : epics.get(epicId).getSubTaskIds()) {
                subTasks.remove(subTaskId);
                historyManager.remove(subTaskId);
            }
            epics.get(epicId).clearAllSubTasks();
            checkEpicStatus(epicId);
            checkEpicTime(epicId);

        }
    }

    @Override
    public SubTask getSubTaskById(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        if (subTask != null) historyManager.add(subTask);
        return subTask;
    }

    @Override
    public void createSubTask(SubTask task) {
        int epicId = task.getEpicId();
        if (epics.containsKey(epicId)) {
            if (task.getStartTime() != null) {
                if (validateTaskTime(task)) {
                    sortedTasks.add(task);
                } else {
                    throw new DateTimeValidateException("Время задачи пересекается с уже добавленными задачами");
                }
            } else {
                tasksWithoutDate.add(task);
            }
            currentId++;
            task.setId(currentId);
            Epic tmpEpic = epics.get(epicId);
            tmpEpic.addSubTaskIds(currentId);
            subTasks.put(currentId, task);
            checkEpicStatus(epicId);
            checkEpicTime(epicId);
        }
    }

    @Override
    public void updateSubTask(SubTask task) {
        if (subTasks.containsKey(task.getId())) {
            subTasks.put(task.getId(), task);
            checkEpicStatus(task.getEpicId());
            checkEpicTime(task.getEpicId());
        }
    }

    @Override
    public void removeSubTaskById(int subTaskId) {
        if (subTasks.containsKey(subTaskId)) {
            int epicId = subTasks.get(subTaskId).getEpicId();
            if (epics.containsKey(epicId)) {
                Epic tmpEpic = epics.get(epicId);
                tmpEpic.removeSubTaskIds(subTaskId);
                subTasks.remove(subTaskId);
                checkEpicStatus(tmpEpic.getId());
                checkEpicTime(tmpEpic.getId());
                historyManager.remove(subTaskId);
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void checkEpicStatus(int epicId) {
        List<SubTask> subTasks = getAllSubTasksByEpicId(epicId);
        Epic epic = epics.get(epicId);
        if (subTasks.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        ArrayList<Status> statuses = new ArrayList<>();
        for (SubTask subTask : this.subTasks.values()) {
            statuses.add(subTask.getStatus());
        }
        if (!statuses.contains(Status.DONE) && !statuses.contains(Status.IN_PROGRESS)) {
            epic.setStatus(Status.NEW);
        } else if (!statuses.contains(Status.NEW) && !statuses.contains(Status.IN_PROGRESS)) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    private void checkEpicTime(int epicId) {
        Epic epic = getEpicById(epicId);
        if (getAllSubTasksByEpicId(epicId).size() != 0) {
            for (SubTask subTask : getAllSubTasksByEpicId(epicId)) {
                if (epic.getStartTime() == null) {
                    if (subTask.getStartTime() != null) {
                        epic.setStartTime(subTask.getStartTime());
                        epic.setDuration(subTask.getDuration());
                        epic.calculateEndTime();
                    }
                } else {
                    if (subTask.getStartTime() != null && subTask.getStartTime().isBefore(epic.getStartTime())) {
                        epic.setStartTime(subTask.getStartTime());
                    }
                    if (subTask.getStartTime() != null && subTask.getEndTime().isAfter(epic.getEndTime())) {
                        epic.setEndTime(subTask.getEndTime());
                    }
                }
            }
        }

    }

    private boolean validateTaskTime(Task t) {
        if (sortedTasks.isEmpty()) return true;
        for (Task allTask : sortedTasks) {
            if ((t.getStartTime().isEqual(allTask.getStartTime()) || t.getStartTime().isAfter(allTask.getStartTime())) &&
                    (t.getStartTime().isBefore(allTask.getEndTime()) || t.getStartTime().isEqual(allTask.getEndTime())) ||
                    (t.getEndTime().isAfter(allTask.getStartTime()) || t.getEndTime().isEqual(allTask.getStartTime())) &&
                            (t.getEndTime().isBefore(allTask.getEndTime()) || t.getEndTime().isEqual(allTask.getEndTime()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        List<Task> result = new ArrayList<>(sortedTasks);
        result.addAll(tasksWithoutDate);
        return result;

    }
}
