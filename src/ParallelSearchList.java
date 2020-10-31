import java.util.LinkedList;

public class ParallelSearchList extends Thread {

    private LinkedList list;
    private int size;
    private long time;
    private int searchNumber;
    private int direction;

    public ParallelSearchList(int size, int searchNumber, int direction) {
        this.size = size;
        this.time = 0;
        this.direction = direction;
        this.searchNumber = searchNumber;
        this.list = new LinkedList();

        for (int i = 0; i < size; i++) {
            list.add(i);
        }
    }

    public long getTime() {
        return time;
    }

    public void run() {

        int start, end;
        long startTime, endTime;

        // Same array, different order depending on direction var
        if (direction == 1) {
            start = 0;
            end = size;
            direction = 1;
        } else {
            start = size;
            end = 0;
            direction = -1;
            // This case will always be faster, as the comparison that the compiler does in each loop will just compare to 0
            // instead of having to retrieve the end value and comparing it
        }

        startTime = System.nanoTime();
        for (int i = start; i < end; i = i + direction) {
            if (searchNumber == (int) list.get(i)) {
                break;
            }
        }
        endTime = System.nanoTime();

        time = endTime - startTime;
    }

}
