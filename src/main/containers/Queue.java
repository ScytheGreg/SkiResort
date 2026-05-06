package containers;

import athlete.Athlete;

public class Queue {
    QueueNode first = null;
    QueueNode last = null;


    public boolean isEmpty(){return first == null;}
    public boolean isSingular(){
        if(isEmpty()) return false;
        return first.getPrev() == null;
    }

    public void pop() throws emptyContainerException{
        if (isEmpty()) throw new emptyContainerException();
        if (isSingular()){
            first = null;
            last = null;
            return;
        }
        first = first.getPrev();
    }

    protected void adjustEnds(){
        if(first.getNext() != null) first = first.getNext();
        if(last.getPrev() != null) last = last.getPrev();
    }

    protected void pushToEmpty(Athlete athlete){
        first = new QueueNode(athlete);
        last = first;
    }

    protected QueueNode getFirst(){return first;}
    protected QueueNode getLast(){return last;}

    public void push(Athlete athlete){
        if (isEmpty()){
            pushToEmpty(athlete);
            return;
        }
        last.emplaceBehind(athlete); // Emplace behind new Athlete
        last = last.getPrev(); // Move to last to new last Athlete
    }

    public Athlete getFront() throws  emptyContainerException{
        if (isEmpty()) throw new emptyContainerException();
        return first.getValue();
    }
    public Athlete takeFront() throws  emptyContainerException{
        Athlete result = getFront();
        pop();
        return result;
    }

}
