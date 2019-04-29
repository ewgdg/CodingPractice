package SegmentTree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RMQ_SegmentTreeTest {
    RMQ_SegmentTree solver;
    @Before
    public void setUp() throws Exception {
        int[] nums = new int[]{5,-1,3,9,11,-2,0,6,18};
        solver = new RMQ_SegmentTree(nums);
    }

    @Test
    public void test1() {


        assertEquals(  -1,solver.rangeMinQuery(0,2)  );
        assertEquals(  -1,solver.rangeMinQuery(0,3)  );
        assertEquals(  -1,solver.rangeMinQuery(0,4)  );
        assertEquals(  -2,solver.rangeMinQuery(0,6)  );
        assertEquals(  0,solver.rangeMinQuery(6,8)  );
        assertEquals(  -2,solver.rangeMinQuery(0,8)  );



    }

    @Test
    public void test2() {

        solver.update(0,0,-105);
        assertEquals(-100,solver.rangeMinQuery(0,8));
        solver.update(1,8,-100);
        assertEquals(-102,solver.rangeMinQuery(0,8));
    }
    @Test
    public void lazyPropagateTest(){

        solver.updateLazy(0,0,-105);
        assertEquals(-100,solver.rmqLazy(0,8));
        solver.update(1,8,-100);
        assertEquals(-102,solver.rmqLazy(0,8));

    }

    @Test
    public void lazyPropagateTest2(){

        solver.updateLazy(1,2,11);
        assertEquals(5,solver.rmqLazy(0,2));
        assertEquals(-2,solver.rmqLazy(0,8));
        assertEquals(5,solver.rmqLazy(0,2));


        assertEquals(  -2,solver.rangeMinQuery(0,6)  );
        assertEquals(  0,solver.rangeMinQuery(6,8)  );
        assertEquals(  -2,solver.rangeMinQuery(0,8)  );

    }

}