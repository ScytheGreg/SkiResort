package containers;

import simulation.Event;

public class QueueNode {
    private QueueNode next = null;
    private QueueNode prev = null;
    private Event value;

    public QueueNode(Event value){
        this.value = value;
    }

    public QueueNode getNext(){return next;}
    public QueueNode getPrev(){return prev;}
    public Event getValue(){return value;}

    public void emplaceBefore(Event event){
        QueueNode other = new QueueNode(event);
        other.setNext(this);
        other.setPrev(prev);
        if(prev != null) {
            prev.setNext(other);
        }
        prev = other;
    }

    public void emplaceBehind(Event event){
        QueueNode other = new QueueNode(event);
        other.setPrev(this);
        other.setNext(next);
        if(next != null) {
            next.setPrev(other);
        }
        next = other;
    }

    public void setNext(QueueNode other){next = other;}
    public void setPrev(QueueNode other){prev = other;}
}