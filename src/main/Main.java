import read.Input;
import resort.SkiResort;
import simulation.Simulation;

public class Main {

    static void main(String[] args){
        Input reader = new Input();
        SkiResort resort = reader.readResortData();
        Simulation simulation = reader.readAthleteData(resort.getNodes());
        simulation.loadPickUpEvents(resort.getSkiLifts());
        simulation.simulate();
    }
}