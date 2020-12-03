package tree;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class PostOrderIterator {

    Stack<Node> stack;
    Node cur,root;

    public PostOrderIterator(Node root){
        cur=root; this.root=root;
        stack= new Stack<>();
    }


    public boolean hasNext(){
        return (!stack.isEmpty() || cur!=null);
    }

    public Node next(){
        while(hasNext()){
            while(cur!=null){
                if(cur.right!=null) stack.add(cur.right);//need to know if right child has been explored
                stack.add(cur);
                cur=cur.left;
            }

            cur = stack.pop();
            if(!stack.isEmpty() && cur.right==stack.peek()){ //must check stack empty before do peek() or pop()
                //right havent been explored
                Node temp = cur;
                cur=stack.pop();
                stack.add(temp);
            }else{
                //do something
                Node res = cur;
                cur=null;
                return res;
            }


        }
        return null;

    }
    //post order similar method with seperated stack for right children
    public static void postOrder(Node root) {
        Node cur = root;
        Stack<Node> stack = new Stack<>();
        Stack<Node> right = new Stack<>();//indicate whether we explore right child
        // boolean first =true;
        while(!stack.isEmpty()||cur!=null){
            while(cur!=null){
                stack.add(cur);
                if(cur.right!=null) right.add(cur.right);//????need to check if right is null first, seems doesn't matter to the result
                cur=cur.left;

            }
            cur=  stack.peek();
            if(!right.isEmpty() && right.peek()==cur.right){
                cur=right.pop();
            }else{
                stack.pop();
                // if(!first){
                //     System.out.print(" ");

                // }else{
                //     first=false;
                // }
                System.out.print(cur);
                cur = null;
            }


        }

    }

    //we can actually use a tovisit set to record whether or not we explored the node.   
    public static void postOrder2(Node root) {
        Node cur = root;
        Stack<Node> stack = new Stack<>();
        Set<Node> tovisit = new HashSet<>();//indicate whether we explore right child

        while(!stack.isEmpty()||cur!=null){
            while(cur!=null){
                stack.add(cur);
                // tovisit.add(cur);
                if(cur.right!=null) tovisit.add(cur.right);
                cur=cur.left;

            }
            cur=  stack.peek();
            if(!tovisit.isEmpty() && tovisit.contains(cur.right) ){
                cur=cur.right;
            }else{
                stack.pop();
                tovisit.remove(cur);
                
                System.out.print(cur);
                cur = null;
            }


        }

    }



    public static void main(String[] args){
        PostOrderIterator iterator = new PostOrderIterator(Node.tree);

        while(iterator.hasNext()){
            System.out.println(iterator.next().val);
        }

    }
}
