package athlete.observer;

import athlete.Athlete;
import simulation.AthleteEvent;

public class HistoryObserver implements AthleteObserver {
    private Integer movesCnt = 0;
    private final Athlete athlete;

    public HistoryObserver(Athlete athlete){
        athlete.attachObserver(this);
        this.athlete = athlete;
    }

    public Athlete getAthlete(){
        return athlete;
    }

    public void update(AthleteEvent event){
        if (event.doesMoveAthlete()){
            ++movesCnt;
            event.getEdge().saveARide(athlete, movesCnt);
        }
    }
}
