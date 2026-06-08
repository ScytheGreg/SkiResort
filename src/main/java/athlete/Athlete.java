package athlete;

import java.time.LocalTime;
import java.util.Random;

import randomness.UniqueGenerator;
import resort.*;
import simulation.Event;
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
    private final MemoryOfRuns memory = new MemoryOfRuns(this);
    private Node currentNode;           // Current location in the resort graph

    public Athlete(AthleteParameters p) {
        this.id = p.id();
        this.advanceLevel = p.advanceLevel();
        this.impulsivity = p.impulsivity();
        this.isTracked = p.isTracked();
        this.boredomRate = p.boredomRate();
        this.levelMatchLure = p.levelMatchLure();
        this.runSmoothnessLure = p.runSmoothnessLure();
        this.boredomLure = p.boredomLure();
        this.currentNode = p.startNode();
        this.startTime = p.startTime();
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
    public void queueUp(Edge edge) {
        edge.addWaiting(this);
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


    /**
     * Calculates total attractiveness as a weighted average:
     * $a = \alpha_d \cdot d + \alpha_w \cdot w + \alpha_z \cdot (1 - z)$
     */
    public double finalRunLure(SkiRun run) {
        return levelMatchLure * this.levelMathCalculate(run) +
                runSmoothnessLure * run.smoothnessCalculate() +
                boredomLure * (1 - memory.calculateRunBoredom(run));
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

    protected boolean isImpulsiveDecision(Random generator) {
        return generator.nextDouble() <= getImpulsivity();
    }

    /**
     * Handles a spontaneous choice where every available path has equal probability.
     */
    protected Event makeImpulsiveDecision(Random generator, LocalTime time) {
        Node current = getCurrentNode();
        SkiRun[] skiRuns = current.getSkiRuns();
        SkiLift[] skiLifts = current.getSkiLifts();

        int n = skiRuns.length + skiLifts.length;
        if (n == 0) return null; // Should not happen in a strongly connected graph

        int takenEdge = generator.nextInt(0, n);
        if (takenEdge < skiRuns.length) {
            return skiRuns[takenEdge].beChoosenByAthlete(this, time);
        }
        // If a lift is chosen, the athlete simply enters the queue
        skiLifts[takenEdge - skiRuns.length].beChoosenByAthlete(this, time);
        return null;
    }

    /**
     * Core decision logic for the athlete.
     */
    public abstract Event makeDecision(int seed, LocalTime time);
}