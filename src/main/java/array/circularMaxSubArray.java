package array;

import java.util.ArrayDeque;
// import java.util.Arrays;
import java.util.Deque;

public class circularMaxSubArray {

    //method 1, next array
    public static int maxSubarraySumCircular1(int[] A) {
        //2 cases
        //case 1: one interval is the max
        int ans = A[0];
        ans = kadane(A);
//        System.out.println(ans);

        int n = A.length;
        //case 2: circular subArray with 2 interval [0,i] & [j,n-1]
//        for each interval1 need to find the max interval2 j>=i+2;
        //precompute the right max subarray end by n-1
        int[] rightSum = new int[n];
        int sum=0;
        for(int i=n-1;i>=0;i--){
            sum+=A[i];
            rightSum[i]=sum;
        }

        for(int i=n-2;i>=0;i--){
            rightSum[i]=Math.max(rightSum[i],rightSum[i+1]);
        }

        sum=0;
        for(int i=0;i<n-2;i++){
            sum+=A[i];
            ans = Math.max(ans,sum+rightSum[i+2]);//what happen if rightsum[i+1], doesnt matter included in kadane ans
        }
        return ans;

    }
    //for one interval non-circular, cannot control length of the subarray
    public static int kadane(int[] nums){
        //Kadane's algorithm
        int res = nums[0];//global max
        int cur = 0;//local max
        for(int i =0 ;i<nums.length;i++){
            cur = Math.max(cur+nums[i],nums[i]);
            res = Math.max(res,cur);
        }
        return res;
//        for x in A:
//        cur = x + max(cur, 0)
//        ans = max(ans, cur)
//        return ans
    }

    //prefixSum + monotone queue for min left prefixSum
    //able to control the length of subarray, set nums 2 long , view it as fixed length  2n num array
    public static int method2(int[] nums){
        int n = nums.length;
        int[] prefixSum = new int[n+n+1];
        for(int i=0;i<2*n;i++){//exclusive boundary
            prefixSum[i+1] = prefixSum[i]+nums[i%n];
        }

        int res= nums[0];
//        System.out.println(Arrays.toString(prefixSum));

        Deque<Integer> monoqueue= new ArrayDeque<>();
        monoqueue.addLast(0); //index not value

        for(int i =0 ;i<n+n;i++){
            if(i-monoqueue.peekFirst()+1>n){
                monoqueue.removeFirst();
            }

            res = Math.max(prefixSum[i+1]-prefixSum[monoqueue.peekFirst()],res);

            while(!monoqueue.isEmpty() && prefixSum[monoqueue.peekLast()]>=prefixSum[i+1]){
                monoqueue.removeLast();
            }
            monoqueue.addLast(i+1);
        }
        return res;



    }

    //method 3, if 2 interval then res = sum-min(middle interval)
    public static int method3(int[] nums){
        int S = 0;
        for (int x: nums)
            S += x;


        //case 1 :  1 interval
        int ans = kadane(nums);

        //case2 : 2 interval
        //find min interior interval
        //the idea is that since we use ans2 to track "2 interval" case, we need to keep at least element(0) and element(N-1) .
        // and so these two elements should not be removed from the array.
        int ans2 = nums[1];
        int cur = 0;
        for(int i =1 ;i<nums.length-1; i++){
            cur = Math.min(cur+nums[i],nums[i]);
            ans2 = Math.min(ans2, cur);
        }
        ans2 = S-ans2;


        return Math.max(ans, ans2);


    }



    public static void main(String[] args){

        System.out.println(maxSubarraySumCircular1(new int[]{5,-3,5}));
        System.out.println(method2(new int[]{5,-3,5}));
        System.out.println(method3(new int[]{5,-3,5}));
    }
}
