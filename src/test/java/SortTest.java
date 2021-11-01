import net.grigoriadi.algorithms.sort.ArraySort;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SortTest {

    private Integer[] subjectArray;

    @Before
    public void setUp() {
        subjectArray = new Integer[]{10,5,8,2,3,6,4,9,1,7};
    }

    @Test
    public void testArraySort() {
        ArraySort<Integer> sort = new ArraySort<>(Integer.class, true);
        sort.sortWithArraySort(subjectArray);
        System.out.println(Arrays.toString(subjectArray));
    }

    @Test
    public void testMergeSortSimple() {
        ArraySort<Integer> sort = new ArraySort<>(Integer.class, true);
        sort.sortWithMergeSort(subjectArray);
        System.out.println(Arrays.toString(subjectArray));

    }

    @Test
    public void testMergeSortRandom() {
        ArraySort<Integer> sort = new ArraySort<>(Integer.class, false);
        Integer[] randomOrder = ArrayUtil.createRandomOrderedIntegers(128);
        System.out.println("randomOrder = " + Arrays.toString(randomOrder));
        sort.sortWithMergeSort(randomOrder);
        System.out.println("randomOrder sorted = " + Arrays.toString(randomOrder));

        Integer last = null;
        for (Integer integer : randomOrder) {
            if (last != null) {
                Assert.assertTrue(last.compareTo(integer) < 0);
            }
            last = integer;
        }
    }


}
