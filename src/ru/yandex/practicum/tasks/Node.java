package ru.yandex.practicum.tasks;

public class Node {
    Task item;
    Node next;
    Node prev;

    Node(Node prev, Task element, Node next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
