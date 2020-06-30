package others;
public class firstmissingpos {
    public int firstMissingPositive(int[] nums) {

//        //radix sort
//
//
//        int maxVal= 0;
//
//        int num_pos=0;
//        for(int num:nums){
//            if(num>0){
//                maxVal=Math.max(num,maxVal);
//                ++num_pos;
//            }
//        }
//
//        int iter = 0 ;
//        int base = 10;
//        int[] bucket=new int[base]; // or we can use buckets as bucket of link lists and keep appending num, space 0(n)
//        for(int i=0;i<base;++i){
//            bucket[i]=0;
//        }
//
//        int[] sorted = new int [num_pos];
//        {
//            int i=0;
//            for(int num:nums){
//                if(num>0){
//                    sorted[i]=num;
//                    i++;
//                }
//            }
//        }
//
//
//
//        int radix=1; //Math.pow(base,iter)
//        while(radix<=maxVal){
//            //clear bucket count every iter
//            for(int i=0;i<base;++i){
//                bucket[i]=0;
//            }
//
//            int[] templist=new int [sorted.length];
//
//            //list to bucket
//            for(int num:sorted){
//
//                int digit=(num/radix)%base;
//                bucket[digit]+=1;
//
//            }
//            //aux
//            for(int i=1;i<base;i++){
//                bucket[i]+=bucket[i-1];
//            }
//
//            //bucket to list
//            // iterate the unsorted array from backwards (this is important to ensure the stableness)
//            for(int i=sorted.length-1;i>=0;--i){
//                int digit=(sorted[i]/radix)%base;
//                templist[bucket[digit]-1]=sorted[i];
//                bucket[digit]--;
//            }
//
//            //copy to num array
//            for(int i=0;i<sorted.length;++i){
//                sorted[i]=templist[i];
//
//            }
//
//            radix*=base;
//            //delete temp bucket
//
//        }
//
//        if(num_pos==0||sorted[0]>1){
//            return 1;
//        }
//
//        //find a gap
//        for(int i=1;i<sorted.length;i++){
//            if(sorted[i]-1>sorted[i-1]){
//                return sorted[i-1]+1;
//            }
//
//        }
//        return sorted[sorted.length-1]+1;

        //the largest possible missing is n,, so we only need to consider num <=n
        if (nums == null || nums.length == 0){
            return 1;
        }
        int n = nums.length;
        for(int i = 0; i < n; ++ i) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int temp = nums[i];
                nums[i] = nums[nums[i] - 1];
                nums[temp - 1] = temp;
            }
        }
        for(int i = 0; i < n; ++ i){
            if(nums[i] != i + 1){
                return i + 1;
            }
        }
        return n + 1;

    }
}

