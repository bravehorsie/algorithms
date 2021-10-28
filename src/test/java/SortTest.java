import net.grigoriadi.algorithms.sort.ArraySort;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
    public void testMergeSort() {
        ArraySort<Integer> sort = new ArraySort<>(Integer.class, false);
        sort.sortWithMergeSort(subjectArray);
        System.out.println(Arrays.toString(subjectArray));

        Integer[] randomOrder = new Integer[128];
        List<Integer> ints = new ArrayList<>();
        IntStream.range(0, 128).forEach(ints::add);
        Random r = new Random();
        for (int i = 0; i < randomOrder.length; i++) {
            Integer next = ints.get(r.nextInt(ints.size()));
            randomOrder[i] = next;
            ints.remove(next);
        }
        System.out.println("randomOrder = " + Arrays.toString(randomOrder));
        sort.sortWithMergeSort(randomOrder);
        System.out.println("randomOrder sorted = " + Arrays.toString(randomOrder));

    }
}
