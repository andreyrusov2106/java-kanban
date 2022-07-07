package ru.yandex.practicum.tasks;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList historyLinkedList = new CustomLinkedList();
    private final Map<Integer, Node> historyMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (historyMap.containsKey(task.getId())) {
            historyLinkedList.removeNode(historyMap.get(task.getId()));
        }
        historyMap.put(task.getId(), historyLinkedList.linkLast(task));
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            historyLinkedList.removeNode(historyMap.get(id));
            historyMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyLinkedList.getTasks();
    }

    public class CustomLinkedList {
        Node head;
        Node tail;

        public Node linkLast(Task element) {
            final Node oldTail = tail;
            final Node newNode = new Node(oldTail, element, null);
            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.next = newNode;
                tail.prev = oldTail;
            }
            return newNode;
        }

        public ArrayList<Task> getTasks() {
            ArrayList<Task> historyArray = new ArrayList<>();
            if (head != null) {
                Node tmpNode = head;
                historyArray.add(tmpNode.item);
                while (tmpNode.next != null) {
                    tmpNode = tmpNode.next;
                    historyArray.add(tmpNode.item);
                }
            }
            return historyArray;
        }

        private void removeNode(Node element) {
            if (element.prev == null && element.next == null) {
                tail = null;
                head = null;
            } else if (element.prev == null) {
                Node newNode = element.next;
                newNode.prev = null;
                head = newNode;
            } else if (element.next == null) {
                Node newNode = element.prev;
                newNode.next = null;
                tail = newNode;
            } else {
                Node nodePrev = element.prev;
                Node nodeNext = element.next;
                nodePrev.next = nodeNext;
                nodeNext.prev = nodePrev;
            }
        }
    }
}

