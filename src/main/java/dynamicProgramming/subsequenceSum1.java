package dynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
// import org.slf4j.impl.StaticLoggerBinder;
import java.util.Map;

public class subsequenceSum1 {

    // return true if find a subsequence sum = k
    public static class NaiveSolver {
        public boolean solve(int[] arr, int n, int k) {
            return recursionHelper(arr, n, k, 0);
        }

        boolean recursionHelper(int[] arr, int n, int k, int i) {
            // terminating condition
            if (k == 0) {
                return true;
            }

            if (i >= n)
                return false;

            // current element is arr[i]
            int cur = arr[i];

            // branch 1: skip current elem
            boolean sub1 = recursionHelper(arr, n, k, i + 1);

            // branch 2: include current elem into current subsequence
            boolean sub2 = recursionHelper(arr, n, k - cur, i + 1);

            return sub1 || sub2;

        }

    }

    public static class MemoizationSolver {
        public boolean solve(int[] arr, int n, int K) {
            Map<String, Boolean> mem = new HashMap<>();
            return recursionHelper(arr, n, K, 0, mem);
        }

        boolean recursionHelper(int[] arr, int n, int k, int i, Map<String, Boolean> mem) {
            // terminating condition
            if (k == 0) {
                return true;
            }

            if (i >= n)
                return false;

            // check mem
            String key = String.format("(%d,%d)", k, i);
            if (mem.containsKey(key)) {
                return mem.get(key);
            }

            // current element is arr[i]
            int cur = arr[i];

            // branch 1: skip current elem
            boolean sub1 = recursionHelper(arr, n, k, i + 1, mem);

            // branch 2: include current elem into current subsequence
            boolean sub2 = recursionHelper(arr, n, k - cur, i + 1, mem);

            // store res into mem
            boolean res = sub1 || sub2;
            mem.put(key, res);

            return res;

        }
    }

    public static class TabulationSolver {
        public boolean solve(int[] arr, int n, int K) {

            boolean[][] dp = new boolean[n][K + 1];

            // init table with results of smallest subproblems
            for (int k = 0; k <= K; k++) {
                if (k == 0 || k == arr[0])
                    dp[0][k] = true;
            }

            // dp[i][k]=dp[i-1][k] || dp[i-1][k-arr[i]]
            // row i depends on row i-1
            for (int i = 1; i < n; i++) {
                for (int k = 0; k <= K; k++) {

                    boolean sub1 = dp[i - 1][k];
                    // note that we assume arr[i] is always positive here
                    boolean sub2 = ((k - arr[i]) < 0 || (k - arr[i]) > K) ? false : dp[i - 1][k - arr[i]];

                    dp[i][k] = sub1 || sub2;
                }
            }

            return dp[n - 1][K];

        }

    }

    // space compression
    public static class CompressedTabulationSolver {
        public boolean solve(int[] arr, int n, int K) {

            boolean[] dp_prev = new boolean[K + 1];
            boolean[] dp_cur = new boolean[K + 1];
            // init table with results of smallest subproblems
            for (int k = 0; k <= K; k++) {
                if (k == 0 || k == arr[0])
                    dp_prev[k] = true;
            }

            // dp_cur[k]=dp_prev[k] || dp_prev[k-arr[i]]
            // row i depends on row i-1
            for (int i = 1; i < n; i++) {

                for (int k = 0; k <= K; k++) {

                    boolean sub1 = dp_prev[k];
                    // note that we assume arr[i] is always positive here
                    boolean sub2 = ((k - arr[i]) < 0 || (k - arr[i]) > K) ? false : dp_prev[k - arr[i]];

                    dp_cur[k] = sub1 || sub2;
                }
                dp_prev = dp_cur;
                dp_cur = new boolean[K + 1];

            }

            return dp_prev[K];

        }

    }

    public static void main(String[] args) {
        subsequenceSum1.NaiveSolver solver1 = new subsequenceSum1.NaiveSolver();
        subsequenceSum1.MemoizationSolver solver2 = new subsequenceSum1.MemoizationSolver();
        subsequenceSum1.TabulationSolver solver3 = new subsequenceSum1.TabulationSolver();
        subsequenceSum1.CompressedTabulationSolver solver4 = new subsequenceSum1.CompressedTabulationSolver();
        System.out.println(solver1.solve(new int[] { 2, 4, 6, 10, 43, 7, 8, 3, 5, 0 }, 10, 16));
        System.out.println(solver2.solve(new int[] { 2, 4, 6, 10, 43, 7, 8, 3, 5, 0 }, 10, 16));
        System.out.println(solver3.solve(new int[] { 2, 4, 6, 10, 43, 7, 8, 3, 5, 0 }, 10, 16));
        System.out.println(solver4.solve(new int[] { 2, 4, 6, 10, 43, 7, 8, 3, 5, 0 }, 10, 16));

    }

}
