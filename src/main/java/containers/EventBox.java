package containers;

import simulation.Event;

public class EventBox implements Comparable<EventBox> {
    private Event event;
    private Long orderNmb;

    public EventBox(Event event, Long orderNmb){
        this.event = event;
        this.orderNmb = orderNmb;
    }

    public Event getEvent(){
        return event;
    }

    public long getOrderNmb(){return orderNmb;}

    // Provides ordering stability
    @Override
    public int compareTo(EventBox other) {
        int x = this.event.getTime().compareTo(other.getEvent().getTime());
        if (x == 0)
            return this.orderNmb.compareTo(other.getOrderNmb());
        return x;
    }
}
