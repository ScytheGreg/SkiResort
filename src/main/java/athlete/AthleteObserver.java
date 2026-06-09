package athlete;

import simulation.Event;

public interface AthleteObserver {
    public void update(Athlete athlete, Event event);
}
