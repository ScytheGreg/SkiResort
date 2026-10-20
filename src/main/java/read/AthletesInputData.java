package read;

import athlete.observer.HistoryObserver;
import containers.CustomPriorityQueue;

import java.util.ArrayList;

public record AthletesInputData(
        CustomPriorityQueue queue,
        ArrayList<HistoryObserver> historyObservers
) {
}
