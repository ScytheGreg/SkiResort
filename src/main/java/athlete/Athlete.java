package athlete;

import java.time.LocalTime;
import java.util.Random;

import resort.*;
import simulation.Event;
import simulation.QueueUp;
import simulation.StartARide;

/**
 * Represents a skier or snowboarder in the simulation.
 * The athlete moves between nodes using ski runs and lifts based on
 * attractiveness and spontaneity.
 */
public abstract class Athlete {
    // Basic identification and characteristics
    private final int id;
    private final int advanceLevel;     // Skill level (0-10)
    private final double impulsivity;   // Probability (epsilon) of a spontaneous choice
    private final boolean isTracked;    // Whether to print activity logs for this athlete
    private final double boredomRate;       // Rate of getting sick of runs


    // Preference weights for calculating attractiveness
    private final double levelMatchLure;     // Alpha_d: weight for difficulty matching
    private final double runSmoothnessLure;  // Alpha_w: weight for surface quality
    private final double boredomLure;        // Alpha_z: weight for boredom rate

    private final LocalTime startTime;  // When the athlete arrives at the resort
    private Node currentNode;           // Current location in the resort graph

    public Athlete(int id,
                   int advanceLevel,
                   double impulsivity,
                   boolean isTracked,
                   double boredomRate,
                   double levelMatchLure,
                   double runSmoothnessLure,
                   double boredomLure,
                   Node startNode,
                   LocalTime startTime) {
        this.id = id;
        this.advanceLevel = advanceLevel;
        this.impulsivity = impulsivity;
        this.isTracked = isTracked;
        this.boredomRate = boredomRate;
        this.levelMatchLure = levelMatchLure;
        this.runSmoothnessLure = runSmoothnessLure;
        this.boredomLure = boredomLure;
        this.currentNode = startNode;
        this.startTime = startTime;
    }

    /**
     * Updates the athlete's position to the end of the specified edge[cite: 11].
     */
    public void move(Edge edge) {
        assert (edge.getBeginning().getId() == currentNode.getId());
        currentNode = edge.getEnd();
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Places the athlete in the waiting line for a specific lift[cite: 32, 146].
     */
    public void queueUp(Edge lift) {
        lift.addWaiting(this);
    }

    /**
     * Calculates the difficulty match attractiveness $d(p_t, p_n)$[cite: 60].
     * * Formula logic:
     * - 0 if the run is 5+ levels too hard.
     * - $1 - \frac{p_t - p_n}{5}$ if the run is harder but within 5 levels.
     * - $\max(0.2, 1 - \frac{p_n - p_t}{7})$ if the run is easier than the athlete's level.
     */
    public double levelMathCalculate(SkiRun run) {
        int difficulty = run.getAdvanceLevel();
        if (difficulty >= advanceLevel + 5) {
            return 0;
        } else if (advanceLevel > difficulty) {
            double expr = 1 - (advanceLevel - difficulty) / 7.0;
            return Math.max(0.2, expr);
        }
        return (1 - (difficulty - advanceLevel) / 5.0);
    }

    public double boredomCalculate(SkiRun run){
        return 1;
    }

    /**
     * Calculates total attractiveness as a weighted average:
     * $a = \alpha_d \cdot d + \alpha_w \cdot w + \alpha_z \cdot z$
     */
    public double finalRunLure(SkiRun run) {
        return levelMatchLure * this.levelMathCalculate(run) +
                runSmoothnessLure * run.smoothnessCalculate() +
                boredomLure * this.boredomCalculate(run);

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

    public double getBoredomRate(){ return boredomRate;}

    private boolean isImpulsiveDecision(Random generator) {
        return generator.nextDouble() <= getImpulsivity();
    }

    /**
     * Handles a spontaneous choice where every available path has equal probability[cite: 109, 110].
     */
    private Event makeImpulsiveDecision(Random generator, LocalTime time) {
        Node current = getCurrentNode();
        SkiRun[] skiRuns = current.getSkiRuns();
        SkiLift[] skiLifts = current.getSkiLifts();

        int n = skiRuns.length + skiLifts.length;
        if (n == 0) return null; // Should not happen in a strongly connected graph [cite: 13, 14]

        int takenEdge = generator.nextInt(0, n);
        if (takenEdge < skiRuns.length) {
            return new StartARide(time, this, skiRuns[takenEdge]);
        }
        // If a lift is chosen, the athlete simply enters the queue [cite: 105, 146]
        queueUp(skiLifts[takenEdge - skiRuns.length]);
        return null;
    }

    /**
     * Core decision logic for the athlete[cite: 97].
     * Considers local runs and runs accessible via one lift to find the best option.
     */
    public Event makeDecision(int seed, LocalTime time) {
        // Stop making new decisions after 15:00:00[cite: 45, 134].
        if (time.isAfter(LocalTime.of(14, 59, 59))) {
            return null;
        }

        // Per-decision generator to ensure unique results per event[cite: 124].
        Random generator = new Random(time.toSecondOfDay() + seed);

        if (isImpulsiveDecision(generator)) {
            return makeImpulsiveDecision(generator, time);
        }

        // 1. Evaluate "Remote" runs: those accessible by taking a lift from here
        int bestLiftIndex = 0;
        double bestRemoteSkiRunLure = 0;

        Node current = getCurrentNode();
        SkiLift[] availableLifts = current.getSkiLifts();

        for (int i = 0; i < availableLifts.length; ++i) {
            Node topStation = availableLifts[i].getEnd();
            for (SkiRun potentialRun : topStation.getSkiRuns()) {
                double runLure = finalRunLure(potentialRun);
                if (runLure > bestRemoteSkiRunLure) {
                    bestLiftIndex = i;
                    bestRemoteSkiRunLure = runLure;
                }
            }
        }

        // 2. Evaluate "Local" runs: those starting directly at the current node
        double bestLocalSkiRunLure = 0;
        SkiRun bestLocalSkiRun = null;

        for (SkiRun treatedRun : current.getSkiRuns()) {
            double runLure = finalRunLure(treatedRun);
            if (runLure > bestLocalSkiRunLure) {
                bestLocalSkiRun = treatedRun;
                bestLocalSkiRunLure = runLure;
            }
        }

        // 3. Final Comparison: Pick the most attractive option [cite: 103]
        if (bestLocalSkiRunLure >= bestRemoteSkiRunLure && bestLocalSkiRun != null) {
            // Start skiing immediately if a local run is best [cite: 104]
            return new StartARide(time, this, bestLocalSkiRun);
        } else if (availableLifts.length > 0) {
            // Queue up for a lift if a remote run is more attractive [cite: 105]
            return new QueueUp(time, this, availableLifts[bestLiftIndex]);
        }

        return null;
    }
}