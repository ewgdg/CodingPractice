public class add2number2 {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        //add the sum to result linkedlist in reversed order , reverse back the result with correct carry

        //check length
        ListNode node1 = l1;
        int n1 =0 ;
        ListNode node2 = l2;
        int n2=0;
        while(node1!=null ){
            node1= node1.next;
            n1++;
        }
        while(node2!=null ){
            node2= node2.next;
            n2++;
        }

        node1=l1; node2=l2;

        ListNode res = new ListNode(-1);

        if(n1>n2){
            int shift = n1-n2;
            while(shift>0){
                appendHead( new ListNode(node1.val ) ,res);
                node1=node1.next;
                shift--;
            }

        }else if(n2>n1){
            int shift = n2-n1;
            while(shift>0){
                appendHead( new ListNode(node2.val ) ,res);
                node2=node2.next;
                shift--;
            }
        }

        while( node1!=null && node2!=null ){
            int sum = node1.val+node2.val;
            appendHead(new ListNode(sum), res);
            node1= node1.next;
            node2=node2.next;
        }

        ListNode cur = res.next;

        res.next=null;

        int carry = 0;
        while(cur!=null){
            int sum =(cur.val+carry);
            int digit = sum %10;
            cur.val = digit;

            ListNode next = cur.next;
            appendHead( cur , res);

            carry = sum / 10 ;
            cur=next;
        }

        if(carry > 0) appendHead(new ListNode(carry),res);

        return res.next;
    }

    public void appendHead(ListNode node, ListNode head){
        node.next = head.next;
        head.next=node;
    }
}

