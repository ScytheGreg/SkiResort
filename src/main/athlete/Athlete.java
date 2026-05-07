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

    public Athlete(String data, int groupId, int id, Node[] nodes) {
        this.id = id;
        Scanner dataScanner = new Scanner(data);
        dataScanner.useLocale(Locale.ENGLISH);

        dataScanner.nextInt(); // Skip number of athletes
        this.advanceLevel = dataScanner.nextInt();
        this.impulsivity = dataScanner.nextDouble();

        if (dataScanner.hasNext("s")) {
            isTracked = true;
            dataScanner.next("s");
        } else
            isTracked = false;

        this.levelMatchLure = dataScanner.nextDouble();
        this.runSmoothnessLure = dataScanner.nextDouble();
        assert (levelMatchLure + runSmoothnessLure == 1);

        this.currentNode = nodes[dataScanner.nextInt()];
        LocalTime firstAthlTime = LocalTime.parse(dataScanner.next(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        int delay = 0;
        if (dataScanner.hasNextInt()) {
            delay = dataScanner.nextInt();
        }
        this.startTime = firstAthlTime.plusSeconds(delay * groupId);
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

}