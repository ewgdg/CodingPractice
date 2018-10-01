import java.util.Arrays;
import java.util.HashMap;

public class largestSumOfAverages {
    //We partition a row of numbers A into at most K adjacent (non-empty) groups, then our score is the sum of the average of each group. What is the largest score we can achieve?
    //
    //Note that our partition must use every number in A, and that scores are not necessarily integers.

    //treat this problem as cut the array into k group. // bt + memo
    //like word break
    //dp
    //tabulation space compress
    //memoi easy to think


    public double solution_memoization(int[] A, int K){
        double[] prefixSum = new double[A.length];
        double sum=0;
        for(int i=0;i<A.length;i++){
            sum+=A[i];
            prefixSum[i]=sum;
        }

        HashMap<String, Double> mem = new HashMap<>();
        return helper(A,prefixSum,K,0,mem);
    }
    static int call=0;
    public double helper(int[] nums,double[] prefixSum , int k , int index, HashMap<String,Double> mem){
        String key = k+":"+index;
//        call++;
        if(mem.containsKey(key)){ //using hashmap is pretty slow , use double[][] is way faster 80ms to 5ms
            call++;
            return mem.get(key);
        }
        //pruning check if enough elem for k
        //terminating
        if(index>=nums.length){
            return 0;
        }
        if(k==1){
            return getAvg(index,nums.length-1,prefixSum,nums);
        }

        double res=0;
        for(int i=index; i<nums.length;i++){
            double subRes = helper(nums,prefixSum,k-1,i+1,mem);
            double avg = getAvg(index,i,prefixSum,nums);
            res = Math.max(res, subRes+avg);

        }
        mem.put(key,res);
        return res;

    }


    public double getAvg(int lo, int hi ,double[] prefixSum , int[] nums){
        return (prefixSum[hi]-prefixSum[lo]+nums[lo] ) / (double)(hi-lo+1);
    }



    public double tabulation(int[] A, int K){
        double[] dp = new double[A.length]; //dp[k][j] --> k group for subarray 0-j; //k relys on k-1 only -> compressed


        double[] prefixSum = new double[A.length];
        double sum=0;
        for(int i=0;i<A.length;i++){
            sum+=A[i];
            prefixSum[i]=sum;
        }
        //init for k==1
        for(int i=0;i<A.length;i++){
            dp[i] = getAvg(0,i,prefixSum,A);
        }

        System.out.println(Arrays.toString(dp) );
        for(int i=2;i<=K;i++){

            double[] next = new double[A.length];
            for(int j=i-1;j<A.length;j++){ // j need at least k elem to be splited

                for(int k=i-2;k<j;k++){ //subproblem dp[k]
                    next[j] = Math.max( next[j],getAvg(k+1,j,prefixSum,A)+dp[k]) ;
                }

            }
            dp=next;
            System.out.println(Arrays.toString(dp) );
        }


        return dp[A.length-1];
    }


    public static void main(String[] args){
        largestSumOfAverages solver = new largestSumOfAverages();

        int[] A = new int[]{4,1,7,5,6,2,3};
        int k = 4;

        System.out.println(solver.solution_memoization(A,k));
        System.out.println(call);
        System.out.println(solver.tabulation(A,k));
    }
}
