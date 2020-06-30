package linkedList;

public class DetectCycle {

    class ListNode{
        int val;
        ListNode next;
        ListNode(int x) {
          val = x;
          next = null;
        }
    }

    //return the entrance of cycle
    public ListNode detectCycle(ListNode head) {
        //corner
        if(head==null) return null;

        ListNode fast=head;
        ListNode slow = head;
        boolean detected = false;
        while(fast!=null && fast.next!=null && !detected){
            fast=fast.next.next;

            slow=slow.next;

            if(fast==slow){
                detected=true; //note we have to check this after we run beyond the head within the while loop
            }
        }

        if(!detected) return null;
        slow=head;
        while(slow!=fast){
            slow=slow.next;
            fast=fast.next;
        }
        return slow;
    }
}
