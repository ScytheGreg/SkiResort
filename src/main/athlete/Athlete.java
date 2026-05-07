package athlete;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import resort.*;

public class Athlete {
    private final int id;
    private final int advanceLevel;
    private final double impulsivity;
    private final boolean isTracked;
    private final double levelMatchLure;
    private final double runSmoothnessLure;
    private final LocalTime startTime;


    private Node currentNode;

    public Athlete(int id,
                   int advanceLevel,
                   double impulsivity,
                   boolean isTracked,
                   double levelMatchLure,
                   double runSmoothnessLure,
                   Node startNode,
                   LocalTime startTime) {

        this.id = id;
        this.advanceLevel = advanceLevel;
        this.impulsivity = impulsivity;
        this.isTracked = isTracked;
        this.levelMatchLure = levelMatchLure;
        this.runSmoothnessLure = runSmoothnessLure;
        this.currentNode = startNode;
        this.startTime = startTime;
    }


    public void move(Edge edge) {
        assert (edge.getBeginning().getId() == currentNode.getId());
        currentNode = edge.getEnd();
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void queueUp(SkiLift lift) {
        lift.addWaiting(this);
    }

    public double levelMathCalculate(SkiRun run) {
        int difficulty = run.getAdvanceLevel();
        if (difficulty >= advanceLevel + 5)
            return 0;
        else if (advanceLevel > difficulty) {
            double expr = 1 - (advanceLevel - difficulty) / 7.0;
            return Math.max(0.2, expr);
        }
        return (1 - (difficulty - advanceLevel) / 5.0);
    }

    public double finalRunLure(SkiRun run) {
        return levelMatchLure * this.levelMathCalculate(run) +
                runSmoothnessLure * run.smoothnessCalculate();
    }
    public Node getCurrentNode(){return currentNode;}

    public double getImpulsivity() {
        return impulsivity;
    }
}