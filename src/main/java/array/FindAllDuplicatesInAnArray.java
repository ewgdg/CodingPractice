package Array;
import java.util.ArrayList;
import java.util.List;

public class FindAllDuplicatesInAnArray {
    public List<Integer> findDuplicates(int[] nums) {
        //note the range of 1<= elem <=n

        int n = nums.length;

        List<Integer> res = new ArrayList<>();
        for(int i=0;i<n;i++){
            int num = nums[i];

            if(i!=num-1 && num!=nums[num-1]){
                int temp = nums[i];
                nums[i]=nums[num-1];
                nums[num-1]=temp;

                i--;//re-exam after swap
            }

            // cannot add res directly, might add one num mult times by keep swapping it

        }
        //use another loop for adding res
        //
        // another method is to negate nums[num] to tell next num is a dup(no swapping), sue negate as boolean
        //// when find a number i, flip the number at position i-1 to negative.
        //    // if the number at position i-1 is already negative, i is the number that occurs twice.
        //
        //    public List<Integer> findDuplicates(int[] nums) {
        //        List<Integer> res = new ArrayList<>();
        //        for (int i = 0; i < nums.length; ++i) {
        //            int index = Math.abs(nums[i])-1;
        //            if (nums[index] < 0)
        //                res.add(Math.abs(index+1));
        //            nums[index] = -nums[index];
        //        }
        //        return res;
        //    }
        for(int i =0;i<n;i++){
            if(i!=nums[i]-1){
                res.add(nums[i]);
            }
        }


        return res;
    }
}
