package simulation;

import containers.Queue;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

abstract public class Event {
    private LocalTime takePlaceTime;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public Event(LocalTime time) {
        takePlaceTime = time;
    }

    public LocalTime getTime() {
        return takePlaceTime;
    }

    public void setTime(LocalTime time) {
        takePlaceTime = time;
    }

    abstract public Queue performAction(int seed);

    @Override
    public String toString() {
        return "At " + takePlaceTime.format(FORMATTER);
    }
}
