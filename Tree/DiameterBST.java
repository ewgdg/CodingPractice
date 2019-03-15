public class DiameterBST {
    //The diameter of a binary tree is the length of the longest path between any two nodes in a tree.
    // This path may or may not pass through the root.
    int max =0;
    public int solution(Node root){
        max=0;

        maxDepth(root);
        return max;


    }

    public int maxDepth(Node root){
        if(root==null) return 0;

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);

        max= Math.max(max,left+right);

        return Math.max(left,right)+1;

    }

    //another similar method
    class Result{
        int depth;
        int diameter;
        public Result(int v1, int v2){
            depth=v1;
            diameter=v2;
        }
    }
    //need 2 info , depth & diameter of subtree
    public Result helper(Node root){
        if(root==null) return new Result(0,0);
        Result left = helper(root.left);
        Result right = helper(root.right);

        int diameter = left.depth+right.depth;
        diameter = Math.max(diameter,left.diameter);
        diameter = Math.max(diameter,right.diameter);

        int depth  = Math.max(left.depth,right.depth)+1;


        return new Result(depth,diameter);


    }



}
