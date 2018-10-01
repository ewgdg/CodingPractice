public class HeightBST {




    public int getHeight(Node root){
        if(root==null){
            return 0;
        }


        int left = getHeight(root.left);
        int right = getHeight(root.right);

        return Math.max(left,right)+1;

    }

}
