package Tree;

import java.util.Stack;

public class SameLeaves {
    // extra space < O(n)

    class LeafIterator{
        Node root;
        Stack<Node> stack;
        Node cur;

        public LeafIterator(Node root){
            this.root=root;
            cur=root;
            stack=new Stack<>();
        }


        public Node next(){
            //or simply dfs

            //inorder
            while(cur!=null || !stack.isEmpty()) {
                while(cur!=null){

                    stack.add(cur);
                    cur=cur.left;
                }
                cur = stack.pop();
                if(cur.left==null&&cur.right==null){
                    //found
                    Node res= cur;
                    cur= cur.right;
                    return res;
                }
                cur= cur.right;
            }
            return null;
        }

    }



    class LeafIterator_dfs{
        Node root;
        Stack<Node> stack;
        Node cur;

        public LeafIterator_dfs(Node root){
            this.root=root;
            cur=root;
            stack=new Stack<>();
            if(root!=null)
                stack.add(root);
        }


        public Node next(){ //next leaf
            //or simply dfs
            boolean found = false;
            while(!stack.isEmpty() && !found) {
                cur = stack.pop();
                if(cur.right==null && cur.left==null){
                    found=true;
                }else{
                    if(cur.right!=null)
                        stack.add(cur.right);
                    if(cur.left!=null)
                        stack.add(cur.left);
                }
            }
            return found?cur:null;
        }

    }

    public boolean solution(Node a , Node b){
        LeafIterator_dfs a_iter = new LeafIterator_dfs(a);
        LeafIterator_dfs b_iter = new LeafIterator_dfs(b);
        Node a_leaf=null;
        Node b_leaf=null;
        do{
            a_leaf = a_iter.next();
            b_leaf = b_iter.next();
            if(a_leaf==null&&b_leaf==null) return true;
            else if(a_leaf==null || b_leaf==null) return false;
            else if(b_leaf.val!=a_leaf.val) return false;


        }while(a_leaf!=null && b_leaf!=null);


//        if(a_leaf==null&&b_leaf==null) return true;
//        else if(a_leaf==null || b_leaf==null) return false;
        return true;

    }



    public static void main(String[] args){
        Node a = new Node(1);
        a.left= new Node(4);
        a.right= new Node(2);
        a.right.left= new Node(7);
        a.right.right= new Node(3);


        Node b= new Node(1);
        b.left= new Node(2);
        b.left.left= new Node(4);
        b.right=new Node(2);
        b.right.right= new Node(10);
        b.right.right.right= new Node(9);

        b.right.right.left= new Node(7);
        b.right.right.right.left= new Node(3);

        SameLeaves solver = new SameLeaves();
        System.out.println( solver.solution(a,b));

    }
}
