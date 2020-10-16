package array;

// Given a sorted array A of unique numbers, find the K-th missing number starting from the leftmost number of the array.

// Example 1:

// Input: A = [4,7,9,10], K = 1
// Output: 5
// Explanation: 
// The first missing number is 5.
// Example 2:

// Input: A = [4,7,9,10], K = 3
// Output: 8
// Explanation: 
// The missing numbers are [5,6,8,...], hence the third missing number is 8.
// Example 3:

// Input: A = [1,2,4], K = 3
// Output: 6
// Explanation: 
// The missing numbers are [3,5,6,7,...], hence the third missing number is 6.

// Note:

// 1 <= A.length <= 50000
// 1 <= A[i] <= 1e7
// 1 <= K <= 1e8

public class MissingElementInSortedArray {

  // my sol. iterate and count
  class Solution1 {
    public int missingElement(int[] nums, int k) {
      // naive
      // count from left
      // [1,100] k=3 , missing = 100-1-1=98, so k-3=0 and res = 1+3=4
      // worst n

      // corner case
      int n = nums.length;

      int prev = nums[0];
      for (int i = 1; i < n; i++) {
        int cur = nums[i];

        int missingCount = cur - prev - 1;

        if (k - missingCount <= 0) {
          return prev + k;
        } else {
          k -= missingCount;
        }
        // update prev
        prev = cur;

      }

      return prev + k;
    }
  }

  // suggested sol 1 ,O n , calculate missing
  class Solution2 {
    // Return how many numbers are missing until nums[idx]
    int missing(int idx, int[] nums) {
      return nums[idx] - nums[0] - idx;
    }

    public int missingElement(int[] nums, int k) {
      int n = nums.length;
      // If kth missing number is larger than
      // the last element of the array
      if (k > missing(n - 1, nums))
        return nums[n - 1] + k - missing(n - 1, nums);

      int idx = 1;
      // find idx such that
      // missing(idx - 1) < k <= missing(idx)
      while (missing(idx, nums) < k)
        idx++;

      // kth missing number is greater than nums[idx - 1]
      // and less than nums[idx]
      return nums[idx - 1] + k - missing(idx - 1, nums);
    }
  }

  // suggested sol2 , binary search
  class Solution {
    // Return how many numbers are missing until nums[idx]
    int missing(int idx, int[] nums) {
      return nums[idx] - nums[0] - idx;
    }

    public int missingElement(int[] nums, int k) {
      int n = nums.length;
      // If kth missing number is larger than
      // the last element of the array
      if (k > missing(n - 1, nums))
        return nums[n - 1] + k - missing(n - 1, nums);

      int left = 0, right = n - 1, pivot;
      // find left = right index such that
      // missing(left - 1) < k <= missing(left)
      while (left != right) {
        pivot = left + (right - left) / 2;

        if (missing(pivot, nums) < k)
          left = pivot + 1;
        else
          right = pivot;
      }

      // kth missing number is greater than nums[idx - 1]
      // and less than nums[idx]
      return nums[left - 1] + k - missing(left - 1, nums);
    }
  }

  // my binary search
  class Solution4 {
    public int missingElement(int[] nums, int k) {
      // by knowing index we can directly calculate the missing count
      // missing = nums[i]-nums[0]-i

      int n = nums.length;
      int left = 0;
      int right = n - 1;

      while (left <= right) {
        int mid = left + (right - left) / 2;
        int missingCount = nums[mid] - nums[0] - mid;

        if (k <= missingCount) {
          right = mid - 1;
        } else if (k > missingCount) {
          left = mid + 1;
        }

      }

      return nums[left - 1] + k - (nums[left - 1] - nums[0] - left + 1);
    }

  }

}