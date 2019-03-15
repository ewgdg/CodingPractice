import java.util.HashMap;

public class burstballoon {
    //Input: [3,1,5,8]
    //Output: 167
    //Explanation: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
    //             coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167

    //get max


    //final state with order
    //if order doesnt matter we can divide the problem by decide whether or not the cur index is picked
    //but the problem required to find the proper ordering
    //with final state method
    //start to think what is the last index to pick(reversed order)
    //this will divide the problem into left and right sub. recursively solve them

    //the reason we start from lastly picked index is that each time we pick an index, the problem is reduced
    //at last the sub problem is a single elem, which is able to divide the problem
    //if decide the first pick elem, we are not able to divide the problem

    public static int helper(int[] nums, int start, int end, Integer[][] mem){
        if(start>end){
            return 0;
        }

        if(mem[start][end]!=null) return mem[start][end];
        int res=0;
        for(int i=start; i<=end; i++){
            //finally picked ballon
            int temp = get(nums,start-1)*nums[i]*get(nums,end+1)+ helper(nums,i+1,end,mem)+helper(nums,start,i-1,mem);

            res = Math.max(res,temp);
        }
        mem[start][end]=res;
        return res;
    }
    public static int get(int[] nums, int i){
        if(i<0) return 1;
        else if(i>=nums.length) return 1;
        return nums[i];
    }

    public static int tabulation(int[] nums){
        int n = nums.length;
        int[][] dp = new int[n][n];

        for(int size=1;size<=n;size++){
            //j<n j=i+size-1 //i is start , j is end
            for(int i = 0;i+size-1<n;i++){
                int j = i+size-1; int res=0;
                for(int p=i;p<=j;p++){
                    res = Math.max(res, get(nums,i-1)*nums[p]*get(nums,j+1) + (i>p-1?0:dp[i][p-1])+ (p+1>j?0:dp[p+1][j]) );
                }
                dp[i][j]=res;
            }
        }
        return dp[0][n-1];

    }

    public static int solve(int[] nums){
        int n = nums.length;
        return helper(nums,0,n-1,new Integer[n][n]);

    }
    public static void main(String[] args){
        int[] nums  = new int[]{3,1,5,8};
        System.out.println(solve(nums));
        System.out.println(tabulation(nums));
    }


}
