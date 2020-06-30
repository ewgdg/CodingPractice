package tree;
public class InOrderIterator {

    //Morris traversal, Notice this will change the leaf node
    Node cur;
    Node root;

    public InOrderIterator(Node root){
        this.root=root;
        this.cur=root;
    }

    public boolean hasNext(){
        return cur!=null;
    }

    public Node next(){
        while(cur!=null) {
            if (cur.left == null) {
                Node res = cur;
                cur = cur.right;
                return res;
            } else {
                Node predecessor = findPredecessor(cur);
                if (predecessor.right == null) {
                    //set predecessor to label
                    predecessor.right = cur;
                    //havent traverse left
                    cur = cur.left;

                } else {
                    Node res = cur;
                    cur = cur.right;
                    predecessor.right = null;
                    return res;
                }

            }
        }
        return null;
    }

    public Node findPredecessor(Node node){
        Node cur = node.left;
        if(cur==null) return null;
        while(cur.right!=null && cur.right!=node){
            cur=cur.right;
        }
        return cur;

    }

    public static void main(String[] args){
        InOrderIterator iterator = new InOrderIterator(Node.bst);
//        Result cur =null;
        while( iterator.hasNext() ){
            System.out.println(iterator.next().val);
        }
    }
}
