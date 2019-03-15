import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class SerializationTree {
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }


    public static final String NULL = "#";
    public static final String splitter = " ";

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        //pre order == DFS
        StringBuilder sb = new StringBuilder();
        helper(root,sb);
        return sb.toString();
    }

    public void helper(TreeNode root, StringBuilder sb){
        if(root==null){
            sb.append(NULL).append(splitter);
            return;
        }

        else{
            sb.append(root.val);
            sb.append(splitter);

            helper(root.left,sb);
            helper(root.right,sb);
        }


    }


    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Queue<String> nodes = new LinkedList<>();
        nodes.addAll( Arrays.asList( data.split(splitter))  );
        return build(nodes);




    }


    public TreeNode build(Queue<String> nodes){
        String val = nodes.poll();
        if(val.equals(NULL)) return null;
        else{
            TreeNode node = new TreeNode(Integer.valueOf(val));
            node.left = build(nodes);
            node.right = build(nodes);
            return node;
        }

    }


// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
}
