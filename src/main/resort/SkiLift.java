package resort;

import containers.Queue;

import java.util.Locale;
import java.util.Scanner;

public class SkiLift extends Edge{
    private Queue waiting;
    int capacity;
    int frequency;

    public SkiLift(String data, int id, Node[] nodes) {
        Scanner dataScanner = new Scanner(data);
        dataScanner.useLocale(Locale.ENGLISH);

        int beg = dataScanner.nextInt();
        int end = dataScanner.nextInt();
        this.frequency = dataScanner.nextInt();
        this.capacity = dataScanner.nextInt();
        int rideTime = dataScanner.nextInt();
        dataScanner.close();
        super(nodes[beg], nodes[end], rideTime, id);

        this.waiting = new Queue();
    }
}
