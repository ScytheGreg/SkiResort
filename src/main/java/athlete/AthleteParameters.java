package athlete;

import resort.Node;
import resort.SkiResort;

import java.time.LocalTime;

public record AthleteParameters(
        int id,
        int advanceLevel,
        double impulsivity,
        double boredomRate,
        double levelMatchLure,
        double runSmoothnessLure,
        double boredomLure,
        Node startNode,
        LocalTime startTime,
        SkiResort resort
) {}
