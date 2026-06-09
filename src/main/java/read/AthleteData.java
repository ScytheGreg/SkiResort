package read;

import athlete.HistoryObserver;
import containers.CustomPriorityQueue;

import java.util.ArrayList;

public record AthleteData(
        CustomPriorityQueue queue,
        ArrayList<HistoryObserver> historyObservers
) {
}
