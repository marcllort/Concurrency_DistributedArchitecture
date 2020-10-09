import java.util.Arrays;

public class ParallelSearchThreads extends Thread {

    private int found;
    private int startPosition;
    private int searchNumber;
    private int[] array;

    public ParallelSearchThreads() {
    }

    public ParallelSearchThreads(int[] array, int searchNumber, int startPosition) {
        this.found = -1;
        this.startPosition = startPosition;
        this.searchNumber = searchNumber;
        this.array = array;
    }

    public int cercaParallela(int aBuscar, int[] array, int numThreads) {

        if (numThreads <= 0) {
            System.out.println("Error: Number of threads must be positive");
        }
        if (array.length == 0) {
            System.out.println("Error: Array Size must be larger than 0");
        }

        int size = array.length;
        int sizePerThread = size / numThreads;
        int position = 0;
        int mod = size % numThreads;

        ParallelSearchThreads threads[] = new ParallelSearchThreads[numThreads];

        for (int i = 0; i < numThreads - 1; i++) {
            threads[i] = new ParallelSearchThreads(Arrays.copyOfRange(array, position, position + sizePerThread), aBuscar, position);
            position += sizePerThread;
            threads[i].start();
        }

        threads[numThreads - 1] = new ParallelSearchThreads(Arrays.copyOfRange(array, position, position + sizePerThread + mod), aBuscar, position);
        threads[numThreads - 1].start();

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
                if (threads[i].getFound() != -1) {
                    System.out.println("Thread number " + i + " found the number (" + aBuscar + ")");
                    return threads[i].getFound();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return -1;
    }

    public int getFound() {
        return found;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == searchNumber) {
                found = startPosition + i;
                break;
            }
        }
    }

}
