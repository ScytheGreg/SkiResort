package containers;

import simulation.Event;

/**
 * That box provides stable comparing system to priority queue.
 */
public class EventBox implements Comparable<EventBox> {
    private Event event;
    private Long orderNmb;

    public EventBox(Event event, Long orderNmb) {
        this.event = event;
        this.orderNmb = orderNmb;
    }

    public Event getEvent() {
        return event;
    }

    public long getOrderNmb() {
        return orderNmb;
    }

    /**
     * Compares that object with other object
     *
     * @param other object to which is compared
     * @return 1 - this > other
     * 0 - this == other
     * -1 - this < other
     * In the order 1. event.getTime, 2. order nmb
     */
    @Override
    public int compareTo(EventBox other) {
        int x = this.event.getTime().compareTo(other.getEvent().getTime());
        if (x == 0)
            return this.orderNmb.compareTo(other.getOrderNmb());
        return x;
    }
}
