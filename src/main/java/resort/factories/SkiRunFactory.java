package resort.factories;

import resort.Node;
import resort.SkiRun;

import java.util.Locale;
import java.util.Scanner;

public class SkiRunFactory {

    public static SkiRun create(String data, int id, Node[] nodes) {
        Scanner sc = new Scanner(data);
        sc.useLocale(Locale.ENGLISH);

        int beg = sc.nextInt();
        int end = sc.nextInt();
        int advanceLevel = sc.nextInt();
        int rideTime = sc.nextInt();
        double baseLure = sc.nextDouble();
        double toughness = sc.nextDouble();

        sc.close();

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