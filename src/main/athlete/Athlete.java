package athlete;

import java.time.LocalTime;
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

    public Athlete(String data, int id){
        this.id = id;
        Scanner dataScanner = new Scanner(data);
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
        LocalTime firstAthlTime = 
    }


    void lift(SkiLift lifter){
        assert(lifter.getBeginning().getId() == currentNode.getId());
        currentNode = lifter.getEnd();
    }

}
