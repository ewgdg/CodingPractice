package tree;
public class CountUnivalSubTree {
    //a unival subtree is tree with all same vals

    public int solve(Node root){
        return helper(root).count;

    }

    class Tuple{
        boolean isUnival;
        int count;
        public Tuple(boolean isUnival, int count){
            this.isUnival=isUnival;
            this.count=count;
        }

    }

    public Tuple helper(Node root){
        if(root==null){
            return new Tuple(true,0);
        }

        Tuple left =helper(root.left);
        Tuple right = helper(root.right);

        boolean isUnival=true;
        if(root.left!=null && root.left.val!=root.val){
            isUnival=false;
        }else if(root.right!=null && root.right.val!=root.val){
            isUnival=false;
        }else if(!left.isUnival||!right.isUnival){
            isUnival=false;
        }
        if(isUnival){
            return new Tuple(true,left.count+right.count+1);
        }else{
            return new Tuple(false,left.count+right.count);
        }

    }

    //follow up
    //multiple children
    //semi-unival tree , 2 unique vals

    public static void main(String[] args){
        Node root = new Node(1);
        root.left=new Node(1);
        root.right = new Node(1);
        root.right.right = new Node(2);


        CountUnivalSubTree solver =new CountUnivalSubTree();


        System.out.println(solver.solve(root));

    }
}
