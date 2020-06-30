package Tree.Trie;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class MaximumXorTest {

    @BeforeEach
    public void setUp() throws Exception {

    }

    @Test
    public void test1() {
        int[] arr = new int[]{0,1,2};
        int[] qs  = new int[]{3,7,2};

        int[] res = MaximumXor.maxXor(arr,qs);
        assertArrayEquals(new int[]{3,7,3},res);
    }

}