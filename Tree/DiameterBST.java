public class DiameterBST {
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




}
