package tree.segmentTree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.params.provider.Arguments.arguments;

// import Tree.SegmentTree.RMQ_SegmentTree;

public class RMQ_SegmentTreeTest {
    RMQ_SegmentTree solver;

    @BeforeEach
    public void setUp() throws Exception {
        int[] nums = new int[] { 5, -1, 3, 9, 11, -2, 0, 6, 18 };
        solver = new RMQ_SegmentTree(nums);
    }

    @ParameterizedTest
    @MethodSource("test1")
    public void test1(int expected, int[] query) {

        assertEquals(expected, solver.rangeMinQuery(query[0], query[1]));

        // assertEquals(-2, solver.rangeMinQuery(0, 3));
        // assertEquals(-1, solver.rangeMinQuery(0, 4));
        // assertEquals(-2, solver.rangeMinQuery(0, 6));
        // assertEquals(0, solver.rangeMinQuery(6, 8));
        // assertEquals(-2, solver.rangeMinQuery(0, 8));

    }

    static Stream<Arguments> test1() {
        return Stream.of(arguments(-1, new int[] { 0, 2 }), arguments(-1, new int[] { 0, 3 }),
                arguments(-1, new int[] { 0, 4 }), arguments(-2, new int[] { 0, 6 }), arguments(0, new int[] { 6, 8 }),
                arguments(-2, new int[] { 0, 8 }));
    }

    @ParameterizedTest
    @MethodSource
    public void test2(int i, int j, int delta, int expected, int[] query) {

        solver.update(i, j, delta);
        assertEquals(expected, solver.rangeMinQuery(query[0], query[1]));
        // solver.update(1, 8, -100);
        // assertEquals(-102, solver.rangeMinQuery(0, 8));
    }

    static Stream<Arguments> test2() {
        return Stream.of(arguments(0, 0, -105, -100, new int[] { 0, 8 }),
                arguments(1, 8, -100, -102, new int[] { 0, 8 }));
    }

    @Test
    public void lazyPropagateTest() {

        solver.updateLazy(0, 0, -105);
        assertEquals(-100, solver.rmqLazy(0, 8));
        solver.update(1, 8, -100);
        assertEquals(-102, solver.rmqLazy(0, 8));

    }

    @Test
    public void lazyPropagateTest2() {

        solver.updateLazy(1, 2, 11);
        assertEquals(5, solver.rmqLazy(0, 2));
        assertEquals(-2, solver.rmqLazy(0, 8));
        assertEquals(5, solver.rmqLazy(0, 2));

        assertEquals(-2, solver.rangeMinQuery(0, 6));
        assertEquals(0, solver.rangeMinQuery(6, 8));
        assertEquals(-2, solver.rangeMinQuery(0, 8));

    }

}
