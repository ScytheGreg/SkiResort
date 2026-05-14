package containers;

import simulation.Event;

import java.time.LocalTime;

public class PriorityQueue extends Queue {

    @Override
    public void push(Event event) {
        if (edgeCasesPush(event))
            return;


        // Edge cases - for sure singular
        LocalTime eventTime = event.getTime();
        LocalTime firtEvent = getFirst().getValue().getTime();
        LocalTime lastEvent = getLast().getValue().getTime();
        if (eventTime.isBefore(firtEvent)) {
            getFirst().emplaceBefore(event);
            adjustEnds();
            return;
        }
        if (eventTime.isAfter(lastEvent) || eventTime.equals(lastEvent)) {
            getLast().emplaceBehind(event);
            adjustEnds();
            return;
        }

        // Iterate through the queue to find the correct insertion point based on time
        boolean isNotInserted = true;
        for (QueueNode curr = getFirst(), after = curr != null ? curr.getPrev() : null
             ; curr != null && after != null && isNotInserted
                ; curr = curr.getPrev(), after = curr != null ? curr.getPrev() : null) {

            LocalTime currTime = curr.getValue().getTime();
            LocalTime afterTime = after.getValue().getTime();
            if (eventTime.equals(currTime) ||
                    (eventTime.isAfter(currTime) && eventTime.isBefore(afterTime))) {
                curr.emplaceBehind(event);
                isNotInserted = false;
            }
        }
    }
}