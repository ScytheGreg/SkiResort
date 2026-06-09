package athlete;

import athlete.observer.HistoryObserver;

public record AthleteCreationBox(
        Athlete athlete,
        HistoryObserver historyObserver
) {}
