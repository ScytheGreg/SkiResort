package containers;

import athlete.Athlete;

public class PriorityQueue extends Queue{


    @Override
    public void push(Athlete athlete){
        if (isEmpty()){pushToEmpty(athlete); return;}
        QueueNode lowerBound = getFirst();
        assert(lowerBound != null);

        while (lowerBound.getPrev() != null && lowerBound.getValue().getTime() < athlete.getTime()){
            lowerBound = lowerBound.getPrev(); // As long as it's smaller, move to next
        }
        assert(lowerBound != null);
        if(lowerBound.getValue().getTime() >= athlete.getTime())
            lowerBound.emplaceBehind(athlete);
        else
            lowerBound.emplaceBefore(athlete);

        adjustEnds();
    }
}
