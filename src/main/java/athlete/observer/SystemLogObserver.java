package athlete.observer;

import simulation.AthleteEvent;


/**
 * SINGULARITY
 */
public class SystemLogObserver implements AthleteObserver {

    private static final SystemLogObserver instance = new SystemLogObserver();

    private SystemLogObserver() {
    }

    public static SystemLogObserver getInstance() {
        return instance;
    }

    /*
     * Logs just performed event
     */
    public void update(AthleteEvent event) {
        System.out.println(event);
    }
}
