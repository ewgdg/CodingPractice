package linkedList;

/*
A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.

Return a deep copy of the list.

The Linked List is represented in the input/output as a list of n nodes. Each node is represented as a pair of [val, random_index] where:

val: an integer representing Node.val
random_index: the index of the node (range from 0 to n-1) where random pointer points to, or null if it does not point to any node.
*/
public class CopyListwithRandomPointer {
  class RandomListNode {
    int label;
    RandomListNode next, random;

    RandomListNode(int x) {
      this.label = x;
    }
  };

  // sol1, use an additional hashmap to store the map between original node and
  // copy
  // check if the copy exist before create it to avoid extra copies
  // problem : extra space

  // sol2, create the copy and insert it into the linkedlist right after the
  // original node. so we can find the copy by reading node.next;
  // Old List: A --> B --> C --> D
  // InterWeaved List: A --> A' --> B --> B' --> C --> C' --> D --> D'
  public RandomListNode copyRandomList(RandomListNode head) {
    // Create the copy of node 1 and insert it between node 1 & node 2 in original
    // Linked List, create the copy of 2 and insert it between 2 & 3.. Continue in
    // this fashion, add the copy of N afte the Nth node
    RandomListNode cur = head;

    while (cur != null) {
      RandomListNode temp = cur.next;

      cur.next = new RandomListNode(cur.label);
      cur.next.next = temp;
      cur = temp;
    }

    cur = head;

    while (cur != null) {
      cur.next.random = cur.random == null ? null : cur.random.next;

      cur = cur.next.next;

    }

    cur = head;
    RandomListNode copy = head == null ? null : head.next;
    RandomListNode ret = copy;
    while (cur != null) {
      cur.next = copy.next;
      cur = cur.next;
      copy.next = cur != null ? cur.next : null;

      copy = copy.next;
    }

    return ret;

  }

}