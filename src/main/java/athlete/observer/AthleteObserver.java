package athlete.observer;

import simulation.AthleteEvent;

// Part of implementation of OBSERVER patter to athlete
public interface AthleteObserver {
    public void update(AthleteEvent event);
}
