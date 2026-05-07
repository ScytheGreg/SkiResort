package resort;

import java.util.Locale;
import java.util.Scanner;

public class Node {
    private final int id, height, lat, lon;
    private final boolean isCommunicated;

    private SkiRun[] skiRuns;
    private SkiLift[] skiLifts;

    public Node(String data, int id) {
        this.id = id;
        Scanner dataScanner = new Scanner(data);
        dataScanner.useLocale(Locale.ENGLISH);
        this.height = dataScanner.nextInt();
        this.lat = dataScanner.nextInt();
        this.lon = dataScanner.nextInt();
        if (dataScanner.hasNext("s")) {
            this.isCommunicated = true;
        } else this.isCommunicated = false;
        dataScanner.close();
    }

    public int getId() {
        return id;
    }

    public boolean isCommunicated() {
        return isCommunicated;
    }

    public void setSkiLifts(SkiLift[] skiLifts) {
        this.skiLifts = skiLifts;
    }

    public void setSkiRuns(SkiRun[] skiRuns){
        this.skiRuns = skiRuns;
    }
}
