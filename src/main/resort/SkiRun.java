package resort;

import java.util.Locale;
import java.util.Scanner;

public class SkiRun extends Edge {
    private final int advanceLevel;
    private final double baseLure;
    private final double toughness;

    private int skierCnt = 0;

    public SkiRun(Node beginning,
                  Node end,
                  int rideTime,
                  int id,
                  int advanceLevel,
                  double baseLure,
                  double toughness) {

        super(beginning, end, rideTime, id);

        this.advanceLevel = advanceLevel;
        this.baseLure = baseLure;
        this.toughness = toughness;
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
