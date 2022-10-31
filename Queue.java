import java.util.LinkedList;

class Memory {
    LinkedList<Integer> list = new LinkedList<>();
    boolean canThdDequeue = false;
    int num;

    synchronized public void dequeue() {
        while (!canThdDequeue) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getStackTrace());
            }
        }
        num = list.removeFirst();
        System.out.println("Dequeued element  : " + num);
        canThdDequeue = false;
        notify();
    }

    synchronized public void enqueue(int data) {
        while (canThdDequeue) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getStackTrace());
            }
        }
        list.add(data);
        System.out.println("enqueued element : " + data);
        canThdDequeue = true;
        notify();
    }
}

class ThdEnqueue implements Runnable {
    Thread t;
    Memory mem;

    ThdEnqueue(Memory mem) {
        this.mem = mem;
        t = new Thread(this, "ThdEnqueue");
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            mem.enqueue(i);
        }
    }
}

class ThdDequeue implements Runnable {
    Memory mem;
    Thread t;

    ThdDequeue(Memory mem) {
        this.mem = mem;
        t = new Thread(this, "ThdDequeue");
    }

    public void run() {
        for (int i = 0; i < 10; i++)
            mem.dequeue();
    }
}

public class Queue {
    public static void main(String args[]) {
        Memory mem = new Memory();
        ThdEnqueue p = new ThdEnqueue(mem);
        ThdDequeue c = new ThdDequeue(mem);
        c.t.start();
        p.t.start();
    }
}
