
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Paths;
// import java.util.Arrays;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tree.BalancedForest_linear;

public class BalancedForestTest {
    BalancedForest_linear solver;

    // @Rule
    // public Timeout timeout = Timeout.seconds(20);
    @BeforeEach
    public void setUp() throws Exception {
        solver = new BalancedForest_linear();

    }

    @Test
    public void test1() {
        int[] c = new int[] { 1, 2, 2, 1, 1 };
        int[][] edges = new int[][] { { 1, 2 }, { 1, 3 }, { 3, 5 }, { 1, 4 } };

        long res = solver.balancedForest(c, edges);
        assertEquals(2, res);

    }

    @Test
    public void test2() {
        int[] c = new int[] { 7, 7, 4, 1, 1, 1 };
        int[][] edges = new int[][] { { 1, 2 }, { 3, 1 }, { 2, 4 }, { 2, 5 }, { 2, 6 } };

        long res = solver.balancedForest(c, edges);
        assertEquals(-1, res);
    }

    @Test
    public void test3() {

        int[] c = new int[] { 100, 100, 99, 99, 98, 98 };
        int[][] edges = new int[][] { { 1, 3 }, { 3, 5 }, { 1, 2 }, { 2, 4 }, { 4, 6 } };

        long res = solver.balancedForest(c, edges);
        assertEquals(297, res);
    }

    @Test
    public void test3_2() {
        int[] c = new int[] { 12, 7, 11, 17, 20, 10 };
        int[][] edges = new int[][] { { 1, 2 }, { 2, 3 }, { 4, 5 }, { 6, 5 }, { 1, 4 } };

        long res = solver.balancedForest(c, edges);
        assertEquals(13, res);
    }

    @Test
    public void test4_1() {
        int[] c = new int[] { 2, 3, 3, 4 };
        int[][] edges = new int[][] { { 1, 2 }, { 1, 4 }, { 2, 3 } };

        long res = solver.balancedForest(c, edges);
        assertEquals(6, res);

    }

    @Test
    public void test4() {
        long[] expected = new long[] { 1714, 5016, 759000000000l, -1, 6 };

        try (TestReader reader = new TestReader("src/test/resources/TestCases/BalancedForestTest4.txt");) {
            for (int i = 0; i < expected.length; i++) {
                Container read = reader.readTest();
                assertEquals(expected[i], solver.balancedForest(read.c, read.edges));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test5() {
        long[] expected = new long[] { 1357940809, 397705399909l, 439044899265l, 104805614260l, -1 };

        try (TestReader reader = new TestReader("src/test/resources/TestCases/BalancedForestTest5.txt");) {
            for (int i = 0; i < expected.length; i++) {
                Container read = reader.readTest();
                assertEquals(expected[i], solver.balancedForest(read.c, read.edges));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class Container {
        int[] c;
        int[][] edges;

        public Container(int[] c, int[][] edges) {
            this.c = c;
            this.edges = edges;
        }
    }

    class TestReader implements Closeable { // use buffered input reader for efficiency
        public TestReader(String file) throws IOException {
            scanner = new Scanner(Paths.get(file));
            scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        }

        Scanner scanner;

        public Container readTest() throws IOException {

            // int q = scanner.nextInt();
            // scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            // for (int qItr = 0; qItr < q; qItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] c = new int[n];

            String[] cItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int cItem = Integer.parseInt(cItems[i]);
                c[i] = cItem;
            }

            int[][] edges = new int[n - 1][2];

            for (int i = 0; i < n - 1; i++) {
                String[] edgesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int edgesItem = Integer.parseInt(edgesRowItems[j]);
                    edges[i][j] = edgesItem;
                }
            }

            return new BalancedForestTest.Container(c, edges);
            // }

        }

        @Override
        public void close() throws IOException {
            scanner.close();
        }
    }
}
