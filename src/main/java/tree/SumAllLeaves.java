package tree;

public class SumAllLeaves {
    //第一题是求二叉树的所有叶节点的和 先是说了inorder的方法，然后面试官问能不能不用额外空间
    public int sum(Node root){

        //use pre order  morris traversal

        Node cur = root;

        int sum=0;
        while(cur!=null){
            if(cur.left==null){
                System.out.println(cur.val);
                if(isLeaf(cur)){
//                    System.out.println("add");
                    sum+=cur.val;
                }
                cur=cur.right;

            }else{//left is not null , impossible for cur to be leaf
                Node predecessor = findPredecessor(cur);

                if(predecessor.right==null){
                    System.out.println(cur.val);
//                    if(isLeaf(cur)){  impossible for cur to be leaf
//                        sum+=cur.val;
//                    }
                    if(isLeaf(predecessor)){//need to exam predecessor right now bc we will add a right node to it so cannot detect leaf latter
                        sum+=predecessor.val; //no need if improve cond of isLeaf and add boolean isLeaf into Node class
                    }
                    predecessor.right=cur;
                    cur=cur.left;
                }else{
                    predecessor.right=null;
                    cur=cur.right;
                }


            }
        }
        return sum;


    }

    public Node findPredecessor(Node node){
        Node cur = node.left;
        while(cur.right!=null && cur.right!=node){
            cur=cur.right;
        }
        return cur;
    }
    public boolean isLeaf(Node n){
        return n.right==null && n.left==null;
    }



    public static void main(String[] args){
        SumAllLeaves solver = new SumAllLeaves();
        Node bst =  new Node(5);
        bst.left = new Node(0);
        bst.left.right = new Node(1);//wrong node
        bst.right = new Node(9);
        bst.right.left = new Node(6);
        bst.right.right = new Node(10);
        Node.insert(bst,11);

        System.out.println(solver.sum(bst));

    }
}
