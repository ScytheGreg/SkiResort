package read.factories;

import resort.Node;
import resort.SkiRun;

import java.util.Locale;
import java.util.Scanner;

/**
 * Factory class for creating SkiRun objects.
 * Downhill runs are characterized by their difficulty and how fast they wear out.
 */
public class SkiRunFactory {

    /**
     * Creates a SkiRun from a raw input string.
     * * @param data  The raw string containing run parameters.
     * @param id    Unique ID for this run.
     * @param nodes Array of already created Nodes used to resolve start/end points.
     * @return A fully initialized SkiRun object.
     * * Expected format: "beginningNodeID endNodeID advanceLevel rideTime baseLure toughness"
     */
    public static SkiRun create(String data, int id, Node[] nodes) {
        Scanner sc = new Scanner(data);
        // Ensure decimal points in baseLure and toughness are parsed correctly
        sc.useLocale(Locale.ENGLISH);

        // 1. Resolve the graph connection
        int beg = sc.nextInt();
        int end = sc.nextInt();

        // 2. Read run-specific attributes
        int advanceLevel = sc.nextInt(); // Skill required (0-10)
        int rideTime = sc.nextInt();     // Constant time to ski down (seconds)

        /* * 3. Read degradation parameters:
         * baseLure (w_base): minimum attractiveness when the run is ruined.
         * toughness (t): resistance to wearing out (0 to 1).
         */
        double baseLure = sc.nextDouble();
        double toughness = sc.nextDouble();

        sc.close();

        // Return the new SkiRun, mapping the IDs to actual Node references
        return new SkiRun(
                nodes[beg],
                nodes[end],
                rideTime,
                id,
                advanceLevel,
                baseLure,
                toughness
        );
    }
}