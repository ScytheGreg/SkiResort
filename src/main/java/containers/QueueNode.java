package containers;

import simulation.Event;

public class QueueNode {
    private QueueNode prev;
    private QueueNode next;
    private final Event value;

    public QueueNode(Event value, QueueNode prev, QueueNode next){
        this.value = value;
        this.prev = prev; this.next = next;
        if (prev != null){
            prev.setNext(this);
        }
        if (next != null){
            next.setPrev(this);
        }
    }

    public QueueNode getNext(){return next;}
    public QueueNode getPrev(){return prev;}
    public Event getValue(){return value;}

    public void emplaceBefore(Event event){
        new QueueNode(event, this, next);
    }

    public void emplaceBehind(Event event){
        new QueueNode(event, prev, this);
    }

    public void setNext(QueueNode other){next = other;}
    public void setPrev(QueueNode other){prev = other;}

}