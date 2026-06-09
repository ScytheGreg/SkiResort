package athlete;

import simulation.AthleteEvent;

public class HistoryObserver implements AthleteObserver {

    private Athlete athlete;

    public HistoryObserver(Athlete athlete){
        this.athlete = athlete;
        athlete.attachObserver(this);
    }

    public void update(AthleteEvent event){
        if (! event.getAthlete().equals(athlete)){
            throw new IllegalArgumentException("History Observer" +
                    "saves only one athlete history");
        }

    }
}
