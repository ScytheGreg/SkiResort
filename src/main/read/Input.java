package read;

import resort.SkiResort;

import java.io.InputStream;
import java.util.Scanner;
public class Input{





    static public SkiResort readResortData(InputStream source){
        try {
            Scanner inputScanner = new Scanner(source);


        }catch (Exception e){
            System.err.println(e.getMessage());
        }


        // Read nodes number

    }
    static public Simulation readAthleteData(InputStream soruce){
        return new Simulation();
    }
}