import java.util.List;

public class RootToLeafSumBT {


    public boolean sum(Node root, int sum, List<Integer> res){
        if(root==null){
            return false;
        }

        if(root.left==null && root.right==null){ //leaf node
            if(sum==root.val){
                res.add(root.val);
                return true;
            }else{
                return false;
            }
        }


        if(sum(root.left,sum-root.val,res)){
            res.add(root.val);
            return true;
        }
        if(sum(root.right,sum-root.val,res)){
            res.add(root.val);
            return true;
        }
        return false;
    }

}

//run test
// watcher
//              sum , left ,right ,...

//stack depth
//          1
//          2
//