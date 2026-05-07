package resort;

import java.util.Locale;
import java.util.Scanner;

public class SkiRun extends Edge {
    private final int advanceLevel;
    private final double baseLure;
    private final double toughness;

    private int skierCnt = 0;

    public SkiRun(String data, int id, Node[] nodes) {
        Scanner dataScanner = new Scanner(data);
        dataScanner.useLocale(Locale.ENGLISH);
        int beg = dataScanner.nextInt();
        int end = dataScanner.nextInt();
        this.advanceLevel = dataScanner.nextInt();
        int rideTime = dataScanner.nextInt();
        this.baseLure = dataScanner.nextDouble();
        this.toughness = dataScanner.nextDouble();
        super(nodes[beg], nodes[end], rideTime, id);
    }

    public void skierRide(){
        skierCnt++;
    }

    public int getAdvanceLevel() {
        return advanceLevel;
    }

    public double smoothnessCalculate(){
        return baseLure + (1 - baseLure) * Math.pow(toughness, skierCnt);
    }
}
