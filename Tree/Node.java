import java.util.LinkedList;
import java.util.Queue;

public class Node {

    public int val;
    public Node left;
    public Node right;
    public Node next;
    public Node(int val){
        this.val=val;
    }

    public static void printDoubleLinkedList(Node node){
        Node cur=node;
        do{
            System.out.println(cur.val);
            cur=cur.right;
        }while(cur!=node);

    }


    public static Node insert(Node root, int val){
        if(root==null){
            return new Node(val);
        }

        if(root.val>val){
            root.left=insert(root.left,val);
        }else{
            root.right=insert(root.right,val);
        }
        return root;
    }

    public static void print(Node root){
        Queue<Node> front = new LinkedList<>();
        front.add(root);
        StringBuilder line = new StringBuilder();
        while(!front.isEmpty()){

            int size = front.size();
            for(int i=0;i<size;i++) {
                Node cur = front.poll();
                if(cur!=null) {
                    line.append(cur.val);
                    if (i < size)
                        line.append(" ");
                    front.add(cur.left);
                    front.add(cur.right);
                }else{
                    line.append("n ");
                }
            }
            System.out.println(line.toString());
            line = new StringBuilder();
        }
        return;

    }


    static Node tree;
    static {
        tree= new Node(1);
        //    1
        //   /\
        //  0  4
        //  /  /\
        //10  2  5
        //    \
        //     3
        tree.left = new Node(0);
        tree.left.left = new Node(10);//wrong node
        tree.right = new Node(4);
        tree.right.left = new Node(2);
        tree.right.right = new Node(5);
        tree.right.left.right = new Node(3);
    }

    static Node bst;
    static{
        bst =  new Node(5);
        bst.left = new Node(0);
        bst.left.right = new Node(1);//wrong node
        bst.right = new Node(9);
        bst.right.left = new Node(6);
        bst.right.right = new Node(10);
        Node.insert(bst,11);
    }
}
