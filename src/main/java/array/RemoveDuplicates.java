package array;

public class RemoveDuplicates {
  // delete duplicates in place for an array
  public int removeDuplicates(int[] nums) {

    // Check for edge cases.
    if (nums == null) {
      return 0;
    }

    // Use the two pointer technique to remove the duplicates in-place.
    // The first element shouldn't be touched; it's already in its correct place.
    int writePointer = 1;
    // Go through each element in the Array.
    for (int readPointer = 1; readPointer < nums.length; readPointer++) {
      // If the current element we're reading is *different* to the previous
      // element...
      if (nums[readPointer] != nums[readPointer - 1]) {
        // Copy it into the next position at the front, tracked by writePointer.
        nums[writePointer] = nums[readPointer];
        // And we need to now increment writePointer, because the next element
        // should be written one space over.
        writePointer++;
      }
    }

    // This turns out to be the correct length value.
    return writePointer;
  }

  // similar question move zeros to the end of array
  public void moveZeroes(int[] nums) {

    int writePointer = 0;

    for (int readPointer = 0; readPointer < nums.length; readPointer++) {
      if (nums[readPointer] != 0) {
        // swap
        // nums[writePointer++]=nums[readPointer] ^ nums[writePointer]
        // ^(nums[readPointer]=nums[writePointer]); //wrong , the second assignment
        // (nums[readPointer]=nums[writePointer]) would use the wrong pointer index
        nums[writePointer] = nums[readPointer] ^ nums[writePointer] ^ (nums[readPointer] = nums[writePointer]);
        writePointer++;
      }

    }

  }

  // another similar //return an array consisting of all the even elements of A,
  // followed by all the odd elements of A.
  public int[] sortArrayByParity(int[] A) {
    int writePointer = 0;

    for (int readPointer = 0; readPointer < A.length; readPointer++) {
      // swap
      if ((A[readPointer] & 1) == 0) {
        A[writePointer] = A[readPointer] ^ A[writePointer] ^ (A[readPointer] = A[writePointer]);
        writePointer++;
      }

    }

    return A;
  }

}