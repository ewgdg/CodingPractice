package tree;

// Convert a Binary Search Tree to a sorted Circular Doubly-Linked List in place.

// You can think of the left and right pointers as synonymous to the predecessor and successor pointers in a doubly-linked list. For a circular doubly linked list, the predecessor of the first element is the last element, and the successor of the last element is the first element.

// We want to do the transformation in place. After the transformation, the left pointer of the tree node should point to its predecessor, and the right pointer should point to its successor. You should return the pointer to the smallest element of the linked list.
public class ConvertBinarySearchTreeToSortedDoublyLinkedList {

  class Solution {
    // naive sol
    // in order traversal and push to a linked list
    // not in place

    // moris traversal
    public Node treeToDoublyList(Node root) {
      if (root == null)
        return root;
      Node cur = root;
      Node head = null;

      Node prev = null;

      while (cur != null) {

        if (cur.left == null) {
          if (head == null) {
            head = cur;
          }
          // do something
          if (prev != null)
            prev.right = cur;
          cur.left = prev;
          prev = cur;
          // explore right subtree
          cur = cur.right;

        } else {

          Node pred = findPredecessor(cur);

          if (pred.right == cur) {
            // done exploration of left tree

            // do something
            if (prev != null)
              prev.right = cur;
            cur.left = prev;
            prev = cur;

            // explore right subtree
            cur = cur.right;

          } else {
            pred.right = cur;
            cur = cur.left;

          }

        }

      }

      prev.right = head;
      head.left = prev;

      return head;

    }

    public Node findPredecessor(Node node) {
      Node cur = node;
      if (cur.left == null) {
        return null;
      }
      cur = cur.left;
      while (cur.right != null && cur.right != node) {
        cur = cur.right;
      }

      return cur;
    }

  }

  // further simplified loop
  class Solution2 {
    // naive sol
    // in order traversal and push to a linked list
    // not in place

    // moris traversal
    public Node treeToDoublyList(Node root) {
      if (root == null)
        return root;
      Node cur = root;
      Node head = null;

      Node prev = null;

      while (cur != null) {

        Node pred = findPredecessor(cur);

        if (pred == null || pred.right == cur) {
          // done exploration of left tree

          if (head == null) {
            head = cur;
          }
          // do something
          if (prev != null)
            prev.right = cur;
          cur.left = prev;
          prev = cur;

          // explore right subtree
          cur = cur.right;

        } else {
          pred.right = cur;
          cur = cur.left;

        }

      }

      prev.right = head;
      head.left = prev;

      return head;

    }

    public Node findPredecessor(Node node) {
      Node cur = node;
      if (cur.left == null) {
        return null;
      }
      cur = cur.left;
      while (cur.right != null && cur.right != node) {
        cur = cur.right;
      }

      return cur;
    }

  }

}
