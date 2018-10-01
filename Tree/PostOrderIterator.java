import java.util.Stack;

public class PostOrderIterator {

    Stack<Node> stack;
    Node cur;

    public PostOrderIterator(Node root){
        cur=root;
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

    public static void main(String[] args){
        PostOrderIterator iterator = new PostOrderIterator(Node.tree);

        while(iterator.hasNext()){
            System.out.println(iterator.next().val);
        }

    }
}
