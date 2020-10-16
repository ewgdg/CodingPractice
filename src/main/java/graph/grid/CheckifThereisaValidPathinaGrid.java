package graph.grid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

//https://leetcode.com/problems/check-if-there-is-a-valid-path-in-a-grid/
//Given a m x n grid. Each cell of the grid represents a street. The street of grid[i][j] can be:
// 1 which means a street connecting the left cell and the right cell.
// 2 which means a street connecting the upper cell and the lower cell.
// 3 which means a street connecting the left cell and the lower cell.
// 4 which means a street connecting the right cell and the lower cell.
// 5 which means a street connecting the left cell and the upper cell.
// 6 which means a street connecting the right cell and the upper cell.

// You will initially start at the street of the upper-left cell (0,0). A valid path in the grid is a path which starts from the upper left cell (0,0) and ends at the bottom-right cell (m - 1, n - 1). The path should only follow the streets.

// Notice that you are not allowed to change any street.

// Return true if there is a valid path in the grid or false otherwise.

// Input: grid = [[2,4,3],[6,5,2]]
// Output: true
// Explanation: As shown you can start at cell (0, 0) and visit all the cells of the grid to reach (m - 1, n - 1).

// Input: grid = [[1,2,1],[1,2,1]]
// Output: false
// Explanation: As shown you the street at cell (0, 0) is not connected with any street of any other cell and you will get stuck at cell (0, 0)
public class CheckifThereisaValidPathinaGrid {
    int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public boolean naiveSol(int[][] grid) {
        int m = grid.length;
        int n = m == 0 ? 0 : grid[0].length;

        HashSet<Node> visited = new HashSet<>();
        // dfs
        Stack<Node> stack = new Stack<>();
        stack.add(new Node(0, 0));
        while (!stack.isEmpty()) {
            Node cur = stack.pop();

            visited.add(cur);


            if (cur.x == n - 1 && cur.y == m - 1) {
                // found
                return true;
            }

            for (int[] dir : dirs) {
                int nextx = cur.x + dir[0];
                int nexty = cur.y + dir[1];

                if (nextx >= 0 && nextx < n && nexty >= 0 && nexty < m) {
                    Node next = new Node(nextx, nexty);
                    // valid next
                    if (isConnected(grid[cur.y][cur.x], grid[nexty][nextx], dir) && !visited.contains(next)) { //note grid[y][x] not grid[x][y]
                        stack.add(next);
                    }
                }
            }

        }
        return false;

    }

    public class Node extends Object {
        public int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int hashCode() {
            return x * 31 + y;
        }

        public boolean equals(Object another) {
            if (another == null)
                return false;
            if (another.getClass() != getClass())
                return false;

            Node that = (Node) another;

            return this.x == that.x && this.y == that.y;

        }

    }

    // check if neighbour cells are connected
    public boolean isConnected(int type1, int type2, int[] dir) {
        // process symmetrical cases
        if ((dir[0] == 0 && dir[1] == -1) || (dir[0] == -1 && dir[1] == 0)) {
            // swap
            type1 = type2 + type1 - (type2 = type1);

        }

        final int type1_copy = type1;
        final int type2_copy = type2;
        // cell2 is below cell1
        if (dir[0] == 0) {
            //Note that, we should avoid using such initialization using Streams,
            // as it could cause a huge performance overhead
            // and lots of garbage object are created
            if (Arrays.stream(new int[]{2, 3, 4}).anyMatch(i -> i == type1_copy)
                && Arrays.stream(new int[]{2, 5, 6}).anyMatch(i -> i == type2_copy)) {
                return true;
            }
        }
        // cell2 is on the right hand side of cell1
        else if (dir[1] == 0) {
            if (Arrays.stream(new int[]{1, 4, 6}).anyMatch(i -> i == type1_copy)
                && Arrays.stream(new int[]{1, 3, 5}).anyMatch(i -> i == type2_copy)) {
                return true;
            }
        } else {
            return false;
        }

        return false;

    }


    //sol2
    //bitwise operation
    //notice each street has only one out direction except the first one.
    //so there is no need to dfs
    static final byte TOP = 0b0001, RIGHT = 0b0010, BOTTOM = 0b0100, LEFT = 0b1000;
    final byte[] STREET_DIRECTIONS = new byte[]{
        0,
        LEFT | RIGHT, //street type 1
        TOP | BOTTOM,   // Street 2
        BOTTOM | LEFT,  // Street 3
        BOTTOM | RIGHT, // Street 4
        TOP | LEFT,     // Street 5
        TOP | RIGHT,    // Street 6
    };

    public boolean sol2(int[][] grid) {
        int m = grid.length;
        if (m == 0) return false;
        int n = grid[0].length;
        if (n == 0) return false;

        byte out = (byte) (STREET_DIRECTIONS[grid[0][0]] & ~LEFT & ~TOP);//cannot beyond bounds
        return navigate(grid, (byte) (out & RIGHT), m, n) || navigate(grid, (byte) (out & BOTTOM), m, n);

    }

    public boolean navigate(int[][] grid, byte init_direction, int m, int n) {
        int i = 0;
        int j = 0;
        byte in_direction = 0, out_direction= init_direction;
        while (true) {

            if (i == m - 1 && j == n - 1) {
                return true;
            }

            switch (out_direction) {
                case TOP:
                    i--;
                    in_direction = BOTTOM;
                    break;

                case BOTTOM:
                    i++;
                    in_direction = TOP;
                    break;

                case LEFT:
                    j--;
                    in_direction = RIGHT;
                    break;
                case RIGHT:
                    j++;
                    in_direction = LEFT;
                    break;

            }

            if(i<0||i>=m || j>=n || j<0){ // out of bounds
                return false;
            }

            //detect loop
            if(i==0&&j==0){
                return false;
            }

            if( (STREET_DIRECTIONS[grid[i][j]]&in_direction)==0){ // unmatched street
                return false;
            }

            out_direction= (byte)(STREET_DIRECTIONS[grid[i][j]]&~in_direction);//get next out

        }

    }

}
