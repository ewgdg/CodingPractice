import java.util.HashMap;

public class PredictWinner {


    public boolean PredictTheWinner(int[] nums) {
        // HashMap<String,Integer> mem = new HashMap<>();
        // return helper(nums,0,nums.length-1,mem)>=0;
        return tabulation(nums)>=0;
        // return minmax(nums,0,nums.length-1,0,1)>=0;
        // return alphaBeta(nums,0,nums.length-1,0,1,Integer.MIN_VALUE,Integer.MAX_VALUE)>=0;
    }

    public int tabulation(int[] nums){
        int[] dp= new int[nums.length]; //space compression reduce from 2d to 1d
        //dp[i][j] represent max from subarray i,j. compressed j so that j is always i+size-1
        int n = nums.length;
        //init
        for(int i=0;i<n;i++){
            dp[i] = nums[i];
        }
        for(int size=2;size<= n; size++){
            for(int i=0;i+size-1<n;i++){
                dp[i]= Math.max( nums[i+size-1]-dp[i], nums[i]-dp[i+1]);
                //nums[j]-dp[i] || nums[i]-dp[i+1]
                //nums[j] - dp[i][j-1] || nums[i] - dp[i+1][j]
            }
        }
        return dp[0];
    }
    public int helper(int[] nums, int lo , int hi, HashMap<String,Integer> mem){
        String key = lo+":"+hi;
        if(mem.containsKey(key)) return mem.get(key);

        if(lo>hi) return 0;
        if(lo==hi) return nums[lo];
        //assume optimal opponent
        int res = 0;
        //option 1, pick lo
        int res1 = nums[lo]-helper(nums,lo+1,hi,mem);
        int res2 = nums[hi]-helper(nums,lo,hi-1,mem);

        res = Math.max(res1,res2);
        mem.put(key,res);
        return res;
    }

    public int minmax(int[] nums, int lo , int hi, int val,int player){
        if(lo>hi) return 0;
        int sign=1;
        if(player!=1) sign = -1;
        if(lo==hi) return nums[lo]*sign+val;
        //assume optimal opponent
        int res = 0;
        int res1 = minmax(nums,lo+1,hi,val+nums[lo]*sign,player^1);
        int res2 = minmax(nums,lo,hi-1,val+nums[hi]*sign,player^1);
        if(player==1){
            res = Math.max(res1,res2);
        }else{
            res = Math.min(res1,res2);
        }

        return res;
    }

    public int alphaBeta(int[] nums, int lo , int hi, int val,int player, int alpha, int beta){
        if(lo>hi) return 0;
        int sign=1;
        if(player!=1) sign = -1;
        if(lo==hi) return nums[lo]*sign+val;

        int res = 0;
        if(player==1){
            int res1 = alphaBeta(nums,lo+1,hi,val+nums[lo]*sign,player^1,alpha,beta);
            if(res1>alpha){
                alpha=res1;
            }
            if(alpha>=beta) return alpha;
            int res2 = alphaBeta(nums,lo,hi-1,val+nums[hi]*sign,player^1,alpha,beta);
            if(res2>alpha){
                alpha=res2;
            }
            return alpha;
        }else{
            int res1 = alphaBeta(nums,lo+1,hi,val+nums[lo]*sign,player^1,alpha,beta);
            if(res1<beta){
                beta=res1;
            }
            if(alpha>=beta) return beta;
            int res2 = alphaBeta(nums,lo,hi-1,val+nums[hi]*sign,player^1,alpha,beta);
            if(res2<beta){
                beta=res2;
            }
            return beta;

        }


    }
}
