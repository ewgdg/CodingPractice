package Tree.SegmentTree;

public class RMQ_DP {

    //dp[i][j] =  min at range [i,j]
    //dp[i][j] = min dp[i][j-1] , nums[j]

    int[][] dp;

    //disadv : n^2 for space , wont work for large amount

    //O n^2
    public void preprocess(int[] nums){
        int n= nums.length;
        dp  = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=i+1; j<n;j++){
                dp[i][j] = Math.min(dp[i][j-1],nums[j]);
            }
        }

    }

    //O 1 for query
    public int rmq (int i, int j){
        return dp[i][j];
    }
}
