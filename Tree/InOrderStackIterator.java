import java.util.Stack;

public class InOrderStackIterator {
    Stack<Node> stack;
    Node cur,root;

    public InOrderStackIterator(Node root){
        this.cur=root;this.root=root;
        stack= new Stack<>();
//        stack.add(cur);
    }

    public boolean hasNext(){
        return (cur!=null||!stack.isEmpty());
    }

    public Node next(){
        //assume called hasNext before call next??//or while(hasNext()){//all code}
        while(cur!=null){
            stack.add(cur);
            cur=cur.left;
        }
        cur = stack.pop();
        Node res = cur;
        cur=cur.right;
        return res;

    }

}
