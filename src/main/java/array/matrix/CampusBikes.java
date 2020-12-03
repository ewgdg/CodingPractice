package array.matrix;

import java.util.*;

// On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.

// Our goal is to assign a bike to each worker. Among the available bikes and workers, we choose the (worker, bike) pair with the shortest Manhattan distance between each other, and assign the bike to that worker. (If there are multiple (worker, bike) pairs with the same shortest Manhattan distance, we choose the pair with the smallest worker index; if there are multiple ways to do that, we choose the pair with the smallest bike index). We repeat this process until there are no available workers.

// The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.

// Return a vector ans of length N, where ans[i] is the index (0-indexed) of the bike that the i-th worker is assigned to.

//key to notice this is a multi src + multi path problem
//so if using bfs, iterate bfs m times, it took m * O grid size, m = number of path

public class CampusBikes {

  // first solution:
  // sort combination of bike and worker
  // pq to sort
  // O mnlogmn
  class Solution {

    class Node implements Comparable<Node> {
      public int w;
      public int b;
      public int cost;

      public Node(int w, int b, int cost) {
        this.w = w;
        this.b = b;
        this.cost = cost;
      }

      public int compareTo(Node node) {
        int diff = this.cost - node.cost;
        if (diff == 0) {
          int w_diff = this.w - node.w;

          diff = w_diff == 0 ? this.b - node.b : w_diff;
        }
        return diff;
      }

      public String toString() {
        return w + ":" + b + ":" + cost;
      }
      // skip
      // getters and setters;
      // ...

    }

    public int[] assignBikes(int[][] workers, int[][] bikes) {
      // multi src and multi path
      // Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
      // naive sol: O mn , O 1000^2, wrong , naive sol takes more than mn bc need to
      // find smallest cost among all comb mn , -> mnlogmn =1000^2*6*log(1000)
      // bfs in one iter : start with workers , O m*grid size, 1000^3 `= 1000000000
      // build a graph , with workers and bikes as node , uniform cost search ,
      // (mn)log(mn) , mn = total number of states, every worker connects to all bikes
      // when build the graph.

      // use pq to sort slightly better then quick sort
      PriorityQueue<Node> pq = new PriorityQueue<>();
      for (int i = 0; i < workers.length; i++) {

        for (int j = 0; j < bikes.length; j++) {
          int[] worker = workers[i]; // [0,0]
          int[] bike = bikes[j]; // [1,2]
          int cost = Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);// 3
          pq.add(new Node(i, j, cost)); // pq= {[0,0,3] }

        }
      }
      // pq={ [1,0,2],[0,0,3],[1,1,3],[0,1,6] }
      int[] res = new int[workers.length];
      Arrays.fill(res, -1);
      Set<Integer> consumed = new HashSet<>();// cost extra space
      while (!pq.isEmpty()) {
        Node node = pq.poll();
        if (res[node.w] == -1 && !consumed.contains(node.b)) {
          res[node.w] = node.b; // res=[1,0] , consumed=[0,1]
          consumed.add(node.b);
        }
      }
      return res;

    }
  }

  // better solution:
  // use bucket sort / counting sort
  // notice the range of input 0-1000
  // so the cost is in range of 0-1000 + 0-1000 = 0-2000
  // this enable us to build buckets
  // O m*n
  class solution2 {

    class Node implements Comparable<Node> {
      public int w;
      public int b;
      public int cost;

      public Node(int w, int b, int cost) {
        this.w = w;
        this.b = b;
        this.cost = cost;
      }

      public int compareTo(Node node) {
        int diff = this.cost - node.cost;
        if (diff == 0) {
          int w_diff = this.w - node.w;

          diff = w_diff == 0 ? this.b - node.b : w_diff;
        }
        return diff;
      }

      public String toString() {
        return w + ":" + b + ":" + cost;
      }
      // skip
      // getters and setters;
      // ...

    }

    public int[] assignBikes(int[][] workers, int[][] bikes) {
      //dont really need buckets here, just a list<node> should be enough with counting sort
      List<Stack<Node>> buckets = new ArrayList<>(Collections.nCopies(2001, null)); //use list instead of array bc it is of generic type
      int[] counter = new int[2001];
      for (int i = 0; i < workers.length; i++) {

        for (int j = 0; j < bikes.length; j++) {
          int[] worker = workers[i]; // [0,0]
          int[] bike = bikes[j]; // [1,2]
          int cost = Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);// 3
          Node node = new Node(i, j, cost);
          if (buckets.get(cost) == null) {
            buckets.set(cost,new Stack<>());
          }
          buckets.get(cost).add(node);
          counter[cost] += 1;
        }
      }

      // prefix sum
      for (int i = 1; i < counter.length; i++) {
        counter[i] = counter[i - 1] + counter[i];
      }

      Node[] sorted = new Node[counter[counter.length - 1]];

      for (int i = buckets.size() - 1; i >= 0; i--) {
        Stack<Node> stack = buckets.get(i);
        while (stack != null && !stack.isEmpty()) {
          Node node = stack.pop();
          sorted[(counter[i]--) - 1] = node;
        }
      }

      // sorted={ [1,0,2],[0,0,3],[1,1,3],[0,1,6] }
      int[] res = new int[workers.length];
      Arrays.fill(res, -1);
      Set<Integer> consumed = new HashSet<>();// cost extra space
      for (int i = 0; i < sorted.length; i++) {
        Node node = sorted[i];
        if (res[node.w] == -1 && !consumed.contains(node.b)) {
          res[node.w] = node.b; // res=[1,0] , consumed=[0,1]
          consumed.add(node.b);
        }
      }
      return res;

    }

  }
  //go further from bucket sort
  //we dont have to really sort it into array
  //the buckets itself could serve our purpose
  // we just read node from bucket
  class solution3 {

    class Node implements Comparable<Node> {
      public int w;
      public int b;
      public int cost;

      public Node(int w, int b, int cost) {
        this.w = w;
        this.b = b;
        this.cost = cost;
      }

      public int compareTo(Node node) {
        int diff = this.cost - node.cost;
        if (diff == 0) {
          int w_diff = this.w - node.w;

          diff = w_diff == 0 ? this.b - node.b : w_diff;
        }
        return diff;
      }

      public String toString() {
        return w + ":" + b + ":" + cost;
      }
      // skip
      // getters and setters;
      // ...

    }

    public int[] assignBikes(int[][] workers, int[][] bikes) {

      List<Queue<Node>> buckets = new ArrayList<>(Collections.nCopies(2001, null)); //use list instead of array bc it is of generic type
      
      for (int i = 0; i < workers.length; i++) {

        for (int j = 0; j < bikes.length; j++) {
          int[] worker = workers[i]; // [0,0]
          int[] bike = bikes[j]; // [1,2]
          int cost = Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);// 3
          Node node = new Node(i, j, cost);
          if (buckets.get(cost) == null) {
            buckets.set(cost,new LinkedList<>());
          }
          buckets.get(cost).add(node);
        
        }
      }

    
      // sorted={ [1,0,2],[0,0,3],[1,1,3],[0,1,6] }
      int[] res = new int[workers.length];
      Arrays.fill(res, -1);
      Set<Integer> consumed = new HashSet<>();// cost extra space
      for (int i = 0; i < buckets.size(); i++) {
        Queue<Node> queue = buckets.get(i);
        while(!queue.isEmpty()){
          Node node = queue.poll();
          if (res[node.w] == -1 && !consumed.contains(node.b)) {
            res[node.w] = node.b; // res=[1,0] , consumed=[0,1]
            consumed.add(node.b);
          }
        }
      }
      return res;

    }

  }

}
