package plots;

import kadra.mapki.GeneratorMapek;

import resort.SkiResort;
import kadra.mapki.styl.*;
import resort.*;

import java.util.LinkedList;

public abstract class Plotter {
    protected abstract LinkedList<String> runLabel(SkiRun run);
    protected abstract LinkedList<String> liftLabel(SkiLift lift);

    public  void plotResort( SkiResort resort, String fileName, GeneratorMapek plotter){
        plotter.zeruj();
        plotNodes(plotter, resort.getNodes());
    }

    private static void plotNodes(GeneratorMapek plotter, Node[] nodes) {
        for (Node node : nodes) {
            StylWezla style = new StylWezla(GruboscKonturu.ZWYKLY);
            if (node.isCommunicated()) {
                style = new StylWezla(GruboscKonturu.POGRUBIONY);
            }
            plotter.dodajWezel(node.getId(), node.getLat(), node.getLon(), style);
        }
    }

    private void plotEdge(Edge edge, StylKrawedzi style, LinkedList<String> label, GeneratorMapek plotter){
        if (label.size() > 1) {
            plotter.dodajKrawedz(edge.getBeginning().getId(),
                    edge.getEnd().getId(), style, label);
        }else{
            plotter.dodajKrawedz(edge.getBeginning().getId(),
                    edge.getEnd().getId(), style, label.getFirst());
        }

    }

    private void plotSkiRuns(GeneratorMapek plotter, SkiRun[] skiRuns) {
        for (SkiRun run : skiRuns) {
            LinkedList<String> label = runLabel(run);
            StylKrawedzi style = new StylKrawedzi(StylLinii.CIAGLA);
            plotEdge(run, style, label, plotter);
        }
    }

    private void plotSkiLifts(GeneratorMapek plotter, SkiLift[] skiLifts) {
        for (SkiLift lift : skiLifts){
            LinkedList<String> label = liftLabel(lift);
            StylKrawedzi style = new StylKrawedzi(StylLinii.PRZERYWANA);
            plotEdge(lift, style, label, plotter);
        }
    }

}
