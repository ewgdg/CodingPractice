public class populatingNextRightPoint {
    public static class TreeLinkNode {
       int val;
       TreeLinkNode left, right, next;
       TreeLinkNode(int x) { val = x; }
    }
    public void connect(TreeLinkNode root) {
        TreeLinkNode level_start = root;
        while(level_start!=null){
            TreeLinkNode parent= level_start;
            TreeLinkNode cur = null;
            TreeLinkNode next = null;
            while(parent!=null){

                while(cur==null && parent!=null){
                    if(parent.left!=null){
                        cur=parent.left;
                        cur.next= parent.right;
                    }
                    else if(parent.right!=null){
                        cur=parent.right;
                    }else{
                        parent = parent.next;
                    }
                }
                if(cur==null) return;

                next=cur.next;
                if(cur.next==null)
                    parent=parent.next;
                while(next==null &&  parent!=null){
                    if(parent.left!=null){
                        next= parent.left;
                        next.next=parent.right;
                    }else if(parent.right!=null){
                        next= parent.right;
                    }else{
                        parent= parent.next;
                    }
                }

                cur.next=next;
                cur=next;



            }
            TreeLinkNode next_start =null;
            while(next_start==null && level_start!=null){
                if(level_start.left!=null) next_start=level_start.left;
                else if(level_start.right!=null) next_start= level_start.right;
                else level_start=level_start.next;
            }
            level_start=next_start;

        }
    }
    public static void main(String[] args){
        TreeLinkNode root = new TreeLinkNode(1);
        root.left= new TreeLinkNode(2);
        root.right = new TreeLinkNode(3);

        root.left.left = new TreeLinkNode(4);
        root.right.right = new TreeLinkNode(5);

        new populatingNextRightPoint().connect(root);
    }
}
