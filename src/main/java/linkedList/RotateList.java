package linkedList;
// Given a linked list, rotate the list to the right by k places, where k is non-negative.

// Example 1:

// Input: 1->2->3->4->5->NULL, k = 2
// Output: 4->5->1->2->3->NULL
// Explanation:
// rotate 1 steps to the right: 5->1->2->3->4->NULL
// rotate 2 steps to the right: 4->5->1->2->3->NULL
// Example 2:

// Input: 0->1->2->NULL, k = 4
// Output: 2->0->1->NULL
// Explanation:
// rotate 1 steps to the right: 2->0->1->NULL
// rotate 2 steps to the right: 1->2->0->NULL
// rotate 3 steps to the right: 0->1->2->NULL
// rotate 4 steps to the right: 2->0->1->NULL
public class RotateList {

  public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

  public ListNode rotateRight(ListNode head, int k) {
    //we can early terminate to avoid dealing with corner case and the use of dummy head
    //k==0, head.next=null
    if (head == null)
      return head;
    // find the new head first
    // the new head is kth node from the last
    // if k> length , k=k%length
    ListNode dummyHead = new ListNode();
    dummyHead.next = head;
    ListNode right = dummyHead;
    ListNode left = dummyHead;
  
    // 2 pointer method to find the kth node from the end
    for (int i = 0; i < k; i++) {

      right = right.next;
      if (right == null) {
        // reset loop
        k = k % (i);
        i = 0 - 1;
        right = dummyHead;
        // return rotateRight(head,k%i); recursive call to early terminate when k==0

      }

    }

    ListNode tail = right;
    ListNode newTail = null;
    //use right.next!=null so that we dont need to use tail and newTail pointer.
    while (right != null) {
      tail = right;
      right = right.next;
      newTail = left;
      left = left.next;

    }

    if (left == null)
      left = head;
    if (left != head)
      tail.next = head;
    if (newTail != null)
      newTail.next = null;

    return left;

  }

}