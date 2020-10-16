package array.matrix;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

// In an infinite chess board with coordinates from -infinity to +infinity, you have a knight at square [0, 0].

// A knight has 8 possible moves it can make, as illustrated below. Each move is two squares in a cardinal direction, then one square in an orthogonal direction.
// final static int[][] dirs = new int[][]{{1,2},{2,1},{2,-1},{1,-2},{-2,1},{-1,2},{-2,-1},{-1,-2}   };

// Return the minimum number of steps needed to move the knight to the square [x, y].  It is guaranteed the answer exists.

// Example 1:

// Input: x = 2, y = 1
// Output: 1
// Explanation: [0, 0] → [2, 1]
// Example 2:

// Input: x = 5, y = 5
// Output: 4
// Explanation: [0, 0] → [2, 1] → [4, 2] → [3, 4] → [5, 5]
public class MinimumKnightMoves {

  // my init sol , use bfs -> pass time limit
  // it turns out we can limit the search area to one quadrant.
  class Node {
    int x, y;

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public String toString() {
      return this.x + ":" + this.y;
    }

  }

  public boolean visit(Set<String> seen, Node node) {

    return seen.add(node.toString());
  }

  public boolean isVisited(Set<String> seen, Node node) {
    return seen.contains(node.toString());
  }

  final static int[][] dirs = new int[][] { { 1, 2 }, { 2, 1 }, { 2, -1 }, { 1, -2 }, { -2, 1 }, { -1, 2 }, { -2, -1 },
      { -1, -2 } };

  public int minKnightMoves1(int x, int y) {
    // radius = sqrt(x^2+y^2)
    // complexity = pi*r^2; O x^2+y^2;

    // or we can make x and y abs bc it is symmetrical

    // number of steps
    int cost = 0;

    // another option is to use a String instead of Node
    Queue<Node> queue = new LinkedList<>();
    // visited nodes
    Set<String> seen = new HashSet<>();

    // init queue insert init pos
    queue.add(new Node(0, 0));

    while (!queue.isEmpty()) {

      int size = queue.size();
      for (int i = 0; i < size; i++) {
        Node node = queue.poll();
        if (isVisited(seen, node)) {
          continue;
        }
        // mark visited
        visit(seen, node);

        if (node.x == x && node.y == y) {
          return cost;
        }

        // explore child
        for (int[] dir : dirs) {
          int newX = node.x + dir[0];
          int newY = node.y + dir[1];

          //limit search to one quadrant
          int signX=1;
          int signY=1;
          if(x<0){
            signX=-1;
          }
          if(y<0){
            signY=-1;
          }
          int pad=-1;
          if(newX*signX>=pad && newY*signY>=pad)
            queue.add(new Node(newX, newY));
        }

      }
      cost++;

    }
    return cost;

  }
  //second trial
  //math cal
  //each move is either hori +/- 2 or 1 vs verti +/-1 or 2
  // min( x/2 or y/2 )? wrong

  // notice that a problem can be build from smaller problem 
  // dynamic problem 
  // probably same complexity as bfs 

}
