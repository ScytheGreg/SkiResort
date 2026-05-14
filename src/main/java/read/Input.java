package read;

import athlete.*;
import containers.PriorityQueue;
import read.factories.AthleteFactory;
import resort.*;
import read.factories.NodeFactory;
import read.factories.SkiLiftFactory;
import read.factories.SkiRunFactory;

import java.util.Scanner;

public class Input {

    private final StdInScanner scanner = new StdInScanner();

    private Node[] parseNodes(String[] nodesData) {
        Node[] nodes = new Node[nodesData.length];
        int idx = 0;

        for (String data : nodesData) {
            nodes[idx] = NodeFactory.create(data, idx);
            ++idx;
        }

        return nodes;
    }

    private SkiLift[] parseSkiLifts(String[] skiLiftsData, Node[] nodes) {
        SkiLift[] skiLifts = new SkiLift[skiLiftsData.length];
        int idx = 0;

        for (String data : skiLiftsData) {
            skiLifts[idx] = SkiLiftFactory.create(data, idx, nodes);
            ++idx;
        }

        return skiLifts;
    }

    private SkiRun[] parseSkiRuns(String[] skiRunsData, Node[] nodes) {
        SkiRun[] skiRuns = new SkiRun[skiRunsData.length];
        int idx = 0;

        for (String data : skiRunsData) {
            skiRuns[idx] = SkiRunFactory.create(data, idx, nodes);
            ++idx;
        }

        return skiRuns;
    }

    public SkiResort readResortData() {
        try {
            String[] nodesData = scanner.readGroup(1, false);
            String[] skiLiftsData = scanner.readGroup(1, false);
            String[] skiRunsData = scanner.readGroup(1, false);

            Node[] nodes = parseNodes(nodesData);
            SkiLift[] skiLifts = parseSkiLifts(skiLiftsData, nodes);
            SkiRun[] skiRuns = parseSkiRuns(skiRunsData, nodes);

            return new SkiResort(
                    nodes, nodes.length,
                    skiLifts, skiLifts.length,
                    skiRuns, skiRuns.length
            );

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return null;
        }
    }

    public PriorityQueue readAthleteData(Node[] nodes, int seed) {
        PriorityQueue athletes = new PriorityQueue();
        String[] athletesData = scanner.readGroup(3, true);
        int id = 0;
        for (String data : athletesData) {
            Scanner athlNumScanner = new Scanner(data);
            int athlNum = athlNumScanner.nextInt();
            athlNumScanner.close();
            for (int i = 0; i < athlNum; ++i, ++id) {
                Athlete man = AthleteFactory.create(data, i, id, nodes);
                athletes.push(man.makeDecision(seed, man.getStartTime()));
            }
        }
        return athletes;
    }

}