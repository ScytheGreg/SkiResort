package containers;

import simulation.Event;

import java.time.LocalTime;

public class StatisticsQueue extends Queue{
    private int maxLength = 0;

    @Override
    public void push(Event event){
        super.push(event);
        if (size() > maxLength){
            maxLength = size();
        }
    }

    public int getMaxLength(){
        return maxLength;
    }

}
