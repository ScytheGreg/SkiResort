import athlete.observer.HistoryObserver;
import containers.CustomPriorityQueue;
import kadra.mapki.GeneratorMapek;
import kadra.mapki.pliki.WyjatekSystemuPlikow;
import plots.AthleteHistoryPlotter;
import plots.EdgesParametersPlotter;
import plots.EdgesStatsPlotter;
import read.AthleteData;
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
        if (args.length != 2) {
            System.err.println("Usage: <program> [seed] [plots-directory]");
            return;
        }

        int seed;

        // 2.0 Parse
        // The seed controls all random decisions (impulsivity) in the simulation.
        try {
            seed = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Error: First argument must be a valid integer.");
            return;
        }

        // 2.1 Save directory
        String directory = args[1];

        // 3. Data Ingestion Phase
        Input reader = new Input();

        // Build the physical map (Nodes, Lifts, and Runs)
        SkiResort resort = reader.readResortData();

        // Build the initial population of events (Athletes arriving at the resort)
        // We pass the nodes so athletes can be placed at their starting locations.
        AthleteData athleteData = reader.readAthleteData(resort, seed);
        CustomPriorityQueue athleteEvents = athleteData.queue();

        // 4. Initialization Phase
        // Create the simulation engine and prime it with lift pickup cycles.
        Simulation simulation = new Simulation(athleteEvents, resort);

        // 5. Execution Phase
        // Start the engine. The simulation will run until the closing time (16:00:00).
        simulation.simulate(seed, resort);

        // 6. Plot phase
        // Plot graphs with resort plan and statistics to .tex files
        try{
            GeneratorMapek  gen = new GeneratorMapek(directory);

            EdgesParametersPlotter plotOne = new EdgesParametersPlotter();
            plotOne.plotResort(resort, "edges-parameters.tex", gen);

            EdgesStatsPlotter plotTwo = new EdgesStatsPlotter();
            plotTwo.plotResort(resort, "edges-stats.tex", gen);


            int k = 0;
            for (HistoryObserver observer : athleteData.historyObservers()){
                AthleteHistoryPlotter plotThree = new AthleteHistoryPlotter(observer);
                plotThree.plotResort(resort, "athlete-history-" + k +".tex" , gen);
                ++k;
            }

        }catch(WyjatekSystemuPlikow exc){
            System.err.println("Ensure correctness of provided directory to save plots");
            exc.printStackTrace();
            System.exit(1);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }



        /* * Note: The resort.log() call is handled inside simulation.simulate()
         * after the loop finishes, printing final stats for every run and lift.
         */
    }
}