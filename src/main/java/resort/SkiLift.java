package resort;

import athlete.Athlete;
import containers.Queue;
import simulation.*;


import java.time.LocalTime;

public class SkiLift extends Edge{
    private Queue waiting;
    private final int capacity;
    private final int frequency;


    public SkiLift(Node beginning, Node end, int rideTime, int id,
                   int frequency, int capacity) {
        super(beginning, end, rideTime, id);

        assert(frequency != 0);
        this.frequency = frequency;
        this.capacity = capacity;
        this.waiting = new Queue();
    }
    public String getName(){return "lift   ";}

    public int getCapacity(){return capacity;}
    public int getFrequency(){return frequency;}
    public Queue pickUpPassengers(LocalTime time){
        Queue   result = new Queue();
        for(int i = 0 ; i < capacity ; ++i){
            if (!waiting.isEmpty()){
                Event ev = waiting.takeFront();
                ev.setTime(time);
                result.push(ev);
            }
        }
        LocalTime nextPickupTime = time.plusSeconds(frequency);

        result.push(new PickUpPassengers(nextPickupTime, this));
        return result;
    }

    @Override
    public void addWaiting(Athlete athlete){
        waiting.push(new StartARide(null, athlete, this));
    }
}
