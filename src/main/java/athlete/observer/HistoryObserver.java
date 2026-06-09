package athlete.observer;

import athlete.Athlete;
import simulation.AthleteEvent;

public class HistoryObserver implements AthleteObserver {
    private Integer movesCnt = 0;


    public HistoryObserver(Athlete athlete){
        athlete.attachObserver(this);
    }

    public void update(AthleteEvent event){
        if (event.doesMoveAthlete()){
            ++movesCnt;
            event.getEdge().saveARide(event.getAthlete(), movesCnt);
        }
    }
}
