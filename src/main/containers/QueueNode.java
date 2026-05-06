package containers;

import athlete.Athlete;

public class QueueNode {
    private QueueNode next = null;
    private QueueNode prev = null;
    private Athlete value;

    public QueueNode(Athlete value){
        this.value = value;
    }

    public QueueNode getNext(){return next;}
    public QueueNode getPrev(){return prev;}
    public Athlete getValue(){return value;}
    public void emplaceBefore(Athlete athlete){
        QueueNode other = new QueueNode(athlete);
        other.setNext(this);
        other.setPrev(prev);
        if(prev != null) {
            prev.setNext(other);
        }
        prev = other;
    }
    public void emplaceBehind(Athlete athlete){
        QueueNode other = new QueueNode(athlete);
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
