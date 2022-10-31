import java.io.*;
import java.util.*;

class countThread implements Runnable {
    String fileName;
    Thread td;
    int count;
    Scanner scan;

    countThread(String fileName) {
        this.fileName = fileName;
        this.td = new Thread(this);
        try {
            this.scan = new Scanner(new File(this.fileName));
        } catch (FileNotFoundException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void run() {
        try {
            while (scan.hasNext()) {
                count += 1;
                scan.next();
            }
        } catch (NullPointerException e) {
            System.out.println("wrong File name");
        }
    }

    public int getCount() {
        return this.count;
    }
}

public class wordcount {
    public static void main(String args[]) {
        String arr[] = new String[10];
        countThread thd;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter file names");
        arr = scan.nextLine().split(" ");
        for (String str : arr) {
            thd = new countThread(str);
            thd.run();
            System.out.println(str + " : " + thd.getCount());
        }
    }
}