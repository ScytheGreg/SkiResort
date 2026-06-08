import containers.PriorityQueue;
import read.Input;
import resort.SkiResort;
import simulation.Simulation;

/**
 * Entry point for the Ski Resort Simulation.
 * Coordinates the reading of input data, the initialization of the graph,
 * and the execution of the discrete event loop.
 */
public class Main {

    public static void main(String[] args) {

        // 1. Validate Command Line Arguments
        if (args.length == 0) {
            System.err.println("Error: Please provide a number as the first argument.");
            return;
        }

        int seed;

        // 2. Parse the Seed
        // The seed controls all random decisions (impulsivity) in the simulation.
        try {
            seed = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Argument must be a valid integer.");
            return;
        }

        // 3. Data Ingestion Phase
        Input reader = new Input();

        // Build the physical map (Nodes, Lifts, and Runs)
        SkiResort resort = reader.readResortData();

        // Build the initial population of events (Athletes arriving at the resort)
        // We pass the nodes so athletes can be placed at their starting locations.
        PriorityQueue athleteEvents = reader.readAthleteData(resort, seed);

        // 4. Initialization Phase
        // Create the simulation engine and prime it with lift pickup cycles.
        Simulation simulation = new Simulation(athleteEvents, resort);

        // 5. Execution Phase
        // Start the engine. The simulation will run until the closing time (16:00:00).
        simulation.simulate(seed, resort);

        /* * Note: The resort.log() call is handled inside simulation.simulate()
         * after the loop finishes, printing final stats for every run and lift.
         */
    }
}