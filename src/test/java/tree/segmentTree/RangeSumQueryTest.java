package tree.segmentTree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// import Tree.SegmentTree.RangeSumQuery;

public class RangeSumQueryTest {
    RangeSumQuery solver;
    int[] nums;

    @BeforeEach
    public void setUp() throws Exception {
        nums = new int[]{8,7,3,4,5,6};
        solver = new RangeSumQuery(nums);
    }

    public int sum(int i,int j){
        int res=0;
        for(int index=i; index<=j;index++){
            res+=nums[index];
        }
        return res;
    }
    @Test
    public void simpleTest() {
        assertEquals(sum(0,1),solver.getSum(0,1));
        assertEquals(sum(0,2),solver.getSum(0,2));
        assertEquals(sum(0,3),solver.getSum(0,3));
        assertEquals(sum(1,2),solver.getSum(1,2));
        assertEquals(sum(3,5),solver.getSum(3,5));
    }

    @Test
    public void updateTest() {

        solver.set(0,99);
        nums[0]=99;
        simpleTest();

        solver.set(2,-5);
        nums[2]=-5;
        simpleTest();

    }

}
