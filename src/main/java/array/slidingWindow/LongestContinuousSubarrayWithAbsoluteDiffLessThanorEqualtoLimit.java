package array.slidingWindow;

import java.util.*;
import java.util.function.Function;

//Given an array of integers nums and an integer limit, 
// return the size of the longest non-empty subarray 
// such that the absolute difference between any two elements of this subarray is less than or equal to limit.

// Input: nums = [8,2,4,7], limit = 4
// Output: 2 
// Explanation: All subarrays are: 
// [8] with maximum absolute diff |8-8| = 0 <= 4.
// [8,2] with maximum absolute diff |8-2| = 6 > 4. 
// [8,2,4] with maximum absolute diff |8-2| = 6 > 4.
// [8,2,4,7] with maximum absolute diff |8-2| = 6 > 4.
// [2] with maximum absolute diff |2-2| = 0 <= 4.
// [2,4] with maximum absolute diff |2-4| = 2 <= 4.
// [2,4,7] with maximum absolute diff |2-7| = 5 > 4.
// [4] with maximum absolute diff |4-4| = 0 <= 4.
// [4,7] with maximum absolute diff |4-7| = 3 <= 4.
// [7] with maximum absolute diff |7-7| = 0 <= 4. 
// Therefore, the size of the longest subarray is 2.
public class LongestContinuousSubarrayWithAbsoluteDiffLessThanorEqualtoLimit {

  // my first sol // sparse table
  // O n^2 + nlogn
  // freaklingly wrong
  // we dont need to cache the max and min
  // just a simple dpouble iter loop to cal max and min iteratively
  class Solution {
    public int longestSubarray(int[] nums, int limit) {
      // naive , guess and verify
      // n^2 * n = n^3 , guess O n^2 , verify O n
      // to verify find max and min and diff O n
      // if we cache the res of diff -> O 1
      // cache max and min -> sparse table nlogn to build, O 1 to get , update O nlogn
      // segment tree - nlogn build, log n get // update logn
      // non-decreasing queue -> cannot
      // dp max and min -> O n^2 to build, O 1 to get

      // sliding window
      // 1 1 1 1 ... 2 2 2 2 2
      //

      // want to know the max/min of a subarray
      // non-decreasing queue to find min

      MaxMinFinder2 maxminfinder = new MaxMinFinder2(nums);

      int res = 0;

      int n = nums.length;
      for (int i = 0; i < n; i++) {
        for (int j = n - 1; j >= i; j--) { // j>=i
          int max = maxminfinder.getMax(i, j);
          int min = maxminfinder.getMin(i, j);
          int diff = max - min;
          if (diff <= limit) {
            res = Math.max(j + 1 - i, res);
            break;
          }

        }
      }
      return res;

    }

    // sparse table
    // dp i j -> max in subarray [i,i+2^j-1] or [i,i+2^j) note to exclude i+2^j
    class MaxMinFinder2 {
      int[][] mindp;
      int[][] maxdp;
      int n;

      public int getMaxLog(int n) {
        return Integer.numberOfTrailingZeros(Integer.highestOneBit(n));
      }

      public MaxMinFinder2(int[] nums) {
        int n = nums.length;
        int maxlog = getMaxLog(n);
        mindp = new int[n][maxlog + 1];
        maxdp = new int[n][maxlog + 1];
        // build
        builddp(nums);

      }

      public int getResult(int i, int j, int[][] dp, Function<int[], Integer> f) {

        int maxlog = getMaxLog(j + 1 - i);

        return f.apply(new int[] { dp[i][maxlog], dp[j + 1 - (1 << maxlog)][maxlog] });

      }

      public int getMax(int i, int j) {
        return getResult(i, j, maxdp, (arr) -> Math.max(arr[0], arr[1]));
      }

      public int getMin(int i, int j) {
        return getResult(i, j, mindp, (arr) -> Math.min(arr[0], arr[1]));
      }

      public void builddp(int[] nums) {
        int n = nums.length;

        // init dp

        for (int[] row : maxdp) {
          Arrays.fill(row, -1);
        }
        for (int[] row : mindp) {
          Arrays.fill(row, Integer.MAX_VALUE);
        }
        for (int i = 0; i < n; i++) {
          maxdp[i][0] = nums[i];
          mindp[i][0] = nums[i];
        }

        // dp i j relies on prev col
        // iterate col by col
        for (int j = 1; (1 << j) <= n; j++) {
          for (int i = 0; i < n; i++) {
            if (i + (1 << j) <= n) {

              maxdp[i][j] = Math.max(maxdp[i][j - 1], maxdp[i + (1 << (j - 1))][j - 1]);
              mindp[i][j] = Math.min(mindp[i][j - 1], mindp[i + (1 << (j - 1))][j - 1]);
            }
          }
        }

      }
    }

    // dp sol
    class MaxMinFinder {
      int[][] mindp;
      int[][] maxdp;
      int n;

      public MaxMinFinder(int[] nums) {
        int n = nums.length;
        mindp = new int[n][n];
        maxdp = new int[n][n];
        // build
        builddp(nums);

      }

      public int getMax(int i, int j) {
        return maxdp[i][j];
      }

      public int getMin(int i, int j) {
        return mindp[i][j];
      }

      public void builddp(int[] nums) {
        int n = nums.length;
        // dp i j = min/mas for subarray(i,i+j)
        // dp i j = max ( dp i j-1 , nums[i+j] ) //relies on prev item of same row
        // another way
        // dp i j = max( nums[i] ,dp[i+1][j-1] ) // pointless to reverse order

        // init dp

        for (int[] row : maxdp) {
          Arrays.fill(row, -1);
        }
        for (int[] row : mindp) {
          Arrays.fill(row, Integer.MAX_VALUE);
        }
        for (int i = 0; i < n; i++) {
          maxdp[i][0] = nums[i];
          mindp[i][0] = nums[i];
        }

        for (int i = 0; i < n; i++) {
          for (int j = 1; j < n; j++) {
            if (i + j < n) {
              maxdp[i][j] = Math.max(nums[i + j], maxdp[i][j - 1]);
              mindp[i][j] = Math.min(nums[i + j], mindp[i][j - 1]);
            }
          }
        }

      }

    }

  }

  // O n^2 naive solution
  class solution2 {
    public int longestSubarray(int[] nums, int limit) {

      int n = nums.length;
      int res = 0;
      for (int i = 0; i < n; i++) {
        int max = -1;
        int min = Integer.MAX_VALUE;
        for (int j = i; j < n; j++) {
          max = Math.max(nums[j], max);
          min = Math.min(nums[j], min);
          int diff = max - min;
          if (diff <= limit) {
            res = Math.max(res, j - i + 1);
          }

        }
      }
      return res;

    }

  }

  // O n solution,  O n space-> trade off take extra space, naive sol n^2 but no extra space.
  // sliding window
  // both pointer on left initially
  // move right pointer until break
  // if break move left pointer
  // this way we are able to capture any subarray
  // just need to maintain min and max while slide window
  // monotone queue to do so
  class solution3 {
    // [1,6,5,5,17] , 2 ,expected 3
    public int longestSubarray(int[] nums, int limit) {

      int n = nums.length;
      if (n == 0)
        return 0;
      int res = 0;

      MaxMinFinder finder = new MaxMinFinder();
      int left = 0;
      int right = 0;

      finder.addRightNum(nums[0]); // max = [1] min =[1]
      while (left < n && right < n) { // n = 5
        // maxQueue = [17] , min = [ 17]
        int min = finder.getMin(); // 1 //1 // 6 // 5 // 5 // 5 // 5 //5 //17
        int max = finder.getMax(); // 1 //6 //6 // 6 // 6 // 17 // 17 // 17 //17
        int diff = max - min;// 0 //5 //0 // 1 // 1 // 12 // 12 // 12 // 0
        if (diff <= limit) {
          res = Math.max(res, right - left + 1); // res = 1 // 1 // 2 //3 //3
          right++; // right = 1 // 2 // 3 // 4 //5
          if (right < n)
            finder.addRightNum(nums[right]); // add 17
        } else if(right-left+1>=res) {//We dont have to shrink window
       
          finder.removeLeftNum(nums[left]); /// remove old left
          left++; // left = 1 // 2 //3 //4
          
          
        }

      }

      return res;

    }

  }

  class MaxMinFinder {
    Deque<Integer> maxQueue;
    Deque<Integer> minQueue;

    public MaxMinFinder() {
      maxQueue = new ArrayDeque<>();
      minQueue = new ArrayDeque<>();

    }

    public int getMax() {

      return maxQueue.peekFirst();
    }

    public int getMin() {

      return minQueue.peekFirst();
    }

    public void removeLeftNum(int num) {
      if (maxQueue.peekFirst() == num) {
        maxQueue.removeFirst();
      }
      if (minQueue.peekFirst() == num) {
        minQueue.removeFirst();
      }

    }

    public void addRightNum(int num) {
      while (!maxQueue.isEmpty() && maxQueue.peekLast() < num) {
        maxQueue.removeLast();
      }
      maxQueue.add(num);

      while (!minQueue.isEmpty() && minQueue.peekLast() > num) {
        minQueue.removeLast();
      }
      minQueue.add(num);

    }

  }

  // further improve by non-shrinking sliding window, small improvement bc complexity is still n
  // original complexity = 2n
  // complexity = n+(n-res*)

  // public int longestSubarray(int[] A, int limit) {
    // Deque<Integer> maxd = new ArrayDeque<>();
    // Deque<Integer> mind = new ArrayDeque<>();
    // int i = 0, j;
    // for (j = 0; j < A.length; ++j) {
      // while (!maxd.isEmpty() && A[j] > maxd.peekLast()) maxd.pollLast();
      // while (!mind.isEmpty() && A[j] < mind.peekLast()) mind.pollLast();
      // maxd.add(A[j]);
      // mind.add(A[j]);
      // if (maxd.peek() - mind.peek() > limit) {
        // if (maxd.peek() == A[i]) maxd.poll();
        // if (mind.peek() == A[i]) mind.poll();
        // ++i;
      // }
    // }
    // return j - i;
  // }


  //----------------------------------------------------------
  // other solution from leetcode, using treeMap
  // note that this sliding window here never decrease its size , so it save some
  // time
  // Solution 2: Use TreeMap
  // Use one tree map can easily get the maximum and the minimum at the same time.
  // In java, we can use TreeMap to count elements.
  // In cpp, it suports multi treeset, that's even better.

  // Time O(NogN)
  // Space O(N)

  // Java
  // @prdp89
  class solution4 {
    public int longestSubarray(int[] A, int limit) {
      int i = 0, j;
      TreeMap<Integer, Integer> m = new TreeMap<>();
      for (j = 0; j < A.length; j++) {
        m.put(A[j], 1 + m.getOrDefault(A[j], 0));
        if (m.lastEntry().getKey() - m.firstEntry().getKey() > limit) {
          m.put(A[i], m.get(A[i]) - 1);
          if (m.get(A[i]) == 0)
            m.remove(A[i]);
          i++;
        }
      }
      return j - i;
    }
  }
}
