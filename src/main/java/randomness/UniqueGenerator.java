package randomness;

import java.util.Random;

public class UniqueGenerator {
    private static UniqueGenerator uniqueGenerator;
    private static Random gen;

    private UniqueGenerator(int seed){
        gen = new Random(seed);
    }

    public static Random getGen(int seed){
        if (gen == null){
            uniqueGenerator = new UniqueGenerator(seed);
        }
        return gen;
    }

}
