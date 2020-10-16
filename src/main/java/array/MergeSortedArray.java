package array;


/*Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

Note:

The number of elements initialized in nums1 and nums2 are m and n respectively.
You may assume that nums1 has enough space (size that is equal to m + n) to hold additional elements from nums2.
Example:

Input:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3

Output: [1,2,2,3,5,6]
 

Constraints:

-10^9 <= nums1[i], nums2[i] <= 10^9
nums1.length == m + n
nums2.length == n

*/

//the difficulty is to avoid using extra space
public class MergeSortedArray {

  public void merge(int[] nums1, int m, int[] nums2, int n) {

    int index1 = m - 1;
    int index2 = n - 1;
    // free space on the right end,start from the right

    for (int i = m + n - 1; i >= 0 && index1 >= 0 && index2 >= 0; --i) {
      // dont need to check i>=0, since m or n will first reach 0
      nums1[i] = nums1[index1] < nums2[index2] ? nums2[index2--] : nums1[index1--];

    }

    // remaining // if index1 has remaining , no need to change
    for (int i = 0; i <= index2; i++) {
      nums1[i] = nums2[i];
    }

  }

}