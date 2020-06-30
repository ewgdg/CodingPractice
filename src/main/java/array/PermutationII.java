package array;

import java.util.*;

public class PermutationII {
    //Given a collection of numbers that might contain duplicates, return all possible unique permutations.
    //note the duplicates!!
    // use a HashSet for res to prevent dup, issue extra cost for examining the String if string is long, O n*m, m is length of string
    // use a HashSet of char of cur loop to prevent same char used for same loop , extra space O n*depth of recursion = n^2
    // sort+ chosen index(cannot swap bc order will changed) Set and prevent use same char as previous index-1 char for cur loop(not used i-1) ,  advantage is the res is in order .less extra space O n
    //sort+linked list??? linkedlist for fast remove
    //sort+swap??doenst work bc the swap will result in disorder, 0,0,0,1,2 ---》 swap --> 0,2,"0,1,0"

    //method 2
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums==null || nums.length==0) { return ans; }
        permute(ans, nums, 0);
        return ans;
    }

    private void permute(List<List<Integer>> ans, int[] nums, int index) {
        if (index == nums.length) {
            List<Integer> temp = new ArrayList<>();
            for (int num: nums) { temp.add(num); }
            ans.add(temp);
            return;
        }
        Set<Integer> appeared = new HashSet<>(); //a HashSet of char of cur loop to prevent same char used for same loop/subproblem
        for (int i=index; i<nums.length; ++i) {
            if (appeared.add(nums[i])) {
                swap(nums, index, i);
                permute(ans, nums, index+1);
                swap(nums, index, i);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int save = nums[i];
        nums[i] = nums[j];
        nums[j] = save;
    }

    //method3
    public List<List<Integer>> permuteUnique2(int[] nums){
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if(nums==null || nums.length==0) return res;
        Set<Integer> chosen = new HashSet<>();
        Arrays.sort(nums);

        helper(nums,chosen,new ArrayList<>(), res);
        return res;
    }

    public void helper(int[] nums, Set<Integer> chosen, List<Integer> tempList,  List<List<Integer>> res){
        if(tempList.size()==nums.length ){

            res.add(new ArrayList<>(tempList));
            return;
        }

        for(int i=0;i<nums.length;i++){
            if(chosen.contains(i) ) continue;
            if(i-1>=0 && nums[i]==nums[i-1] && !chosen.contains(i-1)) continue; //prevent repeating for the same sub prob, check !chosen.contains(i-1) to know if in the same sub prob/same candidate pool/same loop

            chosen.add(i);
            tempList.add(nums[i]);

            helper(nums,chosen,tempList,res);


            chosen.remove(i);
            tempList.remove(tempList.size()-1);


        }


    }

    //method sort + swap Wrong!!!!!!!!!!!!!!!!!
    public List<List<Integer>> permuteUniqueSSS(int[] nums){
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if(nums==null || nums.length==0) return res;
        Arrays.sort(nums);

        helper2(nums,new ArrayList<>(), 0,res);
        return res;
    }

    public void helper2(int[] nums, List<Integer> tempList,int index,  List<List<Integer>> res){
        if(tempList.size()==nums.length ){
//            System.out.println(Arrays.toString(nums));
            res.add(new ArrayList<>(tempList));
            return;
        }

//        int[] copy = Arrays.copyOf(nums,nums.length);
//        System.out.println(Arrays.toString(nums));
        for(int i=index;i<nums.length;i++){

            if(i>index && nums[i]==nums[i-1]) continue; //prevent repeating for the same sub prob

            tempList.add(nums[i]);
            swap(nums,i,index);

            helper2(nums,tempList,index+1,res);



            swap(nums,i,index);
//            assert(Arrays.equals(nums,copy));
            tempList.remove(tempList.size()-1);


        }


    }



    public static void main(String[] args){
        int[] nums = new int[] {0,1,0,0,9};

        PermutationII solver = new PermutationII();
        System.out.println(solver.permuteUnique(nums));
        System.out.println(solver.permuteUnique2(nums));
//        System.out.println(solver.permuteUniqueSSS(nums));
    }



}
