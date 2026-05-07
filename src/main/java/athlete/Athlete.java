package athlete;

import java.time.LocalTime;
import java.util.Random;

import containers.Queue;
import resort.*;
import simulation.Event;
import simulation.QueueUp;
import simulation.StartARide;

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

    public void queueUp(Edge lift) {
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

    public Node getCurrentNode() {
        return currentNode;
    }

    public double getImpulsivity() {
        return impulsivity;
    }

    public int getId() {
        return id;
    }

    public int getAdvanceLevel() {
        return advanceLevel;
    }

    public boolean isTracked() {
        return isTracked;
    }

    private boolean isImpulsiveDecision(Random generator) {
        return generator.nextDouble() <= getImpulsivity();
    }

    private Event makeImpulsiveDecision(Random generator, LocalTime time) {
        Node currentNode = getCurrentNode();
        SkiRun[] skiRuns = currentNode.getSkiRuns();
        SkiLift[] skiLifts = currentNode.getSkiLifts();

        int n = skiRuns.length + skiLifts.length;

        int takenEdge = generator.nextInt(0, n);
        if (takenEdge < skiRuns.length) {
            return new StartARide(time, this, skiRuns[takenEdge]);
        }
        queueUp(skiLifts[takenEdge - skiRuns.length]);
        return null;
    }

    public Event makeDecision(int seed, LocalTime time) {

        if (time.isAfter(LocalTime.of(14, 59, 59))){
            return null;
        }

        Random generator = new Random(time.toSecondOfDay() + seed);

        if (isImpulsiveDecision(generator)) {
            return makeImpulsiveDecision(generator, time);
        }

        // Calculate remotes lure
        int bestLift = 0;
        double bestRemoteSkiRunLure = 0;

        Node currentNode = getCurrentNode();
        SkiLift[] availableLifts = currentNode.getSkiLifts();
        for (int i = 0; i < availableLifts.length; ++i) {
            Node endDest = availableLifts[i].getEnd();
            for (SkiRun treatedRun : endDest.getSkiRuns()) {
                double runLure = finalRunLure(treatedRun);
                if (runLure > bestRemoteSkiRunLure) {
                    bestLift = i;
                    bestRemoteSkiRunLure = runLure;
                }
            }
        }
        // Calculate local lure
        double bestLocalSkiRunLure = 0;
        SkiRun bestLocalSkiRun = null;
        for (SkiRun treatedRun : getCurrentNode().getSkiRuns()) {
            double runLure = finalRunLure(treatedRun);
            if (runLure > bestLocalSkiRunLure) {
                bestLocalSkiRun = treatedRun;
                bestLocalSkiRunLure = runLure;
            }
        }

        // Compare lures
        if (bestLocalSkiRunLure >= bestRemoteSkiRunLure) { // Take local run
            return new StartARide(time, this, bestLocalSkiRun);
        } else { // queue up in desired lift
             return new QueueUp(time, this, availableLifts[bestLift]);
        }
    }

}