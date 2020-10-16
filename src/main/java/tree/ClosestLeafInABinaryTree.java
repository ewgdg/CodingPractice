package tree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//742
//Given a binary tree where every node has a unique value, and a target key k, find the value of the nearest leaf node to target k in the tree.

// Here, nearest to a leaf means the least number of edges travelled on the binary tree to reach any leaf of the tree. Also, a node is called a leaf if it has no children.

// In the following examples, the input tree is represented in flattened form row by row. The actual root tree given will be a TreeNode object.

// Input:
// root = [1, 3, 2], k = 1
// Diagram of binary tree:
//           1
//          / \
//         3   2

// Output: 2 (or 3)

// Explanation: Either 2 or 3 is the nearest leaf node to the target of 1.

// Input:
// root = [1,2,3,4,null,null,null,5,null,6], k = 2
// Diagram of binary tree:
//              1
//             / \
//            2   3
//           /
//          4
//         /
//        5
//       /
//      6

// Output: 3
// Explanation: The leaf node with value 3 (and not the leaf node with value 6) is nearest to the node with value 2.



class ClosestLeafInABinaryTree {

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


  //suggested sol: convert to graph
  //Intuition
  //Instead of a binary tree, if we converted the tree to a general graph, we could find the shortest path to a leaf using breadth-first search.

  class Solution_Graph {
    public int findClosestLeaf(TreeNode root, int k) {
        Map<TreeNode, List<TreeNode>> graph = new HashMap<>();
        dfs(graph, root, null);

        Queue<TreeNode> queue = new LinkedList<>();
        Set<TreeNode> seen = new HashSet<>();

        for (TreeNode node: graph.keySet()) {
            if (node != null && node.val == k) {
                queue.add(node);
                seen.add(node);
            }
        }

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                if (graph.get(node).size() <= 1)
                    return node.val;
                for (TreeNode nei: graph.get(node)) {
                    if (!seen.contains(nei)) {
                        seen.add(nei);
                        queue.add(nei);
                    }
                }
            }
        }
        throw null;
    }

    public void dfs(Map<TreeNode, List<TreeNode>> graph, TreeNode node, TreeNode parent) {
        if (node != null) {
            if (!graph.containsKey(node)) graph.put(node, new LinkedList<TreeNode>());
            if (!graph.containsKey(parent)) graph.put(parent, new LinkedList<TreeNode>());
            graph.get(node).add(parent);
            graph.get(parent).add(node);
            dfs(graph, node.left, node);
            dfs(graph, node.right, node);
        }
    }
  }



//   Approach #2: Annotate Closest Leaf [Accepted]
// Intuition and Algorithm
// Say from each node, we already knew where the closest leaf in it's subtree is. Using any kind of traversal plus memoization, we can remember this information.
// Then the closest leaf to the target (in general, not just subtree) has to have a lowest common ancestor with the target that is on the path from the root to the target. We can find the path from root to target via any kind of traversal, and look at our annotation for each node on this path to determine all leaf candidates, choosing the best one.
// class Solution {
//   List<TreeNode> path;
//   Map<TreeNode, LeafResult> annotation;

//   public int findClosestLeaf(TreeNode root, int k) {
//       path = new ArrayList();
//       annotation = new HashMap();

//       dfs(root, k);

//       int distanceFromTarget = path.size() - 1;
//       int dist = Integer.MAX_VALUE;
//       TreeNode leaf = null;
//       for (TreeNode node: path) {
//           LeafResult lr = closestLeaf(node);
//           if (lr.dist + distanceFromTarget < dist) {
//               dist = lr.dist + distanceFromTarget;
//               leaf = lr.node;
//           }
//           distanceFromTarget--;
//       }
//       return leaf.val;
//   }

//   public boolean dfs(TreeNode node, int k) {
//       if (node == null) {
//           return false;
//       } else if (node.val == k) {
//           path.add(node);
//           return true;
//       } else {
//           path.add(node);
//           boolean ans = dfs(node.left, k);
//           if (ans) return true;
//           ans = dfs(node.right, k);
//           if (ans) return true;
//           path.remove(path.size() - 1);
//           return false;
//       }
//   }

//   public LeafResult closestLeaf(TreeNode root) {
//       if (root == null) {
//           return new LeafResult(null, Integer.MAX_VALUE);
//       } else if (root.left == null && root.right == null) {
//           return new LeafResult(root, 0);
//       } else if (annotation.containsKey(root)) {
//           return annotation.get(root);
//       } else {
//           LeafResult r1 = closestLeaf(root.left);
//           LeafResult r2 = closestLeaf(root.right);
//           LeafResult ans = new LeafResult(r1.dist < r2.dist ? r1.node : r2.node,
//                                           Math.min(r1.dist, r2.dist) + 1);
//           annotation.put(root, ans);
//           return ans;
//       }
//   }
// }
// class LeafResult {
//   TreeNode node;
//   int dist;
//   LeafResult(TreeNode n, int d) {
//       node = n;
//       dist = d;
//   }
// }



  //my sol, post order traversal for lowest common ancestor between highest leaf and target node
  int minDist = Integer.MAX_VALUE;
  int res;

  class Node {
    int val;
    int depth;

    public Node(int val, int depth) {
      this.val = val;
      this.depth = depth;
    }
  }

  public int findClosestLeaf(TreeNode root, int k) {
    helper(root, k, 0);
    return res;

  }

  // return closest leaf or node of k
  public Node helper(TreeNode cur, int k, int depth) {

    // terminating cond
    if (cur == null) {

      return null;

    }

    Node left = helper(cur.left, k, depth + 1);
    Node right = helper(cur.right, k, depth + 1);

    Node closestLeaf;
    if (left == null && right == null) {
      closestLeaf = new Node(cur.val, depth);
    } else if (left == null) {
      closestLeaf = right;
    } else if (right == null) {
      closestLeaf = left;
    } else {
      closestLeaf = left.depth < right.depth ? left : right;
      if (left.val == k || right.val == k) {
        int dist = left.depth - depth + right.depth - depth;
        if (dist < minDist) {
          minDist = dist;
          res = left.val == k ? right.val : left.val;
        }
        closestLeaf = left.val == k ? right : left;
      }
    }

    if (cur.val == k) {

      int dist = closestLeaf.depth - depth;
      if (dist < minDist) {
        minDist = dist;
        res = closestLeaf.val;
      }
      closestLeaf = new Node(k, depth);
    }

    return closestLeaf;

  }
}