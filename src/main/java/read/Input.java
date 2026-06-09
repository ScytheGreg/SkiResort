package read;

import athlete.*;
import athlete.observer.HistoryObserver;
import containers.CustomPriorityQueue;
import read.factories.AthleteFactory;
import resort.*;
import read.factories.NodeFactory;
import read.factories.SkiLiftFactory;
import read.factories.SkiRunFactory;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Orchestrates the reading and parsing of the entire resort and athlete dataset.
 * It uses StdInScanner for raw reading and Factory classes for object instantiation.
 */
public class Input {

    // Internal scanner to handle the structured blocks of input
    private final StdInScanner scanner = new StdInScanner();

    /**
     * Inner class or combined logic from StdInScanner.
     * Processes groups of data (Nodes, Lifts, Runs, Athletes).
     */
    private class StdInScanner {
        private final Scanner sysScanner = new Scanner(System.in);

        public String[] readGroup(int elementLines, boolean isLast) {
            if (!sysScanner.hasNextLine()) return new String[0];

            String head = sysScanner.nextLine();
            if (head.isEmpty()) return new String[0];

            int elemNumb = new Scanner(head).nextInt();
            String[] input = new String[elemNumb];
            Arrays.fill(input, "");

            for (int i = 0; i < elemNumb; ++i) {
                for (int j = 0; j < elementLines; ++j) {
                    if (sysScanner.hasNextLine()) {
                        input[i] += sysScanner.nextLine() + " ";
                    }
                }
            }
            if (!isLast && sysScanner.hasNextLine()) {
                sysScanner.nextLine(); // Consume the empty line separator
            }
            return input;
        }
    }

    /* --- Resort Infrastructure Parsing --- */

    private Node[] parseNodes(String[] nodesData) {
        Node[] nodes = new Node[nodesData.length];
        for (int i = 0; i < nodesData.length; i++) {
            // idx is passed to the factory to assign unique IDs to nodes
            nodes[i] = NodeFactory.create(nodesData[i], i);
        }
        return nodes;
    }

    private SkiLift[] parseSkiLifts(String[] skiLiftsData, Node[] nodes) {
        SkiLift[] skiLifts = new SkiLift[skiLiftsData.length];
        for (int i = 0; i < skiLiftsData.length; i++) {
            skiLifts[i] = SkiLiftFactory.create(skiLiftsData[i], i, nodes);
        }
        return skiLifts;
    }

    private SkiRun[] parseSkiRuns(String[] skiRunsData, Node[] nodes) {
        SkiRun[] skiRuns = new SkiRun[skiRunsData.length];
        for (int i = 0; i < skiRunsData.length; i++) {
            skiRuns[i] = SkiRunFactory.create(skiRunsData[i], i, nodes);
        }
        return skiRuns;
    }

    /**
     * Entry point for reading the physical resort map.
     * @return A SkiResort object with all nodes, runs, and lifts cross-referenced.
     */
    public SkiResort readResortData() {
        try {
            // 1 line per Node, 1 line per Lift, 1 line per Run
            String[] nodesData = scanner.readGroup(1, false);
            String[] skiLiftsData = scanner.readGroup(1, false);
            String[] skiRunsData = scanner.readGroup(1, false);

            Node[] nodes = parseNodes(nodesData);
            SkiLift[] skiLifts = parseSkiLifts(skiLiftsData, nodes);
            SkiRun[] skiRuns = parseSkiRuns(skiRunsData, nodes);

            return new SkiResort(
                    nodes,
                    skiLifts,
                    skiRuns
            );

        } catch (Exception e) {
            System.err.println("Fatal error parsing resort data: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    /* --- Athlete Population Parsing --- */

    /**
     * Reads athletes and schedules their very first action into the simulation.
     * @param resort The already parsed resort (required in athlete constructor)
     * @param seed Simulation seed for decision making.
     * @return A PriorityQueue containing the first event for every athlete.
     */
    public AthleteData readAthleteData(SkiResort resort, int seed) {
        CustomPriorityQueue initialEvents = new CustomPriorityQueue();
        ArrayList<HistoryObserver> historyObservers = new ArrayList<>();

        Node[] nodes = resort.getNodes();
        /* Athletes are described in groups. Each group description takes 3 lines:
         * 1. Count and common attributes
         * 2. Timing/preferences
         * 3. Start node / track status
         */
        String[] groupsOfAthletes = scanner.readGroup(3, true);
        int globalId = 0;

        for (String groupData : groupsOfAthletes) {
            Scanner groupScanner = new Scanner(groupData);
            int countInThisGroup = groupScanner.nextInt();
            groupScanner.close();

            // Create 'countInThisGroup' athletes based on the common template in groupData
            for (int i = 0; i < countInThisGroup; ++i, ++globalId) {
                AthleteCreationBox box = AthleteFactory.create(groupData, i, globalId, resort);

                // If athlete is tracked, it will have his history observer
                Athlete athlete = box.athlete();
                if (box.historyObserver() != null){
                    historyObservers.add(box.historyObserver());
                }
                /* * Crucial Step: The simulation starts with athletes making their
                 * first decision at their specific start times.
                 */
                initialEvents.push(athlete.makeDecision(seed, athlete.getStartTime()));
            }
        }
        return new AthleteData(initialEvents, historyObservers);
    }
}