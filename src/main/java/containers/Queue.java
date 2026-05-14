package containers;

import simulation.Event;

public class Queue {
    QueueNode first = null;
    QueueNode last = null;

    public boolean isEmpty() {
        return first == null;
    }

    public boolean isSingular() {
        if (isEmpty()) return false;
        return first.getPrev() == null;
    }

    public void pop() throws emptyContainerException {
        if (isEmpty()) throw new emptyContainerException();
        if (isSingular()) {
            first = null;
            last = null;
            return;
        }
        first = first.getPrev();
        first.setNext(null);
    }

    protected void adjustEnds() {
        if (first.getNext() != null) first = first.getNext();
        if (last.getPrev() != null) last = last.getPrev();
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
        if (edgeCasesPush(event))
            return;
        last.emplaceBehind(event); // Emplace behind new Event
        last = last.getPrev(); // Move last to new last Event
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
}