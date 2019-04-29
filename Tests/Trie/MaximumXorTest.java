package Trie;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MaximumXorTest {

    @Before
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