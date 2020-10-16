package array.matrix;

import java.util.List;

// A binary matrix means that all elements are 0 or 1. For each individual row of the matrix, this row is sorted in non-decreasing order.

// Given a row-sorted binary matrix binaryMatrix, return leftmost column index(0-indexed) with at least a 1 in it. If such index doesn't exist, return -1.

// You can't access the Binary Matrix directly.  You may only access the matrix using a BinaryMatrix interface:

// BinaryMatrix.get(row, col) returns the element of the matrix at index (row, col) (0-indexed).
// BinaryMatrix.dimensions() returns a list of 2 elements [rows, cols], which means the matrix is rows * cols.
// Submissions making more than 1000 calls to BinaryMatrix.get will be judged Wrong Answer.  Also, any solutions that attempt to circumvent the judge will result in disqualification.

// For custom testing purposes you're given the binary matrix mat as input in the following four examples. You will not have access the binary matrix directly.

// Input: mat = [[0,0],[1,1]]
// Output: 0

// Input: mat = [[0,0,0,1],[0,0,1,1],[0,1,1,1]]
// Output: 1

public class LeftMostColumnWithAtLeastOne {
  interface BinaryMatrix {
    public int get(int row, int col);

    public List<Integer> dimensions();
  }

  // my sol1, top left corner then go down go left, repeat.
  // issue the nested while loop is very ugly, see sol 3
  public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {

    List<Integer> dimensions = binaryMatrix.dimensions();
    int m = dimensions.get(0);
    int n = dimensions.get(1);
    int row = 0, col = n - 1;
    int res = -1;

    while (row < m && col >= 0) {

      while (row < m && binaryMatrix.get(row, col) == 0) {

        row++;
      }

      if (row < m)
        res = col;
      col--;
      while (col >= 0 && row < m && binaryMatrix.get(row, col) == 1) {

        res = col;
        col--;
      }
      row++;

    }

    return res;

  }

  // Approach 2: Binary Search Each Row
  public int leftMostColumnWithOne2(BinaryMatrix binaryMatrix) {
    int rows = binaryMatrix.dimensions().get(0);
    int cols = binaryMatrix.dimensions().get(1);
    int smallestIndex = cols;
    for (int row = 0; row < rows; row++) {
      // Binary Search for the first 1 in the row.
      int lo = 0;
      int hi = cols - 1;
      while (lo < hi) {
        int mid = (lo + hi) / 2;
        if (binaryMatrix.get(row, mid) == 0) {
          lo = mid + 1;
        } else {
          hi = mid;
        }
        // If the last element in the search space is a 1, then this row
        // contained a 1.
        if (binaryMatrix.get(row, lo) == 1) {
          smallestIndex = Math.min(smallestIndex, lo);
        }
      }
    }
    // If smallest_index is still set to cols, then there were no 1's in
    // the grid.
    return smallestIndex == cols ? -1 : smallestIndex;
  }

  // Approach 3: Start at Top Right, Move Only Left and Down
  // better sol bc the looping is well designed.

  // Intuition

  // Did you notice in Approach 2 that we didn't need to finish searching all the
  // rows?
  // One example of this was row 3 on the example in the animation.
  // At the point shown in the image below, it was clear that row 3 could not
  // possibly be better than the minimum we'd found so far.
  // Therefore, an optimization we could have made was to keep track of the
  // minimum index so far, and then abort the search on any rows where we have
  // discovered a 0 at, or to the right of, that minimum index.

  // We can do even better than that; on each search, we can set hi =
  // smallest_index - 1, where smallest_index is the smallest index of a 1 we've
  // seen so far. In most cases, this is a substantial improvement. It works
  // because we're only interested in finding 1s at lower indexes than we
  // previously found. Here is an animation of the above example with this
  // optimized algorithm. The algorithm eliminates as many cells as it can with
  // each API call. It also starts by checking the last cell of the row before
  // proceeding with the binary search, to eliminate needless binary searches
  // where the row only had 0s left in it.
  // Here is what the worst-case looks like. Like before, its time complexity is
  // still O(M \, \log \, N)O(MlogN).
  // While this is no worse than Approach 2, there is a better algorithm.

  // Start in the top right corner, and if the current value is a 0, move down. If
  // it is a 1, then move left.
  // O M+N

  public int leftMostColumnWithOne3(BinaryMatrix binaryMatrix) {

    int rows = binaryMatrix.dimensions().get(0);
    int cols = binaryMatrix.dimensions().get(1);

    // Set pointers to the top-right corner.
    int currentRow = 0;
    int currentCol = cols - 1;

    // Repeat the search until it goes off the grid.
    while (currentRow < rows && currentCol >= 0) {
      if (binaryMatrix.get(currentRow, currentCol) == 0) {
        currentRow++;
      } else {
        currentCol--;
      }
    }

    // If we never left the last column, this is because it was all 0's.
    return (currentCol == cols - 1) ? -1 : currentCol + 1;
  }

  // more readable
  public int leftMostColumnWithOne4(BinaryMatrix binaryMatrix) {

    int rows = binaryMatrix.dimensions().get(0);
    int cols = binaryMatrix.dimensions().get(1);

    // Set pointers to the top-right corner.
    int currentRow = 0;
    int currentCol = cols - 1;

    boolean goleft = false;
    boolean godown = true;
    // Repeat the search until it goes off the grid.
    while (currentRow < rows && currentCol >= 0) {
      int val = binaryMatrix.get(currentRow, currentCol);
      if (val == 1 && godown) {
        godown = false;
        goleft = true;
      } else if (val == 0 && goleft) {
        goleft = false;
        godown = true;
      }

      if (godown) {
        currentRow++;
      }

      if (goleft) {
        currentCol--;
      }
    }

    // If we never left the last column, this is because it was all 0's.
    return (currentCol == cols - 1) ? -1 : currentCol + 1;
  }
}
