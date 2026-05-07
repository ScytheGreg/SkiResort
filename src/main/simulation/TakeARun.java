package simulation;

import athlete.Athlete;
import containers.Queue;
import resort.SkiRun;

import java.time.LocalTime;

public class TakeARun extends AthleteEvent{
    private SkiRun run;
    public TakeARun(LocalTime time, Athlete athlete, SkiRun run){super(time, athlete);this.run = run;}

    public Queue performAction() {
        run.skierRide();
        getAthlete().move(run);
        Queue result = new Queue();
        result.push(new MakeDecision(this.getTime().plusSeconds(run.getRideTime()), getAthlete()));
        return result;
    }
}
