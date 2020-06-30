package Tree;

import java.util.Deque;
import java.util.LinkedList;

public class BinaryTreeZigZagTraverse {

    public static void solution(Node root){
        Deque<Node> deque = new LinkedList<>();
        deque.add(root);

        boolean zig=true;
        while(!deque.isEmpty()){
            int size  = deque.size();

            if(zig) {
                for (int i =0; i<size; i++) {
                    Node cur = deque.removeFirst();
                    if(cur!=null) {
                        System.out.println(cur.val);
                        deque.addLast(cur.left);
                        deque.addLast(cur.right);
                    }

                }
            }else{
                for (int i =0; i<size; i++) {
                    Node cur = deque.removeLast();
                    if(cur!=null) {
                        System.out.println(cur.val);
                        deque.addFirst(cur.right);
                        deque.addFirst(cur.left);
                    }
                }
            }
            zig=!zig;
        }

        return;
    }


    public static void main(String[] args){
        solution(Node.tree);
    }

}
