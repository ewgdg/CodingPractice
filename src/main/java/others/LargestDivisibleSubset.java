package others;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LargestDivisibleSubset {

    //Given a set of distinct positive integers, find the largest subset such that every pair (Si, Sj) of elements in this subset satisfies:
    //
    //Si % Sj = 0 or Sj % Si = 0.
    //
    //If there are multiple solutions, return any subset is fine.



    public HashMap<Integer, List<Integer>> mem = new HashMap<>();

    public List<Integer> solution(int[] nums){
        Arrays.sort(nums);
        return dfs(nums,nums.length-1);

    }

    static  int called=0;
    public List<Integer> dfs(int[] nums, int index){
        if(mem.containsKey(nums[index])){
            called++;
            return mem.get(nums[index]);
        }

        int max =1;

        List<Integer> res = new ArrayList<>();
        for(int i=index-1;i>=0;i--){
            if(nums[index]%nums[i]==0 ){
                List<Integer> ret = dfs(nums,i);
                if(  ret.size()+1 > max ){
                    max=ret.size()+1;
                    res = new ArrayList<>(ret);
                }
            }
        }

        res.add(nums[index]);
        mem.put(nums[index],res);
        return res;


    }


    public List<Integer> DPsolution(int[] nums) {
        Arrays.sort(nums);

        int[] dp = new int[nums.length];
        Arrays.fill(dp,1);

        for(int i =0;i<nums.length;i++){
            for(int j =i-1;j>=0;j--){
                if(nums[i]%nums[j]==0){
                    dp[i] = Math.max(dp[j]+1,dp[i]);
                }
            }
        }

        int maxL = 0;
        int maxIndex =0;
        for(int i =0;i<dp.length;i++){
            if(dp[i]>maxL){
                maxIndex =i;
                maxL=dp[i];
            }
        }

        List<Integer> res =new ArrayList<>();

        if(nums.length==0) return res;

        int prevNum=nums[maxIndex];

        for(int i=maxIndex;i>=0;i--){
            if(prevNum%nums[i]==0 && dp[i]==maxL){
                res.add(nums[i]) ;
                prevNum=nums[i];
                maxL--;
            }
        }

        return res;



    }


    public static void main(String[] args){
        LargestDivisibleSubset solver = new LargestDivisibleSubset();
        System.out.println(solver.solution(new int[]{1,2,4,8,5,3,240,10}));
        System.out.println(called);
    }
}
