package containers;

import simulation.Event;

public class PriorityQueue extends Queue {

    @Override
    public void push(Event event) {
        if (isEmpty()) {
            pushToEmpty(event);
            return;
        }

        QueueNode lowerBound = getFirst();
        assert(lowerBound != null);

        // Iterate through the queue to find the correct insertion point based on time
        while (lowerBound.getPrev() != null && lowerBound.getValue().getTime().isBefore(event.getTime())) {
            lowerBound = lowerBound.getPrev();
        }

        assert(lowerBound != null);

        if (!lowerBound.getValue().getTime().isBefore(event.getTime())) {
            lowerBound.emplaceBehind(event);
        } else {
            lowerBound.emplaceBefore(event);
        }

        adjustEnds();
    }
}