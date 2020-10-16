package graph.grid;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CheckifThereisaValidPathinaGridTest {

    CheckifThereisaValidPathinaGrid solver;

    @BeforeEach
    void setUp() {
         solver = new CheckifThereisaValidPathinaGrid();
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @MethodSource
    void test1(boolean expected , int[][] grid) {
        assertEquals(expected,solver.naiveSol(grid));
        assertEquals(expected,solver.sol2(grid));
    }


    static Stream<Arguments> test1(){
        return Stream.of(Arguments.arguments(true,new int[][]{{2,4,3},{6,5,2}}));
    }
}
