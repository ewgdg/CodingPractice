package Tree;

public class MyCalendarII {

    class Node{
        int start,end;
        int overlapping_count;

        Node left;
        Node right;
        public Node(int s , int e){
            this.start=s; this.end=e;
            overlapping_count=1;
        }
    }
    Node root;

    public MyCalendarII(){
        root= null;//new Result(0,0);//dummy
    }
    public boolean book(int start, int end) {
        if (!insertable(root, start, end)) {
            return false;
        }
        root = insert(root, start, end);
        return true;
    }
    public Node insert(Node cur,int start, int end){
        if(cur==null){
            return new Node(start,end);
        }
        if(start==end) return cur; // no modification for invalid case
        if(start>=cur.end){
            cur.right = insert(cur.right,start,end);
        }else if (end<=cur.start){
            cur.left = insert(cur.left,start,end);
        }else{
            //has overlapping ,need to slice the interval

            //extract the overlapping interval
            int a = Math.min(cur.start,start);
            int b = Math.max(cur.start,start);
            int c = Math.min(cur.end,end);
            int d = Math.max(cur.end,end);

            cur.left = insert(cur.left,a,b);//if it is insertable then the default overlapping is always 1 for cur
            cur.right  = insert(cur.right,c,d);

            //cut the interval out
            cur.start=b;
            cur.end = c;
            cur.overlapping_count+=1;
        }

        return cur;
    }

    public boolean insertable(Node cur,int start, int end){//check if insertable first before we do slicing !!
        //corner case
        if (cur == null) {
            return true;
        }
        if(start>=end){
            return true;
        }

        if(start>=cur.end){
            return insertable(cur.right,start,end);
        }else if (end<=cur.start){
            return insertable(cur.left,start,end);
        }else{
            if (cur.overlapping_count>=2) {
                return false;
            } else if (start >= cur.start && end <= cur.end) {
                return true;
            } else {
                return insertable(cur.left, start, cur.start) && insertable(cur.right, cur.end, end);
            }
        }

//        return true;

    }



}
