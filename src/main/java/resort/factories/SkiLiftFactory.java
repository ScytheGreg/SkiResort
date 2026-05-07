package resort.factories;

import resort.Node;
import resort.SkiLift;

import java.util.Locale;
import java.util.Scanner;

public class SkiLiftFactory {

    public static SkiLift create(String data, int id, Node[] nodes) {
        Scanner sc = new Scanner(data);
        sc.useLocale(Locale.ENGLISH);

        int beg = sc.nextInt();
        int end = sc.nextInt();
        int frequency = sc.nextInt();
        int capacity = sc.nextInt();
        int rideTime = sc.nextInt();

        sc.close();

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