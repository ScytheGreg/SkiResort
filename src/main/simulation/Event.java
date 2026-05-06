package simulation;

import containers.Queue;
import containers.QueueNode;

import java.time.LocalTime;

abstract public class Event {
    private LocalTime takePlaceTime;

    public Event(LocalTime time){
        takePlaceTime = time;
    }
    public int getTime(){
        return takePlaceTime.toSecondOfDay();
    }
    public LocalTime getLocalTime(){return takePlaceTime;}
    abstract public Queue performAction();

}
