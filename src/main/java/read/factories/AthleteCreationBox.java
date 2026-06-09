package read.factories;

import athlete.Athlete;
import athlete.observer.HistoryObserver;

public record AthleteCreationBox(
        Athlete athlete,
        HistoryObserver historyObserver
) {}
