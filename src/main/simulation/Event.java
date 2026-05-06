package simulation;

import java.time.LocalTime;

abstract public class Event {
    private LocalTime takePlaceTime;


    public Event(LocalTime time){
        takePlaceTime = time;
    }
    public int getTime(){
        return takePlaceTime.toSecondOfDay();
    }
    abstract public Event[] performAction();

}
