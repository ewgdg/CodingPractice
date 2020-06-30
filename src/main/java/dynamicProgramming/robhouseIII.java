package dynamicProgramming;

import java.util.HashMap;

//tree structure cannot rob adj vertices
public class robhouseIII {
    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }
    public int rob(TreeNode root) {
        HashMap<TreeNode,Integer> mem = new HashMap<>();
        return rob(root,mem);

    }
    static int call =0;//dp
    //final state method, think from final state, issue -> always need  mem
    public int rob(TreeNode root, HashMap<TreeNode,Integer > mem) {
        if(mem.containsKey(root)){
            call++;
            return mem.get(root);
        }

        if(root==null) return 0;

        //if rob current house
        int rob1 = root.val;
        if(root.right!=null){
            rob1+=rob(root.right.right,mem)+rob(root.right.left,mem);
        }
        if(root.left!=null){
            rob1+=rob(root.left.left,mem)+rob(root.left.right,mem);
        }


        //if not rob current
        int rob2=0;
        rob2+=rob(root.right,mem)+rob(root.left,mem);

        int val = Math.max(rob1,rob2);
        mem.put(root,val);
        return val;


    }



    public int greedySolution(TreeNode root){
        Result result =greedyHelper(root);
        return Math.max(result.max_without_current, result.max_rob_current);

    }

    class Result {
        public int max_rob_current;
        public int max_without_current;
        public Result(int v1, int v2){
            this.max_rob_current=v1;
            this.max_without_current=v2;
        }
    }
    public Result greedyHelper(TreeNode root){
        if(root==null) return new Result(0,0);

        Result left = greedyHelper(root.left);
        Result right = greedyHelper(root.right);

        //decide to rob current house
        int rob1 = root.val+ left.max_without_current + right.max_without_current;
        //decide not to
        int rob2 = Math.max(left.max_rob_current,left.max_without_current) + Math.max(right.max_rob_current,right.max_without_current);

        return new Result(rob1,rob2);

    }

    public static void main(String[] args){
        //3,2,3,null,3,null,1
        TreeNode root = new TreeNode(3);
        root.right=new TreeNode(2);
        root.left = new TreeNode(3);
        root.left.right = new TreeNode(3);
        root.left.left = new TreeNode(2);
        root.right.left = new TreeNode(1);
        root.right.right = new TreeNode(1);

        robhouseIII solver = new robhouseIII();

        System.out.println(solver.rob(root));
        System.out.println(solver.greedySolution(root));
        System.out.println(call);
    }
}
