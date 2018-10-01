import java.util.LinkedList;
import java.util.Queue;

public class LevelLink {
    /*
    1
    /\
   2->3
  /   \
  4 -> 5

     */

    //without extra space
    public Node solution(Node root){
        Node parent=root;
        while(parent!=null){

            Tuple start = getNext(parent,0);
            Node child = start.child;
            parent = start.parent;
            Node nextLevel = child;
            int childId =start.id;

            while(child!=null){
                Tuple next=  getNext(parent,childId);
                child.next = next.child;
                System.out.println(child.val + "->" + (child.next==null?"null":child.next.val) );
                child = child.next;

                childId=next.id;

                parent = next.parent;

            }
            parent= nextLevel;
        }

        return root;
    }

    class Tuple{
        Node child;
        Node parent;
        int id;
        public Tuple(Node child, Node parent, int id){
            this.child=child;
            this.parent= parent;
            this.id=id;
        }
    }

    public Tuple getNext(Node parent,int childiId){
        while(parent!=null) {

            while(childiId<2) {
                if (childiId==0 && parent.left != null) {
                    return new Tuple(parent.left, parent,childiId+1);
                }
                else if (childiId==1&& parent.right != null) {
                    return new Tuple(parent.right, parent,childiId+1);
                }
                childiId++;
            }
            //reset
            parent= parent.next;
            childiId=0;
        }
        return new Tuple(null,null,0);
    }


    public Node solveBST(Node root){
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){

            int size = queue.size();
            Node prev= null;
            for(int i =0 ; i< size; i++) {

                Node cur = queue.poll();
                if (prev != null && cur!=null) {
                    System.out.println(prev.val + "->" + (cur==null?"null":cur.val) );
                    prev.next=cur;
                }
                if(cur!=null){
                    prev =cur;
                    queue.add(cur.left);
                    queue.add(cur.right);
                }
                //if last non-null elem
                if(i==size-1 && prev!=null){
                    prev.next=null;
                    System.out.println(prev.val + "->" + "null" );
                }
            }

        }
        return root;

    }

    public static void main(String[] args){
        Node.print(Node.tree);
        LevelLink solver =new LevelLink();
//        Node node = solver.solution(Node.tree);
//        Node.print(node);

        solver.solveBST(Node.tree);


    }
}
