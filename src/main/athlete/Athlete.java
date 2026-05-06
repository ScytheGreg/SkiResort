package athlete;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import resort.*;
public class Athlete {
    private int id;
    private Node currentNode;
    private int advanceLevel;
    private double impulsivity;
    boolean isTracked = false;

    private double levelMatchLure;
    private double runSmoothnessLure;

    private int startNode;
    LocalTime startTime;

    public Athlete(String data, int groupId, int id){
        this.id = id;
        Scanner dataScanner = new Scanner(data);
        dataScanner.useLocale(Locale.ENGLISH);

        dataScanner.nextInt(); // Skip number of athletes
        this.advanceLevel = dataScanner.nextInt();
        this.impulsivity = dataScanner.nextDouble();
        if (dataScanner.hasNext("s")){
            isTracked = true;
            dataScanner.next("s");
        }
        this.levelMatchLure = dataScanner.nextDouble();
        this.runSmoothnessLure = dataScanner.nextDouble();
        this.startNode = dataScanner.nextInt();
        LocalTime firstAthlTime = LocalTime.parse(dataScanner.next(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        int delay = 0;
        if (dataScanner.hasNextInt()) {
            delay = dataScanner.nextInt();
        }
        this.startTime = firstAthlTime.plusSeconds(delay * groupId);
    }


    void move(Edge edge){
        assert(edge.getBeginning().getId() == currentNode.getId());
        currentNode = edge.getEnd();
        startTime = startTime.plusSeconds(edge.getRideTime());
    }
    public LocalTime getStartTime(){return startTime;}

    public void queueUp(SkiLift lift){
        lift.addWaiting(this);
    }

}
