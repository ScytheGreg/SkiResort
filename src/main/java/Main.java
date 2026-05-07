import simulation.Simulation;

public class Main {

    public static void main(String[] args){

        if (args.length == 0) {
            System.err.println("Error: Please provide a number as the first argument.");
            return;
        }

        int seed;

        try {
            seed = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Argument must be a valid integer.");
            return;
        }
        Simulation simulation = new Simulation(seed);
        simulation.simulate(seed);
    }
}