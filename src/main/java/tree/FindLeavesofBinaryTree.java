package tree;

import java.util.*;


// Given a binary tree, collect a tree's nodes as if you were doing this: Collect and remove all leaves, repeat until the tree is empty.

 

// Example:

// Input: [1,2,3,4,5]
  
//           1
//          / \
//         2   3
//        / \     
//       4   5    

// Output: [[4,5,3],[2],[1]]

public class FindLeavesofBinaryTree {

  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }

  // recursive sol
  class solution1 {

    public List<List<Integer>> findLeaves(TreeNode root) {

      List<List<Integer>> res = new ArrayList<>();
      helper(root, res);
      return res;

    }

    public int helper(TreeNode cur, List<List<Integer>> res) {
      // critical line 0

      // return dist to bottom
      if (cur == null)
        return -1;

      int left_dist = helper(cur.left, res);
      // line 1
      int right_dist = helper(cur.right, res);
      // line 2

      int dist = Math.max(left_dist, right_dist) + 1;
      if (dist >= res.size()) {
        res.add(new ArrayList<>());
      }
      res.get(dist).add(cur.val);

      return dist;

    }
  }

  // iterative sol
  // a general way to transform recursive to iterative with critical line
  // simulation of assembly code of recursion
  // if we simulate the stack behavior we are creating overhead !!

  class solution2 {
    // helper state , state to simulate helper function call stack
    class State {
      public TreeNode node;
      public Integer leftdist;
      public Integer rightdist;
      public State parentState;
      public int criticalLine;

      public State(TreeNode node, State parent) {
        this.node = node;
        this.parentState = parent;
        leftdist = null;
        rightdist = null;
        criticalLine = 0;

      }
    }

    // update parent state bases on critical line
    public void setParentState(State cur, int returnVal) {
      if(cur.parentState==null){
        return;
      }
      if (cur.parentState.criticalLine == 1) {
        cur.parentState.leftdist = returnVal;
      } else {
        cur.parentState.rightdist = returnVal;
      }
    }

    public List<List<Integer>> findLeaves(TreeNode root) {

      List<List<Integer>> res = new ArrayList<>();

      Stack<State> stack = new Stack<>();

      State cur = new State(root, null);
      stack.add(cur);
      while (!stack.isEmpty()) {
        cur = stack.peek();
        final TreeNode curNode = cur.node;
        if (cur.criticalLine == 0) {
          //terminating cond
          if (cur.node == null) {
            setParentState(cur, -1);
            stack.pop();
            continue;
          }
          State left = new State(curNode.left, cur);
          stack.add(left);
          cur.criticalLine = 1;
        } else if (cur.criticalLine == 1) {
          State right = new State(curNode.right, cur);
          stack.add(right);
          cur.criticalLine = 2;
        } else {
          // do something
          int left_dist = cur.leftdist == null ? -1 : cur.leftdist;
          int right_dist = cur.rightdist == null ? -1 : cur.rightdist;
          int dist = Math.max(left_dist, right_dist) + 1;
          if (dist >= res.size()) {
            res.add(new ArrayList<>());
          }
          res.get(dist).add(curNode.val);

          // return to parent
          setParentState(cur, dist);
          stack.pop();
        }

      }

      return res;

    }

  }

  // another way of iterative solution from tree post order iterator, ugly
  // notice the recursive sol is basically a post order traversal
  class Solution3 {
    // helper state , state to simulate helper function call stack
    class State {
      public TreeNode node;
      public Integer leftdist;
      public Integer rightdist;
      public State parentState;

      public State(TreeNode node, State parent) {
        this.node = node;
        this.parentState = parent;
        leftdist = null;
        rightdist = null;

      }
    }

    public List<List<Integer>> findLeaves(TreeNode root) {

      List<List<Integer>> res = new ArrayList<>();
      Map<TreeNode, State> stateMap = new HashMap<>();
      Stack<State> stack = new Stack<>();

      TreeNode cur = root;
      while (cur != null || !stack.isEmpty()) {
        while (cur != null) {
          final TreeNode curNode = cur;
          State curState = stateMap.computeIfAbsent(cur,
              (key) -> new State(curNode, stack.isEmpty() ? null : stack.peek()));
          if (cur.right != null)
            stack.add(stateMap.computeIfAbsent(cur.right, (key) -> new State(curNode.right, curState)));
          stack.add(curState);

          cur = cur.left;
        }

        State state = stack.pop();
        cur = state.node;
        if (!stack.isEmpty() && cur.right == stack.peek().node) {
          stack.pop();
          stack.add(state);
          cur = cur.right;
        } else {

          // have explored right
          int leftdist = state.leftdist == null ? -1 : state.leftdist;
          int rightdist = state.rightdist == null ? -1 : state.rightdist;
          int dist = Math.max(leftdist, rightdist) + 1;

          if (dist >= res.size()) {
            res.add(new ArrayList<>());
          }
          res.get(dist).add(cur.val);

          // update parent
          if (state.parentState != null) {
            if (state.parentState.node.left == cur) {
              // is a left child
              state.parentState.leftdist = dist;
            } else {
              state.parentState.rightdist = dist;
            }
          }
          cur = null;

        }

      }

      return res;

    }

  }
  // another intersting sol , iterative
  // use out degree topological sort 
  // or reverse direction edge of tree into a graph then use dfs topological sort

  //further improvement , we dont need to record any outdegree
  //we just simply reverse the direction of edges and do bfs from leaves
}
