package Array;

import java.util.Arrays;

public class binarySearch {
    public static int solution(int[] nums, int target){
        int left =0;
        int right = nums.length-1;

        while(left<=right){ // <= to recheck
            int mid = left+(right-left)/2;
            if(nums[mid]<target){
                left = mid+1;
            }else if(nums[mid]==target){
                return mid;

            }else{
                right = mid-1;
            }

        }
        return  left;//left for larger val, right for smaller val
    }

    //(1) 写一个Binary Search算法。done
    //(2) 用你刚才写的算法在一个未排序的数组（假设元素没有重复）中查找数字，那么哪些数字是在数组中的但不会被找到？
    public static void checkRange(int[] nums, int left, int right, int lo, int hi, Range[] ranges){
        if(left>right) return;
        int mid = left+(right-left)/2;
        if(ranges[mid]==null) ranges[mid]=new Range();
        Range range = ranges[mid];
        range.lo = lo; //cannot use math.max() , b/c we set range once without updating it
        range.hi = hi;



        checkRange(nums,mid+1,right, Math.max(lo,nums[mid]),hi,ranges); //update bound here, b/c we need more restrictions for next num
        checkRange(nums,left,mid-1,lo,Math.min(hi,nums[mid]),ranges);
    }

    public static boolean[] checkValid(int[] nums){
        boolean[] res = new boolean[nums.length];
        Range[] ranges = new Range[nums.length];
        checkRange(nums,0,nums.length-1,Integer.MIN_VALUE, Integer.MAX_VALUE,ranges);
        for(int i =0;i<nums.length;i++){
            if(ranges[i]!=null && nums[i]<=ranges[i].hi&&nums[i]>=ranges[i].lo){
                res[i]=true;
            }
        }
        return res;

    }

    static class Range{
        public int lo;
        public int hi;
    }


    //combined method
    public static boolean[] checkValid2(int[] nums){
        boolean[] res = new boolean[nums.length];
        checkValid2_helper(nums,0,nums.length-1,Integer.MIN_VALUE,Integer.MAX_VALUE,res);
        return res;
    }
    public static void checkValid2_helper(int[] nums,int left, int right, int lo, int hi, boolean[] res){
        if(left>right) return;
        int mid = left + (right-left)/2;
        if(nums[mid]>=lo && nums[mid]<=hi){
            res[mid] = true;
        }else{
            res[mid] = false;
        }
        checkValid2_helper(nums,mid+1,right,Math.max(lo,nums[mid]),hi,res);
        checkValid2_helper(nums,left,mid-1,lo,Math.min(hi,nums[mid]),res);
    }


    public static int searchDescending(int[] nums,int target){
        int lo = 0;
        int hi = nums.length;

        while(lo<hi){
            int mid = lo+ (hi-lo)/2;
            if(target<nums[mid]){
                lo=mid+1;
            }else{
                hi=mid;
            }

        }

        return lo;
    }
    public static int searchDescending2(int[] nums,int target){
        int lo = 0;
        int hi = nums.length;

        while(lo<=hi){
            int mid = lo+ (hi-lo)/2;
            if(target<nums[mid]){
                lo=mid+1;
            }else if(target> nums[mid]){
                hi=mid-1;
            }else{
                return mid;
            }

        }

        return lo;
    }
    public static void main(String[] args){
        int[] nums = new int[]{4, 3, 5, 7, 6, 8, 2, 9, 1};
        int f = solution(nums, 9);
        System.out.println(nums[f]);

        System.out.println(Arrays.toString( checkValid(nums) ) );
        System.out.println(Arrays.toString( checkValid2(nums) ) );
        System.out.println(searchDescending(new int[]{8,6,4,3,2},5));
    }

}
