package Tree;

public class MorisTraversal {

    public void inOrder(Node root){
        Node cur = root;
        while(cur!=null){

            if(cur.left==null){
                System.out.println(cur.val);
                cur=cur.right;
            }else{
                Node predecessor = findPredecessor(cur);
                if(predecessor.right==null){
                    predecessor.right=cur;
                    cur=cur.left;
                }else{
                    //predecessor pointed to cur node already, all left subtree is done
                    predecessor.right=null;
                    System.out.println(cur.val);
                    cur=cur.right;
                }

            }

        }


    }

    public Node findPredecessor(Node node){
        Node cur = node.left;
        while(cur.right!=null && cur.right!=node){
            cur=cur.right;
        }
        return cur;
    }


    public void preOrder(Node root){
        Node cur= root;

        while(cur!=null){
            if(cur.left==null){
                System.out.println(cur.val);
                cur=cur.right;
            }else{
                Node predecessor = findPredecessor(cur);
                if(predecessor.right==null){//havent traverse left sub yet
                    System.out.println(cur.val);
                    predecessor.right=cur;
                    cur=cur.left;
                }else{
                    predecessor.right=null;
                    cur=cur.right;
                }

            }

        }

    }
    public void postorderMorrisTraversal(Node root) {
        Node cur = root;
        while (cur != null) {
            if (cur.left == null) {
                cur = cur.right;
            } else {
                Node predecessor = findPredecessor(cur);
                if (predecessor.right == null) {
                    predecessor.right = cur;
                    cur = cur.left;
                } else {
                    //printReverse(cur.left,predecessor);//print reversely from cur.left to pred
                    predecessor.right = null;
                    cur = cur.right;

                }

            }
        }
    }
}
