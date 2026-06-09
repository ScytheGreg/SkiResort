package athlete.observer;

import simulation.AthleteEvent;

public interface AthleteObserver {
    public void update(AthleteEvent event);
}
