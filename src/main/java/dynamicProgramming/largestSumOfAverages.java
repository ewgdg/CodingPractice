package dynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;

public class largestSumOfAverages {
    //We partition a row of numbers A into at most K adjacent (non-empty) groups, then our score is the sum of the average of each group. What is the largest score we can achieve?
    //
    //Note that our partition must use every number in A, and that scores are not necessarily integers.

    //treat this problem as cut the array into k group.
    //if use final state method, need to record prevCut/startIndex of subprob, and decide curIndex whether to cut , complexity length*length*k same as back tracking,
    //but take more extra space!!!!!!!!!!!!!!!!!
    // bt + memo //better for tabulation!!!!!!!!!!!!!!!
    //like word break
    //dp
    //tabulation space compress
    //memoi easy to think


    public double solution_memoization(int[] A, int K){
        if(A.length<K) return -1;
        double[] prefixSum = new double[A.length];
        double sum=0;
        for(int i=0;i<A.length;i++){ // better prefixSum[i+1] = prefixSum[i]+nums[i];
            sum+=A[i];
            prefixSum[i]=sum;
        }

        HashMap<String, Double> mem = new HashMap<>();
//        return helper(A,prefixSum,K,0,mem);
//        return helper2(A,prefixSum,0,1,K,mem);
        return helper3(A,prefixSum,K,A.length-1,mem);
    }
    static int call=0;

    //better for space complexity and simpler tabulation
    public double helper(int[] nums,double[] prefixSum , int k , int startIndex, HashMap<String,Double> mem){
        String key = k+":"+startIndex;
//        call++;
        if(mem.containsKey(key)){ //using hashmap is pretty slow , use double[][] is way faster 80ms to 5ms
            call++;
            return mem.get(key);
        }
        //pruning check if enough elem for k
        //terminating
        if(startIndex>=nums.length){//&& k>1
            return 0;
        }
        if(k==1){//total k group = k-1 cut
            return getAvg(startIndex,nums.length-1,prefixSum,nums);
        }



        double res=0;
        for(int i=startIndex+1; i<nums.length;i++){
            double subRes = helper(nums,prefixSum,k-1,i,mem);
            double avg = getAvg(startIndex,i-1,prefixSum,nums);
            res = Math.max(res, subRes+avg);

        }
        mem.put(key,res);
        return res;

    }

    //final state method
    public double helper2(int[] nums,double[] prefixSum, int startIndex, int index, int k, HashMap<String,Double> mem){//index here represent the first/last(from tabulation) cut index of the cur subprob
        if(index>=nums.length) return 0;// or -1 ?? so impossible to pick it bc need find max , better to skip invalid call by pre check
        if(k==1){
            return getAvg(startIndex,nums.length-1,prefixSum,nums);
        }


        String key  =  startIndex+":"+index+":"+k;
        if(mem.containsKey(key)) return mem.get(key);

        //if cut at index
        double sub1  = helper2(nums,prefixSum, index, index+1,k-1,mem)+getAvg(startIndex,index-1,prefixSum,nums);
        //not cut here
        double sub2 = helper2(nums,prefixSum,startIndex,index+1,k,mem);
        double res = Math.max(sub1,sub2);
        mem.put(key,res);
        return res;

    }
    //bt from end index
    public double helper3(int[] nums,double[] prefixSum , int k , int endIndex, HashMap<String,Double> mem){
        //pruning check if enough elem for k
        //terminating
        if(endIndex<0){//&& k>1// early term if endIndex+1<k
            return 0;
        }
        if(k==1){//total k group = k-1 cut
            return getAvg(0,endIndex,prefixSum,nums);
        }

        String key = k+":"+endIndex;
//        call++;
        if(mem.containsKey(key)){ //using hashmap is pretty slow , use double[][] is way faster 80ms to 5ms
            call++;
            return mem.get(key);
        }




        double res=0;
        for(int i=0; i<endIndex;i++){
            double subRes = helper3(nums,prefixSum,k-1,i,mem);

            double avg = getAvg(i+1 , endIndex, prefixSum, nums);
            res = Math.max(res, subRes + avg);

        }

        mem.put(key,res);
        return res;

    }
    public double getAvg(int lo, int hi ,double[] prefixSum , int[] nums){
        return (prefixSum[hi]-prefixSum[lo]+nums[lo] ) / (double)(hi-lo+1);
    }

    public double tabulation2(int[] nums, int K){//converted from bt method
        //no space compression
        double[][] dp = new double[K+1][nums.length]; //dp[k][j] --> k group for subarray 0-j; //k relys on k-1 only -> compressed


        double[] prefixSum = new double[nums.length];
        double sum=0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
            prefixSum[i]=sum;
        }
        //init for k==1
        for(int i=0;i<nums.length;i++){
            dp[1][i] = getAvg(i,nums.length-1,prefixSum,nums);
        }
        for(int k=2;k<=K;k++){

            for(int i=0;i<nums.length;i++){ // j need at least k elem to be splited//can start from 0

                for(int j=i+1;j<nums.length;j++){ //subproblem dp[k]
                    dp[k][i] = Math.max( dp[k][i],getAvg(i,j-1,prefixSum,nums)+dp[k-1][j]) ;
                }

            }

        }
        return dp[K][0];

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
            for(int j=i-1;j<A.length;j++){ // j need at least k elem to be splited//can start from 0

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
        System.out.println(solver.tabulation2(A,k));
    }
}
