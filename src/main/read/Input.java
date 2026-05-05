package read;

import resort.*;

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

    private SkiLift[] parseSkiLifts(String[] skiLiftsData) {
        SkiLift[] skiLifts = new SkiLift[skiLiftsData.length];
        int idx = 0;

        for (String data : skiLiftsData) {
            skiLifts[idx] = new SkiLift(data, idx);
            ++idx;
        }

        return skiLifts;
    }

    private SkiRun[] parseSkiRuns(String[] skiRunsData) {
        SkiRun[] skiRuns = new SkiRun[skiRunsData.length];
        int idx = 0;

        for (String data : skiRunsData) {
            skiRuns[idx] = new SkiRun(data, idx);
            ++idx;
        }

        return skiRuns;
    }

    public SkiResort readResortData() {
        try {
            String[] nodesData = scanner.readGroup(1);
            String[] skiLiftsData = scanner.readGroup(1);
            String[] skiRunsData = scanner.readGroup(1);

            Node[] nodes = parseNodes(nodesData);
            SkiLift[] skiLifts = parseSkiLifts(skiLiftsData);
            SkiRun[] skiRuns = parseSkiRuns(skiRunsData);

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
}