package net.grigoriadi.algorithms.sort;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArraySort<T extends Comparable<T>> {

    private int level;

    private Class<T> tClass;

    private boolean debug;

    public ArraySort(Class<T> tClass, boolean debug) {
        this.tClass = tClass;
        this.debug = debug;
    }

    public void sortWithArraySort(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int right = i;
            int left = i - 1;
            while (left >= 0 && arr[left].compareTo(arr[right]) > 0) {
                T leftElement = arr[left];
                arr[left] = arr[right];
                arr[right] = leftElement;
                right = left;
                left = left - 1;
            }
        }
    }

    public void sortWithMergeSort(T[] arr) {
        mergeSortHalve(arr, 0, arr.length - 1);
    }

    private void mergeSortHalve(T[] half, int start, int end) {
        level++;
        printMessage("Prepare sorting halves: start:" + start + ", end:" + end);
        printMessage(Arrays.toString(half));

        int length = end - start + 1;
        printMessage("length = " + length);
        if (length < 2) {
            throw new IllegalStateException("error calculating halve");
        }
        if (length == 2) {
            if (half[start].compareTo(half[end]) > 0) {
                T first = half[start];
                half[start] = half[end];
                half[end] = first;
                printMessage("Two-element half sorted: start:" + start + ", end:" + end);
            }
            level--;
            return;
        }
        int mid = (length) / 2 + start;
        if (length % 2 == 0) {
            mid--;
        }
        //5-9 -> 7
        //0-4 -> 2
        printMessage("mid = " + mid);
        printMessage("Sorting fisrt half");
        mergeSortHalve(half,start, mid);
        printMessage("Sorting second half");
        int midForSecondHalf = mid;
        if (length > 3) {
            midForSecondHalf++;
        }
        mergeSortHalve(half, midForSecondHalf, end);
        sortTwoHalves(half, start, mid, mid+1, end);
        level--;
    }

    private void sortTwoHalves(T[] arr, int startLeft, int endLeft, int startRight, int endRight) {
        int length = 1 + endRight - startLeft;
        @SuppressWarnings("unchecked")
        T[] copy = (T[]) Array.newInstance(tClass, length);
        printMessage("Sorting result halves: startLeft:" + startLeft + ", endLeft:" + endLeft + " endRight:" + endRight);
        printMessage(Arrays.toString(arr));
        int left = startLeft;
        int right = startRight;
        //0,1,2
        for (int i = 0; i < length; i++) {
            printMessage("left = " + left + ", right = " + right);

            if (right > endRight || (left <= endLeft && arr[left].compareTo(arr[right]) <= 0)) {
                copy[i] = arr[left];
                left++;
            } else {
                copy[i] = arr[right];
                right++;
            }
        }

        for (int i = 0; i < length; i++) {
            arr[startLeft + i] = copy[i];
        }
        printMessage("After sort: "+Arrays.toString(arr));
    }

    private void printMessage(String s) {
        if (!debug) {
            return;
        }

        System.out.print(level + "-");
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(s);
    }
}
