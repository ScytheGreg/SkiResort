package read.factories;

import athlete.Athlete;
import resort.Node;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

/**
 * Factory class responsible for creating Athlete objects from raw string data.
 * It handles the specific staggered arrival logic (delay) for groups of athletes.
 */
public class AthleteFactory {

    /**
     * Creates an individual Athlete from a group description.
     * * @param data      The multi-line string containing the group's parameters.
     * @param groupId   The index of the athlete within this specific group (0 to N-1).
     * @param id        The unique global ID to assign to this athlete.
     * @param nodes     The array of resort nodes to resolve the starting position.
     * @return A fully initialized Athlete object.
     */
    public static Athlete create(String data,
                                 int groupId,
                                 int id,
                                 Node[] nodes) {

        Scanner sc = new Scanner(data);
        // Force ENGLISH locale to ensure double values (e.g., 0.5) are parsed correctly regardless of system settings
        sc.useLocale(Locale.ENGLISH);

        // Skip the first integer (the total number of athletes in this group)
        sc.nextInt();

        // Parameters describing the athlete's skill and behavior
        int advanceLevel = sc.nextInt();
        double impulsivity = sc.nextDouble();

        // Check for the optional "s" flag which indicates this athlete's actions should be logged
        boolean isTracked = false;
        if (sc.hasNext("s")) {
            isTracked = true;
            sc.next("s");
        }

        // Weighting factors for the rational decision-making algorithm
        double levelMatchLure = sc.nextDouble();
        double runSmoothnessLure = sc.nextDouble();

        // Resolve the starting node object using the ID provided in the input
        Node startNode = nodes[sc.nextInt()];

        // Parse the base arrival time (HH:mm:ss)
        LocalTime baseTime = LocalTime.parse(
                sc.next(),
                DateTimeFormatter.ofPattern("HH:mm:ss")
        );

        // Read the optional delay (seconds between arrival of each athlete in the group)
        int delay = 0;
        if (sc.hasNextInt()) {
            delay = sc.nextInt();
        }

        sc.close();

        /*
         * Staggered Arrival Logic:
         * If 10 athletes arrive at 09:00:00 with a delay of 30s:
         * Athlete 0 arrives at 09:00:00
         * Athlete 1 arrives at 09:00:30
         * Athlete 2 arrives at 09:01:00...
         */
        LocalTime startTime = baseTime.plusSeconds((long) delay * groupId);

        return new Athlete(
                id,
                advanceLevel,
                impulsivity,
                isTracked,
                levelMatchLure,
                runSmoothnessLure,
                startNode,
                startTime
        );
    }
}