package Array;

import java.util.ArrayList;
// import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Permutation {
    //with bt we can do combination(order doesnt matter)
    //permutation is similar but order is matter

    //key is to know the unpicked candidate char pool

    //method1 to swap the order so that the we can get a sub problem of unpicked char pool before call the next level, best bc least extra space
    //method2 Use an extra boolean array " boolean[] used" or hashSet of index used to indicate whether the value is added to list. and test all char. but extra space
    //method3 extra space, build a hashMap<char,count> or hashSet<index> as char pool
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        helper(nums,ans,new ArrayList<Integer>(),0);
        return ans;
    }

    public void helper( int[] nums , List<List<Integer>> ans, List<Integer> list ,int begin   ){
        if(begin==nums.length){
            ans.add( list  );
        }



        for(int i=begin;i<nums.length;i++ ){
            nums[begin]= nums[i]+nums[begin] - (nums[i] = nums[begin]) ;
            List<Integer> new_list= new ArrayList<>(list);
            new_list.add(nums[begin]);

            helper(nums,ans,new_list,begin+1);

            nums[begin]= nums[i]+nums[begin] - (nums[i] = nums[begin]) ; //revert the change for previous call
        }

    }
    //with litter space optimized templist, less space consumed
    public void helper2( int[] nums , List<List<Integer>> ans, List<Integer> templist ,int begin   ){
        if(begin==nums.length){
            ans.add( new ArrayList<>(templist)  );
        }



        for(int i=begin;i<nums.length;i++ ){
            nums[begin]= nums[i]+nums[begin] - (nums[i] = nums[begin]) ;

            templist.add(nums[begin]);

            helper2(nums,ans,templist,begin+1);

            templist.remove(templist.size()-1);
            nums[begin]= nums[i]+nums[begin] - (nums[i] = nums[begin]) ; //revert the change for previous call
        }

    }
    //
//    extra boolean array " boolean[] used"
    //or just a HashSet for used index, index such that no dup
    public void helper3(int[] nums , List<List<Integer>> ans, List<Integer> templist , HashSet<Integer> chosen ){
        if(templist.size()==nums.length){
            ans.add( new ArrayList<>(templist)  );
        }
        for(int i=0;i<nums.length;i++){
            if(chosen.contains(i)){
               continue;
            }

            templist.add(nums[i]);
            chosen.add(i);

            helper3(nums,ans,templist,chosen);

            chosen.remove(i);
            templist.remove(templist.size()-1);

        }

    }
}
