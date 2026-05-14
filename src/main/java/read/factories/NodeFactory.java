package read.factories;

import resort.Node;

import java.util.Locale;
import java.util.Scanner;

public class NodeFactory {

    /**
     * Creates a Node from a raw input line.
     * <p>
     * Expected format:
     * height lat lon [s]
     */
    public static Node create(String data, int id) {
        Scanner scanner = new Scanner(data);
        scanner.useLocale(Locale.ENGLISH);

        int height = scanner.nextInt();
        int lat = scanner.nextInt();
        int lon = scanner.nextInt();

        boolean isCommunicated = scanner.hasNext("s");

        scanner.close();

        return new Node(id, height, lat, lon, isCommunicated);
    }
}