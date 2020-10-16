package linkedList;

/*
Given a linked list, remove the n-th node from the end of list and return its head.

Example:

Given linked list: 1->2->3->4->5, and n = 2.

After removing the second node from the end, the linked list becomes 1->2->3->5.
Note:

Given n will always be valid.

Follow up:

Could you do this in one pass?
*/
public class RemoveNthNodeFromEndofList {
  
  // Definition for singly-linked list.
  public class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
 
  public ListNode removeNthFromEnd(ListNode head, int n) {
    //corner case
    
    //create a dummy head b/c we need to find the parent/prev node in corner case when deleted node is head
    ListNode dummy = new ListNode(-1);
    dummy.next=head;
    
   
    //maintain the distance between 2 reference point as n, n+1 to find parent
    ListNode right=dummy;
    for(int i=0;i<n+1;i++){
        right=right.next;
        
    }
    ListNode left=dummy;
    
    //iterate till right node reach the end;
    while(right!=null){
        right=right.next;
        left=left.next;
    }
    

    left.next=left.next.next;
    
   
    return dummy.next;
    
    
    
}
}