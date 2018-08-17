import java.util.*;

public class test {
    public static int findKthLargest(int[] nums, int k) {
        int left = 0;
        int right = nums.length-1;
        while(true){
            int pos = partition(nums,left,right);
            if(pos==k-1) return nums[pos];
            if(pos>k-1){
                right=pos-1;
            }
            else{
                left=pos+1;
            }
        }
    }

    public static int partition(int[] nums, int lo, int hi){
        int pivot = nums[lo];
        int i = lo+1;

        for (int j = i; j <= hi ; j++) {
            if (nums[j] >= pivot) {
                //swap
                nums[j]  = nums[i] + (nums[i]=nums[j])-nums[j];
                i++;

            }
        }
        //swap pivot
        nums[lo] = nums[i-1] + (nums[i-1]=nums[lo]) - nums[lo];
        return i-1;

    }


    public static void main(String[] args)
    {



//        System.out.println(new add2number2().addTwoNumbers(a,b));
    }
}
