package resort;

import athlete.Athlete;

public class SkiRun extends Edge {
    private final int advanceLevel;
    private final double baseLure;
    private final double toughness;

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
    public String getName(){return "ski run";}

    public int getAdvanceLevel() {
        return advanceLevel;
    }

    public double smoothnessCalculate(){
        return baseLure + (1 - baseLure) * Math.pow(toughness, getServedAthletesCnt());
    }

    @Override
    public void addWaiting(Athlete athlete){}
}
