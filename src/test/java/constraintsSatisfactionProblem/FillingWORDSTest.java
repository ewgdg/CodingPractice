package constraintsSatisfactionProblem;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import constraintsSatisfactionProblem.FillingWORDS;



public class FillingWORDSTest {
    FillingWORDS solver;
    @BeforeEach
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
