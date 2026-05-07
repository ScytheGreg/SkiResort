package simulation;

import java.time.LocalTime;
import java.util.Arrays;

import containers.PriorityQueue;
import containers.*;
import read.Input;
import resort.*;

public class Simulation {
    static final private LocalTime START_TIME = LocalTime.of(9, 0);
    static final private LocalTime END_TIME = LocalTime.of(15, 0);
    private PriorityQueue events = new PriorityQueue();

    public Simulation() {
        Input reader = new Input();

        SkiResort resort = reader.readResortData();
        connectResortGraph(resort);

        events = reader.readAthleteData(resort.getNodes());
        loadPickUpEvents(resort.getSkiLifts());
    }

    public void loadPickUpEvents(SkiLift[] lifts) {
        for (SkiLift lift : lifts) {
            events.push(new PickUpPassengers(START_TIME, lift));
        }
    }

    private void countNodesFromEdges(int[] cntResult, Edge[] edges) {
        Arrays.fill(cntResult, 0);
        for (Edge edge : edges) {
            cntResult[edge.getBeginning().getId()]++;
        }
    }

    private void initNodesArrrays(Edge[][] edgeInNodes, int[] edgeCnt, int N) {
        for (int i = 0; i < N; ++i) {
            edgeInNodes[i] = new Edge[edgeCnt[i]];
        }
    }

    private void countNodesArrays(Edge[][] edgeInNodes, int[] edgeCnt, Edge[] edges){
        for (Edge edge : edges){
            int nodeId = edge.getBeginning().getId();
            edgeInNodes[nodeId][--edgeCnt[nodeId]] = edge;
        }
    }

    private void connectEdges(Edge[] edges, int N, Edge[][] edgesInNodes){
        int[] edgeCnt = new int[N]; // Count lifts in nodes
        countNodesFromEdges(edgeCnt, edges);
        initNodesArrrays(edgesInNodes, edgeCnt, N);
        countNodesArrays(edgesInNodes, edgeCnt, edges);
    }


    public void connectResortGraph(SkiResort resort) {
        int N = resort.getNodesNum();

        SkiLift[][] skiLiftsInNodes = new SkiLift[N][];
        connectEdges(resort.getSkiLifts(), N, skiLiftsInNodes);

        SkiRun[][] skiRunsInNodes = new SkiRun[N][];
        connectEdges(resort.getSkiRuns(), N, skiRunsInNodes);

        Node[] nodes = resort.getNodes();
        for(int i = 0 ; i < N ;++i){
            nodes[i].setSkiRuns(skiRunsInNodes[i]);
            nodes[i].setSkiLifts(skiLiftsInNodes[i]);
        }

    }


    public void simulate() throws emptyContainerException {
        while (!events.isEmpty() && events.getFront().getTime().isBefore(END_TIME)) {
            Queue newEvents = events.takeFront().performAction();
            while (!newEvents.isEmpty())
                events.push(newEvents.takeFront());
        }
    }
}
