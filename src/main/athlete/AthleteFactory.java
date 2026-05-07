package athlete;

import resort.Node;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class AthleteFactory {

    public static Athlete create(String data,
                                 int groupId,
                                 int id,
                                 Node[] nodes) {

        Scanner sc = new Scanner(data);
        sc.useLocale(Locale.ENGLISH);

        sc.nextInt(); // skip number of athletes

        int advanceLevel = sc.nextInt();
        double impulsivity = sc.nextDouble();

        boolean isTracked = false;
        if (sc.hasNext("s")) {
            isTracked = true;
            sc.next("s");
        }

        double levelMatchLure = sc.nextDouble();
        double runSmoothnessLure = sc.nextDouble();

        Node startNode = nodes[sc.nextInt()];

        LocalTime baseTime = LocalTime.parse(
                sc.next(),
                DateTimeFormatter.ofPattern("HH:mm:ss")
        );

        int delay = 0;
        if (sc.hasNextInt()) {
            delay = sc.nextInt();
        }

        sc.close();

        LocalTime startTime = baseTime.plusSeconds((long) delay * groupId);

        return new Athlete(
                id,
                advanceLevel,
                impulsivity,
                isTracked,
                levelMatchLure,
                runSmoothnessLure,
                startNode,
                startTime
        );
    }
}