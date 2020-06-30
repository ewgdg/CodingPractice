package tree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindDuplicateSubtrees {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        next_id=1;
        List<TreeNode> res =new ArrayList<>();
        numbering(new HashMap<>(),new HashMap<>(),res,root);
        return res;
    }

    int next_id;
    public int numbering(HashMap<String,Integer> id_map, HashMap<Integer,Integer> counter,List<TreeNode> res ,TreeNode cur ){//assign id to each subtree

        if(cur==null){
            return 0;
        }
        //post order
        int left_id = numbering(id_map,counter,res,cur.left);
        int right_id  = numbering(id_map,counter,res,cur.right);
        String key =  cur.val + ":" + left_id+ ":" +right_id;

        int id =0;
        if(id_map.containsKey(key)){
            id = id_map.get(key);
        }else{
            id = next_id;
            next_id++;
            id_map.put(key,id);
        }

        counter.put(id, counter.getOrDefault(id,0)+1);
        if(counter.get(id)==2){//first time to see the dup
            res.add(cur);
        }

        return id;
    }


}
