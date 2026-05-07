package read;

import athlete.Athlete;
import containers.PriorityQueue;
import resort.*;
import simulation.*;
import java.util.Scanner;

public class Input {

    private StdInScanner scanner = new StdInScanner();

    private Node[] parseNodes(String[] nodesData) {
        Node[] nodes = new Node[nodesData.length];
        int idx = 0;

        for (String data : nodesData) {
            nodes[idx] = new Node(data, idx);
            ++idx;
        }

        return nodes;
    }

    private SkiLift[] parseSkiLifts(String[] skiLiftsData, Node[] nodes) {
        SkiLift[] skiLifts = new SkiLift[skiLiftsData.length];
        int idx = 0;

        for (String data : skiLiftsData) {
            skiLifts[idx] = new SkiLift(data, idx, nodes);
            ++idx;
        }

        return skiLifts;
    }

    private SkiRun[] parseSkiRuns(String[] skiRunsData, Node[] nodes) {
        SkiRun[] skiRuns = new SkiRun[skiRunsData.length];
        int idx = 0;

        for (String data : skiRunsData) {
            skiRuns[idx] = new SkiRun(data, idx, nodes);
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

    public PriorityQueue readAthleteData(Node[] nodes){
        PriorityQueue athletes = new PriorityQueue();
        String[] athletesData = scanner.readGroup(3, true);
        int id = 0;
        for(String data : athletesData){
            Scanner athlNumScanner = new Scanner(data);
            int athlNum = athlNumScanner.nextInt();
            athlNumScanner.close();
            for(int i = 0 ; i < athlNum ; ++i, ++id){
                Athlete man = new Athlete(data, i, id, nodes);
                athletes.push(new MakeDecision(man.getStartTime(), man));
            }
        }
        return athletes;
    }

}