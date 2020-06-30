package others;
public class Sort_List {

    public static class ListNode {
       int val;
       ListNode next;
       ListNode(int x) { val = x; }
   }
    public ListNode sortList(ListNode head) {
        //merge sort
        //get length
        ListNode p = head;
        int length=0;
        while(p!=null) {
            length++;
            p=p.next;
        }

        ListNode dummy = new ListNode(-1);
        dummy.next=head;
        for(int step =1; step<length; step *=2){
            p=dummy;
            while(p.next!=null){

                ListNode left=p.next;
                ListNode right = split(left,step);
                ListNode tail = split(right,step);
                p = merge(left,right,p,tail);

            }



        }
        return dummy.next;


    }

    public final class ss{

        public void ssas(){
            return ;
        }
    }

    public ListNode split(ListNode head, int len){
        ListNode p =head;
        while(len>0&&p!=null){
            p=p.next;
            len--;
        }
        return p;
    }

    public ListNode merge(ListNode l, ListNode r,ListNode head,ListNode tail){
        // ListNode head = new ListNode(-1);
        ListNode p = head;
        ListNode l_end=r;
        while(l!=l_end && r!=tail){
            if(l.val<r.val){
                p.next = l;
                p=l;
                l=l.next;
            }else{
                p.next = r;
                p=r;
                r=r.next;
            }
        }

        if(l!=l_end){
            p.next=l;
            while(p.next!=l_end){
                p=p.next;
            }
            p.next = tail;
        }else{
            p.next=r;
            while(p.next!=tail){
                p=p.next;
            }
        }



        return p;
    }

}
