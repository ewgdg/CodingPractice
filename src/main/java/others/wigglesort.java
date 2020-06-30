package others;
import java.util.Random;

public class wigglesort {
    public void wiggleSort(int[] nums) {
        if(nums.length<=1) return;

        int median = quickSelect(nums,0,nums.length-1, (nums.length+1)/2, new Random() );


        //3 way partition
        int lo = 1; //lowerst odd index
        int hi = nums.length%2==0?  nums.length-2: nums.length-1; //highest even index
        int h=hi;
        int l=lo;
        //3 way partition
        for(int i=0; i<nums.length;i++){
            if(  (i<l||i>=lo || (i&1)==0 ) && nums[i] > median ){
                swap(nums,lo,i);
                lo+=2;
                if(i!=lo)
                    i--;//re-exam ith element after swap
            }else if( (i<=hi||i>h||(i&1)==1) && nums[i]<median){
                swap(nums,hi,i);
                if(i!=hi)
                    i--;//re-exam ith element after swap

                hi-=2; //even index
            }

        }





    }

    public int quickSelect(int[] nums, int lo, int hi, int k, Random rand){
        //get kth large element
        if(lo==hi) return nums[lo];

        int random = rand.nextInt(hi-lo+1)+lo;
        swap(nums,random,lo);
        int pivot = nums[lo];
        int p = lo+1;

        for(int i=lo+1; i<=hi;i++){
            if(nums[i]<=pivot){
                swap(nums,p,i);
                p++;
            }
        }

        --p;
        swap(nums,lo,p);
        if(p-lo==k-1){
            return nums[p];
        }else if(p-lo>=k){
            return quickSelect(nums,lo,p-1,k,rand);
        }else{
            return quickSelect(nums,p+1,hi,k-(p-lo+1),rand);
        }


    }



    public void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }
}
