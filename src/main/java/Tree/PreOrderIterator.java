package Tree;

import java.util.Stack;

public class PreOrderIterator {
    //using stack,
    //benefit: no modification of tree, can check leaf or Not
    //see inorder iterator for morris traversal

    Stack<Node> stack;
    Node cur;
    Node root;

    public PreOrderIterator(Node root){
        this.root=root;
        this.cur= root;
        stack= new Stack<>();
        stack.add(cur);
    }

    public boolean hasNext(){
        return (!stack.isEmpty());
    }
    public Node next(){
        Node res = stack.pop();
        if(res.right!=null) stack.add(res.right);
        if(res.left!=null) stack.add(res.left);
        return res;
    }


    public static void main(String[] args){
        PreOrderIterator iterator = new PreOrderIterator(Node.tree);
        // Node cur =null;
        while(  iterator.hasNext()  ){
            System.out.println(iterator.next().val);
        }

    }
}
