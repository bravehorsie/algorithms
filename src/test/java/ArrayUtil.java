import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ArrayUtil {

    public static Integer[] createRandomOrderedIntegers(int size) {
        Integer[] randomOrder = new Integer[size];
        List<Integer> ints = new ArrayList<>();
        IntStream.range(0, size).forEach(ints::add);
        Random r = new Random();
        for (int i = 0; i < randomOrder.length; i++) {
            Integer next = ints.get(r.nextInt(ints.size()));
            randomOrder[i] = next;
            ints.remove(next);
        }
        return randomOrder;
    }
}
