package containers;

import simulation.Event;

/**
 * Implements Queue of Events based on custom list with QueueNode nodes.
 */
public class Queue {
    QueueNode first = null;
    QueueNode last = null;
    private int length = 0;

    public boolean isEmpty() {
        return first == null;
    }

    public boolean isSingular() {
        if (isEmpty()) return false;
        return first.getPrev() == null;
    }

    public void pop() throws emptyContainerException {
        if (isEmpty()) throw new emptyContainerException();
        --length;
        if (isSingular()) {
            first = null;
            last = null;
            return;
        }
        first = first.getPrev();
        first.setNext(null);
    }


    private void pushToEmpty(Event event) {
        first = new QueueNode(event, null, null);
        last = first;
    }

    protected boolean edgeCasesPush(Event event) {
        if (event == null)
            return true;
        if (isEmpty()) {
            pushToEmpty(event);
            return true;
        }
        return false;
    }

    public QueueNode getFirst() {
        return first;
    }

    public QueueNode getLast() {
        return last;
    }

    public void push(Event event) {
        ++length;
        if (edgeCasesPush(event))
            return;
        last.emplaceBehind(event); // Emplace behind new Event
        last = last.getPrev();    // Move last to new last Event
    }

    public Event getFront() throws emptyContainerException {
        if (isEmpty()) throw new emptyContainerException();
        return first.getValue();
    }

    public Event takeFront() throws emptyContainerException {
        Event result = getFront();
        pop();
        return result;
    }

    public int size() {
        return length;
    }
}