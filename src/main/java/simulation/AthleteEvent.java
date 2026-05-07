package simulation;

import athlete.Athlete;
import containers.Queue;
import resort.Edge;

import java.time.LocalTime;

abstract public class AthleteEvent extends Event {
    private Athlete athlete;
    private Edge edge;
    public AthleteEvent(LocalTime time, Athlete athlete, Edge edge) {
        super(time);
        this.athlete = athlete;
        this.edge =edge;
    }

    public Athlete getAthlete() {
        return athlete;
    }
    public Edge getEdge(){return edge;}
    public String toString(){
        return super.toString() + " the athlete nr " + String.format("%5d", athlete.getId());
    }
    @Override
    public Queue performAction(int seed){
        if(athlete.isTracked()){
            System.out.println(this.toString());
        }
        return new Queue();
    }
}
