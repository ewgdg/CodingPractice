package tree.segmentTree;

public class RMQ_SparseTable {

    //dp[i][j] -> min at range [i,i+2^j)
    //dp[i][j] = min( dp[i][j-1] , dp[i+2^(j-1)][j-1] )

    int[][] dp;

    public void preprocess(int[] nums){
        int n =  nums.length;
        int max_log = 31- Integer.numberOfLeadingZeros(n); //32 -1 =31 bc index based  0
//        System.out.println(max_log);
        dp = new int[n][max_log];

        for(int i =0;i<n;i++){
            dp[i][0]=nums[i];
        }

        for(int j=1; (1<<j)<n ;j++   ){
            for(int i =0;i<n;i++){
                    if( (i+(1<<(j-1) )) >=n  )
                        dp[i][j]=dp[i][j-1];
                    else
                        dp[i][j]  =  Math.min (dp[i][j-1] , dp[i+(1<<(j-1) )][j-1]) ;


            }
        }


    }

    public int rmq(int i, int j){
        int len = j-i+1;
        int log = 0;//31-Integer.numberOfLeadingZeros(len); //log2(len)
        while( (1<<log)<=len   ) log++;
        log--;

        return Math.min(dp[i][log],dp[j+1-(1<<log)][log]) ;


    }

    public static void main(String[] args){
        RMQ_SparseTable solver =new RMQ_SparseTable();

        int[] nums = new int[]{ 3, 1, 5, -9, 8, 2, 4, 7};
        solver.preprocess(nums);
        System.out.println(solver.rmq(1,4));
        System.out.println(solver.rmq(0,4));
        System.out.println(solver.rmq(5,7));


    }



}
