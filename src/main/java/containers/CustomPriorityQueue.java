package containers;

import simulation.Event;

import java.time.LocalTime;
import java.util.PriorityQueue;

/**
 * Following the assignment requirements that class packs
 * standard implementation of Priority Queue remaining interface
 * from the previous part of assignment.
 */
public class CustomPriorityQueue {
    private final PriorityQueue<EventBox> queue = new PriorityQueue<>();
    private long eventsCnt = 0;

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public Event getFront() {
        if (isEmpty()) {
            throw new emptyContainerException();
        }
        return queue.peek() == null ? null : queue.peek().getEvent();
    }

    public Event takeFront() {
        if (isEmpty()) {
            throw new emptyContainerException();
        }
        return queue.peek() == null ? null : queue.poll().getEvent();
    }


    public void push(Event event) {
        queue.add(new EventBox(event, eventsCnt));
        eventsCnt++;
    }
}