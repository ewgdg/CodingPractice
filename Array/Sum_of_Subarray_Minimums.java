import java.util.*;

public class Sum_of_Subarray_Minimums {
//Given an array of integers A, find the sum of min(B), where B ranges over every (contiguous) subarray of A.
//
//Since the answer may be large, return the answer modulo 10^9 + 7.
//
//
//
//Example 1:
//
//Input: [3,1,2,4]
//Output: 17
//Explanation: Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
//Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.  Sum is 17.

    //naive for all subarray[i,j] find minimum, if keep min while iter, O n2

    //method2 observe the pattern
    //for each j maintain a monotone stack
    //replace the repetition by count


    public static int solution(int[] nums){

        Deque<Node> stack = new LinkedList<>();
        long res=0;
        long curSum=0;
        int totSize=0;
        // long fill =0;
        for(int i=0;i<nums.length;i++){
            int cur = nums[i];
            while(!stack.isEmpty()&& cur<stack.peekLast().num){
                Node node = stack.removeLast();
                curSum-=node.num*node.size;
                totSize-=node.size;
            }


            // stack.addLast(cur);
            // curSum+=cur;

            int size = i+1-totSize;
            stack.addLast(new Node(cur,size));
            curSum+=cur*size;
            totSize+=size;


            // if(!stack.isEmpty() && (stack.size()+fill)<i+1 ){
            //     long extra_fill = i+1-stack.size()-fill;
            //     int filler = stack.getLast();
            //     curSum +=  extra_fill*filler;
            //     fill+=extra_fill;
            // }
            res += curSum;
        }
        return (int)(res%(1e9+7));

    }
    static class Node{
        int num;
        int size;
        public Node(int num, int size){
            this.num=num;
            this.size =size;
        }
    }


    //method 3:

//    What can monotonous increase stack do?
//            (1) find the previous less element of each element in a vector with O(n) time:

    //find PLE to denote Previous Less Element.
    //find NLE to denote Next Less Element.  2 method , read from backwards or:
//for(int i = 0; i < A.size(); i++){
//        while(!in_stk.empty() && A[in_stk.top()] > A[i]){
//            auto x = in_stk.top(); in_stk.pop();
//            next_less[x] = i;
//        }
//        in_stk.push(i);
//    }
//    How many subarrays with 3 being its minimum value? #(PLE+NLE)
    //res = sum (element *(PLE+NLE))

    //The last thing that needs to be mentioned for handling duplicate elements:
    //Method: Set strict less and non-strict less(less than or equal to) for finding NLE and PLE respectively. The order doesn't matter.
    //to avoid duplicate count

    //res = sum(A[i] * f(i))
    //where f(i) is the number of subarrays,
    //in which A[i] is the minimum.
    //
    //To get f(i), we need to find out:
    //left[i], the length of strict bigger numbers on the left of A[i],
    //right[i], the length of bigger numbers on the right of A[i].
    //
    //Then,
    //left[i] + 1 equals to
    //the number of subarray ending with A[i],
    //and A[i] is single minimum.
    //
    //right[i] + 1 equals to
    //the number of subarray starting with A[i],
    //and A[i] is the first minimum.
    //
    //Finally f(i) = (left[i] + 1) * (right[i] + 1)
    //This process can be optimized to one pass using one stack in total.//

    public static int solve2(int[] nums){
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        int n = nums.length;
        int res =0;
        int mod = (int)1e9+7;

        int[] prev = new int[n];
        Arrays.fill(prev,-1);
        int[] next = new int[n];
        Arrays.fill(next,n);

        for(int i =0 ;i<n ;i++){
            while(!stack1.isEmpty()&& nums[stack1.peek()]>nums[i] ){
                next[stack1.pop()]=i;
            }
            prev[i]=stack1.isEmpty()?-1:stack1.peek();
            stack1.add(i);
        }

        for(int i =0 ;i<n ;i++){
            res += (nums[i]*(i-prev[i])*(next[i]-i))%mod;
        }
        return res;

    }

    public static int solve3(int[] nums){
        Stack<Integer> stack1 = new Stack<>();

        int n = nums.length;
        int res =0;
        int mod = (int)1e9+7;



        for(int i =0 ;i<n ;i++){
            while(!stack1.isEmpty()&& nums[stack1.peek()]>nums[i] ){
                int mid = stack1.pop();
                int prev =stack1.isEmpty()?-1: stack1.peek();
                res += nums[mid]*(mid-prev)*(i-mid);
            }

            stack1.add(i);
        }

        while(!stack1.isEmpty()){
            int mid = stack1.pop();
            int prev= stack1.isEmpty()?-1: stack1.peek();
            res += nums[mid]*(mid-prev)*(n-mid);
        }
        return res;

    }
    public static void main(String[] args){
        System.out.println(solution(new int[]{
                85,93,93,90
        }));
        System.out.println(solve2(new int[]{
                85,93,93,90
        }));
        System.out.println(solve3(new int[]{
                85,93,93,90
        }));
    }

}
