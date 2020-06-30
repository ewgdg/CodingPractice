package tree.lowestCommonAncestor;

import java.util.LinkedList;
import java.util.Queue;

public class LowestCommonAncestor {
    //follow up DAG? BST?
    //assume no dup
    boolean found1=false;
    boolean found2 =false;
    public int solution(Node root, int val1, int val2){
        //need to reset found1 found2
//        found1=false;
//        found2=false;
        Reference found =  new Reference();
        Node res = helper(root,val1,val2, found);
        if(found.found2 && found.found1){
            return res.val;
        }
        return -1;

    }

    class Reference{
        public boolean found1;
        public boolean found2;

    }


    public Node helper(Node root, int val1, int val2, Reference found){
        if(root==null) return null;
        if(root.val==val1 || root.val==val2){
            if(root.val==val1) found.found1=true;
            else found.found2=true;

            helper(root.left,val1,val2,found);
            helper(root.right,val1,val2,found);
            return root;
        }

        Node left= helper(root.left,val1,val2,found);
        Node right = helper(root.right,val1,val2,found);

        if(left != null && right != null){
            return root;
        }

        if(left==null) return right;
        if(right==null) return left;
        return null;
    }


    public int bstSolution(Node root, int val1, int val2){
        while(root!=null) {
            if (root.val > val1 && root.val > val2) {
                root=root.left;
            }else if(root.val < val1 && root.val < val2) {
                root=root.right;
            }else{
                //either branch out or one of val is found
                boolean found1 = bstSearch(root,val1);
                boolean found2 = bstSearch(root,val2);
                if(found1&&found2) return root.val;
                else return -1;
            }
        }
        return -1;
    }
    public boolean bstSearch(Node root, int val){

        while(root!=null){
            if(val>root.val){
                root = root.right;
            }else if(val< root.val){
                root = root.left;
            }else{
                return true;
            }
        }
        return false;

    }


    class Result{
        boolean found1;
        boolean found2;
        Node ancestor;//possible ancestor, need both found1&& found2 to confirm
        public Result(boolean found1, boolean found2, Node ancestor){
            this.found1=found1; this.found2=found2;
            this.ancestor=ancestor;
        }
    }

    public Result helper(Node root, int val1 , int val2){
        if(root==null) return new Result(false,false,null);
        else if(root.val==val1 || root.val==val2){
            boolean found1 = find(root,val1);
            boolean found2 =  find(root,val2);
            return new Result(found1,found2,root);
        }

        Result left = helper(root.left,val1,val2);
        Result right = helper(root.right,val1,val2);

        if(left.ancestor!=null && right.ancestor!=null){
            //found the true ancestor
            return new Result(true,true,root);
        }

        if(left.ancestor!=null) return left;
        if(right.ancestor!=null) return right;

        //NOT FOUND
        return new Result(false,false,null);

    }
    public boolean find(Node root, int val){
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            Node cur = queue.poll();
            if(cur!=null){
                if(cur.val==val) return true;
                queue.add(cur.left);
                queue.add(cur.right);
            }

        }
        return false;

    }



    public static void main( String[] args){



        LowestCommonAncestor solver = new LowestCommonAncestor();


        //    1
        //   /\
        //  0  3
        //  /  /\
        //10  2  4

        Node root =  new Node(1);
        root.left = new Node(0);
        root.left.right = new Node(10);//wrong node
        root.right = new Node(3);
        root.right.left = new Node(2);
        root.right.right = new Node(4);


        System.out.println(solver.solution(root,2,0));
        System.out.println(solver.solution(root,2,4));
        System.out.println(solver.solution(root,2,5));


        Node bst =  new Node(5);
        bst.left = new Node(0);
        bst.left.right = new Node(1);//wrong node
        bst.right = new Node(9);
        bst.right.left = new Node(6);
        bst.right.right = new Node(10);

        System.out.println(solver.bstSolution(bst,9,6));
        System.out.println(solver.bstSolution(bst,6,10));
        System.out.println(solver.bstSolution(bst,0,6));
        System.out.println(solver.bstSolution(bst,0,7));
    }

}
