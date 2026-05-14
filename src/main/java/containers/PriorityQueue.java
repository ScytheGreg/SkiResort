package containers;

import simulation.Event;

import java.time.LocalTime;

public class PriorityQueue extends Queue {

    @Override
    public void push(Event event) {
        // 1. Handle basic infrastructure edge cases (e.g., empty queue)
        if (edgeCasesPush(event)) {
            return;
        }

        LocalTime eventTime = event.getTime();
        QueueNode firstNode = getFirst();
        QueueNode lastNode = getLast();

        // 2. Optimization: Check if it belongs at the very end
        // (Using !isBefore to handle both 'after' and 'equal' cases)
        if (!eventTime.isBefore(lastNode.getValue().getTime())) {
            lastNode.emplaceBehind(event);
            adjustEnds();
            return;
        }

        // 3. Middle Insertion: Find the correct spot
        // We start from the first node and move "down" the queue.
        QueueNode current = firstNode;

        while (current != null) {
            LocalTime currentTime = current.getValue().getTime();

            /* * Logic: We want to insert the event AFTER all events with the same time
             * but BEFORE the first event that occurs strictly later.
             */
            if (eventTime.isBefore(currentTime)) {
                current.emplaceBefore(event);
                adjustEnds();
                return;
            }

            // Move to the next node in the queue
            current = current.getPrev();
        }
    }
}