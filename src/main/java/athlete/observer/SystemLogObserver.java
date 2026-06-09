package athlete.observer;

import simulation.AthleteEvent;

public class SystemLogObserver implements AthleteObserver {

    private static SystemLogObserver instance = new SystemLogObserver();
    private SystemLogObserver(){}
    public static SystemLogObserver getInstance(){ return instance;}
    public void update(AthleteEvent event){
        System.out.println(event);
    }
}
