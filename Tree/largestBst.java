public class largestBst {

    public int solution(Node root){
        return subBST(root).size;
    }

    class Range{
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
            return null;
        }

        Range left = subBST(root.left);
        Range right = subBST(root.right);

        int left_size = left==null?0:left.size;
        int right_size = right==null?0:right.size;


        if( (left==null|| (left.isBST  && root.val >left.max)   )  && (right==null|| (right.isBST  && root.val<right.min ))  ){

            return new Range(left==null?root.val:left.min,right==null?root.val:right.max,left_size+right_size+1,true);
        }else{
            return new Range(0,0,Math.max(left_size,right_size),false);
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
