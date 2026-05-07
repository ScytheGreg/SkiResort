package simulation;

import containers.Queue;
import containers.QueueNode;

import java.time.LocalTime;

abstract public class Event {
    private LocalTime takePlaceTime;

    public Event(LocalTime time){
        takePlaceTime = time;
    }
    public LocalTime getTime(){return takePlaceTime;}
    public void setTime(LocalTime time){takePlaceTime = time;}
    abstract public Queue performAction(int seed);

}
