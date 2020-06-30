package DynamicProgramming;

import java.util.HashMap;
//Input: [1,2,3,1]
//rob 1 + 3 = 4
public class robHouseI {

    public int solution(int[] nums){
        HashMap<Integer, Integer> mem = new HashMap<>();
        return dfs(0,nums,mem);

    }

    //O(n)
    public int dfs(int index, int[] nums, HashMap<Integer,Integer> mem){
        if(mem.containsKey(index)) return mem.get(index); //overlapping of subproblem so dp.  optimal substructure -> divide and conquer

        if(index>=nums.length) return 0;

        //decide to rob current
        int rob1 = nums[index] + dfs(index+2, nums,mem);
        //not to
        int rob2 = dfs(index+1,nums,mem);

        int val = Math.max(rob1,rob2);
        mem.put(index, val);
        return val;
    }

    public int tabulation(int[] nums){
        //since we need to get res from prev and pprev, we need dp[-1], dp -2, which means the offset is 2 if index starts from 0
        int offset = 2;
        int[] dp = new int[nums.length+offset];
        // init
        dp[0]=0;dp[1]=0;
        for(int i =2 ; i<dp.length;i++){
            int index = i-offset;
            dp[i] = Math.max(dp[i-1], dp[i-2]+nums[index]);
        }
        return dp[dp.length-1];
    }

    //with space compression
    public int tabulation2(int[] nums){
        int prev = 0;
        int pprev =0;
        int res =0;
        for(int i =0; i< nums.length;i++){
            res = Math.max(pprev+nums[i],prev);
            pprev=prev;
            prev=res;
        }
        return res;
    }


    public int greedySol(int[] nums){
        int[] res = greedyHelper(nums,0);

        return Math.max(res[0],res[1]);


    }
    public int[] greedyHelper(int[] nums, int index){

        if(index>=nums.length){
            return new int[]{0,0};
        }

        int[] next = greedyHelper(nums,index+1);

        int rob1 = nums[index] + next[1];
        int rob2 = Math.max(next[0],next[1]);

        return new int[]{rob1,rob2};

    }


    public int greedyIterative(int[] nums){

        int prev_max_rob_prev=0;
        int prev_max_without_rob_prev=0;
        for(int i =0; i< nums.length; i++){
            int cur_rob = nums[i]+prev_max_without_rob_prev;
            int cur_without_rob = Math.max(prev_max_without_rob_prev,prev_max_rob_prev);

            prev_max_rob_prev= cur_rob;
            prev_max_without_rob_prev=cur_without_rob;
        }

        return Math.max(prev_max_rob_prev,prev_max_without_rob_prev);

    }


    public static void main(String[] args){
        int[] nums = new int[]{2,7,9,3,1};
        robHouseI solver =new robHouseI();
        System.out.println(solver.solution(nums));
        System.out.println(solver.greedySol(nums));
        System.out.println(solver.greedyIterative(nums));
    }
}
