import java.util.Arrays;

public class MergeSortThreads extends Thread {

    private int[] array;

    public MergeSortThreads(int[] array) {
        this.array = array;
    }

    public void sort() {
        System.out.println("Unsorted array:");
        System.out.println(Arrays.toString(array));
        long startTime = System.nanoTime();
        this.start();
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long totalTime = System.nanoTime() - startTime;
        System.out.println("Sorted array:");
        System.out.println(Arrays.toString(array));
        System.out.println("Array has been merge-sorted in time: " + totalTime);
    }

    @Override
    public void run() {

        if (array.length > 1) {
            int[] leftArray = Arrays.copyOfRange(array, 0, array.length / 2);
            int[] rightArray = Arrays.copyOfRange(array, array.length / 2, array.length);

            MergeSortThreads leftThread = new MergeSortThreads(leftArray);
            MergeSortThreads rightThread = new MergeSortThreads(rightArray);

            leftThread.start();
            rightThread.start();

            try {
                leftThread.join();
                rightThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            // https://www.geeksforgeeks.org/merge-sort/

            int i = 0, j = 0, k = 0;

            while (i < leftArray.length && j < rightArray.length) {
                if (leftArray[i] < rightArray[j]) {
                    this.array[k] = leftArray[i];
                    i++;
                } else if (rightArray[j] <= leftArray[i]) {
                    this.array[k] = rightArray[j];
                    j++;
                }
                k++;
            }

            while (i < leftArray.length) {
                this.array[k] = leftArray[i];
                i++;
                k++;
            }

            while (j < rightArray.length) {
                this.array[k] = rightArray[j];
                j++;
                k++;
            }

        }
    }

}
