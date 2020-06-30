package LinkedList;

import java.util.IdentityHashMap;

public class MergePointofTwoLists {
    class SinglyLinkedListNode {
          int data;
          SinglyLinkedListNode next;
    }



    //O n space method
    static int findMergeNode(SinglyLinkedListNode head1, SinglyLinkedListNode head2) {

        //System.identityHashCode(obj) gives the default obj hashCode val ->mem addr of that obj
        //IdentityHashSet compare obj==obj instead of obj.equals(obj) for checking existence
        IdentityHashMap<SinglyLinkedListNode,Boolean> set = new IdentityHashMap<>();

        SinglyLinkedListNode cur = head1;
        while(cur!=null){
            set.put(cur,true);
            cur=cur.next;
        }

        cur = head2;
        while(cur!=null){
            if(set.containsKey(cur)){
                return cur.data;
            }
            cur=cur.next;
        }
        return -1;
    }
    //no extra space
    static int findMergeNode2(SinglyLinkedListNode head1, SinglyLinkedListNode head2) {
        //To calculate the merge point, first calculate the difference in the sizes of the linked lists.
        // Move the pointer of the longer linked list by this difference. Increment both pointers till you reach the merge point.

        int size1 =0;
        int size2= 0;

        SinglyLinkedListNode cur =head1;

        while(cur!=null){
            size1++;
            cur=cur.next;
        }
        cur= head2;
        while(cur!=null){
            size2++;
            cur=cur.next;
        }

        int diff;
        SinglyLinkedListNode longer;
        SinglyLinkedListNode shorter;
        if(size1>size2){
            longer = head1;
            shorter = head2;
            diff = size1-size2;
        }else{
            longer = head2;
            shorter = head1;
            diff = size2-size1;
        }

        for(int i=0;i<diff;i++){
            longer=longer.next;
        }

        while(longer!=null && shorter!=null && longer!=shorter){
            longer=longer.next;
            shorter=shorter.next;
        }

        return longer.data;


    }

}
