public class largestBst {

    //333. Largest BST Subtree
    //Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), where largest means subtree with largest number of nodes in it.

    public int solution(Node root){
        return subBST(root).size;
    }

    class Range{//class Result
        public Integer min;
        public Integer max;
        public int size;
        public boolean isBST;
        public Range(Integer min, Integer max, int size, boolean isBST){
            this.min=min;
            this.max=max;
            this.size=size;
            this.isBST=isBST;
        }
    }

    public Range subBST(Node root){
        if(root==null){//terminating at leaf not null
            return new Range(null,null,0,true);
        }

        Range left = subBST(root.left);
        Range right = subBST(root.right);

//        int left_size = left==null?0:left.size;
//        int right_size = right==null?0:right.size;


        if(!left.isBST||!right.isBST||(left.max!=null&&root.val<=left.max) || (right.min!=null&& root.val>=right.min)){
            return new Range(0,0,Math.max(left.size,right.size),false);//not a bst subtree

        }else{
            return new Range(left.min==null?root.val:left.min,right.max==null?root.val:right.max,left.size+right.size+1,true);
        }

    }

    public static void main( String[] args){



        largestBst solver = new largestBst();


        //    1
        //   /\
        //  0  3
        //  /  /\
        //10  2  4

        Node root =  new Node(1);
        root.left = new Node(0);
        root.left.left = new Node(10);//wrong node
        root.right = new Node(3);
        root.right.left = new Node(2);
        root.right.right = new Node(4);


        System.out.println(solver.solution(root));


        Node bst =  new Node(5);
        bst.left = new Node(0);
        bst.left.right = new Node(1);//wrong node
        bst.right = new Node(9);
        bst.right.left = new Node(6);
        bst.right.right = new Node(10);

        System.out.println(solver.solution(bst));

    }


}
