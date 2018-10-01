import java.util.Stack;

public class TreeToDoubleLinkList {
    class Node{
        public int val;
        public Node left;
        public Node right;
        public Node(int val){
            this.val=val;
        }

    }


    public Node concatenate(Node a, Node b){
        if(a==null) return b;
        if(b==null) return a;

        Node aENd = a.left;
        Node bEnd = b.left;

        aENd.right = b;
        b.left=a;

        a.left=bEnd;
        bEnd.right=a;


        return a;

    }

    public Node  convertToList(Node root){
        if(root==null){
            return root;
        }


        Node leftList = convertToList(root.left);
        Node rightList = convertToList(root.right);

        //make root self a circular linked list
        root.left=root;
        root.right=root;

        root = concatenate(leftList,root);
        root = concatenate(root,rightList);

        return root;


    }


    //preOrder to list //same as previous one
    public Node toListInOrder(Node root){
        Stack<Node> stack = new Stack<>();

        Node cur = root;
        Node prev = null;
        Node first =null;
        while(cur!=null || !stack.isEmpty()){
            while(cur!=null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if(first==null) first=cur;
            if(prev!=null) {
                prev.right = cur;
                cur.left=prev;
            }


            prev=cur;
            cur = cur.right;
        }

        if(prev!=null&&first!=null){
            first.left=prev;
            prev.right=first;
        }
        return first;
//        if(root==null){
//            return null;
//        }
//
//        Node res = root;
//        Node right_child = root.right;
//        Node left = toListInOrder(root.left);
//        Node right = toListInOrder(right_child);
//
//        if(left != null){
//            Node leftEnd = left.left;
//            leftEnd.right = root;
//            root.left=leftEnd;
//            res = left;
//
//            res.left=root;
//            root.right=res;
//        }
//
//
//
//        if(right!=null){
//            Node rightEnd =right.left;
//
//            root.right = right;
//            right.left=root;
//
//
//            res.left=rightEnd;
//            rightEnd.right=res;
//        }
//
//        //make a self circular
//        if(left==null && right == null){
//            res.left=res;
//            res.right=res;
//        }
//        return res;

    }

    public Node toListMorrisInOrder(Node root){

        Node cur = root;
        Node prev= null;
        Node first = null;

        while(cur!=null){
            if(cur.left==null){
                if(first==null) first=cur;
                if(prev!=null){
                    prev.right=cur;
                    cur.left=prev;
                }
                prev=cur;
                cur=cur.right;

            }else {
                Node predecessor = findPredecessor(cur);
                if(predecessor.right==null){//not yet left subtree
                    predecessor.right=cur;
                    cur=cur.left;

                }else{
                    if(first==null) first=cur;
                    predecessor.right=null;
                    if(prev!=null){
                        prev.right=cur;
                        cur.left=prev;
                    }
                    prev=cur;
                    cur=cur.right;


                }




            }

        }

        first.left=prev;
        prev.right=first;
        return first;


    }

    public Node findPredecessor(Node node){
        Node cur = node.left;
        while(cur.right!=null && cur.right!=node){
            cur=cur.right;
        }
        return cur;
    }

    public void print(Node node){
        Node cur=node;
        do{
            System.out.println(cur.val);
            cur=cur.right;
        }while(cur!=node);

    }


    public static void main( String[] args){



        TreeToDoubleLinkList solver = new TreeToDoubleLinkList();

        //1 0 4 2 3
        Node root =  solver.new Node(0);
        root.left = solver.new Node(1);
        root.right = solver.new Node(2);
        root.right.left = solver.new Node(4);
        root.right.right = solver.new Node(3);


//        Node l = solver.convertToList(root);
//        Node l = solver.toListInOrder(root);
        Node l = solver.toListMorrisInOrder(root);
        solver.print(l);
    }
}
