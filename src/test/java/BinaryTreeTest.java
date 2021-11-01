import net.grigoriadi.algorithms.sort.BinaryTree;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class BinaryTreeTest {

    @Test
    public void testAddingAndSorting() {
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        Integer[] randomOrderedIntegers = ArrayUtil.createRandomOrderedIntegers(128);

        Arrays.stream(randomOrderedIntegers).forEach(binaryTree::put);
        Collection<Integer> ordered = binaryTree.getOrdered();
        var last = new Object() {
            Integer integer;
        };
        ordered.forEach(integer -> {
            if (last.integer == null) {
                last.integer = integer;
                return;
            }
            Assert.assertTrue(last.integer.compareTo(integer) < 0);
        });
    }

    @Test
    public void testFloorLeftNotMatched() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.put(10);
        tree.put(8);
        tree.put(5);
        tree.put(3);
        Integer floor = tree.floor(1);
        Integer expected = 3;
        Assert.assertEquals(expected, floor);
    }

    @Test
    public void testFloorRightNoLeftAncestors() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.put(20);
        tree.put(30);
        tree.put(40);
        tree.put(38);
        tree.put(42);
        Integer floor = tree.floor(35);
        Integer expected = 30;
        Assert.assertEquals(expected, floor);
    }

    @Test
    public void testFloorRightWithLeftAncestors() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.put(20);
        tree.put(30);
        tree.put(40);
        tree.put(38);
        tree.put(42);
        Integer floor = tree.floor(39);
        Integer expected = 38;
        Assert.assertEquals(expected, floor);
    }

    @Test
    public void testFloorRanomInput() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        Integer[] randomOrderedIntegers = ArrayUtil.createRandomOrderedIntegers(128);
        Arrays.stream(randomOrderedIntegers).forEach(integer -> tree.put(integer * 10));
        Random r = new Random();
        //generate the value sou it is not equal to values generated above
        int lookup = r.nextInt(128) * 10 - 5;
        System.out.println("lookup = " + lookup);
        Integer result = tree.floor(lookup);
        Assert.assertEquals(result, (Integer)(lookup - 5));
    }

    @Test
    public void testRemoveElement() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.put(10);
        tree.put(15);
        tree.put(12);
        tree.put(14);
        tree.put(28);
        tree.put(20);
        tree.put(32);
        tree.put(18);
        tree.put(19);
        tree.put(5);
        tree.put(3);
        tree.put(7);

        System.out.println("tree = " + tree);
        tree.printBFS();

        Assert.assertTrue(tree.contains(15));
        tree.delete(15);
        Assert.assertFalse(tree.contains(15));
        System.out.println("tree after Removal of 15= " + tree);
        tree.printBFS();
    }

    @Test
    public void testRemoveRootElementWithRightChild() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.put(13);
        tree.put(2);
        System.out.println("tree = " + tree);
        tree.printBFS();

        Assert.assertTrue(tree.contains(13));
        tree.delete(13);
        Assert.assertFalse(tree.contains(13));
        System.out.println("tree after Removal of 13= ");
        tree.printBFS();
        tree.delete(2);
        System.out.println("tree after Removal of 2= ");
        tree.printBFS();
        Assert.assertFalse(tree.contains(2));
        tree.printBFS();
    }

    @Test
    public void testRemoveElementRandom() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        Integer[] randomOrderedIntegers = ArrayUtil.createRandomOrderedIntegers(1024);
        Arrays.stream(randomOrderedIntegers).forEach(tree::put);
        Random r = new Random();
        List<Integer> intList = Arrays.stream(randomOrderedIntegers).collect(Collectors.toList());
        Integer next;
        while (intList.size() != 0 && (next = intList.remove(r.nextInt(intList.size()))) != null) {
            Integer sizeBefore = tree.size();
            tree.delete(next);
            Integer sizeAfter = tree.size();
            Assert.assertEquals(sizeBefore, (Integer)(sizeAfter + 1));
            Assert.assertFalse(tree.contains(next));
            //tree.printBFS();
        }
    }

    @Test
    public void testDeleteBonus() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.put(6);
        tree.put(5);
        tree.put(15);
        tree.put(1);
        tree.put(7);
        tree.put(0);
        tree.put(3);
        tree.put(8);
        tree.put(2);
        tree.put(10);
        tree.put(9);
        tree.put(14);
        tree.put(11);
        tree.put(13);
        tree.put(12);

        Integer sizeBefore = tree.size();
        tree.delete(6);
        Integer sizeAfter = tree.size();
        Assert.assertEquals(sizeBefore, (Integer)(sizeAfter + 1));
    }
}
