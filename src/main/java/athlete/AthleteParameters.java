package athlete;

import resort.Node;

import java.time.LocalTime;

public record AthleteParameters(
        int id,
        int advanceLevel,
        double impulsivity,
        boolean isTracked,
        double boredomRate,
        double levelMatchLure,
        double runSmoothnessLure,
        double boredomLure,
        Node startNode,
        LocalTime startTime
) {}
