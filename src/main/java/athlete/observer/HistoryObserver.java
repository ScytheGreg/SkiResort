package athlete.observer;

import athlete.Athlete;
import simulation.AthleteEvent;

/**
 * Responsible for saving athlete taken edges
 */
public class HistoryObserver implements AthleteObserver {
    private int movesCnt = 0;   // Counter to identify every ride
    private final Athlete athlete;

    public HistoryObserver(Athlete athlete) {
        athlete.attachObserver(this); // Automatic bounding with matched athlete
        this.athlete = athlete;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public int getRideId() {
        return movesCnt;
    }

    /**
     * Saves ride in an Edge memory if event moves athlete
     *
     * @param event that has been performed
     */
    public void update(AthleteEvent event) {
        if (event.doesMoveAthlete()) { // Check if event actually moves athlete
            ++movesCnt;
            event.getEdge().saveARide(this);
        }
    }
}
