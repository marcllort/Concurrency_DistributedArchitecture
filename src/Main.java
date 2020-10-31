import java.util.Random;

// Marc Llort (marc.llort)
// Distribuida 2020-2021

public class Main {

    private static int NUMBER_TO_SEARCH = 49;
    private static int MAX_NUMBERS = 1000;
    private static int NUMBER_THREADS = 8;

    public static void main(String[] args) {

        switch (Integer.parseInt(args[0])) {

            case 1:
                // Exercise 3
                ParallelSearchList thread1 = new ParallelSearchList(NUMBER_TO_SEARCH, MAX_NUMBERS, 1);
                ParallelSearchList thread2 = new ParallelSearchList(NUMBER_TO_SEARCH, MAX_NUMBERS, -1);
                thread1.start();
                thread2.start();
                try {
                    thread1.join();
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (thread1.getTime() < thread2.getTime()) {
                    System.out.println("Thread 1 was faster: " + thread1.getTime() + " in comparison with Thread 2: " + thread2.getTime());
                } else {
                    System.out.println("Thread 2 was faster: " + thread2.getTime() + " in comparison with Thread 1: " + thread1.getTime());
                }
                break;

            case 2:
                // Exercise 4
                ParallelSearchThreads parallelSearchThreads = new ParallelSearchThreads();
                int[] array = generateArray();
                int position = parallelSearchThreads.cercaParalela(array[NUMBER_TO_SEARCH], array, NUMBER_THREADS);
                System.out.println("Position in the array was: " + position);
                break;

            case 3:
                // Exercise 5
                ParallelSearchSharedMemory parallelSearchSharedMemory = new ParallelSearchSharedMemory();
                int[] arraySharedMemory = generateArray();
                int positionSharedMemory = parallelSearchSharedMemory.cercaParalela(arraySharedMemory[NUMBER_TO_SEARCH], arraySharedMemory, NUMBER_THREADS);
                System.out.println("Position in the array was: " + positionSharedMemory);
                break;

            case 4:
                // Exercise 7
                long startTime, totalTime;
                int[] arrayMergeSort = generateArray();
                MergeSortThreads mergeSortThreads = new MergeSortThreads(arrayMergeSort);
                mergeSortThreads.sort();
                break;

            case 5:
                // Exercise 8
                int[] arrayMergeSortSeq = generateArray();
                MergeSortSeq mergeSortSeq = new MergeSortSeq(arrayMergeSortSeq);
                mergeSortSeq.mergeSort();
                break;

        }

    }

    private static int[] generateArray() {
        Random rd = new Random();
        int[] arr = new int[MAX_NUMBERS];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rd.nextInt();
        }
        return arr;
    }

}
