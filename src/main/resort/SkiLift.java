package resort;

import athlete.Athlete;
import containers.Queue;
import simulation.*;


import java.util.Locale;
import java.util.Scanner;
import java.time.LocalTime;

public class SkiLift extends Edge{
    private Queue waiting;
    int capacity;
    int frequency;

    public SkiLift(String data, int id, Node[] nodes) {
        Scanner dataScanner = new Scanner(data);
        dataScanner.useLocale(Locale.ENGLISH);

        int beg = dataScanner.nextInt();
        int end = dataScanner.nextInt();
        this.frequency = dataScanner.nextInt();
        this.capacity = dataScanner.nextInt();
        int rideTime = dataScanner.nextInt();
        dataScanner.close();
        super(nodes[beg], nodes[end], rideTime, id);

        this.waiting = new Queue();
    }

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
        result.push(new PickUpPassengers(time.plusSeconds(frequency), this));
        return result;
    }

    public void addWaiting(Athlete athlete){
        waiting.push(new GoUpWithLift(null, athlete, this));
    }
}
