import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class FillingWORDSTest {
    FillingWORDS solver;
    @Before
    public void setUp() throws Exception {
        solver = new FillingWORDS();
    }

    @Test
    public void test1() {
        char[][] b = new char[][]{
                {'+','-','-','-'},
                {'+','-','+','+'},

        };

        String[] w = new String[]{
                "abc","ac"
        };

        solver.solve(b,w);


        char[][] expected = new char[][]{
                {'+','a','b','c'},
                {'+','c','+','+'},

        };

        System.out.println("Actual:");
        System.out.println(Arrays.deepToString(b));

        assertTrue(Arrays.deepEquals(expected,b));

    }
}