public class ParallelSearchSharedMemory extends Thread {

    private static int searchNumber;
    private static int[] array;
    private int found;
    private int startPosition;
    private int endPosition;

    public ParallelSearchSharedMemory() {
    }

    public ParallelSearchSharedMemory(int searchNumber, int startPosition, int endPosition) {
        this.found = -1;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.searchNumber = searchNumber;
    }

    public int cercaParalela(int aBuscar, int[] array, int numThreads) {
        long startTime = System.nanoTime();
        if (numThreads <= 0) {
            System.out.println("Error: Number of threads must be positive");
        }
        if (array.length == 0) {
            System.out.println("Error: Array Size must be larger than 0");
        }
        ParallelSearchSharedMemory.array = array;

        int size = ParallelSearchSharedMemory.array.length;
        int sizePerThread = size / numThreads;
        int position = 0;
        int mod = size % numThreads;

        ParallelSearchSharedMemory threads[] = new ParallelSearchSharedMemory[numThreads];

        // Start threads
        for (int i = 0; i < numThreads - 1; i++) {
            threads[i] = new ParallelSearchSharedMemory(aBuscar, position, position + sizePerThread);
            position += sizePerThread;
            threads[i].start();
        }

        threads[numThreads - 1] = new ParallelSearchSharedMemory(aBuscar, position, position + sizePerThread + mod);
        threads[numThreads - 1].start();

        for (int i = 0; i < numThreads; i++) {
            try {
                // Join and show times
                threads[i].join();
                if (threads[i].getFound() != -1) {
                    long totalTime = System.nanoTime() - startTime;
                    System.out.println("Thread number " + i + " found the number (" + aBuscar + ") in time:" + totalTime);
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
        for (int i = this.startPosition; i < this.endPosition; i++) {
            if (ParallelSearchSharedMemory.array[i] == ParallelSearchSharedMemory.searchNumber) {
                this.found = i;
                break;
            }
        }
    }

}
