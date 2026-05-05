package read;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class StdInScanner {
    private final Scanner scanner = new Scanner(System.in);


    public String[] readGroup(int elementLines) {
        // Read number of elements in group
        String elemNumbString = scanner.nextLine();
        Scanner lineScanner = new Scanner(elemNumbString);
        int elemNumb = lineScanner.nextInt();

        // Read elements description
        String[] input = new String[elemNumb];
        Arrays.fill(input, "");
        for (int i = 0 ; i < elemNumb ; ++i)
            for(int j = 0 ; j < elementLines ; ++j)
                input[i] += scanner.nextLine();

        String end = scanner.nextLine();
        assert(end.equals("\n"));

        return input;
    }
}
