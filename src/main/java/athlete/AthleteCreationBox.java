package athlete;

public record AthleteCreationBox(
        Athlete athlete,
        HistoryObserver historyObserver
) {}
