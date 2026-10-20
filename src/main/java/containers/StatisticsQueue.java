package containers;

import simulation.Event;

import java.time.LocalTime;

// Keeps max length statistics of queue
public class StatisticsQueue extends Queue {
    private int maxLength = 0;

    @Override
    public void push(Event event) {
        super.push(event);
        if (size() > maxLength) {
            maxLength = size();
        }
    }

    public int getMaxLength() {
        return maxLength;
    }

}
