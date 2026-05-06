package simulation;

import resort.Edge;

import java.time.LocalTime;

abstract public class EdgeEvent extends Event{
    private Edge edge;
    public EdgeEvent(LocalTime time, Edge edge){
        super(time); this.edge = edge;
    }
}
