package Array;

import java.util.Arrays;
import java.util.HashMap;
// import java.util.Stack;

public class CountofSmallerNumbersAfterSelf {
    class BIT{
        int[] tree;
        int n;
//        int[] vals;
        public BIT(int n){
            this.n=n;
            tree = new int[n+1];
        }

        public void update(int index, int diff){
            index++;
            while(index<=n){
                tree[index]+=diff;
                index += (-index&index);
            }

        }
        public int sum(int index){
            index++;
            int count=0;
            while(index>0){
                count+=tree[index];
                index -=  (-index&index);
            }
            return count;
        }



    }
    public int[] solve(int[] nums){
        int n = nums.length;
        if(n==0) return new int[]{};

        BIT tree =new BIT(n);

        int[] sorted = Arrays.copyOf(nums,nums.length);
        Arrays.sort(sorted);
        HashMap<Integer,Integer> sorted_index_map = new HashMap<>();
        for(int i =0;i<n;i++){
            sorted_index_map.put(sorted[i],i);
        }
        int[] res =new int[n];
        for(int i =n-1;i>=0;i--){
            int sorted_order = sorted_index_map.get(nums[i]);
            res[i]= tree.sum(sorted_order);
            tree.update(sorted_order,1);
        }

        return res;


    }

    public static void main(String[] args){
        CountofSmallerNumbersAfterSelf solver = new CountofSmallerNumbersAfterSelf();
        System.out.println(Arrays.toString( solver.solve(new int[]{5,2,6,1}) ) );
    }

}
