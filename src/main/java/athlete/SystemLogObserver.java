package athlete;

import simulation.Event;

public class SystemLogObserver implements AthleteObserver {

    private static SystemLogObserver instance = new SystemLogObserver();
    private SystemLogObserver(){}
    public static SystemLogObserver getInstance(){ return instance;}
    public void update(Athlete athlete, Event event){
        System.out.println(event);
    }
}
