public class MyCalendarIII {
    Node root;


    public MyCalendarIII(){
        root=null;
    }

    //worst case O N per book node
    public int book(int start , int end){
        Reference res =new Reference();
        insert(root,start,end,1,res);
        return res.k;

    }
    class Node{
        int start,end;
        int k;//how many overlapping here
        Node left;
        Node right;
        public Node(int s,int e,int k){
            this.start =s;
            this.end = e;
            this.k=k;
        }
    }
    class Reference{
        int k;
        public Reference(){
            k=0;
        }
    }
    public Node insert(Node cur,int start, int end, int k, Reference res){
        if(cur==null){
            //reach bottom
            res.k=Math.max(k,res.k);
            return new Node(start,end,k);
        }

        if(start==end) return cur; // no modification for invalid case

        if(end<=cur.start){
            cur.left = insert(cur.left,start,end,k,res);
        }else if(start>=cur.end){
            cur.right = insert(cur.right,start,end,k,res);
        }else{
            int a = Math.min(cur.start,start);
            int b = Math.max(cur.start,start);
            int c = Math.min(cur.end,end);
            int d = Math.max(cur.end,end);


            cur.left = insert(cur.left,a,b, a==cur.start?cur.k:k,res );
            cur.right = insert(cur.right,c,d,d==cur.end?cur.k:k,res);


            cur.start=b;
            cur.end=c;
            cur.k+=k;
            res.k=Math.max(cur.k,res.k);

        }
        return cur;

    }




}
