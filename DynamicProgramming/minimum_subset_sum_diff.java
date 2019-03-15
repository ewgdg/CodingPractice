import java.util.HashMap;

public class minimum_subset_sum_diff {

    public static int solution(int[] nums){
        int total = 0;
        for(int num: nums){
            total+=num;
        }

        int target = total/2;

        HashMap<String,Boolean> mem = new HashMap<>();

        for(int i=target;i>=0;i--){
            if(sum(nums,i,nums.length-1,mem)){
                return (total-i)-i;
            }
        }
        return total;

    }


    public static boolean sum(int[] nums, int target, int end_index, HashMap<String,Boolean> mem ){
        //mem
        String key = target+":"+end_index ;
        if(mem.containsKey(key)) return mem.get(key);

        //terminating
        if(target<0) return false;
        else if(target==0) return true;
        else if(end_index<0) return false;

        boolean ret2 = sum(nums,target-nums[end_index],end_index-1,mem );
//        if(ret2) return true;

        boolean ret1= false;
        if(!ret2) ret1 = sum(nums,target,end_index-1,mem);
//        if(ret1) return true;
//        boolean ret2 = sum(nums,target-nums[end_index],end_index-1,mem );

        mem.put(key,ret1||ret2);

        return ret1||ret2;

        //dp tabulation
    //f[0] = true;
    //        for (int i = 0; i < l; i ++){
    //            for (int j = tar; j > 0; j --){
    //                if (j >= nums[i]) f[j] = f[j] || f[j - nums[i]];
    //            }
    //        }


        // another dp
//        for(int i =0;i<=l;i++) dp[i][0]=true;
//        for(int i = 1; i < dp.length; i++) {//i is size of subprob here
//            for(int j = 1; j < dp[0].length; j++) {
//                int index = i-1;
//                if(j < nums[index]) {
//                    dp[i][j] = dp[i - 1][j];
//                } else {
//                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[index]];
//                }
//            }
//        }
    }
////with space compression?
    public static boolean sum_dp(int[] nums, int target){
        int l = nums.length;
        boolean[] f = new boolean[target+1];
        f[0] = true;
        for (int i = 0; i < l; i ++){
            for (int j = target; j > 0; j --){ //decreasing bc we only rely on previous one dp, not summing previous all, we only testing cur num which can be used only once
                //otherwise need to use prev[]/next_dp[](better) to store f[] from previous iter  f[j] = prev[j] || prev[j - nums[i]];
                if (j >= nums[i]) f[j] = f[j] || f[j - nums[i]];
            }
        }
        return f[target];
    }


    public static void main(String[] args){
        boolean a =true;
        System.out.println(solution(new int[]{1,5,2,3,8,-1,-10}));
        System.out.println(sum_dp(new int[]{1,5,3},7));
    }
}
