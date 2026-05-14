package read.factories;

import resort.Node;
import resort.SkiLift;

import java.util.Locale;
import java.util.Scanner;

/**
 * Factory class for creating SkiLift objects.
 * Responsible for parsing lift parameters and linking them to existing Nodes.
 */
public class SkiLiftFactory {

    /**
     * Creates a SkiLift from a raw input string.
     * * @param data  The raw string containing lift parameters.
     * @param id    Unique ID for this lift.
     * @param nodes Array of already created Nodes used to resolve start/end points.
     * @return A fully initialized SkiLift object.
     * * Expected format: "beginningNodeID endNodeID frequency capacity rideTime"
     */
    public static SkiLift create(String data, int id, Node[] nodes) {
        Scanner sc = new Scanner(data);
        // Force ENGLISH locale to handle potential decimal inputs consistently
        sc.useLocale(Locale.ENGLISH);

        // Read the indices of the start and end stations
        int beg = sc.nextInt();
        int end = sc.nextInt();

        /* * Simulation-specific parameters:
         * frequency: how many seconds between subsequent chairs/cabins.
         * capacity: how many athletes fit on one chair/cabin.
         * rideTime: how long it takes to reach the top (seconds).
         */
        int frequency = sc.nextInt();
        int capacity = sc.nextInt();
        int rideTime = sc.nextInt();

        sc.close();

        // Resolve Node references from the array and return the new Lift
        return new SkiLift(
                nodes[beg],
                nodes[end],
                rideTime,
                id,
                frequency,
                capacity
        );
    }
}