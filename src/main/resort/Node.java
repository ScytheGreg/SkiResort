package resort;

import java.util.Scanner;

public class Node  {
    int id, height = 0, lat = 0, lon = 0;
    boolean isCommunicated = false;


    public  Node(String data, int id){
        this.id = id;
        Scanner dataScanner =  new Scanner(data);
        this.height = dataScanner.nextInt();
        this.lat = dataScanner.nextInt();
        this.lon = dataScanner.nextInt();
        if (dataScanner.hasNext("s")){
            this.isCommunicated = true;
        }
        dataScanner.close();
    }
}
