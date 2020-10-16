package array.matrix;

import java.util.*;

// Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) 
// You may assume all four edges of the grid are surrounded by water.

// Count the number of !!!distinct!!! islands. An island is considered to be the same as another if and only if one island can be translated 
// (and not rotated or reflected) to equal the other.
// Example 1:
// 11000
// 11000
// 00011
// 00011
// Given the above grid map, return 1.

// Notice that:
// 11
// 1
// and
//  1
// 11
// are considered different island shapes, because we do not consider reflection / rotation.

public class NumberOfDistinctIslands {
  // my init sol, use a set for shape
  class Solution1 {

    public int numDistinctIslands(int[][] grid) {
      // naive method record the shape of each island
      // if we can find a way to record the island shape and find it efficiently
      // we can use line sweep tech to store the island, still not good
      // O(n) for time, space O(n)

      // an island can be translated to equal the other,
      // move the island around to see if find anohter match
      // On^2

      // use list for deep equals and hashcode
      Set<List<String>> shapes = new HashSet<>();

      final int[][] dirs = new int[][] { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };

      Set<String> visited = new HashSet<>();
      int count = 0;

      for (int i = 0; i < grid.length; i++) {

        for (int j = 0; j < grid[0].length; j++) {

          if (grid[i][j] == 0 || visited.contains(nodeToString(i, j))) {
            continue;
          }
          Stack<int[]> stack = new Stack<>();
          stack.add(new int[] { i, j });
          List<String> shape = new ArrayList<>();
          int[] head = new int[] { i, j };
          // dfs
          while (!stack.isEmpty()) {
            int[] cur = stack.pop();

            if (grid[cur[0]][cur[1]] == 0 || visited.contains(nodeToString(cur))) {
              continue;
            }
            visited.add(nodeToString(cur));
            // shape is relative coord to head
            shape.add(nodeToString(cur[0] - head[0], cur[1] - head[1]));
            for (int[] dir : dirs) {
              int[] next = new int[] { cur[0] + dir[0], cur[1] + dir[1] };
              if (next[0] >= 0 && next[0] < grid.length && next[1] >= 0 && next[1] < grid[0].length)
                stack.add(next);
            }

          }
          if (!shapes.contains(shape)) {
            count++;
            shapes.add(shape);
          }
        }
      }
      return count;

    }

    String nodeToString(int i, int j) {
      return i + ":" + j;
    }

    String nodeToString(int[] node) {
      return nodeToString(node[0], node[1]);
    }
  }

  // suggested sol1. similar to my sol, use array for seen, use r*width+c=id to
  // record shape
  class Solution2 {
    int[][] grid;
    boolean[][] seen;
    Set<Integer> shape;

    public void explore(int r, int c, int r0, int c0) {
      if (0 <= r && r < grid.length && 0 <= c && c < grid[0].length && grid[r][c] == 1 && !seen[r][c]) {
        seen[r][c] = true;
        shape.add((r - r0) * 2 * grid[0].length + (c - c0));
        explore(r + 1, c, r0, c0);
        explore(r - 1, c, r0, c0);
        explore(r, c + 1, r0, c0);
        explore(r, c - 1, r0, c0);
      }
    }

    public int numDistinctIslands(int[][] grid) {
      this.grid = grid;
      seen = new boolean[grid.length][grid[0].length];
      Set<Set<Integer>> shapes = new HashSet<>();

      for (int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[0].length; c++) {
          shape = new HashSet<Integer>();
          explore(r, c, r, c);
          if (!shape.isEmpty()) {
            shapes.add(shape);
          }
        }
      }

      return shapes.size();
    }
  }

  // suggested sol2, use path signature for shape, assign diff indices for diff directions
  class Solution3 {
    int[][] grid;
    boolean[][] seen;
    ArrayList<Integer> shape;

    public void explore(int r, int c, int di) {
      if (0 <= r && r < grid.length && 0 <= c && c < grid[0].length && grid[r][c] == 1 && !seen[r][c]) {
        seen[r][c] = true;
        shape.add(di);
        explore(r + 1, c, 1);
        explore(r - 1, c, 2);
        explore(r, c + 1, 3);
        explore(r, c - 1, 4);
        shape.add(0);
      }
    }

    public int numDistinctIslands(int[][] grid) {
      this.grid = grid;
      seen = new boolean[grid.length][grid[0].length];
      Set<List<Integer>> shapes = new HashSet<>();

      for (int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[0].length; c++) {
          shape = new ArrayList<Integer>();
          explore(r, c, 0);
          if (!shape.isEmpty()) {
            shapes.add(shape);
          }
        }
      }

      return shapes.size();
    }
  }

}
