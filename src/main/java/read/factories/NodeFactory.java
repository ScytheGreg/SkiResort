package read.factories;

import resort.Node;

import java.util.Locale;
import java.util.Scanner;

/**
 * Factory class for creating Node objects.
 * Nodes represent the stations or junctions in the ski resort.
 */
public class NodeFactory {

    /**
     * Creates a Node from a single line of input data.
     * * @param data The raw string containing height, latitude, longitude, and optional status.
     * @param id   The unique identifier assigned to this node (usually its index in the input list).
     * @return A fully initialized Node object.
     * * Expected format in input: "height lat lon [s]"
     */
    public static Node create(String data, int id) {
        Scanner scanner = new Scanner(data);
        // Ensure decimal/numeric consistency regardless of system language settings
        scanner.useLocale(Locale.ENGLISH);

        // Read physical coordinates
        int height = scanner.nextInt(); // Altitude in meters
        int lat = scanner.nextInt();    // Latitude coordinate
        int lon = scanner.nextInt();    // Longitude coordinate

        /* * The "s" flag (skomunikowany) indicates if this node is accessible
         * from the outside world (e.g., parking, bus station).
         * Only these nodes can be starting points for athletes.
         */
        boolean isCommunicated = scanner.hasNext("s");

        scanner.close();

        return new Node(id, height, lat, lon, isCommunicated);
    }
}